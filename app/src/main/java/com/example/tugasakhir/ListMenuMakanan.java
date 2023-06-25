package com.example.tugasakhir;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
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
import com.example.tugasakhir.databinding.ActivityListMenuMakananBinding;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListMenuMakanan extends AppCompatActivity {

    ActivityListMenuMakananBinding binding;
    ArrayList<MenuMakanan> arrMenu = new ArrayList<>();
    ArrayList<MenuMakanan> arrMenu2 = new ArrayList<>();
    String idresto = "1";
    DrawerLayout dl;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_menu_makanan);

        binding = ActivityListMenuMakananBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
        if(iget.hasExtra("id_restoran")){
            idresto = iget.getStringExtra("id_restoran");
        }

        binding.buttonTambahMenuMakanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListMenuMakanan.this, TambahMenuMakanan.class);
                i.putExtra("id_restoran", idresto);
                startActivity(i);
            }
        });

        SetRVListMenuMakanan();

        binding.txtSearchMenu.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String namamenu = binding.txtSearchMenu.getText().toString();
                    arrMenu2.clear();
                    for (int i = 0; i < arrMenu.size(); i++) {
                        if(arrMenu.get(i).getNama_menu().toLowerCase().contains(namamenu.toLowerCase())){
                            MenuMakanan mm = new MenuMakanan(arrMenu.get(i).getId_menu(), arrMenu.get(i).getNama_menu(), arrMenu.get(i).getKategori_menu(), arrMenu.get(i).getStatus_menu(), arrMenu.get(i).getDeskripsi_menu(), arrMenu.get(i).getHarga_menu());
                            arrMenu2.add(mm);
                        }
                    }
                    binding.rvMenuMakanan.setHasFixedSize(true);
                    binding.rvMenuMakanan.setLayoutManager(new LinearLayoutManager(ListMenuMakanan.this));
                    ListMenuMakananAdapter adapter = new ListMenuMakananAdapter(arrMenu2, ListMenuMakanan.this, idresto);
                    binding.rvMenuMakanan.setAdapter(adapter);
                    return true;
                }
                else if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_DEL)){
                    arrMenu.clear();
                    SetRVListMenuMakanan();
                }
                return true;
            }
        });

        dl = (DrawerLayout) findViewById(R.id.dl_restoran);
        navigationView = (NavigationView) findViewById(R.id.nav_view_restoran);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.item_edit_profile_restoran){
                    Intent i = new Intent(ListMenuMakanan.this, EditProfileRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_list_pesanan){
                    Intent i = new Intent(ListMenuMakanan.this, ListTransaksiRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_master_fasilitas){
                    Intent i = new Intent(ListMenuMakanan.this, ListFasilitas.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_master_voucher){
                    Intent i = new Intent(ListMenuMakanan.this, ListVoucherRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_pendapatan_restoran){
                    Intent i = new Intent(ListMenuMakanan.this, LaporanPendapatanRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_rating_review_restoran){
                    Intent i = new Intent(ListMenuMakanan.this, LaporanRatingReviewRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_makanan_terlaku){
                    Intent i = new Intent(ListMenuMakanan.this, LaporanMakananTerlaku.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_pelanggan_setia){
                    Intent i = new Intent(ListMenuMakanan.this, LaporanPelangganSetia.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                return true;
            }
        });
    }

    private void SetRVListMenuMakanan(){
        binding.rvMenuMakanan.setHasFixedSize(true);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                getResources().getString(R.string.url),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONObject jsonobject = new JSONObject(response);
                            JSONArray arrtemp = jsonobject.getJSONArray("datamenu");
                            for (int j = 0; j <= arrtemp.length()-1; j++) {
                                JSONObject tempMenu = arrtemp.getJSONObject(j);
                                MenuMakanan menu = new MenuMakanan(
                                        tempMenu.getString("id_menu"),
                                        tempMenu.getString("nama_menu"),
                                        tempMenu.getString("kategori_menu"),
                                        tempMenu.getString("status_menu"),
                                        tempMenu.getString("deskripsi_menu"),
                                        tempMenu.getString("harga_menu"));
                                arrMenu.add(menu);
                            }

                            binding.rvMenuMakanan.setLayoutManager(new LinearLayoutManager(ListMenuMakanan.this));
                            ListMenuMakananAdapter adapter = new ListMenuMakananAdapter(arrMenu, ListMenuMakanan.this, idresto);
                            binding.rvMenuMakanan.setAdapter(adapter);
                            adapter.setOnItemClickCallback(new ListMenuMakananAdapter.OnItemClickCallback() {
                                @Override
                                public void OnEditClicked(MenuMakanan menu) {
                                    Intent i = new Intent(ListMenuMakanan.this, EditMenuMakanan.class);
                                    i.putExtra("id_restoran", idresto);
                                    i.putExtra("menu_makanan", menu);
                                    startActivity(i);
                                }

                                @Override
                                public void OnDeleteClicked(MenuMakanan menu) {
                                    AlertDialog.Builder bulder = new AlertDialog.Builder(ListMenuMakanan.this);
                                    bulder.setMessage("Apakah anda ingin menghapus menu ini?").setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            DeleteMenuMakanan(menu);
                                            Intent in = new Intent(ListMenuMakanan.this, ListMenuMakanan.class);
                                            in.putExtra("id_restoran", idresto);
                                            startActivity(in);
                                        }
                                    }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    });
                                    AlertDialog a = bulder.create();
                                    a.show();
                                }
                            });

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
                params.put("function", "selectAllMenuMakanan");
                params.put("id_restoran", idresto);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ListMenuMakanan.this);
        requestQueue.add(stringRequest);
    }

    private void DeleteMenuMakanan(MenuMakanan mm){
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
                            Toast.makeText(ListMenuMakanan.this, message, Toast.LENGTH_SHORT).show();
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
                params.put("function", "DeleteMenuMakanan");
                params.put("id_menu", mm.getId_menu());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ListMenuMakanan.this);
        requestQueue.add(stringRequest);
    }
}