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
import com.bumptech.glide.Glide;
import com.example.tugasakhir.databinding.ActivityEditVoucherRestoranBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditVoucherRestoran extends AppCompatActivity {

    ActivityEditVoucherRestoranBinding binding;
    VoucherRestoran vr;
    String idresto = "1";
    int SELECT_PICTURE = 200;
    Bitmap bitmap = null;
    boolean adagbr = false;
    String ta = "";
    String tb = "";
    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_voucher_restoran);

        binding = ActivityEditVoucherRestoranBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
        if(iget.hasExtra("id_restoran")){
            idresto = iget.getStringExtra("id_restoran");
            Toast.makeText(EditVoucherRestoran.this, idresto, Toast.LENGTH_SHORT).show();
        }
        if(iget.hasExtra("datavoucher")){
            vr = iget.getParcelableExtra("datavoucher");
        }
        if(iget.hasExtra("tanggal_awal")){
            ta = iget.getStringExtra("tanggal_awal");
        }
        if(iget.hasExtra("tanggal_akhir")){
            tb = iget.getStringExtra("tanggal_akhir");
        }

        binding.textEditKodeVoucher.setText(vr.getKode_voucher());
        if(vr.getJenis_potongan().equalsIgnoreCase("Persen")){
            binding.spinnerEditJenisPotongan.setSelection(1);
            binding.textEditPersenDiskon.setVisibility(View.VISIBLE);
            binding.textEditPersenDiskon.setText(vr.getJumlah_diskon());
            binding.textViewEditPersen.setVisibility(View.VISIBLE);
        }
        binding.textEditMinimumPembelian.setText(vr.getMinimum_pembelian());
        binding.textEditJumlahPotongan.setText(vr.getMaksimal_potongan());
        binding.textEditKuotaVoucher.setText(vr.getKuota_voucher());
        binding.textEditTanggalAwalVouch.setText(ta);
        binding.textEditTanggalAkhirVouch.setText(tb);
        Glide.with(this).load("https://github.com/FlorenciaNatasya/BackendTA/imagesResto/"+ "Banner" + vr.getKode_voucher() +".jpg").into(binding.imageView16);

        binding.imageViewEditTanggalAwalVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditVoucherRestoran.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                if(dayOfMonth < 10 && monthOfYear < 10){
                                    binding.textEditTanggalAwalVouch.setText("0" + dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year);
                                }
                                else if(dayOfMonth < 10){
                                    binding.textEditTanggalAwalVouch.setText("0" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                                else if(monthOfYear < 10){
                                    binding.textEditTanggalAwalVouch.setText(dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year);
                                }
                                else{
                                    binding.textEditTanggalAwalVouch.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        binding.imageViewEditTanggalAkhirVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditVoucherRestoran.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                if(dayOfMonth < 10 && monthOfYear < 10){
                                    binding.textEditTanggalAkhirVouch.setText("0" + dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year);
                                }
                                else if(dayOfMonth < 10){
                                    binding.textEditTanggalAkhirVouch.setText("0" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                                else if(monthOfYear < 10){
                                    binding.textEditTanggalAkhirVouch.setText(dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year);
                                }
                                else{
                                    binding.textEditTanggalAkhirVouch.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        binding.spinnerEditJenisPotongan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(binding.spinnerEditJenisPotongan.getSelectedItem().toString().equalsIgnoreCase("Persen")){
                    binding.textEditPersenDiskon.setVisibility(View.VISIBLE);
                    binding.textViewEditPersen.setVisibility(View.VISIBLE);
                }
                else{
                    binding.textEditPersenDiskon.setVisibility(View.INVISIBLE);
                    binding.textViewEditPersen.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.buttonUploadEditBannerPromosi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
            }
        });

        binding.buttonSubmitEditVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idvouch = binding.textEditKodeVoucher.getText().toString();
                String jenis_voucher = "Diskon";
                String jenis_potongan = binding.spinnerEditJenisPotongan.getSelectedItem().toString();
                String minimum_pembelian = binding.textEditMinimumPembelian.getText().toString();
                String max_pot = binding.textEditJumlahPotongan.getText().toString();
                String kuota = binding.textEditKuotaVoucher.getText().toString();
                String tanggal_awal = binding.textEditTanggalAwalVouch.getText().toString();
                String tanggal_akhir = binding.textEditTanggalAkhirVouch.getText().toString();

                if(jenis_potongan.equalsIgnoreCase("Persen")){
                    if(binding.textEditPersenDiskon.getText().toString().equalsIgnoreCase("")){
                        Toast.makeText(EditVoucherRestoran.this, "Harap diisikan jumlah persenan diskonnya", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(adagbr == false){
                            VoucherRestoran v = new VoucherRestoran(idvouch, jenis_voucher, jenis_potongan, binding.textEditPersenDiskon.getText().toString(), kuota, minimum_pembelian, max_pot, tanggal_awal, tanggal_akhir);
                            EditVoucher(v);
                            Intent i = new Intent(EditVoucherRestoran.this, ListVoucherRestoran.class);
                            i.putExtra("id_restoran", idresto);
                            startActivity(i);
                        }
                        else{
                            VoucherRestoran v = new VoucherRestoran(idvouch, jenis_voucher, jenis_potongan, binding.textEditPersenDiskon.getText().toString(), kuota, minimum_pembelian, max_pot, tanggal_awal, tanggal_akhir);
                            EditVoucher2(v);
                            Intent i = new Intent(EditVoucherRestoran.this, ListVoucherRestoran.class);
                            i.putExtra("id_restoran", idresto);
                            startActivity(i);
                        }
                    }
                }
                else{
                    if (adagbr == false) {
                        VoucherRestoran v = new VoucherRestoran(idvouch, jenis_voucher, jenis_potongan, "0", kuota, minimum_pembelian, max_pot, tanggal_awal, tanggal_akhir);
                        EditVoucher(v);
                        Intent i = new Intent(EditVoucherRestoran.this, ListVoucherRestoran.class);
                        i.putExtra("id_restoran", idresto);
                        startActivity(i);
                    }
                    else{
                        VoucherRestoran v = new VoucherRestoran(idvouch, jenis_voucher, jenis_potongan, "0", kuota, minimum_pembelian, max_pot, tanggal_awal, tanggal_akhir);
                        EditVoucher2(v);
                        Intent i = new Intent(EditVoucherRestoran.this, ListVoucherRestoran.class);
                        i.putExtra("id_restoran", idresto);
                        startActivity(i);
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

    private void EditVoucher(VoucherRestoran voucher){
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
                            Toast.makeText(EditVoucherRestoran.this, message, Toast.LENGTH_SHORT).show();
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
                params.put("function", "EditVoucher");
                params.put("kode_voucher", voucher.getKode_voucher());
                params.put("jenis_voucher", voucher.getJenis_voucher());
                params.put("jenis_potongan", voucher.getJenis_potongan());
                params.put("jumlah_diskon", voucher.getJumlah_diskon());
                params.put("kuota_voucher", voucher.getKuota_voucher());
                params.put("minimum_pembelian", voucher.getMinimum_pembelian());
                params.put("maksimal_potongan", voucher.getMaksimal_potongan());
                params.put("tanggal_awal", voucher.getTanggal_awal());
                params.put("tanggal_berakhir", voucher.getTanggal_akhir());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(EditVoucherRestoran.this);
        requestQueue.add(stringRequest);
    }

    private void EditVoucher2(VoucherRestoran voucher){
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
                            Toast.makeText(EditVoucherRestoran.this, message, Toast.LENGTH_SHORT).show();
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
                params.put("function", "EditVoucher");
                params.put("kode_voucher", voucher.getKode_voucher());
                params.put("jenis_voucher", voucher.getJenis_voucher());
                params.put("jenis_potongan", voucher.getJenis_potongan());
                params.put("jumlah_diskon", voucher.getJumlah_diskon());
                params.put("kuota_voucher", voucher.getKuota_voucher());
                params.put("minimum_pembelian", voucher.getMinimum_pembelian());
                params.put("maksimal_potongan", voucher.getMaksimal_potongan());
                params.put("tanggal_awal", voucher.getTanggal_awal());
                params.put("tanggal_berakhir", voucher.getTanggal_akhir());
                params.put("image_banner", base64imagebanner);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(EditVoucherRestoran.this);
        requestQueue.add(stringRequest);
    }
}
