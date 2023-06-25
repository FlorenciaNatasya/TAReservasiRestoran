package com.example.tugasakhir;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaporanMakananTerlaku extends AppCompatActivity {

    ArrayList<DetailTransaksi> arrTrans = new ArrayList<>();
    ArrayList<DetailTransaksi> arrTrans2 = new ArrayList<>();
    String idresto = "1";
    PieChart pieChart;
    DrawerLayout dl;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_makanan_terlaku);

        Intent iget = getIntent();
        if(iget.hasExtra("id_restoran")){
            idresto = iget.getStringExtra("id_restoran");
        }

        pieChart = findViewById(R.id.pieChart);
        ArrayList<PieEntry> visitors = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://10.0.2.2/TA-service/master.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONObject jsonobject = new JSONObject(response);
                            JSONArray arrtemp = jsonobject.getJSONArray("datatransaksi");
                            for (int j = 0; j <= arrtemp.length() - 1; j++) {
                                JSONObject tempMenu = arrtemp.getJSONObject(j);
                                DetailTransaksi dt = new DetailTransaksi(
                                        tempMenu.getString("nama_makanan"),
                                        tempMenu.getString("jumlah_makanan")
                                );
                                arrTrans.add(dt);
                            }

                            int tot = 0;
                            for (int i = 0; i < arrTrans.size(); i++) {
                                if(arrTrans2.size() <= 0){
                                    DetailTransaksi dt = new DetailTransaksi(arrTrans.get(i).getNama_makanan(), arrTrans.get(i).getJumlah_makanan());
                                    arrTrans2.add(dt);
                                }
                                else{
                                    boolean ada = false;
                                    for (int j = 0; j < arrTrans2.size(); j++) {
                                        if(arrTrans.get(i).getNama_makanan().equalsIgnoreCase(arrTrans2.get(j).getNama_makanan())){
                                            tot = Integer.parseInt(arrTrans2.get(j).getJumlah_makanan());
                                            tot += Integer.parseInt(arrTrans.get(i).getJumlah_makanan());
                                            arrTrans2.get(j).setJumlah_makanan(String.valueOf(tot));
                                            ada = true;
                                        }
                                    }

                                    if(ada == false){
                                        DetailTransaksi dt = new DetailTransaksi(arrTrans.get(i).getNama_makanan(), arrTrans.get(i).getJumlah_makanan());
                                        arrTrans2.add(dt);
                                    }
                                }
                            }

                            Toast.makeText(LaporanMakananTerlaku.this, String.valueOf(arrTrans2.size()), Toast.LENGTH_SHORT).show();

                            for (int i = 0; i < arrTrans2.size(); i++) {
                                visitors.add(new PieEntry(Integer.parseInt(arrTrans2.get(i).getJumlah_makanan()), arrTrans2.get(i).getNama_makanan()));
                            }

                            PieDataSet pieDataSet = new PieDataSet(visitors, "Makanan Terlaris");
                            pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                            pieDataSet.setValueTextColor(Color.BLACK);
                            pieDataSet.setValueTextSize(16f);
                            pieDataSet.setValueFormatter(new ValueFormatter() {
                                @Override
                                public String getFormattedValue(float value) {
                                    return String.valueOf((int) Math.floor(value));
                                }
                            });

                            PieData pieData = new PieData(pieDataSet);
                            pieChart.setData(pieData);
                            pieChart.getDescription().setEnabled(false);
                            pieChart.setCenterText("Makanan Terlaris");
                            pieChart.animate();

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
                params.put("function", "MakananTerlaris");
                params.put("id_restoran", idresto);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(LaporanMakananTerlaku.this);
        requestQueue.add(stringRequest);


        dl = (DrawerLayout) findViewById(R.id.dl_restoran);
        navigationView = (NavigationView) findViewById(R.id.nav_view_restoran);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.item_edit_profile_restoran){
                    Intent i = new Intent(LaporanMakananTerlaku.this, EditProfileRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_master_menu){
                    Intent i = new Intent(LaporanMakananTerlaku.this, ListMenuMakanan.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_master_fasilitas){
                    Intent i = new Intent(LaporanMakananTerlaku.this, ListFasilitas.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_master_voucher){
                    Intent i = new Intent(LaporanMakananTerlaku.this, ListVoucherRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_pendapatan_restoran){
                    Intent i = new Intent(LaporanMakananTerlaku.this, LaporanPendapatanRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_rating_review_restoran){
                    Intent i = new Intent(LaporanMakananTerlaku.this, LaporanRatingReviewRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_list_pesanan){
                    Intent i = new Intent(LaporanMakananTerlaku.this, ListTransaksiRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_pelanggan_setia){
                    Intent i = new Intent(LaporanMakananTerlaku.this, LaporanPelangganSetia.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                return true;
            }
        });
    }

    private void selectMakananTerlaris(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://10.0.2.2/TA-service/master.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONObject jsonobject = new JSONObject(response);
                            JSONArray arrtemp = jsonobject.getJSONArray("datatransaksi");
                            for (int j = 0; j <= arrtemp.length() - 1; j++) {
                                JSONObject tempMenu = arrtemp.getJSONObject(j);
                                DetailTransaksi dt = new DetailTransaksi(
                                        tempMenu.getString("nama_makanan"),
                                        tempMenu.getString("jumlah_makanan")
                                );
                                arrTrans.add(dt);
                            }

                            int tot = 0;
                            for (int i = 0; i < arrTrans.size(); i++) {
                                if(arrTrans2.size() <= 0){
                                    DetailTransaksi dt = new DetailTransaksi(arrTrans.get(i).getNama_makanan(), arrTrans.get(i).getJumlah_makanan());
                                    arrTrans2.add(dt);
                                }
                                else{
                                    int idxJ = 0;
                                    for (int j = 0; j < arrTrans2.size(); j++) {
                                        if(arrTrans.get(i).getNama_makanan().equalsIgnoreCase(arrTrans2.get(j).getNama_makanan())){
                                            idxJ = j;
                                        }
                                    }

                                    if(idxJ >= 0){
                                        tot = Integer.parseInt(arrTrans2.get(idxJ).getJumlah_makanan());
                                        tot += Integer.parseInt(arrTrans.get(i).getJumlah_makanan());
                                        arrTrans2.get(idxJ).setJumlah_makanan(String.valueOf(tot));
                                    }
                                    else{
                                        DetailTransaksi dt = new DetailTransaksi(arrTrans.get(i).getNama_makanan(), arrTrans.get(i).getJumlah_makanan());
                                        arrTrans2.add(dt);
                                    }
                                }
                            }

                            ArrayList<PieEntry> visitors = new ArrayList<>();
                            for (int i = 0; i < arrTrans2.size(); i++) {
                                visitors.add(new PieEntry(Integer.parseInt(arrTrans2.get(i).getJumlah_makanan()), arrTrans2.get(i).getNama_makanan()));
                            }

                            PieDataSet pieDataSet = new PieDataSet(visitors, "Makanan Terlaku");
                            pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                            pieDataSet.setValueTextColor(Color.BLACK);
                            pieDataSet.setValueTextSize(16f);

                            PieData pieData = new PieData(pieDataSet);
                            pieChart.setData(pieData);
                            pieChart.getDescription().setEnabled(false);
                            pieChart.setCenterText("Makanan Terlaku");
                            pieChart.animate();

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
                params.put("function", "MakananTerlaris");
                params.put("id_restoran", idresto);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(LaporanMakananTerlaku.this);
        requestQueue.add(stringRequest);
    }
}