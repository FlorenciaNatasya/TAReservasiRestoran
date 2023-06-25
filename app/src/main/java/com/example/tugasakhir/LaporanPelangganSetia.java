package com.example.tugasakhir;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

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

public class LaporanPelangganSetia extends AppCompatActivity {

    String idresto = "1";
    PieChart pieChart;
    ArrayList<String> arrUser = new ArrayList<>();
    ArrayList<String> arrUser2 = new ArrayList<>();
    ArrayList<String> arrJmlh = new ArrayList<>();
    DrawerLayout dl;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_pelanggan_setia);

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
                                arrUser.add(tempMenu.getString("username_customer"));
                            }

                            for (int i = 0; i < arrUser.size(); i++) {
                                if(arrUser2.size() <= 0){
                                    arrUser2.add(arrUser.get(i));
                                    arrJmlh.add("1");
                                }
                                else{
                                    boolean adauser = false;
                                    int jmlhuser = 0;
                                    int idx = 0;
                                    for (int j = 0; j < arrUser2.size(); j++) {
                                        if(arrUser.get(i).equalsIgnoreCase(arrUser2.get(j))){
                                            adauser = true;
                                            jmlhuser++;
                                            idx = j;
                                        }
                                    }

                                    if(adauser == false){
                                        arrUser2.add(arrUser.get(i));
                                        arrJmlh.add("1");
                                    }
                                    else{
                                        jmlhuser+= Integer.parseInt(arrJmlh.get(idx));
                                        arrJmlh.set(idx, String.valueOf(jmlhuser));
                                    }
                                }
                            }

//                            Toast.makeText(LaporanPelangganSetia.this, String.valueOf(arrUser2.size()), Toast.LENGTH_SHORT).show();

                            for (int i = 0; i < arrUser2.size(); i++) {
                                visitors.add(new PieEntry(Integer.parseInt(arrJmlh.get(i)), arrUser2.get(i)));
                            }

                            PieDataSet pieDataSet = new PieDataSet(visitors, "Pelanggan Setia");
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
                            pieChart.setCenterText("Pelanggan Setia");
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
                params.put("function", "PelangganSetia");
                params.put("id_restoran", idresto);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(LaporanPelangganSetia.this);
        requestQueue.add(stringRequest);

        dl = (DrawerLayout) findViewById(R.id.dl_restoran);
        navigationView = (NavigationView) findViewById(R.id.nav_view_restoran);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.item_edit_profile_restoran){
                    Intent i = new Intent(LaporanPelangganSetia.this, EditProfileRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_master_menu){
                    Intent i = new Intent(LaporanPelangganSetia.this, ListMenuMakanan.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_master_fasilitas){
                    Intent i = new Intent(LaporanPelangganSetia.this, ListFasilitas.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_master_voucher){
                    Intent i = new Intent(LaporanPelangganSetia.this, ListVoucherRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_pendapatan_restoran){
                    Intent i = new Intent(LaporanPelangganSetia.this, LaporanPendapatanRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_rating_review_restoran){
                    Intent i = new Intent(LaporanPelangganSetia.this, LaporanRatingReviewRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_laporan_makanan_terlaku){
                    Intent i = new Intent(LaporanPelangganSetia.this, LaporanMakananTerlaku.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                else if(id == R.id.item_list_pesanan){
                    Intent i = new Intent(LaporanPelangganSetia.this, ListTransaksiRestoran.class);
                    i.putExtra("id_restoran", idresto);
                    startActivity(i);
                }
                return true;
            }
        });
    }
}