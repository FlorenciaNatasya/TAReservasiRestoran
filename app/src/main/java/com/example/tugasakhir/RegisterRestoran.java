package com.example.tugasakhir;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.ActivityRegisterRestoranBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterRestoran extends AppCompatActivity {

    ActivityRegisterRestoranBinding binding;
    int SELECT_PICTURE = 200;
    int SELECT_PICTURE2 = 250;
    Bitmap bitmap = null;
    Bitmap bitmap2 = null;
    Bitmap bitmap3 = null;
    Bitmap bitmap4 = null;
    ArrayList<Restoran> arrResto = new ArrayList<>();
    String namaResto = "";
    String usernameResto = "";
    String passwordResto = "";
    String emailResto = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    if(binding.imageView6.getDrawable()==null){
                        binding.imageView6.setImageURI(selectedImageUri);
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(binding.imageView10.getDrawable()==null){
                        binding.imageView10.setImageURI(selectedImageUri);
                        try {
                            bitmap2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        binding.imageView11.setImageURI(selectedImageUri);
                        try {
                            bitmap3 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            else if(requestCode == SELECT_PICTURE2){
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    //tampilkan gambar
                    binding.imageView7.setImageURI(selectedImageUri);

                    //ubah gambar ke bitmap
                    try {
                        bitmap4 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_restoran);

        try
        {
//            this.getSupportActionBar().hide();
            this.getSupportActionBar().setTitle("Register Data Restoran");
        }
        catch (NullPointerException e){}

        binding = ActivityRegisterRestoranBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonUploadFotoRestoran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
            }
        });

        binding.buttonUploadFotoSertifikatRestoran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE2);
            }
        });

        binding.buttonSubmitRegisterResto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String alamat = binding.txtNamaRestoran.getText().toString();
                String daerah = binding.spinnerDaerah.getSelectedItem().toString();
                if(alamat.equalsIgnoreCase("")){
                    Toast.makeText(RegisterRestoran.this, "Alamat belum diisikan", Toast.LENGTH_SHORT).show();
                }
                else if(binding.imageView6.getDrawable()==null){
                    Toast.makeText(RegisterRestoran.this, "Gambar bagian depan restoran belum diunggah", Toast.LENGTH_SHORT).show();
                }
                else if(binding.imageView10.getDrawable()==null){
                    Toast.makeText(RegisterRestoran.this, "Gambar bagian dalam restoran belum diunggah", Toast.LENGTH_SHORT).show();
                }
                else if(binding.imageView11.getDrawable()==null){
                    Toast.makeText(RegisterRestoran.this, "Gambar bagian ruangan restoran belum diunggah", Toast.LENGTH_SHORT).show();
                }
                else if(binding.imageView7.getDrawable()==null){
                    Toast.makeText(RegisterRestoran.this, "Gambar sertifikat ijin restoran belum diunggah", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent iget = getIntent();
                    if(iget.hasExtra("NamaResto")){
                        namaResto = iget.getStringExtra("NamaResto");
                    }
                    if(iget.hasExtra("UsernameResto")){
                        usernameResto = iget.getStringExtra("UsernameResto");
                    }
                    if(iget.hasExtra("PasswordResto")){
                        passwordResto = iget.getStringExtra("PasswordResto");
                    }
                    if(iget.hasExtra("EmailResto")){
                        emailResto = iget.getStringExtra("EmailResto");
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

                                        boolean ada = false;
                                        for (int i = 0; i < arrResto.size(); i++) {
                                            if(usernameResto.equals(arrResto.get(i).getUsername_restoran())){
                                                ada = true;
                                                Toast.makeText(RegisterRestoran.this, "Username sudah ada", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        if(arrResto.size() <= 0 || ada == false){
                                            Restoran restoran = new Restoran(usernameResto, passwordResto, namaResto, alamat, daerah, emailResto, "");
                                            RegisterRestoran1(restoran);
                                        }

                                        int idx = arrResto.size();
                                        Intent i = new Intent(RegisterRestoran.this, RegisterRestoran2.class);
                                        i.putExtra("id_restoran", arrResto.get(idx-1).getId_restoran());
                                        startActivity(i);

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
                    RequestQueue requestQueue2 = Volley.newRequestQueue(RegisterRestoran.this);
                    requestQueue2.add(stringRequest2);
                }
            }
        });
    }

    private void RegisterRestoran1(Restoran restoran){
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

        ByteArrayOutputStream byteArrayOutputStream4 = new ByteArrayOutputStream();
        bitmap4.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream4);
        byte[] bytes4 = byteArrayOutputStream4.toByteArray();
        final String base64imagesertifikat = Base64.encodeToString(bytes4, Base64.DEFAULT);

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
                            Toast.makeText(RegisterRestoran.this, message, Toast.LENGTH_SHORT).show();
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
                params.put("function", "RegisterRestoran");
                params.put("username_restoran", restoran.getUsername_restoran());
                params.put("password_restoran", restoran.getPassword_restoran());
                params.put("nama_restoran", restoran.getNama_restoran());
                params.put("alamat_restoran", restoran.getAlamat_restoran());
                params.put("daerah_restoran", restoran.getDaerah_restoran());
                params.put("email_restoran", restoran.getEmail_restoran());
                params.put("image_depan", base64imagedepan);
                params.put("image_dalam", base64imagedalam);
                params.put("image_ruangan", base64imageruangan);
                params.put("image_sertifikat", base64imagesertifikat);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(RegisterRestoran.this);
        requestQueue.add(stringRequest);
    }

    private void RegisterRestoran(Restoran restoran){
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

        ByteArrayOutputStream byteArrayOutputStream4 = new ByteArrayOutputStream();
        bitmap4.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream4);
        byte[] bytes4 = byteArrayOutputStream4.toByteArray();
        final String base64imagesertifikat = Base64.encodeToString(bytes4, Base64.DEFAULT);

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
                            Toast.makeText(RegisterRestoran.this, message, Toast.LENGTH_SHORT).show();
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
                params.put("function", "RegisterRestoran");
                params.put("username_restoran", restoran.getUsername_restoran());
                params.put("password_restoran", restoran.getPassword_restoran());
                params.put("nama_restoran", restoran.getNama_restoran());
                params.put("alamat_restoran", restoran.getAlamat_restoran());
                params.put("daerah_restoran", restoran.getDaerah_restoran());
                params.put("email_restoran", restoran.getEmail_restoran());
                params.put("image_depan", base64imagedepan);
                params.put("image_dalam", base64imagedalam);
                params.put("image_ruangan", base64imageruangan);
                params.put("image_sertifikat", base64imagesertifikat);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(RegisterRestoran.this);
        requestQueue.add(stringRequest);
    }
}