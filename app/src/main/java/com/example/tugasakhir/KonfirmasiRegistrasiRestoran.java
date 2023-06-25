package com.example.tugasakhir;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.bumptech.glide.Glide;
import com.example.tugasakhir.databinding.ActivityKonfirmasiRegistrasiRestoranBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class KonfirmasiRegistrasiRestoran extends AppCompatActivity {

    ActivityKonfirmasiRegistrasiRestoranBinding binding;
    String id, nama, alamat, daerah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_registrasi_restoran);

        binding = ActivityKonfirmasiRegistrasiRestoranBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
        if(iget.hasExtra("id_restoran")){
            id = iget.getStringExtra("id_restoran");
        }
        if(iget.hasExtra("nama_restoran")){
            nama = iget.getStringExtra("nama_restoran");
            binding.textViewNamaRestoranYangRequest.setText(nama);
            Glide.with(this).load("http://10.0.2.2/TA-service/imagesResto/"+ nama.replace(" ", "") + "BagianDepan" +".jpg").into(binding.imageViewDepanResto);
            Glide.with(this).load("http://10.0.2.2/TA-service/imagesResto/"+ nama.replace(" ", "") + "BagianDalam" +".jpg").into(binding.imageViewDalamResto);
            Glide.with(this).load("http://10.0.2.2/TA-service/imagesResto/"+ nama.replace(" ", "") + "BagianRuangan" +".jpg").into(binding.imageViewRuangResto);
            Glide.with(this).load("http://10.0.2.2/TA-service/imagesResto/"+ nama.replace(" ", "") + "Sertifikat" +".jpg").into(binding.imageViewSertifResto);
        }
        if(iget.hasExtra("alamat_restoran")){
            alamat = iget.getStringExtra("alamat_restoran");
            binding.textViewAlamatRestoranYangRequest.setText(alamat);
        }
        if(iget.hasExtra("daerah_restoran")){
            daerah = iget.getStringExtra("daerah_restoran");
        }

        binding.buttonTerimaKonfirmasiRegistrasiRestoran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KonfirmasiRegistrasi();
            }
        });
    }

    private void KonfirmasiRegistrasi(){
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
                            Toast.makeText(KonfirmasiRegistrasiRestoran.this, message, Toast.LENGTH_SHORT).show();
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
                params.put("function", "KonfirmasiRegistrasiRestoran");
                params.put("id_restoran", id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(KonfirmasiRegistrasiRestoran.this);
        requestQueue.add(stringRequest);
    }
}