package com.example.tugasakhir;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.FragmentLaporanPendapatanBulananOwnerBinding;
import com.example.tugasakhir.databinding.FragmentLaporanPendapatanBulananRestoranBinding;

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
 * Use the {@link FragmentLaporanPendapatanBulananOwner#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLaporanPendapatanBulananOwner extends Fragment {

    FragmentLaporanPendapatanBulananOwnerBinding binding;
    String bulan;
    ArrayList<HeaderTransaksi> arrHT = new ArrayList<>();
    ArrayList<HeaderTransaksi> arrHT2 = new ArrayList<>();
    int total = 0;

    public FragmentLaporanPendapatanBulananOwner() {
        // Required empty public constructor
    }

    public static FragmentLaporanPendapatanBulananOwner newInstance() {
        FragmentLaporanPendapatanBulananOwner fragment = new FragmentLaporanPendapatanBulananOwner();
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
//        return inflater.inflate(R.layout.fragment_laporan_pendapatan_bulanan_owner, container, false);
        binding = FragmentLaporanPendapatanBulananOwnerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM");
        String date = dateFormat.format(c.getTime());
        binding.textViewBulanLaporanPendapatanOwner.setText(date);

        binding.spinnerLaporanPendapatanBulananOwner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.textViewBulanLaporanPendapatanOwner.setText(binding.spinnerLaporanPendapatanBulananOwner.getSelectedItem().toString());
                if(binding.spinnerLaporanPendapatanBulananOwner.getSelectedItem().toString().equalsIgnoreCase("Januari")){
                    bulan = "01";
                }
                else if(binding.spinnerLaporanPendapatanBulananOwner.getSelectedItem().toString().equalsIgnoreCase("Februari")){
                    bulan = "02";
                }
                else if(binding.spinnerLaporanPendapatanBulananOwner.getSelectedItem().toString().equalsIgnoreCase("Maret")){
                    bulan = "03";
                }
                else if(binding.spinnerLaporanPendapatanBulananOwner.getSelectedItem().toString().equalsIgnoreCase("April")){
                    bulan = "04";
                }
                else if(binding.spinnerLaporanPendapatanBulananOwner.getSelectedItem().toString().equalsIgnoreCase("Mei")){
                    bulan = "05";
                }
                else if(binding.spinnerLaporanPendapatanBulananOwner.getSelectedItem().toString().equalsIgnoreCase("Juni")){
                    bulan = "06";
                }
                else if(binding.spinnerLaporanPendapatanBulananOwner.getSelectedItem().toString().equalsIgnoreCase("Juli")){
                    bulan = "07";
                }
                else if(binding.spinnerLaporanPendapatanBulananOwner.getSelectedItem().toString().equalsIgnoreCase("Agustus")){
                    bulan = "08";
                }
                else if(binding.spinnerLaporanPendapatanBulananOwner.getSelectedItem().toString().equalsIgnoreCase("September")){
                    bulan = "09";
                }
                else if(binding.spinnerLaporanPendapatanBulananOwner.getSelectedItem().toString().equalsIgnoreCase("Oktober")){
                    bulan = "10";
                }
                else if(binding.spinnerLaporanPendapatanBulananOwner.getSelectedItem().toString().equalsIgnoreCase("November")){
                    bulan = "11";
                }
                else if(binding.spinnerLaporanPendapatanBulananOwner.getSelectedItem().toString().equalsIgnoreCase("Desember")){
                    bulan = "12";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        setRVLaporanPendapatanBulanan();

        binding.buttonSetBulanLaporanPendapatanOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRVLaporanPendapatanHarianSetelahSetBulan();
            }
        });
    }

    private void setRVLaporanPendapatanBulanan(){
        binding.rvLaporanPendapatanBulananOwner.setHasFixedSize(true);
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

                            int totalbulanan = 0;
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
                                        if(arrHT2.get(i).getTanggal_reservasi().substring(3,5).equalsIgnoreCase(arrHT3.get(j).getTanggal_reservasi().substring(3,5))){
                                            totalbulanan = Integer.parseInt(arrHT3.get(j).getTotal_harga());
                                            totalbulanan += Integer.parseInt(arrHT2.get(i).getTotal_harga());
                                            arrHT3.get(j).setTotal_harga(String.valueOf(totalbulanan));
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

                            binding.rvLaporanPendapatanBulananOwner.setLayoutManager(new LinearLayoutManager(getActivity()));
                            LaporanPendapatanBulananOwnerAdapter adapter = new LaporanPendapatanBulananOwnerAdapter(arrHT3);
                            binding.rvLaporanPendapatanBulananOwner.setAdapter(adapter);

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

    private void setRVLaporanPendapatanHarianSetelahSetBulan(){
        arrHT.clear();
        arrHT2.clear();
        binding.rvLaporanPendapatanBulananOwner.setHasFixedSize(true);
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
                                if(arrHT.get(i).getTanggal_reservasi().substring(3,5).equalsIgnoreCase(bulan)){
                                    HeaderTransaksi ht = new HeaderTransaksi(arrHT.get(i).getId_htrans_reservasi(), arrHT.get(i).getId_customer(), arrHT.get(i).getNama_client(), arrHT.get(i).getNomor_telepon_client(), arrHT.get(i).getTanggal_reservasi(), arrHT.get(i).getJam_reservasi(), arrHT.get(i).getJumlah_orang(), arrHT.get(i).getNote_transaksi(), arrHT.get(i).getTotal_harga(), arrHT.get(i).getId_restoran(), arrHT.get(i).getStatus_pesanan());
                                    arrHT2.add(ht);
                                    total += Integer.parseInt(arrHT.get(i).getTotal_harga());
                                }
                            }

                            binding.rvLaporanPendapatanBulananOwner.setLayoutManager(new LinearLayoutManager(getActivity()));
                            LaporanPendapatanBulananOwnerAdapter adapter = new LaporanPendapatanBulananOwnerAdapter(arrHT2);
                            binding.rvLaporanPendapatanBulananOwner.setAdapter(adapter);

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