package com.example.tugasakhir;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.ActivityKalenderReservasiRestoranBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class KalenderReservasiRestoran extends AppCompatActivity {

    ActivityKalenderReservasiRestoranBinding binding;
    int year, month, day;
    ArrayList<HeaderTransaksi> arrHtrans = new ArrayList<>();
    ArrayList<HeaderTransaksi> arrHtrans2 = new ArrayList<>();
    ArrayList<HeaderTransaksi> arrHtrans3 = new ArrayList<>();
    ArrayList<String> jmlh = new ArrayList<>();
    String idresto = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalender_reservasi_restoran);

        binding = ActivityKalenderReservasiRestoranBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageViewDatePickerKalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(KalenderReservasiRestoran.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                if(dayOfMonth < 10){
                                    if((monthOfYear + 1) == 1){
                                        binding.textViewTanggalTerpilih.setText("0" + dayOfMonth + "/01/" + year);
                                    }
                                    else if((monthOfYear + 1) == 2){
                                        binding.textViewTanggalTerpilih.setText("0" + dayOfMonth + "/02/" + year);
                                    }
                                    else if((monthOfYear + 1) == 3){
                                        binding.textViewTanggalTerpilih.setText("0" + dayOfMonth + "/03/" + year);
                                    }
                                    else if((monthOfYear + 1) == 4){
                                        binding.textViewTanggalTerpilih.setText("0" + dayOfMonth + "/04/" + year);
                                    }
                                    else if((monthOfYear + 1) == 5){
                                        binding.textViewTanggalTerpilih.setText("0" + dayOfMonth + "/05/" + year);
                                    }
                                    else if((monthOfYear + 1) == 6){
                                        binding.textViewTanggalTerpilih.setText("0" + dayOfMonth + "/06/" + year);
                                    }
                                    else if((monthOfYear + 1) == 7){
                                        binding.textViewTanggalTerpilih.setText("0" + dayOfMonth + "/07/" + year);
                                    }
                                    else if((monthOfYear + 1) == 8){
                                        binding.textViewTanggalTerpilih.setText("0" + dayOfMonth + "/08/" + year);
                                    }
                                    else if((monthOfYear + 1) == 9){
                                        binding.textViewTanggalTerpilih.setText("0" + dayOfMonth + "/09/" + year);
                                    }
                                    else if((monthOfYear + 1) == 10){
                                        binding.textViewTanggalTerpilih.setText("0" + dayOfMonth + "/10/" + year);
                                    }
                                    else if((monthOfYear + 1) == 11){
                                        binding.textViewTanggalTerpilih.setText("0" + dayOfMonth + "/11/" + year);
                                    }
                                    else if((monthOfYear + 1) == 12){
                                        binding.textViewTanggalTerpilih.setText("0" + dayOfMonth + "/12/" + year);
                                    }
                                }
                                else{
                                    if((monthOfYear + 1) == 1){
                                        binding.textViewTanggalTerpilih.setText(dayOfMonth + "/01/" + year);
                                    }
                                    else if((monthOfYear + 1) == 2){
                                        binding.textViewTanggalTerpilih.setText(dayOfMonth + "/02/" + year);
                                    }
                                    else if((monthOfYear + 1) == 3){
                                        binding.textViewTanggalTerpilih.setText(dayOfMonth + "/03/" + year);
                                    }
                                    else if((monthOfYear + 1) == 4){
                                        binding.textViewTanggalTerpilih.setText(dayOfMonth + "/04/" + year);
                                    }
                                    else if((monthOfYear + 1) == 5){
                                        binding.textViewTanggalTerpilih.setText(dayOfMonth + "/05/" + year);
                                    }
                                    else if((monthOfYear + 1) == 6){
                                        binding.textViewTanggalTerpilih.setText(dayOfMonth + "/06/" + year);
                                    }
                                    else if((monthOfYear + 1) == 7){
                                        binding.textViewTanggalTerpilih.setText(dayOfMonth + "/07/" + year);
                                    }
                                    else if((monthOfYear + 1) == 8){
                                        binding.textViewTanggalTerpilih.setText(dayOfMonth + "/08/" + year);
                                    }
                                    else if((monthOfYear + 1) == 9){
                                        binding.textViewTanggalTerpilih.setText(dayOfMonth + "/09/" + year);
                                    }
                                    else if((monthOfYear + 1) == 10){
                                        binding.textViewTanggalTerpilih.setText(dayOfMonth + "/10/" + year);
                                    }
                                    else if((monthOfYear + 1) == 11){
                                        binding.textViewTanggalTerpilih.setText(dayOfMonth + "/11/" + year);
                                    }
                                    else if((monthOfYear + 1) == 12){
                                        binding.textViewTanggalTerpilih.setText(dayOfMonth + "/12/" + year);
                                    }
                                }
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        binding.buttonSetCariTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.rvKalenderReservasirestoran.setHasFixedSize(true);
                binding.rvKalenderReservasirestoran.setLayoutManager(new LinearLayoutManager(KalenderReservasiRestoran.this));
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
                                        arrHtrans.add(ht);
                                    }

                                    for (int i = 0; i < arrHtrans.size(); i++) {
                                        if(arrHtrans.get(i).getId_restoran().equalsIgnoreCase(idresto) && arrHtrans.get(i).getTanggal_reservasi().equalsIgnoreCase(binding.textViewTanggalTerpilih.getText().toString()) && arrHtrans.get(i).getStatus_pesanan().equalsIgnoreCase("Dikonfirmasi")){
                                            HeaderTransaksi ht = new HeaderTransaksi(arrHtrans.get(i).getId_htrans_reservasi(), arrHtrans.get(i).getId_customer(), arrHtrans.get(i).getNama_client(), arrHtrans.get(i).getNomor_telepon_client(), arrHtrans.get(i).getTanggal_reservasi(), arrHtrans.get(i).getJam_reservasi(), arrHtrans.get(i).getJumlah_orang(), arrHtrans.get(i).getNote_transaksi(), arrHtrans.get(i).getTotal_harga(), arrHtrans.get(i).getId_restoran(), arrHtrans.get(i).getStatus_pesanan());
                                            arrHtrans2.add(ht);
                                        }
                                    }

                                    for (int i = 0; i < arrHtrans2.size(); i++) {
                                        if(arrHtrans3.size() <= 0){
                                            HeaderTransaksi ht = new HeaderTransaksi(arrHtrans2.get(i).getId_htrans_reservasi(), arrHtrans2.get(i).getId_customer(), arrHtrans2.get(i).getNama_client(), arrHtrans2.get(i).getNomor_telepon_client(), arrHtrans2.get(i).getTanggal_reservasi(), arrHtrans2.get(i).getJam_reservasi(), arrHtrans2.get(i).getJumlah_orang(), arrHtrans2.get(i).getNote_transaksi(), arrHtrans2.get(i).getTotal_harga(), arrHtrans2.get(i).getId_restoran(), arrHtrans2.get(i).getStatus_pesanan());
                                            arrHtrans3.add(ht);
                                            jmlh.add("1");
                                        }
                                        else{
                                            boolean adaht = false;
                                            int jmlha = 0;
                                            for (int j = 0; j < arrHtrans3.size(); j++) {
                                                if(arrHtrans2.get(i).getTanggal_reservasi().equalsIgnoreCase(arrHtrans2.get(j).getTanggal_reservasi())){
                                                    adaht = true;
                                                    jmlha = Integer.parseInt(jmlh.get(j));
                                                    jmlha++;
                                                    jmlh.set(j, String.valueOf(jmlha));
                                                }
                                            }

                                            if(adaht == false){
                                                HeaderTransaksi ht = new HeaderTransaksi(arrHtrans2.get(i).getId_htrans_reservasi(), arrHtrans2.get(i).getId_customer(), arrHtrans2.get(i).getNama_client(), arrHtrans2.get(i).getNomor_telepon_client(), arrHtrans2.get(i).getTanggal_reservasi(), arrHtrans2.get(i).getJam_reservasi(), arrHtrans2.get(i).getJumlah_orang(), arrHtrans2.get(i).getNote_transaksi(), arrHtrans2.get(i).getTotal_harga(), arrHtrans2.get(i).getId_restoran(), arrHtrans2.get(i).getStatus_pesanan());
                                                arrHtrans3.add(ht);
                                                jmlh.add("1");
                                            }
                                        }
                                    }

                                    Toast.makeText(KalenderReservasiRestoran.this, String.valueOf(arrHtrans3.size()), Toast.LENGTH_SHORT).show();

                                    KalenderReservasiAdapter adapter = new KalenderReservasiAdapter(arrHtrans3, jmlh);
                                    binding.rvKalenderReservasirestoran.setAdapter(adapter);

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
                RequestQueue requestQueue = Volley.newRequestQueue(KalenderReservasiRestoran.this);
                requestQueue.add(stringRequest);
            }
        });
    }
}