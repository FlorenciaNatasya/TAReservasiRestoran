package com.example.tugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tugasakhir.databinding.ActivityPesananSayaBinding;
import com.google.android.material.navigation.NavigationBarView;

public class PesananSaya extends AppCompatActivity {

    ActivityPesananSayaBinding binding;
    String idcust = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_saya);

        binding = ActivityPesananSayaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
        if(iget.hasExtra("id_customer")){
            idcust = iget.getStringExtra("id_customer");
        }

        binding.bnvPesananSaya.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.item_belum_diproses:
                        Fragment belumDiproses = FragmentCustomerBelumDiproses.newInstance(idcust);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, belumDiproses).commit();
                        break;
                    case R.id.item_diproses:
                        Fragment diproses = FragmentCustomerDiproses.newInstance(idcust);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, diproses).commit();
                        break;
                    case R.id.item_selesai:
                        Fragment selesai = FragmentCustomerSelesai.newInstance(idcust);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, selesai).commit();
                        break;
                }
                return true;
            }
        });

        if(savedInstanceState == null){
            binding.bnvPesananSaya.setSelectedItemId(R.id.item_belum_diproses);
        }
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