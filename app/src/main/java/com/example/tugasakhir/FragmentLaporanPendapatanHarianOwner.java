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
import com.example.tugasakhir.databinding.FragmentLaporanPendapatanHarianOwnerBinding;
import com.example.tugasakhir.databinding.FragmentLaporanPendapatanHarianRestoranBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLaporanPendapatanHarianOwner#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLaporanPendapatanHarianOwner extends Fragment {

    FragmentLaporanPendapatanHarianOwnerBinding binding;
    int year, month, day;
    String hari, bulan, tahun;
    int total;
    ArrayList<HeaderTransaksi> arrHT = new ArrayList<>();
    ArrayList<HeaderTransaksi> arrHT2 = new ArrayList<>();

    public FragmentLaporanPendapatanHarianOwner() {
        // Required empty public constructor
    }

    public static FragmentLaporanPendapatanHarianOwner newInstance() {
        FragmentLaporanPendapatanHarianOwner fragment = new FragmentLaporanPendapatanHarianOwner();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_laporan_pendapatan_harian_owner, container, false);
        binding = FragmentLaporanPendapatanHarianOwnerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        String date = dateFormat.format(c.getTime());
        binding.textViewTanggalYangDitentukanOwner.setText(date);

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
                                        binding.textViewTanggalYangDitentukanOwner.setText("0" + dayOfMonth + " Januari " + year);
                                        hari = "0" + dayOfMonth;
                                        bulan = "01";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 2){
                                        binding.textViewTanggalYangDitentukanOwner.setText("0" + dayOfMonth + " Februari " + year);
                                        hari = "0" + dayOfMonth;
                                        bulan = "02";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 3){
                                        binding.textViewTanggalYangDitentukanOwner.setText("0" + dayOfMonth + " Maret " + year);
                                        hari = "0" + dayOfMonth;
                                        bulan = "03";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 4){
                                        binding.textViewTanggalYangDitentukanOwner.setText("0" + dayOfMonth + " April " + year);
                                        hari = "0" + dayOfMonth;
                                        bulan = "04";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 5){
                                        binding.textViewTanggalYangDitentukanOwner.setText("0" + dayOfMonth + " Mei " + year);
                                        hari = "0" + dayOfMonth;
                                        bulan = "05";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 6){
                                        binding.textViewTanggalYangDitentukanOwner.setText("0" + dayOfMonth + " Juni " + year);
                                        hari = "0" + dayOfMonth;
                                        bulan = "06";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 7){
                                        binding.textViewTanggalYangDitentukanOwner.setText("0" + dayOfMonth + " Juli " + year);
                                        hari = "0" + dayOfMonth;
                                        bulan = "07";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 8){
                                        binding.textViewTanggalYangDitentukanOwner.setText("0" + dayOfMonth + " Agustus " + year);
                                        hari = "0" + dayOfMonth;
                                        bulan = "08";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 9){
                                        binding.textViewTanggalYangDitentukanOwner.setText("0" + dayOfMonth + " September " + year);
                                        hari = "0" + dayOfMonth;
                                        bulan = "09";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 10){
                                        binding.textViewTanggalYangDitentukanOwner.setText("0" + dayOfMonth + " Oktober " + year);
                                        hari = "0" + dayOfMonth;
                                        bulan = "10";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 11){
                                        binding.textViewTanggalYangDitentukanOwner.setText("0" + dayOfMonth + " November " + year);
                                        hari = "0" + dayOfMonth;
                                        bulan = "11";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 12){
                                        binding.textViewTanggalYangDitentukanOwner.setText("0" + dayOfMonth + " Desember " + year);
                                        hari = "0" + dayOfMonth;
                                        bulan = "12";
                                        tahun = String.valueOf(year);
                                    }
                                }
                                else{
                                    if((monthOfYear + 1) == 1){
                                        binding.textViewTanggalYangDitentukanOwner.setText(dayOfMonth + " Januari " + year);
                                        hari = String.valueOf(dayOfMonth);
                                        bulan = "01";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 2){
                                        binding.textViewTanggalYangDitentukanOwner.setText(dayOfMonth + " Februari " + year);
                                        hari = String.valueOf(dayOfMonth);
                                        bulan = "02";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 3){
                                        binding.textViewTanggalYangDitentukanOwner.setText(dayOfMonth + " Maret " + year);
                                        hari = String.valueOf(dayOfMonth);
                                        bulan = "03";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 4){
                                        binding.textViewTanggalYangDitentukanOwner.setText(dayOfMonth + " April " + year);
                                        hari = String.valueOf(dayOfMonth);
                                        bulan = "04";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 5){
                                        binding.textViewTanggalYangDitentukanOwner.setText(dayOfMonth + " Mei " + year);
                                        hari = String.valueOf(dayOfMonth);
                                        bulan = "05";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 6){
                                        binding.textViewTanggalYangDitentukanOwner.setText(dayOfMonth + " Juni " + year);
                                        hari = String.valueOf(dayOfMonth);
                                        bulan = "06";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 7){
                                        binding.textViewTanggalYangDitentukanOwner.setText(dayOfMonth + " Juli " + year);
                                        hari = String.valueOf(dayOfMonth);
                                        bulan = "07";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 8){
                                        binding.textViewTanggalYangDitentukanOwner.setText(dayOfMonth + " Agustus " + year);
                                        hari = String.valueOf(dayOfMonth);
                                        bulan = "08";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 9){
                                        binding.textViewTanggalYangDitentukanOwner.setText(dayOfMonth + " September " + year);
                                        hari = String.valueOf(dayOfMonth);
                                        bulan = "09";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 10){
                                        binding.textViewTanggalYangDitentukanOwner.setText(dayOfMonth + " Oktober " + year);
                                        hari = String.valueOf(dayOfMonth);
                                        bulan = "10";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 11){
                                        binding.textViewTanggalYangDitentukanOwner.setText(dayOfMonth + " November " + year);
                                        hari = String.valueOf(dayOfMonth);
                                        bulan = "11";
                                        tahun = String.valueOf(year);
                                    }
                                    else if((monthOfYear + 1) == 12){
                                        binding.textViewTanggalYangDitentukanOwner.setText(dayOfMonth + " Desember " + year);
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

        binding.buttonSetTanggalLaporanPendapatanOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRVLaporanPendapatanHarianSetelahSetTanggal();
            }
        });
    }

    private void setRVLaporanPendapatanHarian(){
        binding.rvLaporanPendapatanHarianOwner.setHasFixedSize(true);
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
                                if(arrHT.get(i).getStatus_pesanan().equalsIgnoreCase("Selesai")){
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
                                    for (int j = 0; j < arrHT3.size(); j++) {
                                        if(arrHT2.get(i).getTanggal_reservasi().equalsIgnoreCase(arrHT3.get(j).getTanggal_reservasi())){
                                            totalharian = Integer.parseInt(arrHT3.get(j).getTotal_harga());
                                            totalharian += Integer.parseInt(arrHT2.get(i).getTotal_harga());
                                            arrHT3.get(j).setTotal_harga(String.valueOf(totalharian));
                                            totaltrans = Integer.parseInt(arrHT3.get(j).getId_htrans_reservasi()) + 1;
                                            arrHT3.get(j).setId_htrans_reservasi(String.valueOf(totaltrans));
                                            adakmbr = true;
                                        }
//                                        else{
//                                            HeaderTransaksi ht = new HeaderTransaksi(arrHT2.get(i).getId_htrans_reservasi(), arrHT2.get(i).getId_customer(), arrHT2.get(i).getNama_client(), arrHT2.get(i).getNomor_telepon_client(), arrHT2.get(i).getTanggal_reservasi(), arrHT2.get(i).getJam_reservasi(), arrHT2.get(i).getJumlah_orang(), arrHT2.get(i).getNote_transaksi(), arrHT2.get(i).getTotal_harga(), arrHT2.get(i).getId_restoran(), arrHT2.get(i).getStatus_pesanan());
//                                            arrHT3.add(ht);
//                                        }
                                    }

                                    if(adakmbr == false) {
                                        HeaderTransaksi ht = new HeaderTransaksi("1", arrHT2.get(i).getId_customer(), arrHT2.get(i).getNama_client(), arrHT2.get(i).getNomor_telepon_client(), arrHT2.get(i).getTanggal_reservasi(), arrHT2.get(i).getJam_reservasi(), arrHT2.get(i).getJumlah_orang(), arrHT2.get(i).getNote_transaksi(), arrHT2.get(i).getTotal_harga(), arrHT2.get(i).getId_restoran(), arrHT2.get(i).getStatus_pesanan());
                                        arrHT3.add(ht);
                                    }
                                }
                            }

                            binding.rvLaporanPendapatanHarianOwner.setLayoutManager(new LinearLayoutManager(getActivity()));
                            LaporanPendapatanHarianOwnerAdapter adapter = new LaporanPendapatanHarianOwnerAdapter(arrHT3);
                            binding.rvLaporanPendapatanHarianOwner.setAdapter(adapter);

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
        binding.rvLaporanPendapatanHarianOwner.setHasFixedSize(true);
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
                                if(arrHT.get(i).getStatus_pesanan().equalsIgnoreCase("Selesai") && arrHT.get(i).getTanggal_reservasi().substring(0,2).equalsIgnoreCase(hari) && arrHT.get(i).getTanggal_reservasi().substring(3,5).equalsIgnoreCase(bulan) && arrHT.get(i).getTanggal_reservasi().substring(6).equalsIgnoreCase(tahun)){
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

                            binding.rvLaporanPendapatanHarianOwner.setLayoutManager(new LinearLayoutManager(getActivity()));
                            LaporanPendapatanHarianOwnerAdapter adapter = new LaporanPendapatanHarianOwnerAdapter(arrHT3);
                            binding.rvLaporanPendapatanHarianOwner.setAdapter(adapter);

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