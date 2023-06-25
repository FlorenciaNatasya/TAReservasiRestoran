package com.example.tugasakhir;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.ActivityFormPilihMakananBinding;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FormPilihMakanan extends AppCompatActivity implements GestureDetector.OnGestureListener {

    ActivityFormPilihMakananBinding binding;
    ArrayList<MenuMakanan> arrMenu = new ArrayList<>();
    ArrayList<HeaderTransaksi> arrRes = new ArrayList<>();
    Reservation reservation;
    String idresto = "1";
    String idhtrans;
    int total;
    String totalharga;
    ArrayList<DetailTransaksi> arrPil = new ArrayList<>();
    PilihMakananAdapter adapter;
    GestureDetector gd;
    String idcust = "1";
    ArrayList<ClaimVoucherClass> arrVouch = new ArrayList<>();
    ArrayList<ClaimVoucherClass> arrVouch2 = new ArrayList<>();
    ArrayList<VoucherRestoran> arrVR = new ArrayList<>();
    ArrayList<VoucherRestoran> arrVR2 = new ArrayList<>();
    ArrayList<String> arrSVouch = new ArrayList<>();
    ArrayList<String> arrKodeVouch = new ArrayList<>();
    boolean adapotongan = false;
    int jmlhpotongan = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pilih_makanan);

        try
        {
//            this.getSupportActionBar().hide();
            this.getSupportActionBar().setTitle("Form Pilih Makanan");
        }
        catch (NullPointerException e){}

        binding = ActivityFormPilihMakananBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent iget = getIntent();
        if(iget.hasExtra("datareservasi")){
            reservation = iget.getParcelableExtra("datareservasi");
        }
        if(iget.hasExtra("id_restoran")){
            idresto = iget.getStringExtra("id_restoran");
        }
        if(iget.hasExtra("id_customer")){
            idcust = iget.getStringExtra("id_customer");
        }

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
                            for (int i = 0; i <= arrtemp.length()-1; i++) {
                                JSONObject tempMenu = arrtemp.getJSONObject(i);
                                HeaderTransaksi ht = new HeaderTransaksi(
                                        tempMenu.getString("id_htrans_reservasi"),
                                        tempMenu.getString("id_customer"),
                                        tempMenu.getString("nama_client"),
                                        tempMenu.getString("nomor_telepon_client"),
                                        tempMenu.getString("tanggal_reservasi"),
                                        tempMenu.getString("jam_reservasi"),
                                        tempMenu.getString("jumlah_orang"),
                                        tempMenu.getString("note_transaksi"),
                                        tempMenu.getString("total_harga"),
                                        tempMenu.getString("id_restoran"),
                                        tempMenu.getString("status_pesanan"));
                                arrRes.add(ht);
                            }

                            for (int i = 0; i < arrRes.size(); i++) {
                                if(arrRes.get(i).getId_customer().equals(reservation.getId_customer()) && arrRes.get(i).getNama_client().equals(reservation.getNama_client()) && arrRes.get(i).getNomor_telepon_client().equals(reservation.getNomor_telepon()) && arrRes.get(i).getTanggal_reservasi().equals(reservation.getTanggal_reservasi()) && arrRes.get(i).getJam_reservasi().equals(reservation.getJam_reservasi()) && arrRes.get(i).getJumlah_orang().equals(reservation.getJumlah_orang()) && arrRes.get(i).getNote_transaksi().equals(reservation.getNote_transaksi())){
                                    idhtrans = arrRes.get(i).getId_htrans_reservasi();
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
                params.put("function", "selectHTransaksi");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(FormPilihMakanan.this);
        requestQueue.add(stringRequest);

        SetRVListMenuMakanan();
        selectVoucherClaimed();
        selectVoucherRestoran();

        binding.buttonSubmitPilihMakanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //masukin id restoran, status pesanan, total harga, dtrans
                for (int i = 0; i < arrPil.size(); i++) {
                    DetailTransaksi dt = new DetailTransaksi(arrPil.get(i).getNama_makanan(), arrPil.get(i).getJumlah_makanan(), arrPil.get(i).getHarga_makanan());
                    AddPesananMakanan(dt);
                }

                AlertDialog.Builder bulder = new AlertDialog.Builder(FormPilihMakanan.this);
                bulder.setMessage("Apakah anda ingin meminjam fasilitas restoran?").setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent in = new Intent(FormPilihMakanan.this, CustomerPilihFasilitas.class);
                        in.putExtra("id_customer", idcust);
                        in.putExtra("id_htrans", idhtrans);
                        in.putExtra("id_restoran", idresto);
                        startActivity(in);
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog a = bulder.create();
                a.show();
            }
        });

        gd = new GestureDetector(this);
    }

    private void SetRVListMenuMakanan(){
        binding.rvPilihanMenuMakanan.setHasFixedSize(true);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                getResources().getString(R.string.url),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONObject jsonobject = new JSONObject(response);
                            JSONArray arrtemp = jsonobject.getJSONArray("datamenu");
                            for (int j = 0; j <= arrtemp.length()-1; j++) {
                                JSONObject tempMenu = arrtemp.getJSONObject(j);
                                MenuMakanan menu = new MenuMakanan(
                                        tempMenu.getString("id_menu"),
                                        tempMenu.getString("nama_menu"),
                                        tempMenu.getString("kategori_menu"),
                                        tempMenu.getString("status_menu"),
                                        tempMenu.getString("deskripsi_menu"),
                                        tempMenu.getString("harga_menu"));
                                arrMenu.add(menu);
                            }
                            if (arrMenu != null) {
                                binding.rvPilihanMenuMakanan.setLayoutManager(new LinearLayoutManager(FormPilihMakanan.this));
                                adapter = new PilihMakananAdapter(arrMenu, FormPilihMakanan.this);
                                binding.rvPilihanMenuMakanan.setAdapter(adapter);
                                adapter.setOnItemClickCallback(new PilihMakananAdapter.OnItemClickCallback() {
                                    @Override
                                    public void OnPlusClicked(MenuMakanan menu, String jmlh) {
                                        total = adapter.TotalItem();
                                        binding.textViewTotalItemPesanan.setText(String.valueOf(total) + " porsi");

                                        totalharga = adapter.TotalHarga();
                                        binding.textViewTotalHargaMakanan.setText(adapter.formatRupiah(Integer.parseInt(totalharga)));

                                        if(arrPil == null){
                                            DetailTransaksi a = new DetailTransaksi(menu.getNama_menu(), jmlh, menu.getHarga_menu());
                                            arrPil.add(a);
                                        }
                                        else{
                                            boolean ada = false;
                                            for (int i = 0; i < arrPil.size(); i++) {
                                                if(arrPil.get(i).getNama_makanan().equals(menu.getNama_menu())){
                                                    arrPil.get(i).setJumlah_makanan(jmlh);
                                                    ada = true;
                                                }
                                            }

                                            if(ada==false){
                                                DetailTransaksi a = new DetailTransaksi(menu.getNama_menu(), jmlh, menu.getHarga_menu());
                                                arrPil.add(a);
                                            }
                                        }
                                    }

                                    @Override
                                    public void OnMinusClicked(MenuMakanan menu, String jmlh) {
                                        total = adapter.TotalItem();
                                        binding.textViewTotalItemPesanan.setText(String.valueOf(total) + " porsi");

                                        totalharga = adapter.TotalHarga();
                                        binding.textViewTotalHargaMakanan.setText(adapter.formatRupiah(Integer.parseInt(totalharga)));

                                        if(arrPil != null){
                                            for (int i = 0; i < arrPil.size(); i++) {
                                                if(arrPil.get(i).getNama_makanan().equals(menu.getNama_menu())){
                                                    arrPil.get(i).setJumlah_makanan(jmlh);
                                                }
                                            }
                                        }
                                    }
                                });

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
                params.put("function", "selectAllMenuMakanan");
                params.put("id_restoran", idresto);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(FormPilihMakanan.this);
        requestQueue.add(stringRequest);
    }

    private void AddPesananMakanan(DetailTransaksi dt){
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
                            Toast.makeText(FormPilihMakanan.this, message, Toast.LENGTH_SHORT).show();
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
                params.put("function", "AddPesananMakanan");
                params.put("id_htrans", idhtrans);
                params.put("nama_makanan", dt.getNama_makanan());
                params.put("jumlah_makanan", dt.getJumlah_makanan());
                if(adapotongan == false){
                    params.put("total_harga", adapter.TotalPesanan());
                }
                else{
                    int hslakhir = Integer.parseInt(adapter.TotalPesanan()) - jmlhpotongan;
                    params.put("total_harga", String.valueOf(hslakhir));
                }
                params.put("id_restoran", idresto);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(FormPilihMakanan.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onDown(@NonNull MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(@NonNull MotionEvent downEvent, @NonNull MotionEvent moveEvent, float velocityX, float velocityY) {
        boolean result = false;
        float diffY = moveEvent.getY() - downEvent.getY();
        float diffX = moveEvent.getX() - downEvent.getX();
        if(Math.abs(diffX) < Math.abs(diffY)){
            if(Math.abs(diffY) > 100 && Math.abs(velocityY) > 100){
                if(diffY < 0){
                    binding.rvPilihanMenuMakanan.setVisibility(View.INVISIBLE);
                    binding.rvRincianPesananMakanan.setVisibility(View.VISIBLE);
                    binding.textView67.setVisibility(View.VISIBLE);
                    binding.textView69.setVisibility(View.VISIBLE);
                    binding.textView75.setVisibility(View.VISIBLE);
                    binding.spinnerVoucherRestoran.setVisibility(View.VISIBLE);
                    binding.textViewRincianJmlhMakanan.setVisibility(View.VISIBLE);
                    binding.textViewAdminTransaksi.setVisibility(View.VISIBLE);
                    binding.textViewRincianJmlhMakanan.setText(adapter.formatRupiah(Integer.parseInt(adapter.TotalPesanan())));
                    binding.textViewAdminTransaksi.setText(adapter.BiayaAdmin());
                    binding.textView76.setVisibility(View.VISIBLE);
                    binding.textViewJumlahPotonganVoucher.setVisibility(View.VISIBLE);

                    binding.rvRincianPesananMakanan.setHasFixedSize(true);
                    binding.rvRincianPesananMakanan.setLayoutManager(new LinearLayoutManager(FormPilihMakanan.this));
                    ListMakananTerpilihAdapter adapter2 = new ListMakananTerpilihAdapter(arrPil, FormPilihMakanan.this);
                    binding.rvRincianPesananMakanan.setAdapter(adapter2);

                    for (int i = 0; i < arrVR2.size(); i++) {
                        for (int j = 0; j < arrVouch2.size(); j++) {
                            if(arrVR2.get(i).getKode_voucher().equals(arrVouch2.get(j).getKode_voucher())){
                                if(arrVR2.get(i).getJenis_potongan().equalsIgnoreCase("Persen")){
                                    arrSVouch.add("Diskon " + arrVR2.get(i).getJumlah_diskon() + "%  Min.Blj Rp." + arrVR2.get(i).getMinimum_pembelian() + " Max.Pot Rp." + arrVR2.get(i).getMaksimal_potongan());
                                }
                                else{
                                    arrSVouch.add("Diskon Rp." + arrVR2.get(i).getMaksimal_potongan() + " Min.Blj Rp." + arrVR2.get(i).getMinimum_pembelian());
                                }
                                arrKodeVouch.add(arrVR2.get(i).getKode_voucher());
                            }
                        }
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                            (FormPilihMakanan.this, android.R.layout.simple_spinner_item, arrSVouch);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                            .simple_spinner_dropdown_item);
                    binding.spinnerVoucherRestoran.setAdapter(spinnerArrayAdapter);

                    binding.spinnerVoucherRestoran.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if(binding.spinnerVoucherRestoran.getSelectedItem().toString().contains("%")){
//                                String selected = binding.spinnerVoucherRestoran.getSelectedItem().toString();
//                                int jmlhdisc = Integer.parseInt(selected.substring(7,9));
//                                int jmlhpot = Integer.parseInt(adapter.TotalHarga()) * 100 / jmlhdisc;
//                                binding.textViewJumlahPotonganVoucher.setText("- Rp." + String.valueOf(jmlhpot));
//                                int grandtotal = Integer.parseInt(adapter.TotalHarga()) - jmlhpot;
//                                binding.textViewTotalHargaMakanan.setText("Rp" + String.valueOf(grandtotal));
                                for (int j = 0; j < arrVR2.size(); j++) {
                                    if(arrVR2.get(j).getKode_voucher().equals(arrKodeVouch.get(i)) && (Integer.parseInt(adapter.TotalPesanan()) >= Integer.parseInt(arrVR2.get(j).getMinimum_pembelian()))){
                                        int jmlhdisc = Integer.parseInt(arrVR2.get(j).getJumlah_diskon());
                                        int jmlhpot = Integer.parseInt(adapter.TotalHarga()) * 100 / jmlhdisc;
                                        if(jmlhpot > Integer.parseInt(arrVR2.get(j).getMaksimal_potongan())){
                                            binding.textViewJumlahPotonganVoucher.setText("- " + adapter.formatRupiah(Integer.parseInt(arrVR2.get(j).getMaksimal_potongan())));
                                            int grandtotal = Integer.parseInt(adapter.TotalHarga()) - Integer.parseInt(arrVR2.get(j).getMaksimal_potongan());
                                            binding.textViewTotalHargaMakanan.setText(adapter.formatRupiah(grandtotal));
                                            adapotongan = true;
                                            jmlhpotongan = Integer.parseInt(arrVR2.get(j).getMaksimal_potongan());
                                        }
                                        else{
                                            binding.textViewJumlahPotonganVoucher.setText("- " + adapter.formatRupiah(jmlhpot));
                                            int grandtotal = Integer.parseInt(adapter.TotalHarga()) - jmlhpot;
                                            binding.textViewTotalHargaMakanan.setText(adapter.formatRupiah(grandtotal));
                                            adapotongan = true;
                                            jmlhpotongan = jmlhpot;
                                        }
                                    }
                                }
                            }
                            else{
                                for (int j = 0; j < arrVR2.size(); j++) {
                                    if(arrVR2.get(j).getKode_voucher().equals(arrKodeVouch.get(i)) && (Integer.parseInt(adapter.TotalPesanan()) >= Integer.parseInt(arrVR2.get(j).getMinimum_pembelian()))){
                                        int jmlhpot = Integer.parseInt(arrVR2.get(j).getMaksimal_potongan());
                                        binding.textViewJumlahPotonganVoucher.setText("- " + adapter.formatRupiah(jmlhpot));
                                        int grandtotal = Integer.parseInt(adapter.TotalHarga()) - jmlhpot;
                                        binding.textViewTotalHargaMakanan.setText(adapter.formatRupiah(grandtotal));
                                        adapotongan = true;
                                        jmlhpotongan = jmlhpot;
                                    }
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
                else{
                    binding.rvPilihanMenuMakanan.setVisibility(View.VISIBLE);
                    binding.rvRincianPesananMakanan.setVisibility(View.INVISIBLE);
                    binding.textView67.setVisibility(View.INVISIBLE);
                    binding.textView69.setVisibility(View.INVISIBLE);
                    binding.textView75.setVisibility(View.INVISIBLE);
                    binding.spinnerVoucherRestoran.setVisibility(View.INVISIBLE);
                    binding.textViewRincianJmlhMakanan.setVisibility(View.INVISIBLE);
                    binding.textViewAdminTransaksi.setVisibility(View.INVISIBLE);
                    binding.textView76.setVisibility(View.INVISIBLE);
                    binding.textViewJumlahPotonganVoucher.setVisibility(View.INVISIBLE);
                }
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gd.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private void selectVoucherClaimed(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                getResources().getString(R.string.url),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray arrtemp = jsonObject.getJSONArray("dataClaimVoucher");
                            for (int j = 0; j <= arrtemp.length() - 1; j++) {
                                JSONObject tempMenu = arrtemp.getJSONObject(j);
                                ClaimVoucherClass vouch = new ClaimVoucherClass(
                                        tempMenu.getString("kode_voucher"),
                                        tempMenu.getString("id_customer"));
                                arrVouch.add(vouch);
                            }

                            for (int i = 0; i < arrVouch.size(); i++) {
                                if(arrVouch.get(i).getId_customer().equalsIgnoreCase(idcust)){
                                    ClaimVoucherClass cvc = new ClaimVoucherClass(arrVouch.get(i).getKode_voucher(), arrVouch.get(i).getId_customer());
                                    arrVouch2.add(cvc);
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
                params.put("function", "selectVoucherYangDiclaim");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(FormPilihMakanan.this);
        requestQueue.add(stringRequest);
    }

    private void selectVoucherRestoran(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://10.0.2.2/TA-service/master.php",
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
                                arrVR.add(vouch);
                            }

                            for (int i = 0; i < arrVR.size(); i++) {
                                if(arrVR.get(i).getId_restoran().equals(idresto) && arrVR.get(i).getStatus_voucher().equalsIgnoreCase("Ada")){
                                    VoucherRestoran v = new VoucherRestoran(arrVR.get(i).getId_voucher(), arrVR.get(i).getKode_voucher(), arrVR.get(i).getJenis_voucher(), arrVR.get(i).getJenis_potongan(), arrVR.get(i).getJumlah_diskon(), arrVR.get(i).getKuota_voucher(), arrVR.get(i).getMinimum_pembelian(), arrVR.get(i).getMaksimal_potongan(), arrVR.get(i).getTanggal_awal(), arrVR.get(i).getTanggal_akhir(), arrVR.get(i).getStatus_voucher(), arrVR.get(i).getId_restoran());
                                    arrVR2.add(v);
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
        RequestQueue requestQueue = Volley.newRequestQueue(FormPilihMakanan.this);
        requestQueue.add(stringRequest);
    }
}