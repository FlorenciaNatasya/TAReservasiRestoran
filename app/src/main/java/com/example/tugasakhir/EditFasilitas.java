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
import com.example.tugasakhir.databinding.ActivityEditFasilitasBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditFasilitas extends AppCompatActivity {

    ActivityEditFasilitasBinding binding;
    String idresto = "1";
    FasilitasRestoran fr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_fasilitas);

        binding = ActivityEditFasilitasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
        if(iget.hasExtra("id_restoran")){
            idresto = iget.getStringExtra("id_restoran");
        }
        if(iget.hasExtra("datafasilitas")){
            fr = iget.getParcelableExtra("datafasilitas");
        }

        binding.textEditNamaFasilitas.setText(fr.getNama_fasilitas());
        binding.textEditJumlahFasilitas.setText(fr.getJumlah_fasilitas());
        if(fr.getStatus_fasilitas().equalsIgnoreCase("Ada")){
            binding.radioButtonAdaFasilitas.setChecked(true);
        }
        else{
            binding.radioButtonTidakAdaFasilitas.setChecked(true);
        }

        binding.buttonSubmitEditFasilitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namafasilitas = binding.textEditNamaFasilitas.getText().toString();
                String jumlahfasilitas = binding.textEditJumlahFasilitas.getText().toString();
                int id = binding.radioGroupStatusFasilitas.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(id);
                String status = rb.getText().toString();

                FasilitasRestoran f = new FasilitasRestoran(fr.getId_fasilitas(), namafasilitas, jumlahfasilitas, status, idresto);
                EditFasilitasRestoran(f);
                Intent i = new Intent(EditFasilitas.this, ListFasilitas.class);
                i.putExtra("id_restoran", idresto);
                startActivity(i);
            }
        });
    }

    private void EditFasilitasRestoran(FasilitasRestoran fr){
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
                            Toast.makeText(EditFasilitas.this, message, Toast.LENGTH_SHORT).show();
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
                params.put("function", "EditFasilitas");
                params.put("id_fasilitas", fr.getId_fasilitas());
                params.put("nama_fasilitas", fr.getNama_fasilitas());
                params.put("jumlah_fasilitas", fr.getJumlah_fasilitas());
                params.put("status_fasilitas", fr.getStatus_fasilitas());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(EditFasilitas.this);
        requestQueue.add(stringRequest);
    }
}