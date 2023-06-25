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
import com.example.tugasakhir.databinding.ActivityEditProfileCustomerBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditProfileCustomer extends AppCompatActivity {

    ActivityEditProfileCustomerBinding binding;
    String idcust = "1";
    ArrayList<Customer> datacust = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_customer);

        binding = ActivityEditProfileCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
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
                            JSONArray arrtemp = jsonobject.getJSONArray("datacust");
                            for (int j = 0; j <= arrtemp.length() - 1; j++) {
                                JSONObject tempMenu = arrtemp.getJSONObject(j);
                                Customer cust = new Customer(
                                        tempMenu.getString("id_customer"),
                                        tempMenu.getString("username_customer"),
                                        tempMenu.getString("password_customer"),
                                        tempMenu.getString("nama_customer"),
                                        tempMenu.getString("email_customer"),
                                        tempMenu.getString("status_customer"));
                                datacust.add(cust);
                            }

                            for (int i = 0; i < datacust.size(); i++) {
                                if(datacust.get(i).getId().equalsIgnoreCase(idcust)){
                                    binding.textEditUsernameCustomer.setText(datacust.get(i).getUsername());
                                    binding.textEditNamaCustomer.setText(datacust.get(i).getNama());
                                    binding.textEditEmailCustomer.setText(datacust.get(i).getEmail());
                                }
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
                params.put("function", "selectAllCust");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(EditProfileCustomer.this);
        requestQueue.add(stringRequest);

        binding.buttonLogoutCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditProfileCustomer.this, MainActivity.class);
                startActivity(i);
            }
        });

        binding.buttonSubmitEditProfileCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.textEditUsernameCustomer.getText().toString();
                String nama = binding.textEditNamaCustomer.getText().toString();
                String email = binding.textEditEmailCustomer.getText().toString();
                String password = binding.textEditPasswordCustomer.getText().toString();
                String confpass = binding.textEditConfirmPasswordCustomer.getText().toString();

                if(password.equalsIgnoreCase("") && confpass.equalsIgnoreCase("")){
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
                                        Toast.makeText(EditProfileCustomer.this, message, Toast.LENGTH_SHORT).show();
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
                            params.put("function", "EditProfileCustomer");
                            params.put("id_customer", idcust);
                            params.put("username", username);
                            params.put("nama", nama);
                            params.put("email", email);
                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(EditProfileCustomer.this);
                    requestQueue.add(stringRequest);
                }
                else{
                    if(password.equals(confpass)){
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
                                            Toast.makeText(EditProfileCustomer.this, message, Toast.LENGTH_SHORT).show();
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
                                params.put("function", "EditProfileCustomer");
                                params.put("id_customer", idcust);
                                params.put("username", username);
                                params.put("nama", nama);
                                params.put("email", email);
                                params.put("password", password);
                                return params;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(EditProfileCustomer.this);
                        requestQueue.add(stringRequest);
                    }
                    else{
                        Toast.makeText(EditProfileCustomer.this, "Password dan konfirmasi password tidak sesuai", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.buttonHapusAccountCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                    Toast.makeText(EditProfileCustomer.this, message, Toast.LENGTH_SHORT).show();
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
                        params.put("function", "DeleteAccountCustomer");
                        params.put("id_customer", idcust);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(EditProfileCustomer.this);
                requestQueue.add(stringRequest);

                Intent it = new Intent(EditProfileCustomer.this, MainActivity.class);
                startActivity(it);
            }
        });
    }
}