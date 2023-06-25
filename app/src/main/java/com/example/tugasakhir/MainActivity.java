package com.example.tugasakhir;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.ActivityListRestoranBinding;
import com.example.tugasakhir.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ArrayList<Customer> arrCust = new ArrayList<>();
    ArrayList<Restoran> arrResto = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getSupportActionBar().hide();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageView14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.txtPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    binding.txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    binding.imageView14.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                }
                else{
                    binding.txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.imageView14.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                }
            }
        });

        binding.buttonToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Register.class);
                startActivity(i);
            }
        });

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.txtUsername.getText().toString();
                String password = binding.txtPassword.getText().toString();

                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST,
                        getResources().getString(R.string.url),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                System.out.println(response);

                                try {
                                    JSONObject jsonobject = new JSONObject(response);
                                    JSONArray arrtemp = jsonobject.getJSONArray("datacust");
                                    for (int j = 0; j <= arrtemp.length()-1; j++) {
                                        JSONObject tempMenu = arrtemp.getJSONObject(j);
                                        Customer cust = new Customer(
                                                tempMenu.getString("id_customer"),
                                                tempMenu.getString("username_customer"),
                                                tempMenu.getString("password_customer"),
                                                tempMenu.getString("nama_customer"),
                                                tempMenu.getString("email_customer"),
                                                tempMenu.getString("status_customer"));
                                        arrCust.add(cust);
                                    }

                                    boolean tdkada = false;
                                    for (int i = 0; i < arrCust.size(); i++) {
                                        if(username.equals(arrCust.get(i).getUsername()) && password.equals(arrCust.get(i).getPassword()) && arrCust.get(i).getStatus().equalsIgnoreCase("Aktif")){
                                            Intent masuk = new Intent(MainActivity.this, DashboardCustomer.class);
                                            masuk.putExtra("id_customer", arrCust.get(i).getId());
                                            startActivity(masuk);
                                        }
                                        else if(username.equals(arrCust.get(i).getUsername()) && !password.equals(arrCust.get(i).getPassword()) && arrCust.get(i).getStatus().equalsIgnoreCase("Aktif")){
                                            Toast.makeText(MainActivity.this, "Password Salah", Toast.LENGTH_SHORT).show();
                                        }
                                        else if(password.equals(arrCust.get(i).getPassword()) && !username.equals(arrCust.get(i).getUsername()) && arrCust.get(i).getStatus().equalsIgnoreCase("Aktif")){
                                            Toast.makeText(MainActivity.this, "Username salah", Toast.LENGTH_SHORT).show();
                                        }
                                        else if(username.equals(arrCust.get(i).getUsername()) && arrCust.get(i).getStatus().equalsIgnoreCase("Tidak Aktif")){
                                            Toast.makeText(MainActivity.this, "Akun telah dihapus", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            tdkada = true;
                                        }
                                    }

                                    if(tdkada == true){
                                        Toast.makeText(MainActivity.this, "Akun tidak ada", Toast.LENGTH_SHORT).show();
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
                        params.put("function", "selectAllCust");
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(stringRequest);

                StringRequest stringRequest2 = new StringRequest(
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

                                    for (int i = 0; i < arrResto.size(); i++) {
                                        if(username.equals(arrResto.get(i).getUsername_restoran()) && password.equals(arrResto.get(i).getPassword_restoran()) && arrResto.get(i).getStatus_restoran().equalsIgnoreCase("Aktif")){
                                            Intent masuk = new Intent(MainActivity.this, ListTransaksiRestoran.class);
                                            masuk.putExtra("id_restoran", arrResto.get(i).getId_restoran());
                                            startActivity(masuk);
                                        }
                                        else if(username.equals(arrResto.get(i).getUsername_restoran()) && !password.equals(arrResto.get(i).getPassword_restoran()) && arrResto.get(i).getStatus_restoran().equalsIgnoreCase("Aktif")){
                                            Toast.makeText(MainActivity.this, "Password Salah", Toast.LENGTH_SHORT).show();
                                        }
                                        else if(password.equals(arrResto.get(i).getPassword_restoran()) && !username.equals(arrResto.get(i).getUsername_restoran()) && arrResto.get(i).getStatus_restoran().equalsIgnoreCase("Aktif")){
                                            Toast.makeText(MainActivity.this, "Username salah", Toast.LENGTH_SHORT).show();
                                        }
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
                RequestQueue requestQueue2 = Volley.newRequestQueue(MainActivity.this);
                requestQueue2.add(stringRequest2);
            }
        });

        binding.buttoncoba2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ListTransaksiRestoran.class);
                startActivity(i);
//                Uri uri = Uri.parse("https://app.sandbox.midtrans.com/payment-links/1677756420976");
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
            }
        });
    }
}