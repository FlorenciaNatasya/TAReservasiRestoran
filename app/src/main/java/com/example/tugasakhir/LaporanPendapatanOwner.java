package com.example.tugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.tugasakhir.databinding.ActivityLaporanPendapatanOwnerBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class LaporanPendapatanOwner extends AppCompatActivity {

    ActivityLaporanPendapatanOwnerBinding binding;
    DrawerLayout dl;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_pendapatan_owner);

        binding = ActivityLaporanPendapatanOwnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bnvLaporanPendapatanOwner.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.item_laporan_harian:
                        Fragment harian = FragmentLaporanPendapatanHarianOwner.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, harian).commit();
                        break;
                    case R.id.item_laporan_bulanan:
                        Fragment bulanan = FragmentLaporanPendapatanBulananOwner.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, bulanan).commit();
                        break;
                    case R.id.item_laporan_tahunan:
                        Fragment tahunan = FragmentLaporanPendapatanTahunanOwner.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, tahunan).commit();
                        break;
                }
                return true;
            }
        });

        if(savedInstanceState == null){
            binding.bnvLaporanPendapatanOwner.setSelectedItemId(R.id.item_laporan_harian);
        }

        dl = (DrawerLayout) findViewById(R.id.dl_admin);
        navigationView = (NavigationView) findViewById(R.id.nav_view_admin);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.item_konfirmasi_registrasi_restoran){
                    Intent i = new Intent(LaporanPendapatanOwner.this, ListRegistrasiRestoran.class);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_rating_semua_resto){
                    Intent i = new Intent(LaporanPendapatanOwner.this, LaporanRatingReviewSemuaRestoran.class);
                    startActivity(i);
                }
                return true;
            }
        });
    }
}