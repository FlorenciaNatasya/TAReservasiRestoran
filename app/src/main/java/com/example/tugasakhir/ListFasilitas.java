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
import com.example.tugasakhir.databinding.ActivityListFasilitasBinding;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListFasilitas extends AppCompatActivity {

    ActivityListFasilitasBinding binding;
    ArrayList<FasilitasRestoran> arrFas = new ArrayList<>();
    ArrayList<FasilitasRestoran> arrFas2 = new ArrayList<>();
    ArrayList<FasilitasRestoran> arrFas3 = new ArrayList<>();
    String idresto = "1";
    DrawerLayout dl;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_fasilitas);

        binding = ActivityListFasilitasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.getSupportActionBar().hide();

        Intent iget = getIntent();
        if(iget.hasExtra("id_restoran")){
            idresto = iget.getStringExtra("id_restoran");
        }

        setRVFasilitas();

        binding.txtSearchFasilitas.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String fas = binding.txtSearchFasilitas.getText().toString();
                    arrFas3.clear();
                    for (int i = 0; i < arrFas2.size(); i++) {
                        if(arrFas2.get(i).getNama_fasilitas().toLowerCase().contains(fas.toLowerCase())){
                            FasilitasRestoran fr = new FasilitasRestoran(arrFas2.get(i).getId_fasilitas(), arrFas2.get(i).getNama_fasilitas(), arrFas2.get(i).getJumlah_fasilitas(), arrFas2.get(i).getStatus_fasilitas(), arrFas2.get(i).getId_restoran());
                            arrFas3.add(fr);
                        }
                    }
                    binding.rvListFasilitas.setHasFixedSize(true);
                    binding.rvListFasilitas.setLayoutManager(new LinearLayoutManager(ListFasilitas.this));
                    ListFasilitasAdapter adapter = new ListFasilitasAdapter(arrFas3);
                    binding.rvListFasilitas.setAdapter(adapter);
                    adapter.setOnItemClickCallback(new ListFasilitasAdapter.OnItemClickCallback() {
                        @Override
                        public void OnEditClicked(FasilitasRestoran fr) {
                            Intent i = new Intent(ListFasilitas.this, EditFasilitas.class);
                            i.putExtra("id_restoran", idresto);
                            i.putExtra("datafasilitas", fr);
                            startActivity(i);
                        }

                        @Override
                        public void OnDeleteClicked(FasilitasRestoran fr) {
                            AlertDialog.Builder bulder = new AlertDialog.Builder(ListFasilitas.this);
                            bulder.setMessage("Apakah anda ingin menghapus fasilitas ini?").setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DeleteFasilitas(fr);
                                    Intent in = new Intent(ListFasilitas.this, ListFasilitas.class);
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
                    return true;
                }
                else if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_DEL)){
                    arrFas.clear();
                    arrFas2.clear();
                    setRVFasilitas();
                }
                return true;
            }
        });

        binding.buttonTambahFasilitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListFasilitas.this, TambahFasilitas.class);
                i.putExtra("id_restoran", idresto);
                startActivity(i);
            }
        });

        dl = (DrawerLayout) findViewById(R.id.dl_restoran);
        navigationView = (NavigationView) findViewById(R.id.nav_view_restoran);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.item_edit_profile_restoran){
                    Intent i = new Intent(ListFasilitas.this, EditProfileRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_master_menu){
                    Intent i = new Intent(ListFasilitas.this, ListMenuMakanan.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_list_pesanan){
                    Intent i = new Intent(ListFasilitas.this, ListTransaksiRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_master_voucher){
                    Intent i = new Intent(ListFasilitas.this, ListVoucherRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_pendapatan_restoran){
                    Intent i = new Intent(ListFasilitas.this, LaporanPendapatanRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_rating_review_restoran){
                    Intent i = new Intent(ListFasilitas.this, LaporanRatingReviewRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_makanan_terlaku){
                    Intent i = new Intent(ListFasilitas.this, LaporanMakananTerlaku.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_pelanggan_setia){
                    Intent i = new Intent(ListFasilitas.this, LaporanPelangganSetia.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                return true;
            }
        });
    }

    private void setRVFasilitas(){
        binding.rvListFasilitas.setHasFixedSize(true);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                getResources().getString(R.string.url),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONObject jsonobject = new JSONObject(response);
                            JSONArray arrtemp = jsonobject.getJSONArray("datafasilitas");
                            for (int j = 0; j <= arrtemp.length()-1; j++) {
                                JSONObject tempMenu = arrtemp.getJSONObject(j);
                                FasilitasRestoran fas = new FasilitasRestoran(
                                        tempMenu.getString("id_fasilitas"),
                                        tempMenu.getString("nama_fasilitas"),
                                        tempMenu.getString("jumlah_fasilitas"),
                                        tempMenu.getString("status_fasilitas"),
                                        tempMenu.getString("id_restoran"));
                                arrFas.add(fas);
                            }
                            if (arrFas != null) {
                                for (int i = 0; i < arrFas.size(); i++) {
                                    if(arrFas.get(i).getId_restoran().equalsIgnoreCase(idresto) && arrFas.get(i).getStatus_fasilitas().equalsIgnoreCase("Ada")){
                                        FasilitasRestoran fr = new FasilitasRestoran(arrFas.get(i).getId_fasilitas(), arrFas.get(i).getNama_fasilitas(), arrFas.get(i).getJumlah_fasilitas(), arrFas.get(i).getStatus_fasilitas(), arrFas.get(i).getId_restoran());
                                        arrFas2.add(fr);
                                    }
                                }
                                binding.rvListFasilitas.setLayoutManager(new LinearLayoutManager(ListFasilitas.this));
                                ListFasilitasAdapter adapter = new ListFasilitasAdapter(arrFas2);
                                binding.rvListFasilitas.setAdapter(adapter);
                                adapter.setOnItemClickCallback(new ListFasilitasAdapter.OnItemClickCallback() {
                                    @Override
                                    public void OnEditClicked(FasilitasRestoran fr) {
                                        Intent i = new Intent(ListFasilitas.this, EditFasilitas.class);
                                        i.putExtra("id_restoran", idresto);
                                        i.putExtra("datafasilitas", fr);
                                        startActivity(i);
                                    }

                                    @Override
                                    public void OnDeleteClicked(FasilitasRestoran fr) {
                                        AlertDialog.Builder bulder = new AlertDialog.Builder(ListFasilitas.this);
                                        bulder.setMessage("Apakah anda ingin menghapus fasilitas ini?").setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                DeleteFasilitas(fr);
                                                Intent in = new Intent(ListFasilitas.this, ListFasilitas.class);
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
                params.put("function", "selectAllFasilitas");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ListFasilitas.this);
        requestQueue.add(stringRequest);
    }

    private void DeleteFasilitas(FasilitasRestoran fr){
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
                            Toast.makeText(ListFasilitas.this, message, Toast.LENGTH_SHORT).show();
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
                params.put("function", "DeleteFasilitas");
                params.put("id_fasilitas", fr.getId_fasilitas());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ListFasilitas.this);
        requestQueue.add(stringRequest);
    }
}