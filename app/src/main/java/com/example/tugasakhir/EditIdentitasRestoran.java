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
import com.example.tugasakhir.databinding.ActivityEditIdentitasRestoranBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditIdentitasRestoran extends AppCompatActivity {

    ActivityEditIdentitasRestoranBinding binding;
    String idresto;
    ArrayList<Restoran> arrResto = new ArrayList<>();
    ArrayList<Restoran> arrResto1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_identitas_restoran);

        binding = ActivityEditIdentitasRestoranBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
        if(iget.hasExtra("id_restoran")){
            idresto = iget.getStringExtra("id_restoran");
        }

        selectDataRestoran();

        binding.buttonSubmitEditProfileRestoran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.textEditUsernameRestoran.getText().toString();
                String nama = binding.textEditNamaRestoran.getText().toString();
                String email = binding.textEditEmailRestoran.getText().toString();
                String password = binding.textEditPasswordRestoran.getText().toString();
                String confpassword = binding.textEditConfirmPasswordRestoran.getText().toString();
                String alamat = binding.textEditAlamatRestoran.getText().toString();
                String daerah = binding.spinnerEditDaerahRestoran.getSelectedItem().toString();

                if(password.equalsIgnoreCase("") && confpassword.equalsIgnoreCase("")){
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
                                        Toast.makeText(EditIdentitasRestoran.this, message, Toast.LENGTH_SHORT).show();
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
                            params.put("function", "EditIdentitasRestoran");
                            params.put("id_restoran", idresto);
                            params.put("username", username);
                            params.put("nama", nama);
                            params.put("email", email);
                            params.put("alamat", alamat);
                            params.put("daerah", daerah);
                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(EditIdentitasRestoran.this);
                    requestQueue.add(stringRequest);
                }
                else{
                    if(password.equals(confpassword)){
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
                                            Toast.makeText(EditIdentitasRestoran.this, message, Toast.LENGTH_SHORT).show();
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
                                params.put("function", "EditIdentitasRestoran");
                                params.put("id_restoran", idresto);
                                params.put("username", username);
                                params.put("nama", nama);
                                params.put("email", email);
                                params.put("password", password);
                                params.put("alamat", alamat);
                                params.put("daerah", daerah);
                                return params;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(EditIdentitasRestoran.this);
                        requestQueue.add(stringRequest);
                    }
                    else{
                        Toast.makeText(EditIdentitasRestoran.this, "Password dan Confirm Password tidak sama", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void selectDataRestoran(){
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
                                        tempMenu.getString("username_restoran"),
                                        tempMenu.getString("password_restoran"),
                                        tempMenu.getString("nama_restoran"),
                                        tempMenu.getString("alamat_restoran"),
                                        tempMenu.getString("daerah_restoran"),
                                        tempMenu.getString("email_restoran"),
                                        tempMenu.getString("status_restoran"));
                                arrResto.add(resto);
                            }
                            if (arrResto != null) {
                                for (int i = 0; i < arrResto.size(); i++) {
                                    if(arrResto.get(i).getId_restoran().equalsIgnoreCase(idresto)){
                                        Restoran r = new Restoran(arrResto.get(i).getId_restoran(), arrResto.get(i).getUsername_restoran(), arrResto.get(i).getPassword_restoran(), arrResto.get(i).getNama_restoran(), arrResto.get(i).getAlamat_restoran(), arrResto.get(i).getDaerah_restoran(), arrResto.get(i).getEmail_restoran(), arrResto.get(i).getStatus_restoran());
                                        arrResto1.add(r);
                                    }
                                }
                            }

                            binding.textEditUsernameRestoran.setText(arrResto1.get(0).getUsername_restoran());
                            binding.textEditNamaRestoran.setText(arrResto1.get(0).getNama_restoran());
                            binding.textEditEmailRestoran.setText(arrResto1.get(0).getEmail_restoran());
                            binding.textEditAlamatRestoran.setText(arrResto1.get(0).getAlamat_restoran());
                            if(arrResto1.get(0).getDaerah_restoran().equalsIgnoreCase("Surabaya Utara")){
                                binding.spinnerEditDaerahRestoran.setSelection(1);
                            }
                            else if(arrResto1.get(0).getDaerah_restoran().equalsIgnoreCase("Surabaya Selatan")){
                                binding.spinnerEditDaerahRestoran.setSelection(2);
                            }
                            else if(arrResto1.get(0).getDaerah_restoran().equalsIgnoreCase("Surabaya Timur")){
                                binding.spinnerEditDaerahRestoran.setSelection(3);
                            }
                            else if(arrResto1.get(0).getDaerah_restoran().equalsIgnoreCase("Surabaya Barat")){
                                binding.spinnerEditDaerahRestoran.setSelection(4);
                            }
                            if(arrResto1.get(0).getDaerah_restoran().equalsIgnoreCase("Surabaya Tengah")){
                                binding.spinnerEditDaerahRestoran.setSelection(5);
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
                params.put("function", "selectAllRestoran");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(EditIdentitasRestoran.this);
        requestQueue.add(stringRequest);
    }
}