package com.example.tugasakhir;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.ActivityDashboardCustomerBinding;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DashboardCustomer extends AppCompatActivity {

    ActivityDashboardCustomerBinding binding;
    DrawerLayout dl;
    NavigationView navigationView;
    String idcust = "1";
    ArrayList<VoucherRestoran> arrVouch = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_customer);

        binding = ActivityDashboardCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dl = (DrawerLayout) findViewById(R.id.dl_customer);
        navigationView = (NavigationView) findViewById(R.id.nav_view_customer);

//        if(!dl.isDrawerOpen(Gravity.LEFT)){
//            dl.openDrawer(Gravity.LEFT);
//        }

        Intent iget = getIntent();
        if(iget.hasExtra("id_customer")){
            idcust = iget.getStringExtra("id_customer");
            Toast.makeText(this, idcust, Toast.LENGTH_SHORT).show();
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.item_edit_profile_customer){
                    Intent i = new Intent(DashboardCustomer.this, EditProfileCustomer.class);
                    i.putExtra("id_customer", idcust);
                    startActivity(i);
                }
                else if (id == R.id.item_form_reservasi){
                    Intent i = new Intent(DashboardCustomer.this, FormReservasi.class);
                    i.putExtra("id_customer", idcust);
                    startActivity(i);
                }
                else if(id == R.id.item_list_resto){
                    Intent i = new Intent(DashboardCustomer.this, ListRestoran.class);
                    i.putExtra("id_customer", idcust);
                    startActivity(i);
                }
                else if(id == R.id.item_chat_customer){

                }
                else if(id == R.id.item_pesanan_saya){
                    Intent i = new Intent(DashboardCustomer.this, PesananSaya.class);
                    i.putExtra("id_customer", idcust);
                    startActivity(i);
                }

                return true;
            }
        });

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

                            binding.rvDashboardCustomer.setHasFixedSize(true);
                            binding.rvDashboardCustomer.setLayoutManager(new LinearLayoutManager(DashboardCustomer.this));
                            DashboardCustomerAdapter adapter = new DashboardCustomerAdapter(arrVouch, DashboardCustomer.this);
                            binding.rvDashboardCustomer.setAdapter(adapter);
                            adapter.setOnItemClickCallback(new DashboardCustomerAdapter.OnItemClickCallback() {
                                @Override
                                public void OnItemClicked(VoucherRestoran vr) {

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
        RequestQueue requestQueue = Volley.newRequestQueue(DashboardCustomer.this);
        requestQueue.add(stringRequest);
    }
}