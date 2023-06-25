package com.example.tugasakhir;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.ActivityRegisterRestoran2Binding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterRestoran2 extends AppCompatActivity {

    ActivityRegisterRestoran2Binding binding;
    String id_restoran = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_restoran2);

        binding = ActivityRegisterRestoran2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
        if(iget.hasExtra("id_restoran")){
            id_restoran = iget.getStringExtra("id_restoran");
        }
        int id = Integer.parseInt(id_restoran);
        int id2 = id+1;
        id_restoran = String.valueOf(id2);

        binding.checkBoxSetiapHari.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(binding.checkBoxSetiapHari.isChecked() == false){
                    binding.checkBoxSenin.setChecked(false);
                    binding.checkBoxSenin.setEnabled(true);
                    binding.checkBoxSelasa.setChecked(false);
                    binding.checkBoxSelasa.setEnabled(true);
                    binding.checkBoxRabu.setChecked(false);
                    binding.checkBoxRabu.setEnabled(true);
                    binding.checkBoxKamis.setChecked(false);
                    binding.checkBoxKamis.setEnabled(true);
                    binding.checkBoxJumat.setChecked(false);
                    binding.checkBoxJumat.setEnabled(true);
                    binding.checkBoxSabtu.setChecked(false);
                    binding.checkBoxSabtu.setEnabled(true);
                    binding.checkBoxMinggu.setChecked(false);
                    binding.checkBoxMinggu.setEnabled(true);
                }
                else{
                    binding.checkBoxSenin.setChecked(true);
                    binding.checkBoxSenin.setEnabled(false);
                    binding.checkBoxSelasa.setChecked(true);
                    binding.checkBoxSelasa.setEnabled(false);
                    binding.checkBoxRabu.setChecked(true);
                    binding.checkBoxRabu.setEnabled(false);
                    binding.checkBoxKamis.setChecked(true);
                    binding.checkBoxKamis.setEnabled(false);
                    binding.checkBoxJumat.setChecked(true);
                    binding.checkBoxJumat.setEnabled(false);
                    binding.checkBoxSabtu.setChecked(true);
                    binding.checkBoxSabtu.setEnabled(false);
                    binding.checkBoxMinggu.setChecked(true);
                    binding.checkBoxMinggu.setEnabled(false);
                }
            }
        });

        binding.buttonSubmitHariJam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buka = binding.editTextTimeMulai.getText().toString();
                String tutup = binding.editTextTimeTutup.getText().toString();
                if(binding.checkBoxSetiapHari.isChecked()==true){
                    HariJamBukaResto hjbr = new HariJamBukaResto(id_restoran, "Setiap Hari", buka + "-" + tutup);
                    AddHariJamBukaResto(hjbr);
                }
                else{
                    if(binding.checkBoxSenin.isChecked()==true){
                        HariJamBukaResto hjbr = new HariJamBukaResto(id_restoran, "Senin", buka + "-" + tutup);
                        AddHariJamBukaResto(hjbr);
                    }
                    if(binding.checkBoxSelasa.isChecked()==true){
                        HariJamBukaResto hjbr = new HariJamBukaResto(id_restoran, "Selasa", buka + "-" + tutup);
                        AddHariJamBukaResto(hjbr);
                    }
                    if(binding.checkBoxRabu.isChecked()==true){
                        HariJamBukaResto hjbr = new HariJamBukaResto(id_restoran, "Rabu", buka + "-" + tutup);
                        AddHariJamBukaResto(hjbr);
                    }
                    if(binding.checkBoxKamis.isChecked()==true){
                        HariJamBukaResto hjbr = new HariJamBukaResto(id_restoran, "Kamis", buka + "-" + tutup);
                        AddHariJamBukaResto(hjbr);
                    }
                    if(binding.checkBoxJumat.isChecked()==true){
                        HariJamBukaResto hjbr = new HariJamBukaResto(id_restoran, "Jumat", buka + "-" + tutup);
                        AddHariJamBukaResto(hjbr);
                    }
                    if(binding.checkBoxSabtu.isChecked()==true){
                        HariJamBukaResto hjbr = new HariJamBukaResto(id_restoran, "Sabtu", buka + "-" + tutup);
                        AddHariJamBukaResto(hjbr);
                    }
                    if(binding.checkBoxMinggu.isChecked()==true){
                        HariJamBukaResto hjbr = new HariJamBukaResto(id_restoran, "Minggu", buka + "-" + tutup);
                        AddHariJamBukaResto(hjbr);
                    }
                }
            }
        });

    }

    private void AddHariJamBukaResto(HariJamBukaResto hjbr) {
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
                            Toast.makeText(RegisterRestoran2.this, message, Toast.LENGTH_SHORT).show();
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
                params.put("function", "RegisterRestoran2");
                params.put("id_restoran", hjbr.getId_restoran());
                params.put("hari_buka", hjbr.getHari_buka());
                params.put("jam_buka", hjbr.getJam_buka());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(RegisterRestoran2.this);
        requestQueue.add(stringRequest);
    }
}