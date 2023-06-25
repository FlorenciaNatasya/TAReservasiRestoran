package com.example.tugasakhir;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.FragmentLaporanPendapatanHarianRestoranBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLaporanPendapatanHarianRestoran#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLaporanPendapatanHarianRestoran extends Fragment {

    FragmentLaporanPendapatanHarianRestoranBinding binding;
    int year, month, day;
    String hari, bulan, tahun;
    int total;
    ArrayList<HeaderTransaksi> arrHT = new ArrayList<>();
    ArrayList<HeaderTransaksi> arrHT2 = new ArrayList<>();

    private static final String ARG_PARAM1 = "id_restoran";
    private String id_restoran;


    public FragmentLaporanPendapatanHarianRestoran() {
        // Required empty public constructor
    }

    public static FragmentLaporanPendapatanHarianRestoran newInstance(String id_restoran) {
        FragmentLaporanPendapatanHarianRestoran fragment = new FragmentLaporanPendapatanHarianRestoran();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, id_restoran);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id_restoran = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_laporan_pendapatan_harian_restoran, container, false);
        binding = FragmentLaporanPendapatanHarianRestoranBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        String date = dateFormat.format(c.getTime());
        binding.textViewTanggalYangDitentukan.setText(date);

        binding.imageView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                if(dayOfMonth < 10){
                                    if((monthOfYear + 1) == 1){
                                        binding.textViewTanggalYangDitentukan.setText("0" + dayOfMonth + " Januari " + year);
                                        hari = "0" + dayOfMonth;
                                        bulan = "01";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 2){
                                        binding.textViewTanggalYangDitentukan.setText("0" + dayOfMonth + " Februari " + year);
                                        hari = "0" + dayOfMonth;
                                        bulan = "02";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 3){
                                        binding.textViewTanggalYangDitentukan.setText("0" + dayOfMonth + " Maret " + year);
                                        hari = "0" + dayOfMonth;
                                        bulan = "03";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 4){
                                        binding.textViewTanggalYangDitentukan.setText("0" + dayOfMonth + " April " + year);
                                        hari = "0" + dayOfMonth;
                                        bulan = "04";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 5){
                                        binding.textViewTanggalYangDitentukan.setText("0" + dayOfMonth + " Mei " + year);
                                        hari = "0" + dayOfMonth;
                                        bulan = "05";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 6){
                                        binding.textViewTanggalYangDitentukan.setText("0" + dayOfMonth + " Juni " + year);
                                        hari = "0" + dayOfMonth;
                                        bulan = "06";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 7){
                                        binding.textViewTanggalYangDitentukan.setText("0" + dayOfMonth + " Juli " + year);
                                        hari = "0" + dayOfMonth;
                                        bulan = "07";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 8){
                                        binding.textViewTanggalYangDitentukan.setText("0" + dayOfMonth + " Agustus " + year);
                                        hari = "0" + dayOfMonth;
                                        bulan = "08";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 9){
                                        binding.textViewTanggalYangDitentukan.setText("0" + dayOfMonth + " September " + year);
                                        hari = "0" + dayOfMonth;
                                        bulan = "09";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 10){
                                        binding.textViewTanggalYangDitentukan.setText("0" + dayOfMonth + " Oktober " + year);
                                        hari = "0" + dayOfMonth;
                                        bulan = "10";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 11){
                                        binding.textViewTanggalYangDitentukan.setText("0" + dayOfMonth + " November " + year);
                                        hari = "0" + dayOfMonth;
                                        bulan = "11";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 12){
                                        binding.textViewTanggalYangDitentukan.setText("0" + dayOfMonth + " Desember " + year);
                                        hari = "0" + dayOfMonth;
                                        bulan = "12";
                                        tahun = String.valueOf(year);
                                    }
                                }
                                else{
                                    if((monthOfYear + 1) == 1){
                                        binding.textViewTanggalYangDitentukan.setText(dayOfMonth + " Januari " + year);
                                        hari = String.valueOf(dayOfMonth);
                                        bulan = "01";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 2){
                                        binding.textViewTanggalYangDitentukan.setText(dayOfMonth + " Februari " + year);
                                        hari = String.valueOf(dayOfMonth);
                                        bulan = "02";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 3){
                                        binding.textViewTanggalYangDitentukan.setText(dayOfMonth + " Maret " + year);
                                        hari = String.valueOf(dayOfMonth);
                                        bulan = "03";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 4){
                                        binding.textViewTanggalYangDitentukan.setText(dayOfMonth + " April " + year);
                                        hari = String.valueOf(dayOfMonth);
                                        bulan = "04";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 5){
                                        binding.textViewTanggalYangDitentukan.setText(dayOfMonth + " Mei " + year);
                                        hari = String.valueOf(dayOfMonth);
                                        bulan = "05";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 6){
                                        binding.textViewTanggalYangDitentukan.setText(dayOfMonth + " Juni " + year);
                                        hari = String.valueOf(dayOfMonth);
                                        bulan = "06";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 7){
                                        binding.textViewTanggalYangDitentukan.setText(dayOfMonth + " Juli " + year);
                                        hari = String.valueOf(dayOfMonth);
                                        bulan = "07";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 8){
                                        binding.textViewTanggalYangDitentukan.setText(dayOfMonth + " Agustus " + year);
                                        hari = String.valueOf(dayOfMonth);
                                        bulan = "08";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 9){
                                        binding.textViewTanggalYangDitentukan.setText(dayOfMonth + " September " + year);
                                        hari = String.valueOf(dayOfMonth);
                                        bulan = "09";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 10){
                                        binding.textViewTanggalYangDitentukan.setText(dayOfMonth + " Oktober " + year);
                                        hari = String.valueOf(dayOfMonth);
                                        bulan = "10";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 11){
                                        binding.textViewTanggalYangDitentukan.setText(dayOfMonth + " November " + year);
                                        hari = String.valueOf(dayOfMonth);
                                        bulan = "11";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 12){
                                        binding.textViewTanggalYangDitentukan.setText(dayOfMonth + " Desember " + year);
                                        hari = String.valueOf(dayOfMonth);
                                        bulan = "12";
                                        tahun = String.valueOf(year);
                                    }
                                }
                            }
                        }, year, month, day);
