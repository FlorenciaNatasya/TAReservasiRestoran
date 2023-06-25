package com.example.tugasakhir;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.ActivityDetailTransaksiBelumDiprosesBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailTransaksiBelumDiproses extends AppCompatActivity {

    ActivityDetailTransaksiBelumDiprosesBinding binding;
    ArrayList<DetailTransaksi> arrTrans = new ArrayList<>();
    ArrayList<FasilitasYangDipinjamClass> arrFaspin = new ArrayList<>();
    HeaderTransaksi ht;
    String idresto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi_belum_diproses);

        binding = ActivityDetailTransaksiBelumDiprosesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
        if(iget.hasExtra("transaksi")){
            ht = iget.getParcelableExtra("transaksi");
        }
        if(iget.hasExtra("id_restoran")){
            idresto = iget.getStringExtra("id_restoran");
        }

        binding.textViewDetailNamaClient.setText(ht.getNama_client());
        binding.textViewDetailNoTelp.setText(ht.getNomor_telepon_client());
        binding.textViewDetailTanggalReservasi.setText(ht.getTanggal_reservasi());
        binding.textViewDetailJamReservasi.setText(ht.getJam_reservasi());
        binding.textViewDetailJumlahOrang.setText(ht.getJumlah_orang() + " orang");
        binding.textViewDetailNoteTransaksi.setText(ht.getNote_transaksi());
        binding.textViewDetailFasilitasYangDipinjam.setText("-");

        binding.rvDetailTransaksi.setHasFixedSize(true);
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
                            for (int j = 0; j <= arrtemp.length() - 1; j++) {
                                JSONObject tempMenu = arrtemp.getJSONObject(j);
                                DetailTransaksi dt = new DetailTransaksi(
                                        tempMenu.getString("nama_makanan"),
                                        tempMenu.getString("jumlah_makanan"),
                                        tempMenu.getString("harga_menu")
                                );
                                arrTrans.add(dt);
                            }

                            if(arrTrans != null){
                                binding.rvDetailTransaksi.setLayoutManager(new LinearLayoutManager(DetailTransaksiBelumDiproses.this));
                                ListMakananYangDipesanAdapter adapter = new ListMakananYangDipesanAdapter(arrTrans);
                                binding.rvDetailTransaksi.setAdapter(adapter);
                            }

                            int tp = 0;
                            for (int i = 0; i < arrTrans.size(); i++) {
                                tp += (Integer.parseInt(arrTrans.get(i).getHarga_makanan()) * Integer.parseInt(arrTrans.get(i).getJumlah_makanan()));
                            }
                            binding.textViewDetailTotalPesanan.setText(String.valueOf(tp));
                            int admin = tp * 25 / 100;
                            binding.textViewDetailAdmin.setText(String.valueOf(admin));
                            int total = tp + admin;
                            binding.textViewDetailTotalTransaksi.setText(String.valueOf(total));

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
                params.put("function", "selectDTransaksi");
                params.put("id_htrans", ht.getId_htrans_reservasi());
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DetailTransaksiBelumDiproses.this);
        requestQueue.add(stringRequest);

        setFasilitasYangDipinjam();

        binding.buttonProsesTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MemprosesPesanan();
            }
        });
    }

    private void setFasilitasYangDipinjam(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://10.0.2.2/TA-service/master.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONObject jsonobject = new JSONObject(response);
                            JSONArray arrtemp = jsonobject.getJSONArray("dataFasPin");
                            for (int j = 0; j <= arrtemp.length() - 1; j++) {
                                JSONObject tempMenu = arrtemp.getJSONObject(j);
                                FasilitasYangDipinjamClass faspin = new FasilitasYangDipinjamClass(
                                        tempMenu.getString("id_htrans_reservasi"),
                                        tempMenu.getString("nama_fasilitas"),
                                        tempMenu.getString("jumlah_faspin"),
                                        tempMenu.getString("id_fasilitas")
                                );
                                arrFaspin.add(faspin);
                            }

                            String fas = "";
                            boolean adafas = false;
                            for (int i = 0; i < arrFaspin.size(); i++) {
                                if(arrFaspin.get(i).getId_htrans().equalsIgnoreCase(ht.getId_htrans_reservasi())){
                                    adafas = true;
                                    fas += arrFaspin.get(i).getNamfas();
                                }
                            }

                            if(adafas == false){
                                binding.textViewDetailFasilitasYangDipinjam.setText("-");
                            }
                            else{
                                binding.textViewDetailFasilitasYangDipinjam.setText(fas);
                            }

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
                params.put("function", "selectFasilitasYangDipinjam");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DetailTransaksiBelumDiproses.this);
        requestQueue.add(stringRequest);
    }

    private void MemprosesPesanan(){
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
                            Toast.makeText(DetailTransaksiBelumDiproses.this, message, Toast.LENGTH_SHORT).show();
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
                params.put("function", "MemprosesPesanan");
                params.put("id_htrans", ht.getId_htrans_reservasi());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(DetailTransaksiBelumDiproses.this);
        requestQueue.add(stringRequest);
    }
}