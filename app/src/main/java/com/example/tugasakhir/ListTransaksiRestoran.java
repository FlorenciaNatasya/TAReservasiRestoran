package com.example.tugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tugasakhir.databinding.ActivityListTransaksiRestoranBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class ListTransaksiRestoran extends AppCompatActivity {

    ActivityListTransaksiRestoranBinding binding;
    String idresto = "1";
    DrawerLayout dl;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_transaksi_restoran);

        binding = ActivityListTransaksiRestoranBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
        if(iget.hasExtra("id_restoran")){
            idresto = iget.getStringExtra("id_restoran");
        }

        binding.bnvListTransaksi.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.item_belum_diproes_restoran:
                        Fragment belumDiproses = FragmentRestoranBelumDiproses.newInstance(idresto);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, belumDiproses).commit();
                        break;
                    case R.id.item_diproses_restoran:
                        Fragment diproses = FragmentRestoranDiproses.newInstance(idresto);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, diproses).commit();
                        break;
                    case R.id.item_selesai_restoran:
                        Fragment selesai = FragmentRestoranSelesai.newInstance(idresto);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, selesai).commit();
                        break;
                }
                return true;
            }
        });

        if(savedInstanceState == null){
            binding.bnvListTransaksi.setSelectedItemId(R.id.item_belum_diproes_restoran);
        }

        dl = (DrawerLayout) findViewById(R.id.dl_restoran);
        navigationView = (NavigationView) findViewById(R.id.nav_view_restoran);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.item_edit_profile_restoran){
                    Intent i = new Intent(ListTransaksiRestoran.this, EditProfileRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_master_menu){
                    Intent i = new Intent(ListTransaksiRestoran.this, ListMenuMakanan.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_master_fasilitas){
                    Intent i = new Intent(ListTransaksiRestoran.this, ListFasilitas.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_master_voucher){
                    Intent i = new Intent(ListTransaksiRestoran.this, ListVoucherRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_pendapatan_restoran){
                    Intent i = new Intent(ListTransaksiRestoran.this, LaporanPendapatanRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_rating_review_restoran){
                    Intent i = new Intent(ListTransaksiRestoran.this, LaporanRatingReviewRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_makanan_terlaku){
                    Intent i = new Intent(ListTransaksiRestoran.this, LaporanMakananTerlaku.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_pelanggan_setia){
                    Intent i = new Intent(ListTransaksiRestoran.this, LaporanPelangganSetia.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.item_logout:
                Intent in = new Intent(this, MainActivity.class);
                startActivity(in);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}