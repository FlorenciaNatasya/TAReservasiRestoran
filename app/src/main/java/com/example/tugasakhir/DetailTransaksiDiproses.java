package com.example.tugasakhir;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.ActivityDetailTransaksiDiprosesBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailTransaksiDiproses extends AppCompatActivity {

    ActivityDetailTransaksiDiprosesBinding binding;
    ArrayList<DetailTransaksi> arrTrans = new ArrayList<>();
    ArrayList<FasilitasYangDipinjamClass> arrFaspin = new ArrayList<>();
    HeaderTransaksi ht;
    String idresto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi_diproses);

        binding = ActivityDetailTransaksiDiprosesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
        if(iget.hasExtra("transaksi")){
            ht = iget.getParcelableExtra("transaksi");
        }
        if(iget.hasExtra("id_restoran")){
            idresto = iget.getStringExtra("id_restoran");
        }

        if(iget.hasExtra("diproses")){
            binding.buttonProsesScanQrCode.setVisibility(View.VISIBLE);
        }
        else if(iget.hasExtra("selesai")){
            binding.buttonProsesScanQrCode.setEnabled(false);
            binding.buttonProsesScanQrCode.setVisibility(View.INVISIBLE);
        }

        binding.textViewDetailNamaClient2.setText(ht.getNama_client());
        binding.textViewDetailNoTelp2.setText(ht.getNomor_telepon_client());
        binding.textViewDetailTanggalReservasi2.setText(ht.getTanggal_reservasi());
        binding.textViewDetailJamReservasi2.setText(ht.getJam_reservasi());
        binding.textViewDetailJumlahOrang2.setText(ht.getJumlah_orang() + " orang");
        binding.textViewDetailNoteTransaksi2.setText(ht.getNote_transaksi());

        setRVPesanan();
        setFasilitasYangDipinjam();

    }

    private void setRVPesanan(){
        binding.rvDetailTransaksi2.setHasFixedSize(true);
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
                                binding.rvDetailTransaksi2.setLayoutManager(new LinearLayoutManager(DetailTransaksiDiproses.this));
                                ListMakananYangDipesanAdapter adapter = new ListMakananYangDipesanAdapter(arrTrans);
                                binding.rvDetailTransaksi2.setAdapter(adapter);
                            }

                            int tp = 0;
                            for (int i = 0; i < arrTrans.size(); i++) {
                                tp += (Integer.parseInt(arrTrans.get(i).getHarga_makanan()) * Integer.parseInt(arrTrans.get(i).getJumlah_makanan()));
                            }
                            binding.textViewDetailTotalPesanan2.setText(String.valueOf(tp));
                            int admin = tp * 25 / 100;
                            binding.textViewDetailAdmin2.setText(String.valueOf(admin));
                            int total = tp + admin;
                            binding.textViewDetailTotalTransaksi2.setText(String.valueOf(total));

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
        RequestQueue requestQueue = Volley.newRequestQueue(DetailTransaksiDiproses.this);
        requestQueue.add(stringRequest);
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
                                binding.textViewDetailFasilitasYangDipinjam2.setText("-");
                            }
                            else{
                                binding.textViewDetailFasilitasYangDipinjam2.setText(fas);
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
        RequestQueue requestQueue = Volley.newRequestQueue(DetailTransaksiDiproses.this);
        requestQueue.add(stringRequest);
    }
}