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
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.ActivityTambahMenuMakananBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TambahMenuMakanan extends AppCompatActivity {

    ActivityTambahMenuMakananBinding binding;
    int SELECT_PICTURE = 200;
    Bitmap bitmap = null;
    String idresto = "1";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    //tampilkan gambar
                    binding.imageView3.setImageURI(selectedImageUri);

                    //ubah gambar ke bitmap
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
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
        setContentView(R.layout.activity_tambah_menu_makanan);

        binding = ActivityTambahMenuMakananBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
        if(iget.hasExtra("id_restoran")){
            idresto = iget.getStringExtra("id_restoran");
        }

        binding.buttonSelectImageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
            }
        });

        binding.buttonSubmitTambahMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = binding.txtNamaMenu.getText().toString();
                String kategori = binding.txtKategoriMenu.getText().toString();
                String desc = binding.txtDeskripsiMenu.getText().toString();
                String harga = binding.txtHargaMenu.getText().toString();
                if(nama.equalsIgnoreCase("") || kategori.equalsIgnoreCase("") || desc.equalsIgnoreCase("") || harga.equalsIgnoreCase("") || binding.rgstatusmakanan.getCheckedRadioButtonId() == -1){
                    Toast.makeText(TambahMenuMakanan.this, "Ada field kosong", Toast.LENGTH_SHORT).show();
                }
                else{
                    int selectedId = binding.rgstatusmakanan.getCheckedRadioButtonId();
                    RadioButton rb = (RadioButton) findViewById(selectedId);
                    String status = rb.getText().toString();

                    MenuMakanan mm = new MenuMakanan(nama, kategori, status, desc, harga);
                    AddMenuMakanan(mm);
                }
            }
        });
    }

    private void AddMenuMakanan(MenuMakanan menu){
        ByteArrayOutputStream byteArrayOutputStream;
        byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            final String base64image = Base64.encodeToString(bytes, Base64.DEFAULT);
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
                                Toast.makeText(TambahMenuMakanan.this, message, Toast.LENGTH_SHORT).show();
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
                    params.put("function", "AddMenuMakanan");
                    params.put("nama_menu", menu.getNama_menu());
                    params.put("kategori_menu", menu.getKategori_menu());
                    params.put("status_menu",menu.getStatus_menu());
                    params.put("deskripsi_menu", menu.getDeskripsi_menu());
                    params.put("harga_menu", menu.getHarga_menu());
                    params.put("id_restoran", idresto);
                    params.put("image", base64image);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(TambahMenuMakanan.this);
            requestQueue.add(stringRequest);
        }
        else {
            Toast.makeText(this, "Silahkan masukkan gambar", Toast.LENGTH_SHORT).show();
        }
    }
}