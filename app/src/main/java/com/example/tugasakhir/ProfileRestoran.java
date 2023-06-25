package com.example.tugasakhir;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.tugasakhir.databinding.ActivityProfileRestoranBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileRestoran extends AppCompatActivity {

    ActivityProfileRestoranBinding binding;
    Restoran resto;
    ArrayList<HariJamBukaResto> arrWkt = new ArrayList<>();
    ArrayList<RatingReviewClass> arrRR = new ArrayList<>();
    ArrayList<RatingReviewClass> arrRR2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_restoran);

        binding = ActivityProfileRestoranBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
        if(iget.hasExtra("datarestoran")){
            resto = iget.getParcelableExtra("datarestoran");
        }

        Glide.with(this).load("http://10.0.2.2/TA-service/imagesResto/"+ resto.getNama_restoran().replace(" ", "") + "BagianDepan" +".jpg").into(binding.imageView17);
        binding.textViewNamaRestorandiProfile.setText(resto.getNama_restoran());
        binding.textViewAlamatRestodiProfile.setText(resto.getAlamat_restoran() + ", " + resto.getDaerah_restoran());

        setJamBukaResto();

        setRatingReviewResto();
    }

    private void setJamBukaResto(){
        StringRequest stringRequest = new StringRequest(
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
                                        tempMenu.getString("hari_buka"),
                                        tempMenu.getString("jam_buka"));
                                arrWkt.add(rr);
                            }

                            String jmbuka = "Waktu buka : \n";
                            for (int i = 0; i < arrWkt.size(); i++) {
                                jmbuka += arrWkt.get(i).getHari_buka() + " pukul " + arrWkt.get(i).getJam_buka() + "\n";
                            }

                            binding.textViewHariJamBukaRestodiProfile.setText(jmbuka);

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
                params.put("function", "selectHariJamBukaResto");
                params.put("id_restoran", resto.getId_restoran());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ProfileRestoran.this);
        requestQueue.add(stringRequest);
    }

    private void setRatingReviewResto(){
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

                            for (int i = 0; i < arrRR.size(); i++) {
                                if(arrRR.get(i).getId_restoran().equals(resto.getId_restoran())){
                                    RatingReviewClass rrc = new RatingReviewClass(arrRR.get(i).getJumlah_bintang(), arrRR.get(i).getReview(), arrRR.get(i).getKategori_pilihan(), arrRR.get(i).getId_restoran(), arrRR.get(i).getId_customer());
                                    arrRR2.add(rrc);
                                }
                            }

                            binding.rvRatingReviewRestoranDiProfile.setHasFixedSize(true);
                            binding.rvRatingReviewRestoranDiProfile.setLayoutManager(new LinearLayoutManager(ProfileRestoran.this));
                            RatingReviewAdapter adapter = new RatingReviewAdapter(arrRR2);
                            binding.rvRatingReviewRestoranDiProfile.setAdapter(adapter);

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
        RequestQueue requestQueue = Volley.newRequestQueue(ProfileRestoran.this);
        requestQueue.add(stringRequest);
    }
}