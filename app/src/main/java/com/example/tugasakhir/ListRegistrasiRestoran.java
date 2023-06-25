package com.example.tugasakhir;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.ActivityListRegistrasiRestoranBinding;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListRegistrasiRestoran extends AppCompatActivity {

    ActivityListRegistrasiRestoranBinding binding;
    ArrayList<Restoran> arrResto = new ArrayList<>();
    ArrayList<Restoran> arrResto1 = new ArrayList<>();
    DrawerLayout dl;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_registrasi_restoran);

        binding = ActivityListRegistrasiRestoranBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.getSupportActionBar().setTitle("List Registrasi Restoran");

        SetRVListRestoran();

        dl = (DrawerLayout) findViewById(R.id.dl_admin);
        navigationView = (NavigationView) findViewById(R.id.nav_view_admin);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.item_laporan_pendapatan_owner){
                    Intent i = new Intent(ListRegistrasiRestoran.this, LaporanPendapatanOwner.class);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_rating_semua_resto){
                    Intent i = new Intent(ListRegistrasiRestoran.this, LaporanRatingReviewSemuaRestoran.class);
                    startActivity(i);
                }
                return true;
            }
        });
    }

    private void SetRVListRestoran(){
        binding.rvListRegistrasiRestoran.setHasFixedSize(true);
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
                                    if(arrResto.get(i).getStatus_restoran().equals("Belum Aktif")){
                                        Restoran r = new Restoran(arrResto.get(i).getId_restoran(), arrResto.get(i).getUsername_restoran(), arrResto.get(i).getPassword_restoran(), arrResto.get(i).getNama_restoran(), arrResto.get(i).getAlamat_restoran(), arrResto.get(i).getDaerah_restoran(), arrResto.get(i).getEmail_restoran(), arrResto.get(i).getStatus_restoran());
                                        arrResto1.add(r);
                                    }
                                }
                                binding.rvListRegistrasiRestoran.setLayoutManager(new LinearLayoutManager(ListRegistrasiRestoran.this));
                                ListRegistrasiRestoranAdapter adapter = new ListRegistrasiRestoranAdapter(arrResto1, ListRegistrasiRestoran.this);
                                binding.rvListRegistrasiRestoran.setAdapter(adapter);
                                adapter.setOnItemClickCallback(new ListRegistrasiRestoranAdapter.OnItemClickCallback() {
                                    @Override
                                    public void OnItemClicked(Restoran r) {
                                        Intent i = new Intent(ListRegistrasiRestoran.this, KonfirmasiRegistrasiRestoran.class);
                                        i.putExtra("id_restoran", r.getId_restoran());
                                        i.putExtra("nama_restoran", r.getNama_restoran());
                                        i.putExtra("alamat_restoran", r.getAlamat_restoran());
                                        i.putExtra("daerah_restoran", r.getDaerah_restoran());
                                        startActivity(i);
                                    }
                                });
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
        RequestQueue requestQueue = Volley.newRequestQueue(ListRegistrasiRestoran.this);
        requestQueue.add(stringRequest);
    }
}