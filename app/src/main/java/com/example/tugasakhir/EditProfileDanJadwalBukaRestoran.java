package com.example.tugasakhir;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.tugasakhir.databinding.ActivityEditProfileDanJadwalBukaRestoranBinding;
import com.google.zxing.common.BitArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditProfileDanJadwalBukaRestoran extends AppCompatActivity {

    ActivityEditProfileDanJadwalBukaRestoranBinding binding;
    String idresto;
    ArrayList<Restoran> arrResto = new ArrayList<>();
    ArrayList<Restoran> arrResto1 = new ArrayList<>();
    ArrayList<HariJamBukaResto> arrWkt = new ArrayList<>();
    int SELECT_PICTURE = 200;
    Bitmap bitmap;
    Bitmap bitmap2;
    Bitmap bitmap3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_dan_jadwal_buka_restoran);

        binding = ActivityEditProfileDanJadwalBukaRestoranBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
        if(iget.hasExtra("id_restoran")){
            idresto = iget.getStringExtra("id_restoran");
        }

        selectDataRestoran();
        setJamBukaResto();

        binding.buttonEditGmbrRestoran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
            }
        });

        binding.checkBoxEditSetiapHari.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(binding.checkBoxEditSetiapHari.isChecked() == false){
                    binding.checkBoxEditSenin.setChecked(false);
                    binding.checkBoxEditSenin.setEnabled(true);
                    binding.checkBoxEditSelasa.setChecked(false);
                    binding.checkBoxEditSelasa.setEnabled(true);
                    binding.checkBoxEditRabu.setChecked(false);
                    binding.checkBoxEditRabu.setEnabled(true);
                    binding.checkBoxEditKamis.setChecked(false);
                    binding.checkBoxEditKamis.setEnabled(true);
                    binding.checkBoxEditJumat.setChecked(false);
                    binding.checkBoxEditJumat.setEnabled(true);
                    binding.checkBoxEditSabtu.setChecked(false);
                    binding.checkBoxEditSabtu.setEnabled(true);
                    binding.checkBoxEditMinggu.setChecked(false);
                    binding.checkBoxEditMinggu.setEnabled(true);
                }
                else{
                    binding.checkBoxEditSenin.setChecked(true);
                    binding.checkBoxEditSenin.setEnabled(false);
                    binding.checkBoxEditSelasa.setChecked(true);
                    binding.checkBoxEditSelasa.setEnabled(false);
                    binding.checkBoxEditRabu.setChecked(true);
                    binding.checkBoxEditRabu.setEnabled(false);
                    binding.checkBoxEditKamis.setChecked(true);
                    binding.checkBoxEditKamis.setEnabled(false);
                    binding.checkBoxEditJumat.setChecked(true);
                    binding.checkBoxEditJumat.setEnabled(false);
                    binding.checkBoxEditSabtu.setChecked(true);
                    binding.checkBoxEditSabtu.setEnabled(false);
                    binding.checkBoxEditMinggu.setChecked(true);
                    binding.checkBoxEditMinggu.setEnabled(false);
                }
            }
        });

        binding.buttonSubmitEditGbrJamRestoran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buka = binding.textEditWaktuBukaRestoran.getText().toString();
                String tutup = binding.textEditTutupWaktuRestoran.getText().toString();
                if(binding.checkBoxEditSetiapHari.isChecked()==true){
                    HariJamBukaResto hjbr = new HariJamBukaResto(idresto, "Setiap Hari", buka + "-" + tutup);
                    EditGambarDanHariJamBuka(hjbr);
                }
                else{
                    if(binding.checkBoxEditSenin.isChecked()==true){
                        HariJamBukaResto hjbr = new HariJamBukaResto(idresto, "Senin", buka + "-" + tutup);
                        EditGambarDanHariJamBuka(hjbr);
                    }
                    if(binding.checkBoxEditSelasa.isChecked()==true){
                        HariJamBukaResto hjbr = new HariJamBukaResto(idresto, "Selasa", buka + "-" + tutup);
                        EditGambarDanHariJamBuka(hjbr);
                    }
                    if(binding.checkBoxEditRabu.isChecked()==true){
                        HariJamBukaResto hjbr = new HariJamBukaResto(idresto, "Rabu", buka + "-" + tutup);
                        EditGambarDanHariJamBuka(hjbr);
                    }
                    if(binding.checkBoxEditKamis.isChecked()==true){
                        HariJamBukaResto hjbr = new HariJamBukaResto(idresto, "Kamis", buka + "-" + tutup);
                        EditGambarDanHariJamBuka(hjbr);
                    }
                    if(binding.checkBoxEditJumat.isChecked()==true){
                        HariJamBukaResto hjbr = new HariJamBukaResto(idresto, "Jumat", buka + "-" + tutup);
                        EditGambarDanHariJamBuka(hjbr);
                    }
                    if(binding.checkBoxEditSabtu.isChecked()==true){
                        HariJamBukaResto hjbr = new HariJamBukaResto(idresto, "Sabtu", buka + "-" + tutup);
                        EditGambarDanHariJamBuka(hjbr);
                    }
                    if(binding.checkBoxEditMinggu.isChecked()==true){
                        HariJamBukaResto hjbr = new HariJamBukaResto(idresto, "Minggu", buka + "-" + tutup);
                        EditGambarDanHariJamBuka(hjbr);
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    if(binding.imageView21.getDrawable()==null){
                        binding.imageView21.setImageURI(selectedImageUri);
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(binding.imageView23.getDrawable()==null){
                        binding.imageView23.setImageURI(selectedImageUri);
                        try {
                            bitmap2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        binding.imageView24.setImageURI(selectedImageUri);
                        try {
                            bitmap3 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void selectDataRestoran(){
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
                            if (arrResto != null) {
                                for (int i = 0; i < arrResto.size(); i++) {
                                    if(arrResto.get(i).getId_restoran().equalsIgnoreCase(idresto)){
                                        Glide.with(EditProfileDanJadwalBukaRestoran.this).asBitmap().load("https://reservasirestoran.me/imagesResto/"+ arrResto.get(i).getNama_restoran().replace(" ", "") + "BagianDepan" +".jpg").into(new CustomTarget<Bitmap>() {
                                            @Override
                                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                binding.imageView21.setImageBitmap(resource);
                                                bitmap = resource;
                                            }
                                            @Override
                                            public void onLoadCleared(@Nullable Drawable placeholder) {
                                            }
                                        });
                                        Glide.with(EditProfileDanJadwalBukaRestoran.this).asBitmap().load("https://reservasirestoran.me/imagesResto/"+ arrResto.get(i).getNama_restoran().replace(" ", "") + "BagianDalam" +".jpg").into(new CustomTarget<Bitmap>() {
                                            @Override
                                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                binding.imageView23.setImageBitmap(resource);
                                                bitmap2 = resource;
                                            }
                                            @Override
                                            public void onLoadCleared(@Nullable Drawable placeholder) {
                                            }
                                        });
                                        Glide.with(EditProfileDanJadwalBukaRestoran.this).asBitmap().load("https://reservasirestoran.me/imagesResto/"+ arrResto.get(i).getNama_restoran().replace(" ", "") + "BagianRuangan" +".jpg").into(new CustomTarget<Bitmap>() {
                                            @Override
                                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                binding.imageView24.setImageBitmap(resource);
                                                bitmap3 = resource;
                                            }
                                            @Override
                                            public void onLoadCleared(@Nullable Drawable placeholder) {
                                            }
                                        });
                                        Restoran r = new Restoran(arrResto.get(i).getId_restoran(), arrResto.get(i).getUsername_restoran(), arrResto.get(i).getPassword_restoran(), arrResto.get(i).getNama_restoran(), arrResto.get(i).getAlamat_restoran(), arrResto.get(i).getDaerah_restoran(), arrResto.get(i).getEmail_restoran(), arrResto.get(i).getStatus_restoran());
                                        arrResto1.add(r);
                                    }
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
        RequestQueue requestQueue = Volley.newRequestQueue(EditProfileDanJadwalBukaRestoran.this);
        requestQueue.add(stringRequest);
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

                            if(arrWkt.get(0).getHari_buka().equalsIgnoreCase("Setiap Hari")){
                                binding.checkBoxEditSetiapHari.setChecked(true);
                            }
                            if(arrWkt.get(0).getHari_buka().equalsIgnoreCase("Senin")){
                                binding.checkBoxEditSenin.setChecked(true);
                            }
                            if(arrWkt.get(0).getHari_buka().equalsIgnoreCase("Selasa")){
                                binding.checkBoxEditSelasa.setChecked(true);
                            }
                            if(arrWkt.get(0).getHari_buka().equalsIgnoreCase("Rabu")){
                                binding.checkBoxEditRabu.setChecked(true);
                            }
                            if(arrWkt.get(0).getHari_buka().equalsIgnoreCase("Kamis")){
                                binding.checkBoxEditKamis.setChecked(true);
                            }
                            if(arrWkt.get(0).getHari_buka().equalsIgnoreCase("Jumat")){
                                binding.checkBoxEditJumat.setChecked(true);
                            }
                            if(arrWkt.get(0).getHari_buka().equalsIgnoreCase("Sabtu")){
                                binding.checkBoxEditSabtu.setChecked(true);
                            }
                            if(arrWkt.get(0).getHari_buka().equalsIgnoreCase("Minggu")){
                                binding.checkBoxEditMinggu.setChecked(true);
                            }

                            binding.textEditWaktuBukaRestoran.setText(arrWkt.get(0).getJam_buka().substring(0,5));
                            binding.textEditTutupWaktuRestoran.setText(arrWkt.get(0).getJam_buka().substring(6));

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
                params.put("id_restoran", idresto);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(EditProfileDanJadwalBukaRestoran.this);
        requestQueue.add(stringRequest);
    }

    private void EditGambarDanHariJamBuka(HariJamBukaResto hjbr){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        final String base64imagedepan = Base64.encodeToString(bytes, Base64.DEFAULT);

        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream2);
        byte[] bytes2 = byteArrayOutputStream2.toByteArray();
        final String base64imagedalam = Base64.encodeToString(bytes2, Base64.DEFAULT);

        ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
        bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream3);
        byte[] bytes3 = byteArrayOutputStream3.toByteArray();
        final String base64imageruangan = Base64.encodeToString(bytes3, Base64.DEFAULT);

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
                            Toast.makeText(EditProfileDanJadwalBukaRestoran.this, message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("function", "EditGambarDanHariJamBuka");
                params.put("id_restoran", idresto);
                params.put("nama", arrResto1.get(0).getNama_restoran());
                params.put("hari_buka", hjbr.getHari_buka());
                params.put("jam_buka", hjbr.getJam_buka());
                params.put("image_depan", base64imagedepan);
                params.put("image_dalam", base64imagedalam);
                params.put("image_ruangan", base64imageruangan);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(EditProfileDanJadwalBukaRestoran.this);
        requestQueue.add(stringRequest);
    }
}
