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
import com.example.tugasakhir.databinding.ActivityListVoucherRestoranBinding;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListVoucherRestoran extends AppCompatActivity {

    ActivityListVoucherRestoranBinding binding;
    ArrayList<VoucherRestoran> arrVouch = new ArrayList<>();
    ArrayList<VoucherRestoran> arrVouch2 = new ArrayList<>();
    ArrayList<VoucherRestoran> arrVouchS = new ArrayList<>();
    String idresto = "";
    DrawerLayout dl;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_voucher_restoran);

        binding = ActivityListVoucherRestoranBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
        if(iget.hasExtra("id_restoran")){
            idresto = iget.getStringExtra("id_restoran");
        }

        setRvListVoucher();

        binding.txtSearchVoucher.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)){
                    String namvouch = binding.txtSearchVoucher.getText().toString();
                    arrVouchS.clear();

                    return true;
                }
                else if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_DEL)){

                }
                return true;
            }
        });

        binding.buttonTambahVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListVoucherRestoran.this, TambahVoucherRestoran.class);
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
                    Intent i = new Intent(ListVoucherRestoran.this, EditProfileRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_master_menu){
                    Intent i = new Intent(ListVoucherRestoran.this, ListMenuMakanan.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_master_fasilitas){
                    Intent i = new Intent(ListVoucherRestoran.this, ListFasilitas.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_list_pesanan){
                    Intent i = new Intent(ListVoucherRestoran.this, ListTransaksiRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_pendapatan_restoran){
                    Intent i = new Intent(ListVoucherRestoran.this, LaporanPendapatanRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_rating_review_restoran){
                    Intent i = new Intent(ListVoucherRestoran.this, LaporanRatingReviewRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_makanan_terlaku){
                    Intent i = new Intent(ListVoucherRestoran.this, LaporanMakananTerlaku.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_pelanggan_setia){
                    Intent i = new Intent(ListVoucherRestoran.this, LaporanPelangganSetia.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                return true;
            }
        });
    }

    private void setRvListVoucher(){
        binding.rvListVoucher.setHasFixedSize(true);
        binding.rvListVoucher.setLayoutManager(new LinearLayoutManager(ListVoucherRestoran.this));
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

                            for (int i = 0; i < arrVouch.size(); i++) {
                                if(arrVouch.get(i).getId_restoran().equals(idresto) && arrVouch.get(i).getStatus_voucher().equalsIgnoreCase("Ada")){
                                    VoucherRestoran v = new VoucherRestoran(arrVouch.get(i).getId_voucher(), arrVouch.get(i).getKode_voucher(), arrVouch.get(i).getJenis_voucher(), arrVouch.get(i).getJenis_potongan(), arrVouch.get(i).getJumlah_diskon(), arrVouch.get(i).getKuota_voucher(), arrVouch.get(i).getMinimum_pembelian(), arrVouch.get(i).getMaksimal_potongan(), arrVouch.get(i).getTanggal_awal(), arrVouch.get(i).getTanggal_akhir(), arrVouch.get(i).getStatus_voucher(), arrVouch.get(i).getId_restoran());
                                    arrVouch2.add(v);
                                }
                            }

                            ListVoucherAdapter adapter = new ListVoucherAdapter(arrVouch2);
                            binding.rvListVoucher.setAdapter(adapter);
                            adapter.setOnItemClickCallback(new ListVoucherAdapter.OnItemClickCallback() {
                                @Override
                                public void OnEditClicked(VoucherRestoran vr) {
                                    Intent i = new Intent(ListVoucherRestoran.this, EditVoucherRestoran.class);
                                    i.putExtra("id_restoran", idresto);
                                    i.putExtra("datavoucher", vr);
                                    i.putExtra("tanggal_awal", vr.getTanggal_awal());
                                    i.putExtra("tanggal_akhir", vr.getTanggal_akhir());
                                    startActivity(i);
                                }

                                @Override
                                public void OnDeleteClicked(VoucherRestoran vr) {
                                    AlertDialog.Builder bulder = new AlertDialog.Builder(ListVoucherRestoran.this);
                                    bulder.setMessage("Apakah anda ingin menghapus voucher ini?").setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            DeleteVoucher(vr);
                                            Intent in = new Intent(ListVoucherRestoran.this, ListVoucherRestoran.class);
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
        RequestQueue requestQueue = Volley.newRequestQueue(ListVoucherRestoran.this);
        requestQueue.add(stringRequest);
    }

    private void DeleteVoucher(VoucherRestoran vr){
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
                            Toast.makeText(ListVoucherRestoran.this, message, Toast.LENGTH_SHORT).show();
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
                params.put("function", "DeleteVoucher");
                params.put("id_voucher", vr.getId_voucher());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ListVoucherRestoran.this);
        requestQueue.add(stringRequest);
    }
}