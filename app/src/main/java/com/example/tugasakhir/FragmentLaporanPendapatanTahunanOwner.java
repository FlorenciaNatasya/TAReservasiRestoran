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
import com.example.tugasakhir.databinding.FragmentLaporanPendapatanTahunanOwnerBinding;
import com.example.tugasakhir.databinding.FragmentLaporanPendapatanTahunanRestoranBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLaporanPendapatanTahunanOwner#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLaporanPendapatanTahunanOwner extends Fragment {

    FragmentLaporanPendapatanTahunanOwnerBinding binding;
    ArrayList<HeaderTransaksi> arrHT = new ArrayList<>();
    ArrayList<HeaderTransaksi> arrHT2 = new ArrayList<>();
    int total = 0 ;

    public FragmentLaporanPendapatanTahunanOwner() {
        // Required empty public constructor
    }

    public static FragmentLaporanPendapatanTahunanOwner newInstance() {
        FragmentLaporanPendapatanTahunanOwner fragment = new FragmentLaporanPendapatanTahunanOwner();
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
//        return inflater.inflate(R.layout.fragment_laporan_pendapatan_tahunan_owner, container, false);
        binding = FragmentLaporanPendapatanTahunanOwnerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.spinnerLaporanPendapatanTahunanOwner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.textViewTahunLaporanPendapatanOwner.setText(binding.spinnerLaporanPendapatanTahunanOwner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        setRVLaporanPendapatanTahunan();

        binding.buttonSetTahunLaporanPendapatanOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRVLaporanPendapatanHarianSetelahSetTahun();
            }
        });
    }

    private void setRVLaporanPendapatanTahunan(){
        binding.rvLaporanPendapatanTahunanOwner.setHasFixedSize(true);
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

                            int totaltahunan = 0;
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
                                        if(arrHT2.get(i).getTanggal_reservasi().substring(6).equalsIgnoreCase(arrHT3.get(j).getTanggal_reservasi().substring(6))){
                                            totaltahunan = Integer.parseInt(arrHT3.get(j).getTotal_harga());
                                            totaltahunan += Integer.parseInt(arrHT2.get(i).getTotal_harga());
                                            arrHT3.get(j).setTotal_harga(String.valueOf(totaltahunan));
                                            totaltrans = Integer.parseInt(arrHT3.get(j).getId_htrans_reservasi()) + 1;
                                            arrHT3.get(j).setId_htrans_reservasi(String.valueOf(totaltrans));
                                            adakmbr = true;
                                        }
//                                        else{
//                                            HeaderTransaksi ht = new HeaderTransaksi(arrHT2.get(i).getId_htrans_reservasi(), arrHT2.get(i).getId_customer(), arrHT2.get(i).getNama_client(), arrHT2.get(i).getNomor_telepon_client(), arrHT2.get(i).getTanggal_reservasi(), arrHT2.get(i).getJam_reservasi(), arrHT2.get(i).getJumlah_orang(), arrHT2.get(i).getNote_transaksi(), arrHT2.get(i).getTotal_harga(), arrHT2.get(i).getId_restoran(), arrHT2.get(i).getStatus_pesanan());
//                                            arrHT3.add(ht);
//                                        }
                                    }

                                     if(adakmbr == false){
                                        HeaderTransaksi ht = new HeaderTransaksi("1", arrHT2.get(i).getId_customer(), arrHT2.get(i).getNama_client(), arrHT2.get(i).getNomor_telepon_client(), arrHT2.get(i).getTanggal_reservasi(), arrHT2.get(i).getJam_reservasi(), arrHT2.get(i).getJumlah_orang(), arrHT2.get(i).getNote_transaksi(), arrHT2.get(i).getTotal_harga(), arrHT2.get(i).getId_restoran(), arrHT2.get(i).getStatus_pesanan());
                                        arrHT3.add(ht);
                                    }
                                }
                            }

                            binding.rvLaporanPendapatanTahunanOwner.setLayoutManager(new LinearLayoutManager(getActivity()));
                            LaporanPendapatanTahunanOwnerAdapter adapter = new LaporanPendapatanTahunanOwnerAdapter(arrHT3);
                            binding.rvLaporanPendapatanTahunanOwner.setAdapter(adapter);

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

    private void setRVLaporanPendapatanHarianSetelahSetTahun(){
        arrHT.clear();
        arrHT2.clear();
        binding.rvLaporanPendapatanTahunanOwner.setHasFixedSize(true);
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
                                if(arrHT.get(i).getTanggal_reservasi().substring(6).equalsIgnoreCase(binding.spinnerLaporanPendapatanTahunanOwner.getSelectedItem().toString())){
                                    HeaderTransaksi ht = new HeaderTransaksi(arrHT.get(i).getId_htrans_reservasi(), arrHT.get(i).getId_customer(), arrHT.get(i).getNama_client(), arrHT.get(i).getNomor_telepon_client(), arrHT.get(i).getTanggal_reservasi(), arrHT.get(i).getJam_reservasi(), arrHT.get(i).getJumlah_orang(), arrHT.get(i).getNote_transaksi(), arrHT.get(i).getTotal_harga(), arrHT.get(i).getId_restoran(), arrHT.get(i).getStatus_pesanan());
                                    arrHT2.add(ht);
                                    total += Integer.parseInt(arrHT.get(i).getTotal_harga());
                                }
                            }

                            binding.rvLaporanPendapatanTahunanOwner.setLayoutManager(new LinearLayoutManager(getActivity()));
                            LaporanPendapatanTahunanOwnerAdapter adapter = new LaporanPendapatanTahunanOwnerAdapter(arrHT2);
                            binding.rvLaporanPendapatanTahunanOwner.setAdapter(adapter);

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