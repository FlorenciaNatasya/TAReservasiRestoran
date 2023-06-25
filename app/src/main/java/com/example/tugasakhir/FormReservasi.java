package com.example.tugasakhir;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.ActivityFormReservasiBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class FormReservasi extends AppCompatActivity {

    ActivityFormReservasiBinding binding;
    int year, month, day, hour, minute;
    String idcust;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_reservasi);

        try
        {
//            this.getSupportActionBar().hide();
            this.getSupportActionBar().setTitle("Form Reservasi Restoran");
        }
        catch (NullPointerException e){}

        binding = ActivityFormReservasiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
        if(iget.hasExtra("id_customer")){
            idcust = iget.getStringExtra("id_customer");
        }

        binding.imageViewDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(FormReservasi.this,
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

                TimePickerDialog timePickerDialog = new TimePickerDialog(FormReservasi.this,
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
                String nama = binding.txtNamaClient.getText().toString();
                String notlp = binding.txtNoTelpClient.getText().toString();
                String tanggal = binding.txtTanggalRerservasi.getText().toString();
                String jam = binding.txtJamResrvasi.getText().toString();
                String jumlah = binding.txtJumlahOrang.getText().toString();
                String note = binding.txtNoteTransaksi.getText().toString();

                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String date = dateFormat.format(c.getTime());

                if(nama.equalsIgnoreCase("") || notlp.equalsIgnoreCase("") || tanggal.equalsIgnoreCase("") ||
                jam.equalsIgnoreCase("") || jumlah.equalsIgnoreCase("")) {
                    Toast.makeText(FormReservasi.this, "Ada field yang kosong", Toast.LENGTH_SHORT).show();
                }
                else if(date.substring(3,5).equalsIgnoreCase(tanggal.substring(3,5)) && (Integer.parseInt(date.substring(0,2)) > Integer.parseInt(tanggal.substring(0,2)))){
                    Toast.makeText(FormReservasi.this, "Tanggal reservasi harus minimal hari ini", Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(date.substring(3,5)) > Integer.parseInt(tanggal.substring(3,5))){
                    Toast.makeText(FormReservasi.this, "Tanggal reservasi harus minimal hari ini", Toast.LENGTH_SHORT).show();
                }
                else if(date.equalsIgnoreCase(tanggal) && ((c.get(Calendar.HOUR_OF_DAY)+3) > Integer.parseInt(jam.substring(0,2)))){
                    Toast.makeText(FormReservasi.this, "Jam tidak cocok, minimal 3 jam dari jam saat ini", Toast.LENGTH_SHORT).show();
                }
                else{
                    Reservation reservation = new Reservation(idcust, nama, notlp, tanggal, jam, jumlah, note);
                    AddReservation(reservation);
                    Toast.makeText(FormReservasi.this, "Berhasil Reservasi", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(FormReservasi.this, ListRestoran.class);
                    i.putExtra("datareservasi", reservation);
                    i.putExtra("id_customer", idcust);
                    startActivity(i);
                }
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
                            Toast.makeText(FormReservasi.this, message, Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(FormReservasi.this);
        requestQueue.add(stringRequest);
    }
}