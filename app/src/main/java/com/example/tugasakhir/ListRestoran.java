package com.example.tugasakhir;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.ActivityListRestoranBinding;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ListRestoran extends AppCompatActivity {

    ActivityListRestoranBinding binding;
    DrawerLayout dl;
    NavigationView nav_view;
    ArrayList<Restoran> arrResto = new ArrayList<>();
    ArrayList<Restoran> arrResto1 = new ArrayList<>();
    ArrayList<Restoran> arrResto2 = new ArrayList<>();
    ArrayList<Restoran> arrResto3 = new ArrayList<>();
    ArrayList<Restoran> arrResto4 = new ArrayList<>();
    ArrayList<RatingReviewClass> arrRR = new ArrayList<>();
    ArrayList<RatingReviewClass> arrRR2 = new ArrayList<>();
    ArrayList<MenuMakanan> arrMM = new ArrayList<>();
    ArrayList<String> idresto = new ArrayList<>();
    ArrayList<FasilitasRestoran> arrFas = new ArrayList<>();
    ArrayList<FasilitasRestoran> arrFas1 = new ArrayList<>();
    ArrayList<HariJamBukaResto> arrWkt = new ArrayList<>();
    Reservation reservation;
    String idcust;
    ListRestoranAdapter adapter;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_restoran);

        this.getSupportActionBar().hide();

        binding = ActivityListRestoranBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
        if(iget.hasExtra("datareservasi")){
            reservation = iget.getParcelableExtra("datareservasi");
        }
        if(iget.hasExtra("id_customer")){
            idcust = iget.getStringExtra("id_customer");
        }

        nav_view = (NavigationView) findViewById(R.id.nav_view);

        SetRVListRestoran();
        selectRatingReview();
        selectRestoBerdasarkanHarga();
        selectFasilitas();

        binding.txtSearchResto.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String namaygdicari = binding.txtSearchResto.getText().toString();
                    arrResto2.clear();
                    for (int i = 0; i < arrResto1.size(); i++) {
                        if(arrResto1.get(i).getNama_restoran().toLowerCase().contains(namaygdicari.toLowerCase())){
//                            Toast.makeText(ListRestoran.this, "Ada", Toast.LENGTH_SHORT).show();
                            Restoran r = new Restoran(arrResto1.get(i).getId_restoran(), arrResto1.get(i).getUsername_restoran(), arrResto1.get(i).getPassword_restoran(), arrResto1.get(i).getNama_restoran(), arrResto1.get(i).getAlamat_restoran(), arrResto1.get(i).getDaerah_restoran(), arrResto1.get(i).getEmail_restoran(), arrResto1.get(i).getStatus_restoran());
                            arrResto2.add(r);
                        }
                    }
                    binding.rvListRestoran.setHasFixedSize(true);
                    binding.rvListRestoran.setLayoutManager(new LinearLayoutManager(ListRestoran.this));
                    adapter = new ListRestoranAdapter(arrResto2, ListRestoran.this, arrRR);
                    binding.rvListRestoran.setAdapter(adapter);
                    adapter.setOnItemClickCallback(new ListRestoranAdapter.OnItemClickCallback() {
                        @Override
                        public void OnItemClicked(Restoran restoran) {
                            Intent i = new Intent(ListRestoran.this, FormPilihMakanan.class);
                            i.putExtra("id_restoran", restoran.getId_restoran());
                            i.putExtra("datareservasi", reservation);
                            i.putExtra("id_customer", idcust);
                            startActivity(i);
                        }

                        @Override
                        public void OnKalenderClicked(Restoran restoran) {
                            Intent i = new Intent(ListRestoran.this, KalenderReservasiRestoran.class);
                            i.putExtra("id_restoran", restoran.getId_restoran());
                            i.putExtra("datareservasi", reservation);
                            i.putExtra("id_customer", idcust);
                            startActivity(i);
                        }

                        @Override
                        public void OnVoucherClicked(Restoran restoran) {
                            Intent i = new Intent(ListRestoran.this, ClaimVoucher.class);
                            i.putExtra("id_restoran", restoran.getId_restoran());
                            i.putExtra("datareservasi", reservation);
                            i.putExtra("id_customer", idcust);
                            startActivity(i);
                        }

                        @Override
                        public void OnChatClicked(Restoran restoran) {

                        }

                        @Override
                        public void OnProfileClicked(Restoran restoran) {
                            Intent i = new Intent(ListRestoran.this, ProfileRestoran.class);
                            i.putExtra("datarestoran", restoran);
                            i.putExtra("datareservasi", reservation);
                            i.putExtra("id_customer", idcust);
                            i.putExtra("id_restoran", restoran.getId_restoran());
                            startActivity(i);
                        }
                    });
                    return true;
                }
                else if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_DEL)){
                    arrResto.clear();
                    SetRVListRestoran();
                }
                return true;
            }
        });

        dl = (DrawerLayout) findViewById(R.id.drawer_layout);

        binding.buttonFilterResto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!dl.isDrawerOpen(Gravity.RIGHT)){
                    dl.openDrawer(Gravity.RIGHT);
                }
            }
        });

        View header_view = nav_view.getHeaderView(0);
        CheckBox restohalal = (CheckBox) header_view.findViewById(R.id.checkBoxRestoranHalal);
        CheckBox restononhalal = (CheckBox) header_view.findViewById(R.id.checkBoxRestoranNonHalal);
        CheckBox restowestern = (CheckBox) header_view.findViewById(R.id.checkBoxRestoranWestern);
        CheckBox restochinese = (CheckBox) header_view.findViewById(R.id.checkBoxRestoranChinese);
        CheckBox restojapan = (CheckBox) header_view.findViewById(R.id.checkBoxRestoranJapanese);
        CheckBox restokorean = (CheckBox) header_view.findViewById(R.id.checkBoxRestoranKorean);
        CheckBox restoseafood = (CheckBox) header_view.findViewById(R.id.checkBoxRestoranSeafood);
        EditText etHargaMin = (EditText) header_view.findViewById(R.id.etHargaMin);
        EditText etHargaMax = (EditText) header_view.findViewById(R.id.etHargaMax);
        Spinner spinnerDaerah = (Spinner) header_view.findViewById(R.id.spinnerDaerahSurabaya);
        CheckBox sound = (CheckBox) header_view.findViewById(R.id.checkBoxSoundSystem);
        CheckBox proyektor = (CheckBox) header_view.findViewById(R.id.checkBoxProyektor);
        CheckBox privateroom = (CheckBox) header_view.findViewById(R.id.checkBoxPrivateRoom);
        CheckBox band = (CheckBox) header_view.findViewById(R.id.checkBoxBand);
