package com.example.tugasakhir;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.ActivityTambahVoucherRestoranBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TambahVoucherRestoran extends AppCompatActivity {

    ActivityTambahVoucherRestoranBinding binding;
    String idresto = "1";
    ArrayList<VoucherRestoran> arrVouch = new ArrayList<>();
    int SELECT_PICTURE = 200;
    Bitmap bitmap = null;
    boolean adagbr = false;
    int year, month, day;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    if(binding.imageView16.getDrawable()==null){
                        binding.imageView16.setImageURI(selectedImageUri);
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        adagbr = true;
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_voucher_restoran);

        binding = ActivityTambahVoucherRestoranBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
        if(iget.hasExtra("id_restoran")){
            idresto = iget.getStringExtra("id_restoran");
            Toast.makeText(this, idresto, Toast.LENGTH_SHORT).show();
        }

        binding.spinnerJenisPotongan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(binding.spinnerJenisPotongan.getSelectedItem().toString().equalsIgnoreCase("Persen")){
                    binding.textPersenDiskon.setVisibility(View.VISIBLE);
                    binding.textViewPersen.setVisibility(View.VISIBLE);
                }
                else{
                    binding.textPersenDiskon.setVisibility(View.INVISIBLE);
                    binding.textViewPersen.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.imageViewDPTanggalAwalVouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(TambahVoucherRestoran.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                if(dayOfMonth < 10 && monthOfYear < 10){
                                    binding.textTanggalAwalVouch.setText("0" + dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year);
                                }
                                else if(dayOfMonth < 10){
                                    binding.textTanggalAwalVouch.setText("0" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                                else if(monthOfYear < 10){
                                    binding.textTanggalAwalVouch.setText(dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year);
                                }
                                else{
                                    binding.textTanggalAwalVouch.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        binding.imageViewDPTanggalAkhirVouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(TambahVoucherRestoran.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                if(dayOfMonth < 10 && monthOfYear < 10){
                                    binding.textTanggalAkhirVouch.setText("0" + dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year);
                                }
                                else if(dayOfMonth < 10){
                                    binding.textTanggalAkhirVouch.setText("0" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                                else if(monthOfYear < 10){
                                    binding.textTanggalAkhirVouch.setText(dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year);
                                }
                                else{
                                    binding.textTanggalAkhirVouch.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        binding.buttonUploadBannerPromosi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
            }
        });

        binding.buttonSubmitTambahVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idvouch = binding.textKodeVoucher.getText().toString();
                String jenis_voucher = "Diskon";
                String jenis_potongan = binding.spinnerJenisPotongan.getSelectedItem().toString();
                String minimum_pembelian = binding.textMinimumPembelian.getText().toString();
                String max_pot = binding.textJumlahPotongan.getText().toString();
                String kuota = binding.textKuotaVoucher.getText().toString();
                String tanggal_awal = binding.textTanggalAwalVouch.getText().toString();
                String tanggal_akhir = binding.textTanggalAkhirVouch.getText().toString();

                if(idvouch.equalsIgnoreCase("") || minimum_pembelian.equalsIgnoreCase("") || max_pot.equalsIgnoreCase("") || kuota.equalsIgnoreCase("")){
                    Toast.makeText(TambahVoucherRestoran.this, "Ada yang kosong", Toast.LENGTH_SHORT).show();
                }
                else{
                    StringRequest stringRequest = new StringRequest(
                            Request.Method.POST,
                            getResources().getString(R.string.url),
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    System.out.println(response);

                                    try {
                                        JSONObject jsonobject = new JSONObject(response);
                                        JSONArray arrtemp = jsonobject.getJSONArray("datavoucher");
                                        for (int j = 0; j <= arrtemp.length() - 1; j++) {
                                            JSONObject tempMenu = arrtemp.getJSONObject(j);
                                            VoucherRestoran vouch = new VoucherRestoran(
                                                    tempMenu.getString("id_voucher"),
                                                    tempMenu.getString("kode_voucher"),
                                                    tempMenu.getString("jenis_voucher"),
                                                    tempMenu.getString("jenis_potongan"),
                                                    tempMenu.getString("jumlah_diskon"),
                                                    tempMenu.getString("kuota_voucher"),
                                                    tempMenu.getString("minimum_pembelian"),
                                                    tempMenu.getString("maksimal_potongan"),
                                                    tempMenu.getString("tanggal_awal"),
                                                    tempMenu.getString("tanggal_berakhir"),
                                                    tempMenu.getString("status_voucher"),
                                                    tempMenu.getString("id_restoran"));
                                            arrVouch.add(vouch);
                                        }

                                        boolean ada = false;
                                        for (int i = 0; i < arrVouch.size(); i++) {
                                            if(arrVouch.get(i).getKode_voucher().equals(idvouch)){
                                                ada = true;
                                                Toast.makeText(TambahVoucherRestoran.this, "Kode voucher sudah dipakai, mohon membuat kode voucher yang lain", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        if(ada == false || arrVouch.size() <= 0){
                                            if(jenis_potongan.equalsIgnoreCase("Persen")){
                                                if(binding.textPersenDiskon.getText().toString().equalsIgnoreCase("")){
                                                    Toast.makeText(TambahVoucherRestoran.this, "Harap diisikan jumlah persenan diskonnya", Toast.LENGTH_SHORT).show();
                                                }
                                                else {
                                                    if(adagbr == false){
                                                        VoucherRestoran v = new VoucherRestoran(idvouch, jenis_voucher, jenis_potongan, binding.textPersenDiskon.getText().toString(), kuota, minimum_pembelian, max_pot, tanggal_awal, tanggal_akhir, "Ada", idresto);
                                                        AddVoucher(v);
                                                        Intent i = new Intent(TambahVoucherRestoran.this, ListVoucherRestoran.class);
                                                        i.putExtra("id_restoran", idresto);
                                                        startActivity(i);
                                                    }
                                                    else{
                                                        VoucherRestoran v = new VoucherRestoran(idvouch, jenis_voucher, jenis_potongan, binding.textPersenDiskon.getText().toString(), kuota, minimum_pembelian, max_pot, tanggal_awal, tanggal_akhir, "Ada", idresto);
                                                        AddVoucher2(v);
                                                        Intent i = new Intent(TambahVoucherRestoran.this, ListVoucherRestoran.class);
                                                        i.putExtra("id_restoran", idresto);
                                                        startActivity(i);
                                                    }
                                                }
                                            }
                                            else{
                                                if (adagbr == false) {
                                                    VoucherRestoran v = new VoucherRestoran(idvouch, jenis_voucher, jenis_potongan, "0", kuota, minimum_pembelian, max_pot, tanggal_awal, tanggal_akhir, "Ada", idresto);
                                                    AddVoucher(v);
                                                    Intent i = new Intent(TambahVoucherRestoran.this, ListVoucherRestoran.class);
                                                    i.putExtra("id_restoran", idresto);
                                                    startActivity(i);
                                                }
                                                else{
                                                    VoucherRestoran v = new VoucherRestoran(idvouch, jenis_voucher, jenis_potongan, "0", kuota, minimum_pembelian, max_pot, tanggal_awal, tanggal_akhir, "Ada", idresto);
                                                    AddVoucher2(v);
                                                    Intent i = new Intent(TambahVoucherRestoran.this, ListVoucherRestoran.class);
                                                    i.putExtra("id_restoran", idresto);
                                                    startActivity(i);
                                                }
                                            }
                                        }

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
                            params.put("function", "selectAllVoucher");
                            return params;
                        }

                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(TambahVoucherRestoran.this);
                    requestQueue.add(stringRequest);
                }
            }
        });
    }

    private void AddVoucher(VoucherRestoran voucher){
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
                            Toast.makeText(TambahVoucherRestoran.this, message, Toast.LENGTH_SHORT).show();
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
                params.put("function", "AddVoucher");
                params.put("kode_voucher", voucher.getKode_voucher());
                params.put("jenis_voucher", voucher.getJenis_voucher());
                params.put("jenis_potongan", voucher.getJenis_potongan());
                params.put("jumlah_diskon", voucher.getJumlah_diskon());
                params.put("kuota_voucher", voucher.getKuota_voucher());
                params.put("minimum_pembelian", voucher.getMinimum_pembelian());
                params.put("maksimal_potongan", voucher.getMaksimal_potongan());
                params.put("tanggal_awal", voucher.getTanggal_awal());
                params.put("tanggal_berakhir", voucher.getTanggal_akhir());
                params.put("status_voucher", voucher.getStatus_voucher());
                params.put("id_restoran", voucher.getId_restoran());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(TambahVoucherRestoran.this);
        requestQueue.add(stringRequest);
    }

    private void AddVoucher2(VoucherRestoran voucher){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        final String base64imagebanner = Base64.encodeToString(bytes, Base64.DEFAULT);

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
                            Toast.makeText(TambahVoucherRestoran.this, message, Toast.LENGTH_SHORT).show();
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
                params.put("function", "AddVoucher");
                params.put("kode_voucher", voucher.getKode_voucher());
                params.put("jenis_voucher", voucher.getJenis_voucher());
                params.put("jenis_potongan", voucher.getJenis_potongan());
                params.put("jumlah_diskon", voucher.getJumlah_diskon());
                params.put("kuota_voucher", voucher.getKuota_voucher());
                params.put("minimum_pembelian", voucher.getMinimum_pembelian());
                params.put("maksimal_potongan", voucher.getMaksimal_potongan());
                params.put("tanggal_awal", voucher.getTanggal_awal());
                params.put("tanggal_berakhir", voucher.getTanggal_akhir());
                params.put("status_voucher", voucher.getStatus_voucher());
                params.put("id_restoran", voucher.getId_restoran());
                params.put("image_banner", base64imagebanner);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(TambahVoucherRestoran.this);
        requestQueue.add(stringRequest);
    }
}
