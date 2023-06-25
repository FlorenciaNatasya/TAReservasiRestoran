package com.example.tugasakhir;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.ActivityListRestoranYangBelumDikonfirmasiBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListRestoranYangBelumDikonfirmasi extends AppCompatActivity {

    ActivityListRestoranYangBelumDikonfirmasiBinding binding;
    ArrayList<Restoran> arrResto = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_restoran_yang_belum_dikonfirmasi);

        binding = ActivityListRestoranYangBelumDikonfirmasiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SetRVListRestoranYangBelumDikonfirmasi();
    }

    private void SetRVListRestoranYangBelumDikonfirmasi(){
        binding.rvRestoYangBelumDikonfirmasi.setHasFixedSize(true);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                getResources().getString(R.string.url),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONObject jsonobject = new JSONObject(response);
                            JSONArray arrtemp = jsonobject.getJSONArray("datarestoran");
                            for (int j = 0; j <= arrtemp.length()-1; j++) {
                                JSONObject tempMenu = arrtemp.getJSONObject(j);
                                Restoran resto = new Restoran(
                                        tempMenu.getString("id_restoran"),
                                        tempMenu.getString("nama_restoran"),
                                        tempMenu.getString("alamat_restoran"),
                                        tempMenu.getString("daerah_restoran"));
                                arrResto.add(resto);
                            }
                            if (arrResto != null) {
                                binding.rvRestoYangBelumDikonfirmasi.setLayoutManager(new LinearLayoutManager(ListRestoranYangBelumDikonfirmasi.this));
                                RestoranBelumDikonfirmasiAdapter adapter = new RestoranBelumDikonfirmasiAdapter(arrResto, ListRestoranYangBelumDikonfirmasi.this);
                                adapter.setOnItemClickedCallback(new RestoranBelumDikonfirmasiAdapter.OnItemClickedCallback() {
                                    @Override
                                    public void onItemClicked(Restoran restoran) {
                                        Intent i = new Intent(ListRestoranYangBelumDikonfirmasi.this, KonfirmasiRegistrasiRestoran.class);
                                        i.putExtra("id_restoran", restoran.getId_restoran());
                                        i.putExtra("nama_restoran", restoran.getNama_restoran());
                                        i.putExtra("alamat_restoran", restoran.getAlamat_restoran());
                                        i.putExtra("daerah_restoran", restoran.getDaerah_restoran());
                                        startActivity(i);
                                    }
                                });
                                binding.rvRestoYangBelumDikonfirmasi.setAdapter(adapter);
                            }
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
                params.put("function", "selectDataRestoBelumAktif");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ListRestoranYangBelumDikonfirmasi.this);
        requestQueue.add(stringRequest);
    }
}