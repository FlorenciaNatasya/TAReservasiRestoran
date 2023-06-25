package com.example.tugasakhir;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.ActivityFormRescheduleBinding;
import com.example.tugasakhir.databinding.ActivityFormReservasiBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FormReschedule extends AppCompatActivity {

    ActivityFormRescheduleBinding binding;
    int year, month, day, hour, minute;
    String idcust;
    PesananSayaClass psc;
    ArrayList<HeaderTransaksi> arrRes = new ArrayList<>();
    ArrayList<HeaderTransaksi> arrRes2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_reschedule);

        try
        {
//            this.getSupportActionBar().hide();
            this.getSupportActionBar().setTitle("Form Reservasi Restoran");
        }
        catch (NullPointerException e){}

        binding = ActivityFormRescheduleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
        if(iget.hasExtra("id_customer")){
            idcust = iget.getStringExtra("id_customer");
        }
        if(iget.hasExtra("datapesanan")){
            psc = iget.getParcelableExtra("datapesanan");
        }

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://10.0.2.2/TA-service/master.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONObject jsonobject = new JSONObject(response);
                            JSONArray arrtemp = jsonobject.getJSONArray("datatransaksi");
                            for (int i = 0; i <= arrtemp.length()-1; i++) {
                                JSONObject tempMenu = arrtemp.getJSONObject(i);
                                HeaderTransaksi ht = new HeaderTransaksi(
                                        tempMenu.getString("id_htrans_reservasi"),
                                        tempMenu.getString("id_customer"),
                                        tempMenu.getString("nama_client"),
                                        tempMenu.getString("nomor_telepon_client"),
                                        tempMenu.getString("tanggal_reservasi"),
                                        tempMenu.getString("jam_reservasi"),
                                        tempMenu.getString("jumlah_orang"),
                                        tempMenu.getString("note_transaksi"),
                                        tempMenu.getString("total_harga"),
                                        tempMenu.getString("id_restoran"),
                                        tempMenu.getString("status_pesanan"));
                                arrRes.add(ht);
                            }

                            for (int i = 0; i < arrRes.size(); i++) {
                                if(arrRes.get(i).getId_htrans_reservasi().equalsIgnoreCase(psc.getId_htrans())){
                                    HeaderTransaksi ht = new HeaderTransaksi(arrRes.get(i).getId_htrans_reservasi(), arrRes.get(i).getId_customer(), arrRes.get(i).getNama_client(), arrRes.get(i).getNomor_telepon_client(), arrRes.get(i).getTanggal_reservasi(), arrRes.get(i).getJam_reservasi(), arrRes.get(i).getJumlah_orang(), arrRes.get(i).getNote_transaksi(), arrRes.get(i).getTotal_harga(), arrRes.get(i).getId_restoran(), arrRes.get(i).getStatus_pesanan());
                                    arrRes2.add(ht);
                                }
                            }

                            binding.txtNamaClient.setText(arrRes2.get(0).getNama_client());
                            binding.txtNoTelpClient.setText(arrRes2.get(0).getNomor_telepon_client());
                            binding.txtTanggalRerservasi.setText(arrRes2.get(0).getTanggal_reservasi());
                            binding.txtJamResrvasi.setText(arrRes2.get(0).getJam_reservasi());
                            binding.txtJumlahOrang.setText(arrRes2.get(0).getJumlah_orang());
                            binding.txtNoteTransaksi.setText(arrRes2.get(0).getNote_transaksi());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("function", "selectHTransaksi");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(FormReschedule.this);
        requestQueue.add(stringRequest);

        binding.imageViewDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(FormReschedule.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                if(dayOfMonth < 10 && monthOfYear < 10){
                                    binding.txtTanggalRerservasi.setText("0" + dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year);
                                }
                                else if(dayOfMonth < 10){
                                    binding.txtTanggalRerservasi.setText("0" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                                else if(monthOfYear < 10){
                                    binding.txtTanggalRerservasi.setText(dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year);
                                }
                                else{
                                    binding.txtTanggalRerservasi.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        binding.imageViewTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(FormReschedule.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                if(minute == 0 && hourOfDay > 10){
                                    binding.txtJamResrvasi.setText(hourOfDay + ":" + minute + "0");
                                }
                                else if(minute == 0 && hourOfDay < 10){
                                    binding.txtJamResrvasi.setText( "0" + hourOfDay + ":" + minute + "0");
                                }
                                else if(minute < 10 && hourOfDay > 10){
                                    binding.txtJamResrvasi.setText(hourOfDay + ":0" + minute);
                                }
                                else if(minute < 10 && hourOfDay < 10){
                                    binding.txtJamResrvasi.setText( "0" + hourOfDay + ":0" + minute);
                                }
                                else if(minute > 10 && hourOfDay < 10){
                                    binding.txtJamResrvasi.setText("0" + hourOfDay + ":" + minute);
                                }
                                else{
                                    binding.txtJamResrvasi.setText(hourOfDay + ":" + minute);
                                }
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        binding.buttonReservasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String nama = binding.txtNamaClient.getText().toString();
//                String notlp = binding.txtNoTelpClient.getText().toString();
                String tanggal = binding.txtTanggalRerservasi.getText().toString();
                String jam = binding.txtJamResrvasi.getText().toString();
//                String jumlah = binding.txtJumlahOrang.getText().toString();
                String note = binding.txtNoteTransaksi.getText().toString();
                RescheduleTanggalReservasi(psc.getId_htrans(), tanggal, jam, note);
//
//                Calendar c = Calendar.getInstance();
//                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//                String date = dateFormat.format(c.getTime());
//
//                if(nama.equalsIgnoreCase("") || notlp.equalsIgnoreCase("") || tanggal.equalsIgnoreCase("") ||
//                jam.equalsIgnoreCase("") || jumlah.equalsIgnoreCase("")) {
//                    Toast.makeText(FormReschedule.this, "Ada field yang kosong", Toast.LENGTH_SHORT).show();
//                }
//                else if(date.substring(3,5).equalsIgnoreCase(tanggal.substring(3,5)) && (Integer.parseInt(date.substring(0,2)) > Integer.parseInt(tanggal.substring(0,2)))){
//                    Toast.makeText(FormReschedule.this, "Tanggal reservasi harus minimal hari ini", Toast.LENGTH_SHORT).show();
//                }
//                else if(Integer.parseInt(date.substring(3,5)) > Integer.parseInt(tanggal.substring(3,5))){
//                    Toast.makeText(FormReschedule.this, "Tanggal reservasi harus minimal hari ini", Toast.LENGTH_SHORT).show();
//                }
//                else if(date.equalsIgnoreCase(tanggal) && ((c.get(Calendar.HOUR_OF_DAY)+3) > Integer.parseInt(jam.substring(0,2)))){
//                    Toast.makeText(FormReschedule.this, "Jam tidak cocok, minimal 3 jam dari jam saat ini", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Reservation reservation = new Reservation(idcust, nama, notlp, tanggal, jam, jumlah, note);
//                    AddReservation(reservation);
//                    Toast.makeText(FormReschedule.this, "Berhasil Reservasi", Toast.LENGTH_SHORT).show();
//
//                    Intent i = new Intent(FormReschedule.this, ListRestoran.class);
//                    i.putExtra("datareservasi", reservation);
//                    i.putExtra("id_customer", idcust);
//                    startActivity(i);
//                }

            }
        });
    }

    private void AddReservation(Reservation reservation){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                getResources().getString(R.string.url),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            Toast.makeText(FormReschedule.this, message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("function", "AddReservation");
                params.put("id_customer", idcust);
                params.put("nama_client", reservation.getNama_client());
                params.put("nomor_telp", reservation.getNomor_telepon());
                params.put("tanggal_reservasi", reservation.getTanggal_reservasi());
                params.put("jam_reservasi", reservation.getJam_reservasi());
                params.put("jumlah_orang", reservation.getJumlah_orang());
                params.put("note_transaksi", reservation.getNote_transaksi());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(FormReschedule.this);
        requestQueue.add(stringRequest);
    }

    private void RescheduleTanggalReservasi(String idhtras, String tanggal, String jam, String note){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                getResources().getString(R.string.url),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            Toast.makeText(FormReschedule.this, message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("function", "RescheduleTanggalReservasi");
                params.put("id_htrans", idhtras);
                params.put("tanggal_reservasi", tanggal);
                params.put("jam_reservasi", jam);
                params.put("note_transaksi", note);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(FormReschedule.this);
        requestQueue.add(stringRequest);
    }
}