package com.example.tugasakhir;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.ActivityCustomerPilihFasilitasBinding;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerPilihFasilitas extends AppCompatActivity {

    ActivityCustomerPilihFasilitasBinding binding;
    ArrayList<FasilitasRestoran> fasResto = new ArrayList<>();
    ArrayList<FasilitasRestoran> fasResto2 = new ArrayList<>();
    ArrayList<String> namafas = new ArrayList<>();
    ArrayList<FasilitasYangDipinjamClass> fasYangDipinjam = new ArrayList<>();
    int jaw = 0;
    String idcust;
    String idhtrans;
    String idresto = "1";

    DrawerLayout dl;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_pilih_fasilitas);

        binding = ActivityCustomerPilihFasilitasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
        if(iget.hasExtra("id_htrans")){
            idhtrans = iget.getStringExtra("id_htrans");
        }
        if(iget.hasExtra("id_customer")){
            idcust = iget.getStringExtra("id_customer");
        }
        if(iget.hasExtra("id_restoran")){
            idresto = iget.getStringExtra("id_restoran");
        }

        dl = (DrawerLayout) findViewById(R.id.dl_customer);
        navigationView = (NavigationView) findViewById(R.id.nav_view_customer);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.item_edit_profile_customer){
                    Intent i = new Intent(CustomerPilihFasilitas.this, EditProfileCustomer.class);
                    i.putExtra("id_customer", idcust);
                    startActivity(i);
                }
                else if (id == R.id.item_form_reservasi){
                    Intent i = new Intent(CustomerPilihFasilitas.this, FormReservasi.class);
                    i.putExtra("id_customer", idcust);
                    startActivity(i);
                }
                else if(id == R.id.item_list_resto){
                    Intent i = new Intent(CustomerPilihFasilitas.this, ListRestoran.class);
                    i.putExtra("id_customer", idcust);
                    startActivity(i);
                }
                else if(id == R.id.item_chat_customer){

                }
                else if(id == R.id.item_pesanan_saya){
                    Intent i = new Intent(CustomerPilihFasilitas.this, PesananSaya.class);
                    i.putExtra("id_customer", idcust);
                    startActivity(i);
                }

                return true;
            }
        });

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
                                fasResto.add(fas);
                            }

                            for (int i = 0; i < fasResto.size(); i++) {
                                if(fasResto.get(i).getId_restoran().equalsIgnoreCase(idresto) && !fasResto.get(i).getJumlah_fasilitas().equalsIgnoreCase("0")){
                                    namafas.add(fasResto.get(i).getNama_fasilitas());
                                }
                            }

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                    (CustomerPilihFasilitas.this, android.R.layout.simple_spinner_item, namafas);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                                    .simple_spinner_dropdown_item);
                            binding.spinnerPilihFasilitas.setAdapter(spinnerArrayAdapter);

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
        RequestQueue requestQueue = Volley.newRequestQueue(CustomerPilihFasilitas.this);
        requestQueue.add(stringRequest);

        binding.buttonMenambahFasilitaskeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fas = binding.spinnerPilihFasilitas.getSelectedItem().toString();
                String jmlh = binding.textJumlahFasYgDpinjam.getText().toString();

                for (int i = 0; i < fasResto.size(); i++) {
                    if(fas.equalsIgnoreCase(fasResto.get(i).getNama_fasilitas()) && (Integer.parseInt(binding.textJumlahFasYgDpinjam.getText().toString()) > Integer.parseInt(fasResto.get(i).getJumlah_fasilitas()))){
                        binding.textJumlahFasYgDpinjam.setText(fasResto.get(i).getJumlah_fasilitas());
                        Toast.makeText(CustomerPilihFasilitas.this, "Maksimum jumlah yang bisa dipinjam adalah " + fasResto.get(i).getJumlah_fasilitas(), Toast.LENGTH_SHORT).show();
                    }
                    else if(fas.equalsIgnoreCase(fasResto.get(i).getNama_fasilitas()) && (Integer.parseInt(fasResto.get(i).getJumlah_fasilitas()) >= Integer.parseInt(jmlh))){
                        String ja = "";
                        int jb = 0;
                        boolean ada = false;
                        for (int j = 0; j < fasYangDipinjam.size(); j++) {
                            if(fasYangDipinjam.get(j).getNamfas().equalsIgnoreCase(fas)){
                                ja = fasYangDipinjam.get(j).getJmlh();
                                jb = Integer.parseInt(ja) + 1;
                                fasYangDipinjam.get(j).setJmlh(String.valueOf(jb));
                                ada = true;
                            }
                        }

                        if(ada == false) {
                            FasilitasYangDipinjamClass fyd = new FasilitasYangDipinjamClass(fas, binding.textJumlahFasYgDpinjam.getText().toString(), fasResto.get(i).getId_fasilitas());
                            fasYangDipinjam.add(fyd);
                        }

                        int js = 0;
                        for (int j = 0; j < fasResto.size(); j++) {
                            if(fasResto.get(j).getNama_fasilitas().equalsIgnoreCase(fas)){
                                js = Integer.parseInt(fasResto.get(j).getJumlah_fasilitas());
                                js -= 1;
                                fasResto.get(j).setJumlah_fasilitas(String.valueOf(js));
                            }
                        }
                    }
                }

                binding.rvListFasilitasYangDipinjam.setHasFixedSize(true);
                binding.rvListFasilitasYangDipinjam.setLayoutManager(new LinearLayoutManager(CustomerPilihFasilitas.this));
                PilihFasilitasAdapter adapter = new PilihFasilitasAdapter(fasYangDipinjam);
                binding.rvListFasilitasYangDipinjam.setAdapter(adapter);
                adapter.setOnItemClickCallback(new PilihFasilitasAdapter.OnItemClickCallback() {
                    @Override
                    public void OnDeleteClicked(FasilitasYangDipinjamClass fas) {
                        fasYangDipinjam.remove(fas);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });

        binding.buttonFixFasilitasYangDipinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < fasYangDipinjam.size(); i++) {
                    FasilitasYangDipinjamClass fyd = new FasilitasYangDipinjamClass(fasYangDipinjam.get(i).getNamfas(), fasYangDipinjam.get(i).getJmlh(), fasYangDipinjam.get(i).getId_fasilitas());
                    pinjamfasilitas(fyd);
                }

                for (int i = 0; i < fasYangDipinjam.size(); i++) {
                    for (int j = 0; j < fasResto.size(); j++) {
                        if(fasYangDipinjam.get(i).getNamfas().equalsIgnoreCase(fasResto.get(j).getNama_fasilitas())){
                            jaw = Integer.parseInt(fasResto.get(j).getJumlah_fasilitas());
                            jaw -= Integer.parseInt(fasYangDipinjam.get(i).getJmlh());
                            minusfasilitas(fasResto.get(j).getId_fasilitas(), String.valueOf(jaw));
                        }
                    }
                }
            }
        });
    }

    private void pinjamfasilitas(FasilitasYangDipinjamClass fyd){
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
                            Toast.makeText(CustomerPilihFasilitas.this, message, Toast.LENGTH_SHORT).show();
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
                params.put("function", "requestPinjamFasilitas");
                params.put("id_htrans", idhtrans);
                params.put("nama_fasilitas", fyd.getNamfas());
                params.put("jumlah_faspin", fyd.getJmlh());
                params.put("id_fasilitas", fyd.getId_fasilitas());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(CustomerPilihFasilitas.this);
        requestQueue.add(stringRequest);
    }

    private void minusfasilitas(String id, String jumlah){
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
                            Toast.makeText(CustomerPilihFasilitas.this, message, Toast.LENGTH_SHORT).show();
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
                params.put("function", "minusFasilitas");
                params.put("id_fasilitas", id);
                params.put("jumlah_fasilitas", jumlah);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(CustomerPilihFasilitas.this);
        requestQueue.add(stringRequest);
    }
}