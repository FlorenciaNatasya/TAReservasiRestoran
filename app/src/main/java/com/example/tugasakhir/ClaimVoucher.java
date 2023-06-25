package com.example.tugasakhir;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClaimVoucher extends AppCompatActivity {

    RecyclerView rvClaimVouch;
    ArrayList<VoucherRestoran> arrVouch = new ArrayList<>();
    ArrayList<VoucherRestoran> arrVouch2 = new ArrayList<>();
    String idresto;
    String idcust;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_voucher);

        rvClaimVouch = findViewById(R.id.rvClaimVoucherRestoran);

        Intent iget = getIntent();
        if(iget.hasExtra("id_restoran")){
            idresto = iget.getStringExtra("id_restoran");
        }
        if(iget.hasExtra("id_customer")){
            idcust = iget.getStringExtra("id_customer");
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
                            JSONArray arrtemp = jsonobject.getJSONArray("datavoucher");
                            for (int j = 0; j <= arrtemp.length() - 1; j++) {
                                JSONObject tempMenu = arrtemp.getJSONObject(j);
                                VoucherRestoran vouch = new VoucherRestoran(
                                        tempMenu.getString("id_voucher"),
                                        tempMenu.getString("kode_voucher"),
                                        tempMenu.getString("jenis_voucher"),
                                        tempMenu.getString("jenis_potongan"),
                                        tempMenu.getString("jumlah_diskon"),
                                        tempMenu.getString("kuota_voucher"),
                                        tempMenu.getString("minimum_pembelian"),
                                        tempMenu.getString("maksimal_potongan"),
                                        tempMenu.getString("tanggal_awal"),
                                        tempMenu.getString("tanggal_berakhir"),
                                        tempMenu.getString("status_voucher"),
                                        tempMenu.getString("id_restoran"));
                                arrVouch.add(vouch);
                            }

                            for (int i = 0; i < arrVouch.size(); i++) {
                                if(arrVouch.get(i).getId_restoran().equals(idresto)){
                                    VoucherRestoran v = new VoucherRestoran(arrVouch.get(i).getKode_voucher(), arrVouch.get(i).getJenis_voucher(), arrVouch.get(i).getJenis_potongan(), arrVouch.get(i).getJumlah_diskon(), arrVouch.get(i).getKuota_voucher(), arrVouch.get(i).getMinimum_pembelian(), arrVouch.get(i).getMaksimal_potongan(), arrVouch.get(i).getTanggal_awal(), arrVouch.get(i).getTanggal_akhir(), arrVouch.get(i).getStatus_voucher(), arrVouch.get(i).getId_restoran());
                                    arrVouch2.add(v);
                                }
                            }

                            rvClaimVouch.setHasFixedSize(true);
                            rvClaimVouch.setLayoutManager(new LinearLayoutManager(ClaimVoucher.this));
                            ClaimVoucherAdapter adapter = new ClaimVoucherAdapter(arrVouch2);
                            rvClaimVouch.setAdapter(adapter);
                            adapter.setOnItemClickCallback(new ClaimVoucherAdapter.OnItemClickCallback() {
                                @Override
                                public void OnClaimClicked(VoucherRestoran vr) {
                                    ClaimVoucher(vr.getKode_voucher(), idcust);
                                }
                            });

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
                params.put("function", "selectAllVoucher");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(ClaimVoucher.this);
        requestQueue.add(stringRequest);

    }

    private void ClaimVoucher(String idvouch, String idcust){
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
                            Toast.makeText(ClaimVoucher.this, message, Toast.LENGTH_SHORT).show();
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
                params.put("function", "ClaimVoucher");
                params.put("kode_voucher", idvouch);
                params.put("id_customer", idcust);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ClaimVoucher.this);
        requestQueue.add(stringRequest);
    }
}