//                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        setRVLaporanPendapatanHarian();

        binding.buttonSetTanggalLaporanPendapatanRestoran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRVLaporanPendapatanHarianSetelahSetTanggal();
            }
        });
    }

    private void setRVLaporanPendapatanHarian(){
        binding.rvLaporanPendapatanHarianRestoran.setHasFixedSize(true);
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
                                arrHT.add(ht);
                            }

                            for (int i = 0; i < arrHT.size(); i++) {
                                if(arrHT.get(i).getId_restoran().equalsIgnoreCase(id_restoran) && arrHT.get(i).getStatus_pesanan().equalsIgnoreCase("Selesai")){
                                    HeaderTransaksi ht = new HeaderTransaksi(arrHT.get(i).getId_htrans_reservasi(), arrHT.get(i).getId_customer(), arrHT.get(i).getNama_client(), arrHT.get(i).getNomor_telepon_client(), arrHT.get(i).getTanggal_reservasi(), arrHT.get(i).getJam_reservasi(), arrHT.get(i).getJumlah_orang(), arrHT.get(i).getNote_transaksi(), arrHT.get(i).getTotal_harga(), arrHT.get(i).getId_restoran(), arrHT.get(i).getStatus_pesanan());
                                    arrHT2.add(ht);
                                }
                            }

                            int totalharian = 0;
                            int totaltrans = 0;
                            ArrayList<HeaderTransaksi> arrHT3 = new ArrayList<>();
                            for (int i = 0; i < arrHT2.size(); i++) {
                                if(arrHT3.size() <= 0){
                                    HeaderTransaksi ht = new HeaderTransaksi("1", arrHT2.get(i).getId_customer(), arrHT2.get(i).getNama_client(), arrHT2.get(i).getNomor_telepon_client(), arrHT2.get(i).getTanggal_reservasi(), arrHT2.get(i).getJam_reservasi(), arrHT2.get(i).getJumlah_orang(), arrHT2.get(i).getNote_transaksi(), arrHT2.get(i).getTotal_harga(), arrHT2.get(i).getId_restoran(), arrHT2.get(i).getStatus_pesanan());
                                    arrHT3.add(ht);
                                }
                                else{
                                    boolean adakmbr = false;
//                                    int idxJ = 0;
                                    for (int j = 0; j < arrHT3.size(); j++) {
                                        if(arrHT2.get(i).getTanggal_reservasi().equalsIgnoreCase(arrHT3.get(j).getTanggal_reservasi())){
                                            totalharian = Integer.parseInt(arrHT3.get(j).getTotal_harga());
                                            totalharian += Integer.parseInt(arrHT2.get(i).getTotal_harga());
                                            arrHT3.get(j).setTotal_harga(String.valueOf(totalharian));
                                            totaltrans = Integer.parseInt(arrHT3.get(j).getId_htrans_reservasi()) + 1;
                                            arrHT3.get(j).setId_htrans_reservasi(String.valueOf(totaltrans));
                                            adakmbr = true;
//                                            idxJ = j;
                                        }
//                                        else{
//                                            HeaderTransaksi ht = new HeaderTransaksi(arrHT2.get(i).getId_htrans_reservasi(), arrHT2.get(i).getId_customer(), arrHT2.get(i).getNama_client(), arrHT2.get(i).getNomor_telepon_client(), arrHT2.get(i).getTanggal_reservasi(), arrHT2.get(i).getJam_reservasi(), arrHT2.get(i).getJumlah_orang(), arrHT2.get(i).getNote_transaksi(), arrHT2.get(i).getTotal_harga(), arrHT2.get(i).getId_restoran(), arrHT2.get(i).getStatus_pesanan());
//                                            arrHT3.add(ht);
//                                        }
                                    }
//                                    if(idxJ >= 0){
//                                        totalharian = Integer.parseInt(arrHT3.get(idxJ).getTotal_harga());
//                                        totalharian += Integer.parseInt(arrHT2.get(i).getTotal_harga());
//                                        arrHT3.get(idxJ).setTotal_harga(String.valueOf(totalharian));
//                                        totaltrans = Integer.parseInt(arrHT3.get(idxJ).getId_htrans_reservasi()) + 1;
//                                        arrHT3.get(idxJ).setId_htrans_reservasi(String.valueOf(totaltrans));
//                                    }
                                   if(adakmbr == false){
                                        HeaderTransaksi ht = new HeaderTransaksi("1", arrHT2.get(i).getId_customer(), arrHT2.get(i).getNama_client(), arrHT2.get(i).getNomor_telepon_client(), arrHT2.get(i).getTanggal_reservasi(), arrHT2.get(i).getJam_reservasi(), arrHT2.get(i).getJumlah_orang(), arrHT2.get(i).getNote_transaksi(), arrHT2.get(i).getTotal_harga(), arrHT2.get(i).getId_restoran(), arrHT2.get(i).getStatus_pesanan());
                                        arrHT3.add(ht);
                                    }
                                }
                            }

                            binding.rvLaporanPendapatanHarianRestoran.setLayoutManager(new LinearLayoutManager(getActivity()));
                            LaporanPendapatanRestoranAdapter adapter = new LaporanPendapatanRestoranAdapter(arrHT3);
                            binding.rvLaporanPendapatanHarianRestoran.setAdapter(adapter);

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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void setRVLaporanPendapatanHarianSetelahSetTanggal(){
        arrHT.clear();
        arrHT2.clear();
        binding.rvLaporanPendapatanHarianRestoran.setHasFixedSize(true);
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
                                arrHT.add(ht);
                            }

                            for (int i = 0; i < arrHT.size(); i++) {
                                if(arrHT.get(i).getId_restoran().equalsIgnoreCase(id_restoran) && arrHT.get(i).getStatus_pesanan().equalsIgnoreCase("Selesai") && arrHT.get(i).getTanggal_reservasi().substring(0,2).equalsIgnoreCase(hari) && arrHT.get(i).getTanggal_reservasi().substring(3,5).equalsIgnoreCase(bulan) && arrHT.get(i).getTanggal_reservasi().substring(6).equalsIgnoreCase(tahun)){
                                    HeaderTransaksi ht = new HeaderTransaksi(arrHT.get(i).getId_htrans_reservasi(), arrHT.get(i).getId_customer(), arrHT.get(i).getNama_client(), arrHT.get(i).getNomor_telepon_client(), arrHT.get(i).getTanggal_reservasi(), arrHT.get(i).getJam_reservasi(), arrHT.get(i).getJumlah_orang(), arrHT.get(i).getNote_transaksi(), arrHT.get(i).getTotal_harga(), arrHT.get(i).getId_restoran(), arrHT.get(i).getStatus_pesanan());
                                    arrHT2.add(ht);
                                    total += Integer.parseInt(arrHT.get(i).getTotal_harga());
                                }
                            }

                            int totalharian = 0;
                            int totaltrans = 0;
                            ArrayList<HeaderTransaksi> arrHT3 = new ArrayList<>();
                            for (int i = 0; i < arrHT2.size(); i++) {
                                if(arrHT3.size() <= 0){
                                    HeaderTransaksi ht = new HeaderTransaksi(arrHT2.get(i).getId_htrans_reservasi(), arrHT2.get(i).getId_customer(), arrHT2.get(i).getNama_client(), arrHT2.get(i).getNomor_telepon_client(), arrHT2.get(i).getTanggal_reservasi(), arrHT2.get(i).getJam_reservasi(), arrHT2.get(i).getJumlah_orang(), arrHT2.get(i).getNote_transaksi(), arrHT2.get(i).getTotal_harga(), arrHT2.get(i).getId_restoran(), arrHT2.get(i).getStatus_pesanan());
                                    arrHT3.add(ht);
                                    totaltrans += 1;
                                }
                                else{
                                    for (int j = 0; j < arrHT3.size(); j++) {
                                        if(arrHT2.get(i).getTanggal_reservasi().equalsIgnoreCase(arrHT3.get(j).getTanggal_reservasi())){
                                            totalharian = Integer.parseInt(arrHT3.get(j).getTotal_harga());
                                            totalharian += Integer.parseInt(arrHT2.get(i).getTotal_harga());
                                            arrHT3.get(j).setTotal_harga(String.valueOf(totalharian));
                                            totaltrans += 1;
                                            arrHT3.get(j).setId_htrans_reservasi(String.valueOf(totaltrans));
                                        }
                                        else{
                                            HeaderTransaksi ht = new HeaderTransaksi(arrHT2.get(i).getId_htrans_reservasi(), arrHT2.get(i).getId_customer(), arrHT2.get(i).getNama_client(), arrHT2.get(i).getNomor_telepon_client(), arrHT2.get(i).getTanggal_reservasi(), arrHT2.get(i).getJam_reservasi(), arrHT2.get(i).getJumlah_orang(), arrHT2.get(i).getNote_transaksi(), arrHT2.get(i).getTotal_harga(), arrHT2.get(i).getId_restoran(), arrHT2.get(i).getStatus_pesanan());
                                            arrHT3.add(ht);
                                        }
                                    }
                                }
                            }

                            binding.rvLaporanPendapatanHarianRestoran.setLayoutManager(new LinearLayoutManager(getActivity()));
                            LaporanPendapatanRestoranAdapter adapter = new LaporanPendapatanRestoranAdapter(arrHT3);
                            binding.rvLaporanPendapatanHarianRestoran.setAdapter(adapter);

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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public String formatRupiah(int number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}