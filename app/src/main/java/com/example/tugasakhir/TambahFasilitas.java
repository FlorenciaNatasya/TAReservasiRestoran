package com.example.tugasakhir;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.ActivityTambahFasilitasBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TambahFasilitas extends AppCompatActivity {

    ActivityTambahFasilitasBinding binding;
    String idresto = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_fasilitas);

        this.getSupportActionBar().setTitle("Tambah Fasilitas");

        binding = ActivityTambahFasilitasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
        if(iget.hasExtra("id_restoran")){
            idresto = iget.getStringExtra("id_restoran");
        }

        binding.buttonSubmitTambahFasilitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namafasilitas = binding.textNamaFasilitas.getText().toString();
                String jumlahfasilitas = binding.textJumlahFasilitas.getText().toString();
                int id = binding.radioGroupStatusFasilitas.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(id);
                String status = rb.getText().toString();

                if(namafasilitas.equalsIgnoreCase("") || jumlahfasilitas.equalsIgnoreCase("") || binding.radioGroupStatusFasilitas.getCheckedRadioButtonId() == -1){
                    Toast.makeText(TambahFasilitas.this, "Ada isian yang kosong", Toast.LENGTH_SHORT).show();
                }
                else{
                    FasilitasRestoran f = new FasilitasRestoran(namafasilitas, jumlahfasilitas, status, idresto);
                    AddFasilitasRestoran(f);
                    Intent i = new Intent(TambahFasilitas.this, ListFasilitas.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
            }
        });
    }

    private void AddFasilitasRestoran(FasilitasRestoran fasilitas){
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
                            Toast.makeText(TambahFasilitas.this, message, Toast.LENGTH_SHORT).show();
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
                params.put("function", "AddFasilitas");
                params.put("nama_fasilitas", fasilitas.getNama_fasilitas());
                params.put("jumlah_fasilitas", fasilitas.getJumlah_fasilitas());
                params.put("status_fasilitas", fasilitas.getStatus_fasilitas());
                params.put("id_restoran", fasilitas.getId_restoran());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(TambahFasilitas.this);
        requestQueue.add(stringRequest);
    }
}