package com.example.tugasakhir;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.ActivityLaporanRatingReviewRestoranBinding;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaporanRatingReviewRestoran extends AppCompatActivity {

    ActivityLaporanRatingReviewRestoranBinding binding;
    ArrayList<RatingReviewClass> arrRR = new ArrayList<>();
    ArrayList<RatingReviewClass> arrRR2 = new ArrayList<>();
    String idresto = "1";
    DrawerLayout dl;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_rating_review_restoran);

        binding = ActivityLaporanRatingReviewRestoranBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.getSupportActionBar().setTitle("Laporan Rating Review");

        Intent iget = getIntent();
        if(iget.hasExtra("id_restoran")){
            idresto = iget.getStringExtra("id_restoran");
//            Toast.makeText(this, idresto, Toast.LENGTH_SHORT).show();
        }

        setRatingReviewResto();

        dl = (DrawerLayout) findViewById(R.id.dl_restoran);
        navigationView = (NavigationView) findViewById(R.id.nav_view_restoran);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.item_edit_profile_restoran){
                    Intent i = new Intent(LaporanRatingReviewRestoran.this, EditProfileRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_master_menu){
                    Intent i = new Intent(LaporanRatingReviewRestoran.this, ListMenuMakanan.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_master_fasilitas){
                    Intent i = new Intent(LaporanRatingReviewRestoran.this, ListFasilitas.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_master_voucher){
                    Intent i = new Intent(LaporanRatingReviewRestoran.this, ListVoucherRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_pendapatan_restoran){
                    Intent i = new Intent(LaporanRatingReviewRestoran.this, LaporanPendapatanRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_list_pesanan){
                    Intent i = new Intent(LaporanRatingReviewRestoran.this, ListTransaksiRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_makanan_terlaku){
                    Intent i = new Intent(LaporanRatingReviewRestoran.this, LaporanMakananTerlaku.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_pelanggan_setia){
                    Intent i = new Intent(LaporanRatingReviewRestoran.this, LaporanPelangganSetia.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                return true;
            }
        });
    }

    private void setRatingReviewResto(){
        binding.rvListRatingReviewRestoran.setHasFixedSize(true);
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

                            for (int i = 0; i < arrRR.size(); i++) {
                                if(arrRR.get(i).getId_restoran().equalsIgnoreCase(idresto)){
                                    RatingReviewClass rrc = new RatingReviewClass(arrRR.get(i).getJumlah_bintang(), arrRR.get(i).getReview(), arrRR.get(i).getKategori_pilihan(), arrRR.get(i).getId_restoran(), arrRR.get(i).getId_customer());
                                    arrRR2.add(rrc);
                                }
                            }

                            binding.rvListRatingReviewRestoran.setLayoutManager(new LinearLayoutManager(LaporanRatingReviewRestoran.this));
                            RatingReviewAdapter adapter = new RatingReviewAdapter(arrRR2);
                            binding.rvListRatingReviewRestoran.setAdapter(adapter);

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
        RequestQueue requestQueue = Volley.newRequestQueue(LaporanRatingReviewRestoran.this);
        requestQueue.add(stringRequest);
    }
}