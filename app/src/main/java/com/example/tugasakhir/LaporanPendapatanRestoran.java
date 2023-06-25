package com.example.tugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.tugasakhir.databinding.ActivityLaporanPendapatanRestoranBinding;
import com.example.tugasakhir.databinding.ActivityListRestoranBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class LaporanPendapatanRestoran extends AppCompatActivity {

    ActivityLaporanPendapatanRestoranBinding binding;
    String idresto = "1";
    DrawerLayout dl;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_pendapatan_restoran);

        binding = ActivityLaporanPendapatanRestoranBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
        if(iget.hasExtra("id_restoran")){
            idresto = iget.getStringExtra("id_restoran");
        }

        binding.bnvLaporanPendapatanRestoran.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.item_laporan_harian:
                        Fragment harian = FragmentLaporanPendapatanHarianRestoran.newInstance(idresto);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, harian).commit();
                        break;
                    case R.id.item_laporan_bulanan:
                        Fragment bulanan = FragmentLaporanPendapatanBulananRestoran.newInstance(idresto);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, bulanan).commit();
                        break;
                    case R.id.item_laporan_tahunan:
                        Fragment tahunan = FragmentLaporanPendapatanTahunanRestoran.newInstance(idresto);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, tahunan).commit();
                        break;
                }
                return true;
            }
        });

        if(savedInstanceState == null){
            binding.bnvLaporanPendapatanRestoran.setSelectedItemId(R.id.item_laporan_harian);
        }

        dl = (DrawerLayout) findViewById(R.id.dl_restoran);
        navigationView = (NavigationView) findViewById(R.id.nav_view_restoran);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.item_edit_profile_restoran){
                    Intent i = new Intent(LaporanPendapatanRestoran.this, EditProfileRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_master_menu){
                    Intent i = new Intent(LaporanPendapatanRestoran.this, ListMenuMakanan.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_master_fasilitas){
                    Intent i = new Intent(LaporanPendapatanRestoran.this, ListFasilitas.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_master_voucher){
                    Intent i = new Intent(LaporanPendapatanRestoran.this, ListVoucherRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_list_pesanan){
                    Intent i = new Intent(LaporanPendapatanRestoran.this, ListTransaksiRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_rating_review_restoran){
                    Intent i = new Intent(LaporanPendapatanRestoran.this, LaporanRatingReviewRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_makanan_terlaku){
                    Intent i = new Intent(LaporanPendapatanRestoran.this, LaporanMakananTerlaku.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_pelanggan_setia){
                    Intent i = new Intent(LaporanPendapatanRestoran.this, LaporanPelangganSetia.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                return true;
            }
        });
    }
}