//        Button setReview = (Button) header_view.findViewById(R.id.buttonSetTopReview);
        CheckBox rating5 = (CheckBox) header_view.findViewById(R.id.checkBoxRating5);
        CheckBox rating4 = (CheckBox) header_view.findViewById(R.id.checkBoxRating4);
        CheckBox rating3 = (CheckBox) header_view.findViewById(R.id.checkBoxRating3);
        CheckBox rating2 = (CheckBox) header_view.findViewById(R.id.checkBoxRating2);
        CheckBox rating1 = (CheckBox) header_view.findViewById(R.id.checkBoxRating1);
        Button buttonSubmitFilter = (Button) header_view.findViewById(R.id.buttonSubmitFilterResto);
//        setReview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.rvListRestoran.setHasFixedSize(true);
//                binding.rvListRestoran.setLayoutManager(new LinearLayoutManager(ListRestoran.this));
//                boolean adarat = false;
//                int jmlhcusrat = 0;
//                float rating = 0;
//                int idx = 0;
//                for (int i = 0; i < arrResto1.size(); i++) {
//                    for (int j = 0; j < arrRR.size(); j++) {
//                        if(arrResto1.get(i).getId_restoran().equalsIgnoreCase(arrRR.get(j).getId_restoran())){
//                            rating += Float.parseFloat(arrRR.get(j).getJumlah_bintang());
//                            jmlhcusrat++;
//                            adarat = true;
//                            idx = j;
//                        }
//                    }
//                }
//
//                if(adarat == false){
//                    rating = 0;
//                }
//                else{
//                    float hasil = rating / jmlhcusrat;
//                    String rat = String.format("%.1f", hasil);
//                    RatingReviewClass rrc = new RatingReviewClass(rat, arrRR.get(idx).getId_restoran());
//                    arrRR2.add(rrc);
//                }
//
//                Collections.sort(arrRR2, (rc1, rc2)->{
//                    return Integer.parseInt(rc2.getJumlah_bintang())-Integer.parseInt(rc1.getJumlah_bintang());
//                });
//
//                for (int i = 0; i < arrRR2.size(); i++) {
//                    for (int j = 0; j < arrResto1.size(); j++) {
//                        if(arrRR2.get(i).getId_restoran().equalsIgnoreCase(arrResto1.get(j).getId_restoran())){
//                            Restoran rhalal = new Restoran(arrResto1.get(j).getId_restoran(), arrResto1.get(j).getUsername_restoran(), arrResto1.get(j).getPassword_restoran(), arrResto1.get(j).getNama_restoran(), arrResto1.get(j).getAlamat_restoran(), arrResto1.get(j).getDaerah_restoran(), arrResto1.get(j).getEmail_restoran(), arrResto1.get(j).getStatus_restoran());
//                            arrResto3.add(rhalal);
//                        }
//                    }
//                }
//
//                adapter = new ListRestoranAdapter(arrResto3, ListRestoran.this, arrRR);
//                binding.rvListRestoran.setAdapter(adapter);
//                adapter.setOnItemClickCallback(new ListRestoranAdapter.OnItemClickCallback() {
//                    @Override
//                    public void OnItemClicked(Restoran restoran) {
//                        Intent i = new Intent(ListRestoran.this, FormPilihMakanan.class);
//                        i.putExtra("id_restoran", restoran.getId_restoran());
//                        i.putExtra("datareservasi", reservation);
//                        i.putExtra("id_customer", idcust);
//                        startActivity(i);
//                    }
//
//                    @Override
//                    public void OnKalenderClicked(Restoran restoran) {
//
//                    }
//
//                    @Override
//                    public void OnVoucherClicked(Restoran restoran) {
//                        Intent i = new Intent(ListRestoran.this, ClaimVoucher.class);
//                        i.putExtra("id_restoran", restoran.getId_restoran());
//                        i.putExtra("datareservasi", reservation);
//                        i.putExtra("id_customer", idcust);
//                        startActivity(i);
//                    }
//
//                    @Override
//                    public void OnChatClicked(Restoran restoran) {
//
//                    }
//
//                    @Override
//                    public void OnProfileClicked(Restoran restoran) {
//                        Intent i = new Intent(ListRestoran.this, ProfileRestoran.class);
//                        i.putExtra("datarestoran", restoran);
//                        i.putExtra("datareservasi", reservation);
//                        i.putExtra("id_customer", idcust);
//                        startActivity(i);
//                    }
//                });
//            }
//        });
        buttonSubmitFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrResto3.clear();
                arrResto4.clear();
                binding.rvListRestoran.setHasFixedSize(true);
                binding.rvListRestoran.setLayoutManager(new LinearLayoutManager(ListRestoran.this));
                if(restohalal.isChecked()){
                    for (int j = 0; j < arrResto1.size(); j++) {
                        for (int i = 0; i < arrRR.size(); i++) {
                            if(arrRR.get(i).getKategori_pilihan().equalsIgnoreCase("Restoran Halal") && arrRR.get(i).getId_restoran().equals(arrResto1.get(j).getId_restoran())){
                                Restoran rhalal = new Restoran(arrResto1.get(j).getId_restoran(), arrResto1.get(j).getUsername_restoran(), arrResto1.get(j).getPassword_restoran(), arrResto1.get(j).getNama_restoran(), arrResto1.get(j).getAlamat_restoran(), arrResto1.get(j).getDaerah_restoran(), arrResto1.get(j).getEmail_restoran(), arrResto1.get(j).getStatus_restoran());
                                arrResto3.add(rhalal);
                            }
                        }
                    }
                }
                if(restononhalal.isChecked()){
                    for (int j = 0; j < arrResto1.size(); j++) {
                        for (int i = 0; i < arrRR.size(); i++) {
                            if(arrRR.get(i).getKategori_pilihan().equalsIgnoreCase("Restoran Non Halal") && arrRR.get(i).getId_restoran().equals(arrResto1.get(j).getId_restoran())){
                                Restoran rhalal = new Restoran(arrResto1.get(j).getId_restoran(), arrResto1.get(j).getUsername_restoran(), arrResto1.get(j).getPassword_restoran(), arrResto1.get(j).getNama_restoran(), arrResto1.get(j).getAlamat_restoran(), arrResto1.get(j).getDaerah_restoran(), arrResto1.get(j).getEmail_restoran(), arrResto1.get(j).getStatus_restoran());
                                arrResto3.add(rhalal);
                            }
                        }
                    }
                }
                if(restowestern.isChecked()){
                    for (int j = 0; j < arrResto1.size(); j++) {
                        for (int i = 0; i < arrRR.size(); i++) {
                            if(arrRR.get(i).getKategori_pilihan().equalsIgnoreCase("Restoran Western") && arrRR.get(i).getId_restoran().equals(arrResto1.get(j).getId_restoran())){
                                Restoran rhalal = new Restoran(arrResto1.get(j).getId_restoran(), arrResto1.get(j).getUsername_restoran(), arrResto1.get(j).getPassword_restoran(), arrResto1.get(j).getNama_restoran(), arrResto1.get(j).getAlamat_restoran(), arrResto1.get(j).getDaerah_restoran(), arrResto1.get(j).getEmail_restoran(), arrResto1.get(j).getStatus_restoran());
                                arrResto3.add(rhalal);
                            }
                        }
                    }
                }
                if(restochinese.isChecked()){
                    for (int j = 0; j < arrResto1.size(); j++) {
                        for (int i = 0; i < arrRR.size(); i++) {
                            if(arrRR.get(i).getKategori_pilihan().equalsIgnoreCase("Restoran Chinese") && arrRR.get(i).getId_restoran().equals(arrResto1.get(j).getId_restoran())){
                                Restoran rhalal = new Restoran(arrResto1.get(j).getId_restoran(), arrResto1.get(j).getUsername_restoran(), arrResto1.get(j).getPassword_restoran(), arrResto1.get(j).getNama_restoran(), arrResto1.get(j).getAlamat_restoran(), arrResto1.get(j).getDaerah_restoran(), arrResto1.get(j).getEmail_restoran(), arrResto1.get(j).getStatus_restoran());
                                arrResto3.add(rhalal);
                            }
                        }
                    }
                }
                if(restojapan.isChecked()){
                    for (int j = 0; j < arrResto1.size(); j++) {
                        for (int i = 0; i < arrRR.size(); i++) {
                            if(arrRR.get(i).getKategori_pilihan().equalsIgnoreCase("Restoran Japanese") && arrRR.get(i).getId_restoran().equals(arrResto1.get(j).getId_restoran())){
                                Restoran rhalal = new Restoran(arrResto1.get(j).getId_restoran(), arrResto1.get(j).getUsername_restoran(), arrResto1.get(j).getPassword_restoran(), arrResto1.get(j).getNama_restoran(), arrResto1.get(j).getAlamat_restoran(), arrResto1.get(j).getDaerah_restoran(), arrResto1.get(j).getEmail_restoran(), arrResto1.get(j).getStatus_restoran());
                                arrResto3.add(rhalal);
                            }
                        }
                    }
                }
                if(restokorean.isChecked()){
                    for (int j = 0; j < arrResto1.size(); j++) {
                        for (int i = 0; i < arrRR.size(); i++) {
                            if(arrRR.get(i).getKategori_pilihan().equalsIgnoreCase("Restoran Korean") && arrRR.get(i).getId_restoran().equals(arrResto1.get(j).getId_restoran())){
                                Restoran rhalal = new Restoran(arrResto1.get(j).getId_restoran(), arrResto1.get(j).getUsername_restoran(), arrResto1.get(j).getPassword_restoran(), arrResto1.get(j).getNama_restoran(), arrResto1.get(j).getAlamat_restoran(), arrResto1.get(j).getDaerah_restoran(), arrResto1.get(j).getEmail_restoran(), arrResto1.get(j).getStatus_restoran());
                                arrResto3.add(rhalal);
                            }
                        }
                    }
                }
                if(restoseafood.isChecked()){
                    for (int j = 0; j < arrResto1.size(); j++) {
                        for (int i = 0; i < arrRR.size(); i++) {
                            if(arrRR.get(i).getKategori_pilihan().equalsIgnoreCase("Restoran Seafood") && arrRR.get(i).getId_restoran().equals(arrResto1.get(j).getId_restoran())){
                                Restoran rhalal = new Restoran(arrResto1.get(j).getId_restoran(), arrResto1.get(j).getUsername_restoran(), arrResto1.get(j).getPassword_restoran(), arrResto1.get(j).getNama_restoran(), arrResto1.get(j).getAlamat_restoran(), arrResto1.get(j).getDaerah_restoran(), arrResto1.get(j).getEmail_restoran(), arrResto1.get(j).getStatus_restoran());
                                arrResto3.add(rhalal);
                            }
                        }
                    }
                }
                if(!etHargaMin.getText().toString().equalsIgnoreCase("") && !etHargaMax.getText().toString().equalsIgnoreCase("")){
                    for (int i = 0; i < arrMM.size(); i++) {
                        if((Integer.parseInt(arrMM.get(i).getHarga_menu()) <= Integer.parseInt(etHargaMax.getText().toString())) && (Integer.parseInt(arrMM.get(i).getHarga_menu()) >= Integer.parseInt(etHargaMin.getText().toString()))){
                            idresto.add(arrMM.get(i).getId_restoran());
                        }
                    }

                    for (int i = 0; i < arrResto1.size(); i++) {
                        for (int j = 0; j < idresto.size(); j++) {
                            if(arrResto1.get(i).getId_restoran().equalsIgnoreCase(idresto.get(j))){
                                Restoran rnew = new Restoran(arrResto1.get(i).getId_restoran(), arrResto1.get(i).getUsername_restoran(), arrResto1.get(i).getPassword_restoran(), arrResto1.get(i).getNama_restoran(), arrResto1.get(i).getAlamat_restoran(), arrResto1.get(i).getDaerah_restoran(), arrResto1.get(i).getEmail_restoran(), arrResto1.get(i).getStatus_restoran());
                                arrResto3.add(rnew);
                            }
                        }
                    }
                }
                else if(!etHargaMin.getText().toString().equalsIgnoreCase("") && etHargaMax.getText().toString().equalsIgnoreCase("")){
                    for (int i = 0; i < arrMM.size(); i++) {
                        if((Integer.parseInt(arrMM.get(i).getHarga_menu()) >= Integer.parseInt(etHargaMin.getText().toString()))){
                            idresto.add(arrMM.get(i).getId_restoran());
                        }
                    }

                    for (int i = 0; i < arrResto1.size(); i++) {
                        for (int j = 0; j < idresto.size(); j++) {
                            if(arrResto1.get(i).getId_restoran().equalsIgnoreCase(idresto.get(j))){
                                Restoran rnew = new Restoran(arrResto1.get(i).getId_restoran(), arrResto1.get(i).getUsername_restoran(), arrResto1.get(i).getPassword_restoran(), arrResto1.get(i).getNama_restoran(), arrResto1.get(i).getAlamat_restoran(), arrResto1.get(i).getDaerah_restoran(), arrResto1.get(i).getEmail_restoran(), arrResto1.get(i).getStatus_restoran());
                                arrResto3.add(rnew);
                            }
                        }
                    }
                }
                if(!spinnerDaerah.getSelectedItem().toString().equalsIgnoreCase("")){
                    for (int i = 0; i < arrResto1.size(); i++) {
                        if(arrResto1.get(i).getDaerah_restoran().equalsIgnoreCase(spinnerDaerah.getSelectedItem().toString())){
                            Restoran rnew = new Restoran(arrResto1.get(i).getId_restoran(), arrResto1.get(i).getUsername_restoran(), arrResto1.get(i).getPassword_restoran(), arrResto1.get(i).getNama_restoran(), arrResto1.get(i).getAlamat_restoran(), arrResto1.get(i).getDaerah_restoran(), arrResto1.get(i).getEmail_restoran(), arrResto1.get(i).getStatus_restoran());
                            arrResto3.add(rnew);
                        }
                    }
                }
                if(sound.isChecked()){
                    for (int i = 0; i < arrResto1.size(); i++) {
                        for (int j = 0; j < arrFas1.size(); j++) {
                            if(arrResto1.get(i).getId_restoran().equalsIgnoreCase(arrFas1.get(j).getId_restoran()) && arrFas1.get(j).getNama_fasilitas().equalsIgnoreCase("Sound System")){
                                Restoran rnew = new Restoran(arrResto1.get(i).getId_restoran(), arrResto1.get(i).getUsername_restoran(), arrResto1.get(i).getPassword_restoran(), arrResto1.get(i).getNama_restoran(), arrResto1.get(i).getAlamat_restoran(), arrResto1.get(i).getDaerah_restoran(), arrResto1.get(i).getEmail_restoran(), arrResto1.get(i).getStatus_restoran());
                                arrResto3.add(rnew);
                            }
                        }
                    }
                }
                if(proyektor.isChecked()){
                    for (int i = 0; i < arrResto1.size(); i++) {
                        for (int j = 0; j < arrFas1.size(); j++) {
                            if(arrResto1.get(i).getId_restoran().equalsIgnoreCase(arrFas1.get(j).getId_restoran()) && arrFas1.get(j).getNama_fasilitas().equalsIgnoreCase("Proyektor")){
                                Restoran rnew = new Restoran(arrResto1.get(i).getId_restoran(), arrResto1.get(i).getUsername_restoran(), arrResto1.get(i).getPassword_restoran(), arrResto1.get(i).getNama_restoran(), arrResto1.get(i).getAlamat_restoran(), arrResto1.get(i).getDaerah_restoran(), arrResto1.get(i).getEmail_restoran(), arrResto1.get(i).getStatus_restoran());
                                arrResto3.add(rnew);
                            }
                        }
                    }
                }
                if(privateroom.isChecked()){
                    for (int i = 0; i < arrResto1.size(); i++) {
                        for (int j = 0; j < arrFas1.size(); j++) {
                            if(arrResto1.get(i).getId_restoran().equalsIgnoreCase(arrFas1.get(j).getId_restoran()) && arrFas1.get(j).getNama_fasilitas().equalsIgnoreCase("Private Room")){
                                Restoran rnew = new Restoran(arrResto1.get(i).getId_restoran(), arrResto1.get(i).getUsername_restoran(), arrResto1.get(i).getPassword_restoran(), arrResto1.get(i).getNama_restoran(), arrResto1.get(i).getAlamat_restoran(), arrResto1.get(i).getDaerah_restoran(), arrResto1.get(i).getEmail_restoran(), arrResto1.get(i).getStatus_restoran());
                                arrResto3.add(rnew);
                            }
                        }
                    }
                }
                if(band.isChecked()){
                    for (int i = 0; i < arrResto1.size(); i++) {
                        for (int j = 0; j < arrFas1.size(); j++) {
                            if(arrResto1.get(i).getId_restoran().equalsIgnoreCase(arrFas1.get(j).getId_restoran()) && arrFas1.get(j).getNama_fasilitas().equalsIgnoreCase("Band")){
                                Restoran rnew = new Restoran(arrResto1.get(i).getId_restoran(), arrResto1.get(i).getUsername_restoran(), arrResto1.get(i).getPassword_restoran(), arrResto1.get(i).getNama_restoran(), arrResto1.get(i).getAlamat_restoran(), arrResto1.get(i).getDaerah_restoran(), arrResto1.get(i).getEmail_restoran(), arrResto1.get(i).getStatus_restoran());
                                arrResto3.add(rnew);
                            }
                        }
                    }
                }
                if(rating5.isChecked()){
//                    boolean adarat = false;
//                    int jmlhcusrat = 0;
//                    float rating = 0;
//                    int idx = 0;
//                    for (int i = 0; i < arrResto1.size(); i++) {
//                        adarat = false;
//                        jmlhcusrat = 0;
//                        rating = 0;
//                        idx = 0;
//                        for (int j = 0; j < arrRR.size(); j++) {
//                            if(arrResto1.get(i).getId_restoran().equalsIgnoreCase(arrRR.get(j).getId_restoran())){
//                                rating += Float.parseFloat(arrRR.get(j).getJumlah_bintang());
//                                jmlhcusrat++;
//                                adarat = true;
//                                idx = j;
//                            }
//                        }
//
//                        if(adarat == false){
//                            rating = 0;
//                        }
//                        else{
//                            float hasil = rating / jmlhcusrat;
//                            String rat = String.format("%.1f", hasil);
//                            RatingReviewClass rrc = new RatingReviewClass(rat, arrRR.get(idx).getId_restoran());
//                            arrRR2.add(rrc);
//                        }
//                    }

//                    Toast.makeText(ListRestoran.this, String.valueOf(arrRR2.get(1).getJumlah_bintang()), Toast.LENGTH_SHORT).show();

                    for (int i = 0; i < arrRR2.size(); i++) {
                        for (int j = 0; j < arrResto1.size(); j++) {
                            if(arrRR2.get(i).getId_restoran().equalsIgnoreCase(arrResto1.get(j).getId_restoran()) && arrRR2.get(i).getJumlah_bintang().substring(0,1).equalsIgnoreCase("5")){
                                Restoran rhalal = new Restoran(arrResto1.get(j).getId_restoran(), arrResto1.get(j).getUsername_restoran(), arrResto1.get(j).getPassword_restoran(), arrResto1.get(j).getNama_restoran(), arrResto1.get(j).getAlamat_restoran(), arrResto1.get(j).getDaerah_restoran(), arrResto1.get(j).getEmail_restoran(), arrResto1.get(j).getStatus_restoran());
                                arrResto3.add(rhalal);
                            }
                        }
                    }
                }
                if(rating4.isChecked()){
                    for (int i = 0; i < arrRR2.size(); i++) {
                        for (int j = 0; j < arrResto1.size(); j++) {
                            if(arrRR2.get(i).getId_restoran().equalsIgnoreCase(arrResto1.get(j).getId_restoran()) && arrRR2.get(i).getJumlah_bintang().substring(0,1).equalsIgnoreCase("4")){
                                Restoran rhalal = new Restoran(arrResto1.get(j).getId_restoran(), arrResto1.get(j).getUsername_restoran(), arrResto1.get(j).getPassword_restoran(), arrResto1.get(j).getNama_restoran(), arrResto1.get(j).getAlamat_restoran(), arrResto1.get(j).getDaerah_restoran(), arrResto1.get(j).getEmail_restoran(), arrResto1.get(j).getStatus_restoran());
                                arrResto3.add(rhalal);
                            }
                        }
                    }
                }
                if(rating3.isChecked()){
                    for (int i = 0; i < arrRR2.size(); i++) {
                        for (int j = 0; j < arrResto1.size(); j++) {
                            if(arrRR2.get(i).getId_restoran().equalsIgnoreCase(arrResto1.get(j).getId_restoran()) && arrRR2.get(i).getJumlah_bintang().substring(0,1).equalsIgnoreCase("3")){
                                Restoran rhalal = new Restoran(arrResto1.get(j).getId_restoran(), arrResto1.get(j).getUsername_restoran(), arrResto1.get(j).getPassword_restoran(), arrResto1.get(j).getNama_restoran(), arrResto1.get(j).getAlamat_restoran(), arrResto1.get(j).getDaerah_restoran(), arrResto1.get(j).getEmail_restoran(), arrResto1.get(j).getStatus_restoran());
                                arrResto3.add(rhalal);
                            }
                        }
                    }
                }
                if(rating2.isChecked()){
                    for (int i = 0; i < arrRR2.size(); i++) {
                        for (int j = 0; j < arrResto1.size(); j++) {
                            if(arrRR2.get(i).getId_restoran().equalsIgnoreCase(arrResto1.get(j).getId_restoran()) && arrRR2.get(i).getJumlah_bintang().substring(0,1).equalsIgnoreCase("2")){
                                Restoran rhalal = new Restoran(arrResto1.get(j).getId_restoran(), arrResto1.get(j).getUsername_restoran(), arrResto1.get(j).getPassword_restoran(), arrResto1.get(j).getNama_restoran(), arrResto1.get(j).getAlamat_restoran(), arrResto1.get(j).getDaerah_restoran(), arrResto1.get(j).getEmail_restoran(), arrResto1.get(j).getStatus_restoran());
                                arrResto3.add(rhalal);
                            }
                        }
                    }
                }
                if(rating1.isChecked()){
                    for (int i = 0; i < arrRR2.size(); i++) {
                        for (int j = 0; j < arrResto1.size(); j++) {
                            if(arrRR2.get(i).getId_restoran().equalsIgnoreCase(arrResto1.get(j).getId_restoran()) && arrRR2.get(i).getJumlah_bintang().substring(0,1).equalsIgnoreCase("1")){
                                Restoran rhalal = new Restoran(arrResto1.get(j).getId_restoran(), arrResto1.get(j).getUsername_restoran(), arrResto1.get(j).getPassword_restoran(), arrResto1.get(j).getNama_restoran(), arrResto1.get(j).getAlamat_restoran(), arrResto1.get(j).getDaerah_restoran(), arrResto1.get(j).getEmail_restoran(), arrResto1.get(j).getStatus_restoran());
                                arrResto3.add(rhalal);
                            }
                        }
                    }
                }

                for (int i = 0; i < arrResto3.size(); i++) {
                    if(arrResto4.size() <= 0){
                        Restoran rnew = new Restoran(arrResto3.get(i).getId_restoran(), arrResto3.get(i).getUsername_restoran(), arrResto3.get(i).getPassword_restoran(), arrResto3.get(i).getNama_restoran(), arrResto3.get(i).getAlamat_restoran(), arrResto3.get(i).getDaerah_restoran(), arrResto3.get(i).getEmail_restoran(), arrResto3.get(i).getStatus_restoran());
                        arrResto4.add(rnew);
                    }
                    else{
                        for (int j = 0; j < arrResto4.size(); j++) {
                            if(!arrResto3.get(i).getId_restoran().equalsIgnoreCase(arrResto4.get(j).getId_restoran())){
                                Restoran rnew = new Restoran(arrResto3.get(i).getId_restoran(), arrResto3.get(i).getUsername_restoran(), arrResto3.get(i).getPassword_restoran(), arrResto3.get(i).getNama_restoran(), arrResto3.get(i).getAlamat_restoran(), arrResto3.get(i).getDaerah_restoran(), arrResto3.get(i).getEmail_restoran(), arrResto3.get(i).getStatus_restoran());
                                arrResto4.add(rnew);
                            }
                        }
                    }
                }

                adapter = new ListRestoranAdapter(arrResto4, ListRestoran.this, arrRR);
                binding.rvListRestoran.setAdapter(adapter);
                adapter.setOnItemClickCallback(new ListRestoranAdapter.OnItemClickCallback() {
                    @Override
                    public void OnItemClicked(Restoran restoran) {
                        Intent i = new Intent(ListRestoran.this, FormPilihMakanan.class);
                        i.putExtra("id_restoran", restoran.getId_restoran());
                        i.putExtra("datareservasi", reservation);
                        i.putExtra("id_customer", idcust);
                        startActivity(i);
                    }

                    @Override
                    public void OnKalenderClicked(Restoran restoran) {
                        Intent i = new Intent(ListRestoran.this, KalenderReservasiRestoran.class);
                        i.putExtra("id_restoran", restoran.getId_restoran());
                        i.putExtra("datareservasi", reservation);
                        i.putExtra("id_customer", idcust);
                        startActivity(i);
                    }

                    @Override
                    public void OnVoucherClicked(Restoran restoran) {
                        Intent i = new Intent(ListRestoran.this, ClaimVoucher.class);
                        i.putExtra("id_restoran", restoran.getId_restoran());
                        i.putExtra("datareservasi", reservation);
                        i.putExtra("id_customer", idcust);
                        startActivity(i);
                    }

                    @Override
                    public void OnChatClicked(Restoran restoran) {

                    }

                    @Override
                    public void OnProfileClicked(Restoran restoran) {
                        Intent i = new Intent(ListRestoran.this, ProfileRestoran.class);
                        i.putExtra("datarestoran", restoran);
                        i.putExtra("datareservasi", reservation);
                        i.putExtra("id_customer", idcust);
                        i.putExtra("id_restoran", restoran.getId_restoran());
                        startActivity(i);
                    }
                });
            }
        });
    }

    private void SetRVListRestoran(){
        binding.rvListRestoran.setHasFixedSize(true);
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

                            StringRequest stringRequest2 = new StringRequest(
                                    Request.Method.POST,
                                    getResources().getString(R.string.url),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            System.out.println(response);

                                            try {
                                                JSONObject jsonobject = new JSONObject(response);
                                                JSONArray arrtemp = jsonobject.getJSONArray("datajambuka");
                                                for (int j = 0; j <= arrtemp.length()-1; j++) {
                                                    JSONObject tempMenu = arrtemp.getJSONObject(j);
                                                    HariJamBukaResto rr = new HariJamBukaResto(
                                                            tempMenu.getString("id_restoran"),
                                                            tempMenu.getString("hari_buka"),
                                                            tempMenu.getString("jam_buka"));
                                                    arrWkt.add(rr);
                                                }

                                                if (arrResto != null) {
                                                    DateFormat dateFormat = new SimpleDateFormat("EEEE");
                                                    DateFormat df=new SimpleDateFormat("dd/MM/yyyy");
                                                    String haridrtgl = "";
                                                    try {
                                                        haridrtgl=dateFormat.format(df.parse(reservation.getTanggal_reservasi()));
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }
                                                    String hari = "";
                                                    if(haridrtgl.equalsIgnoreCase("Monday")){
                                                        hari = "Senin";
                                                    }
                                                    else if(haridrtgl.equalsIgnoreCase("Tuesday")){
                                                        hari = "Selasa";
                                                    }
                                                    else if(haridrtgl.equalsIgnoreCase("Wednesday")){
                                                        hari = "Rabu";
                                                    }
                                                    else if(haridrtgl.equalsIgnoreCase("Thursday")){
                                                        hari = "Kamis";
                                                    }
                                                    else if(haridrtgl.equalsIgnoreCase("Friday")){
                                                        hari = "Jumat";
                                                    }
                                                    else if(haridrtgl.equalsIgnoreCase("Saturday")){
                                                        hari = "Sabtu";
                                                    }
                                                    else if(haridrtgl.equalsIgnoreCase("Sunday")){
                                                        hari = "Minggu";
                                                    }
                                                    for (int i = 0; i < arrResto.size(); i++) {
                                                        for (int j = 0; j < arrWkt.size(); j++) {
                                                            if(arrResto.get(i).getStatus_restoran().equals("Aktif") && (arrWkt.get(j).getHari_buka().equalsIgnoreCase(hari) || arrWkt.get(j).getHari_buka().equalsIgnoreCase("Setiap Hari")) && (Integer.parseInt(reservation.getJam_reservasi().substring(0,2)) >= Integer.parseInt(arrWkt.get(j).getJam_buka().substring(0,2))) && (Integer.parseInt(reservation.getJam_reservasi().substring(0,2)) < Integer.parseInt(arrWkt.get(j).getJam_buka().substring(6,8))) && arrResto.get(i).getId_restoran().equals(arrWkt.get(j).getId_restoran())){
                                                                Restoran r = new Restoran(arrResto.get(i).getId_restoran(), arrResto.get(i).getUsername_restoran(), arrResto.get(i).getPassword_restoran(), arrResto.get(i).getNama_restoran(), arrResto.get(i).getAlamat_restoran(), arrResto.get(i).getDaerah_restoran(), arrResto.get(i).getEmail_restoran(), arrResto.get(i).getStatus_restoran());
                                                                arrResto1.add(r);
                                                            }
                                                        }
                                                    }
                                                    binding.rvListRestoran.setLayoutManager(new LinearLayoutManager(ListRestoran.this));
                                                    adapter = new ListRestoranAdapter(arrResto1, ListRestoran.this, arrRR);
                                                    binding.rvListRestoran.setAdapter(adapter);
                                                    adapter.setOnItemClickCallback(new ListRestoranAdapter.OnItemClickCallback() {
                                                        @Override
                                                        public void OnItemClicked(Restoran restoran) {
                                                            Intent i = new Intent(ListRestoran.this, FormPilihMakanan.class);
                                                            i.putExtra("id_restoran", restoran.getId_restoran());
                                                            i.putExtra("datareservasi", reservation);
                                                            i.putExtra("id_customer", idcust);
                                                            startActivity(i);
                                                        }

                                                        @Override
                                                        public void OnKalenderClicked(Restoran restoran) {
                                                            Intent i = new Intent(ListRestoran.this, KalenderReservasiRestoran.class);
                                                            i.putExtra("id_restoran", restoran.getId_restoran());
                                                            i.putExtra("datareservasi", reservation);
                                                            i.putExtra("id_customer", idcust);
                                                            startActivity(i);
                                                        }

                                                        @Override
                                                        public void OnVoucherClicked(Restoran restoran) {
                                                            Intent i = new Intent(ListRestoran.this, FormPilihMakanan.class);
                                                            i.putExtra("id_restoran", restoran.getId_restoran());
                                                            i.putExtra("id_customer", idcust);
                                                            startActivity(i);
                                                        }

                                                        @Override
                                                        public void OnChatClicked(Restoran restoran) {

                                                        }

                                                        @Override
                                                        public void OnProfileClicked(Restoran restoran) {
                                                            Intent i = new Intent(ListRestoran.this, ProfileRestoran.class);
                                                            i.putExtra("datarestoran", restoran);
                                                            i.putExtra("datareservasi", reservation);
                                                            i.putExtra("id_customer", idcust);
                                                            startActivity(i);
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
                                    params.put("function", "selectRestoBerdasarkanHariJamBuka");
                                    return params;
                                }
                            };
                            RequestQueue requestQueue2 = Volley.newRequestQueue(ListRestoran.this);
                            requestQueue2.add(stringRequest2);

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
        RequestQueue requestQueue = Volley.newRequestQueue(ListRestoran.this);
        requestQueue.add(stringRequest);
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

                            boolean adarat = false;
                            int jmlhcusrat = 0;
                            float rating = 0;
                            int idx = 0;
                            for (int i = 0; i < arrResto1.size(); i++) {
                                adarat = false;
                                jmlhcusrat = 0;
                                rating = 0;
                                idx = 0;
                                for (int j = 0; j < arrRR.size(); j++) {
                                    if(arrResto1.get(i).getId_restoran().equalsIgnoreCase(arrRR.get(j).getId_restoran())){
                                        rating += Float.parseFloat(arrRR.get(j).getJumlah_bintang());
                                        jmlhcusrat++;
                                        adarat = true;
                                        idx = j;
                                    }
                                }

                                if(adarat == false){
                                    rating = 0;
                                }
                                else{
                                    float hasil = rating / jmlhcusrat;
                                    String rat = String.format("%.1f", hasil);
                                    RatingReviewClass rrc = new RatingReviewClass(rat, arrRR.get(idx).getId_restoran());
                                    arrRR2.add(rrc);
                                }
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
        RequestQueue requestQueue = Volley.newRequestQueue(ListRestoran.this);
        requestQueue.add(stringRequest);
    }

    private void selectRestoBerdasarkanHarga(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                getResources().getString(R.string.url),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONObject jsonobject = new JSONObject(response);
                            JSONArray arrtemp = jsonobject.getJSONArray("dataFilterHarga");
                            for (int j = 0; j <= arrtemp.length()-1; j++) {
                                JSONObject tempMenu = arrtemp.getJSONObject(j);
                                MenuMakanan mm = new MenuMakanan(tempMenu.getString("harga_menu"), tempMenu.getString("id_restoran"));
                                arrMM.add(mm);
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
                params.put("function", "FilterHarga");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ListRestoran.this);
        requestQueue.add(stringRequest);
    }
    
    private void selectFasilitas(){
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

                            for (int i = 0; i < arrFas.size(); i++) {
                                if(arrFas.get(i).getStatus_fasilitas().equalsIgnoreCase("Ada")){
                                    FasilitasRestoran fr = new FasilitasRestoran(arrFas.get(i).getId_fasilitas(), arrFas.get(i).getNama_fasilitas(), arrFas.get(i).getJumlah_fasilitas(), arrFas.get(i).getStatus_fasilitas(), arrFas.get(i).getId_restoran());
                                    arrFas1.add(fr);
                                }
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
        RequestQueue requestQueue = Volley.newRequestQueue(ListRestoran.this);
        requestQueue.add(stringRequest);
    }

//    private void selectRestoBerdasarkanHargaMinMax(String hmin, String hmax){
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.POST,
//                getResources().getString(R.string.url),
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        System.out.println(response);
//
//                        try {
//                            JSONObject jsonobject = new JSONObject(response);
//                            JSONArray arrtemp = jsonobject.getJSONArray("dataFilterHarga");
//                            for (int j = 0; j <= arrtemp.length()-1; j++) {
//                                JSONObject tempMenu = arrtemp.getJSONObject(j);
//                                MenuMakanan mm = new MenuMakanan(tempMenu.getString("harga_menu"), tempMenu.getString("id_restoran"));
//                                arrMM.add(mm);
//                            }
//
//                            for (int i = 0; i < arrMM.size(); i++) {
//                                if((Integer.parseInt(arrMM.get(i).getHarga_menu()) <= Integer.parseInt(hmax)) && (Integer.parseInt(arrMM.get(i).getHarga_menu()) >= Integer.parseInt(hmin))){
//                                    idresto.add(arrMM.get(i).getId_restoran());
//                                }
//                            }
//
//                            for (int i = 0; i < arrResto1.size(); i++) {
//                                for (int j = 0; j < idresto.size(); j++) {
//                                    if(arrResto1.get(i).getId_restoran().equalsIgnoreCase(idresto.get(j))){
//                                        Restoran rnew = new Restoran(arrResto1.get(i).getId_restoran(), arrResto1.get(i).getUsername_restoran(), arrResto1.get(i).getPassword_restoran(), arrResto1.get(i).getNama_restoran(), arrResto1.get(i).getAlamat_restoran(), arrResto1.get(i).getDaerah_restoran(), arrResto1.get(i).getEmail_restoran(), arrResto1.get(i).getStatus_restoran());
//                                        arrResto3.add(rnew);
//                                    }
//                                }
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }
//        ){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("function", "FilterHarga");
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(ListRestoran.this);
//        requestQueue.add(stringRequest);
//    }
}