package com.example.tugasakhir;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.ActivityRatingReviewBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RatingReview extends AppCompatActivity {

    ActivityRatingReviewBinding binding;
    PesananSayaClass psc;
    String idcust;
    String idresto;
    ArrayList<Restoran> arrResto = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_review);

        binding = ActivityRatingReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
        if(iget.hasExtra("datapesanan")){
            psc = iget.getParcelableExtra("datapesanan");
        }
        if(iget.hasExtra("id_customer")){
            idcust = iget.getStringExtra("id_customer");
        }

        binding.buttonSubmitRatingReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float star = binding.ratingBar.getRating();
                String review = binding.textReview.getText().toString();
                String kategori = binding.spinnerKategoriResto.getSelectedItem().toString();

                selectRestoran();
                RatingReviewClass rr = new RatingReviewClass(String.valueOf(star), review, kategori, idresto, idcust);
                AddRatingReview(rr);
            }
        });
    }

    private void AddRatingReview(RatingReviewClass rr){
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
                            Toast.makeText(RatingReview.this, message, Toast.LENGTH_SHORT).show();
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
                params.put("function", "AddRatingReview");
                params.put("jumlah_bintang", rr.getJumlah_bintang());
                params.put("review", rr.getReview());
                params.put("kategori_terpilih", rr.getKategori_pilihan());
                params.put("id_restoran", rr.getId_restoran());
                params.put("id_customer", rr.getId_customer());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(RatingReview.this);
        requestQueue.add(stringRequest);
    }

    private void selectRestoran(){
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

                            for (int i = 0; i < arrResto.size(); i++) {
                                if(psc.getNama_restoran().equalsIgnoreCase(arrResto.get(i).getNama_restoran())){
                                    idresto = arrResto.get(i).getId_restoran();
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
                params.put("function", "selectAllRestoran");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(RatingReview.this);
        requestQueue.add(stringRequest);
    }
}