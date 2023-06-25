package com.example.tugasakhir;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.ActivityEditProfileRestoranBinding;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfileRestoran extends AppCompatActivity {

    ActivityEditProfileRestoranBinding binding;
    String idresto;
    DrawerLayout dl;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_restoran);

        binding = ActivityEditProfileRestoranBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
        if(iget.hasExtra("id_restoran")){
            idresto = iget.getStringExtra("id_restoran");
        }

        binding.buttonUbahIdentitasRestoran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditProfileRestoran.this, EditIdentitasRestoran.class);
                i.putExtra("id_restoran", idresto);
                startActivity(i);
            }
        });

        binding.buttonUbahProfileRestoran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditProfileRestoran.this, EditProfileDanJadwalBukaRestoran.class);
                i.putExtra("id_restoran", idresto);
                startActivity(i);
            }
        });

        binding.buttonHapusAccountRestoran.setOnClickListener(new View.OnClickListener() {
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
                                    Toast.makeText(EditProfileRestoran.this, message, Toast.LENGTH_SHORT).show();
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
                        params.put("function", "HapusAccountRestoran");
                        params.put("id_restoran", idresto);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(EditProfileRestoran.this);
                requestQueue.add(stringRequest);

                Intent it = new Intent(EditProfileRestoran.this, MainActivity.class);
                startActivity(it);
            }
        });

        binding.buttonLogoutRestoran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditProfileRestoran.this, MainActivity.class);
                startActivity(i);
            }
        });

        dl = (DrawerLayout) findViewById(R.id.dl_restoran);
        navigationView = (NavigationView) findViewById(R.id.nav_view_restoran);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.item_list_pesanan){
                    Intent i = new Intent(EditProfileRestoran.this, ListTransaksiRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_master_menu){
                    Intent i = new Intent(EditProfileRestoran.this, ListMenuMakanan.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_master_fasilitas){
                    Intent i = new Intent(EditProfileRestoran.this, ListFasilitas.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_master_voucher){
                    Intent i = new Intent(EditProfileRestoran.this, ListVoucherRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_pendapatan_restoran){
                    Intent i = new Intent(EditProfileRestoran.this, LaporanPendapatanRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_rating_review_restoran){
                    Intent i = new Intent(EditProfileRestoran.this, LaporanRatingReviewRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_makanan_terlaku){
                    Intent i = new Intent(EditProfileRestoran.this, LaporanMakananTerlaku.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_pelanggan_setia){
                    Intent i = new Intent(EditProfileRestoran.this, LaporanPelangganSetia.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                return true;
            }
        });
    }
}