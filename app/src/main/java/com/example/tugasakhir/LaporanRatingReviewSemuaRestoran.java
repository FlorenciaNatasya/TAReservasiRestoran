package com.example.tugasakhir;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.ActivityLaporanRatingReviewSemuaRestoranBinding;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaporanRatingReviewSemuaRestoran extends AppCompatActivity {

    ActivityLaporanRatingReviewSemuaRestoranBinding binding;
    ArrayList<RatingReviewClass> arrRR = new ArrayList<>();
    ArrayList<Restoran> arrResto = new ArrayList<>();
    ArrayList<Restoran> arrResto1 = new ArrayList<>();
    ArrayList<Restoran> arrResto2 = new ArrayList<>();
    DrawerLayout dl;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_rating_review_semua_restoran);

        binding = ActivityLaporanRatingReviewSemuaRestoranBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.getSupportActionBar().setTitle("Laporan Rating Review Restoran");

        binding.rvLaporanRatingSemuaResto.setHasFixedSize(true);
        binding.rvLaporanRatingSemuaResto.setLayoutManager(new LinearLayoutManager(this));

        selectRatingReview();
        SetRVListRestoran();

        binding.textSearchRestoLaporanRR.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String namaygdicari = binding.textSearchRestoLaporanRR.getText().toString();
                    arrResto2.clear();
                    for (int i = 0; i < arrResto1.size(); i++) {
                        if(arrResto1.get(i).getNama_restoran().toLowerCase().contains(namaygdicari.toLowerCase())){
//                            Toast.makeText(ListRestoran.this, "Ada", Toast.LENGTH_SHORT).show();
                            Restoran r = new Restoran(arrResto1.get(i).getId_restoran(), arrResto1.get(i).getUsername_restoran(), arrResto1.get(i).getPassword_restoran(), arrResto1.get(i).getNama_restoran(), arrResto1.get(i).getAlamat_restoran(), arrResto1.get(i).getDaerah_restoran(), arrResto1.get(i).getEmail_restoran(), arrResto1.get(i).getStatus_restoran());
                            arrResto2.add(r);
                        }
                    }

                    ListRatingRestoranAdapter adapter = new ListRatingRestoranAdapter(arrResto2, LaporanRatingReviewSemuaRestoran.this, arrRR);
                    binding.rvLaporanRatingSemuaResto.setAdapter(adapter);

                    return true;
                }
                else if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_DEL)){
                    arrResto.clear();
                    SetRVListRestoran();
                }
                return true;
            }
        });

        dl = (DrawerLayout) findViewById(R.id.dl_admin);
        navigationView = (NavigationView) findViewById(R.id.nav_view_admin);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.item_konfirmasi_registrasi_restoran){
                    Intent i = new Intent(LaporanRatingReviewSemuaRestoran.this, ListRegistrasiRestoran.class);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_pendapatan_owner){
                    Intent i = new Intent(LaporanRatingReviewSemuaRestoran.this, LaporanPendapatanOwner.class);
                    startActivity(i);
                }
                return true;
            }
        });
    }

    private void selectRatingReview(){
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
                                RatingReviewClass rr = new RatingReviewClass(
                                        tempMenu.getString("jumlah_bintang"),
                                        tempMenu.getString("review"),
                                        tempMenu.getString("kategori_terpilih"),
                                        tempMenu.getString("id_restoran"),
                                        tempMenu.getString("id_customer"));
                                arrRR.add(rr);
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
                params.put("function", "selectRatingReview");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LaporanRatingReviewSemuaRestoran.this);
        requestQueue.add(stringRequest);
    }

    private void SetRVListRestoran(){
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

                            for (int i = 0; i < arrResto.size(); i++) {
                                if(arrResto.get(i).getStatus_restoran().equalsIgnoreCase("Aktif")){
                                    Restoran r = new Restoran(arrResto.get(i).getId_restoran(), arrResto.get(i).getUsername_restoran(), arrResto.get(i).getPassword_restoran(), arrResto.get(i).getNama_restoran(), arrResto.get(i).getAlamat_restoran(), arrResto.get(i).getDaerah_restoran(), arrResto.get(i).getEmail_restoran(), arrResto.get(i).getStatus_restoran());
                                    arrResto1.add(r);
                                }
                            }

                            ListRatingRestoranAdapter adapter = new ListRatingRestoranAdapter(arrResto1, LaporanRatingReviewSemuaRestoran.this, arrRR);
                            binding.rvLaporanRatingSemuaResto.setAdapter(adapter);

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
        RequestQueue requestQueue = Volley.newRequestQueue(LaporanRatingReviewSemuaRestoran.this);
        requestQueue.add(stringRequest);
    }
}