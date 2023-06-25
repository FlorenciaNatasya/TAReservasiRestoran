package com.example.tugasakhir;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.FragmentCustomerBelumDiprosesBinding;
import com.example.tugasakhir.databinding.FragmentRestoranBelumDiprosesBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRestoranBelumDiproses#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRestoranBelumDiproses extends Fragment {

    FragmentRestoranBelumDiprosesBinding binding;
    ArrayList<HeaderTransaksi> arrHtrans = new ArrayList<>();
    ArrayList<HeaderTransaksi> arrHtrans2 = new ArrayList<>();
    private static final String Param1 = "idresto";
    String idresto;

    public FragmentRestoranBelumDiproses() {
        // Required empty public constructor
    }

    public static FragmentRestoranBelumDiproses newInstance(String idresto) {
        FragmentRestoranBelumDiproses fragment = new FragmentRestoranBelumDiproses();
        Bundle args = new Bundle();
        args.putString(Param1, idresto);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idresto = getArguments().getString(Param1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_restoran_belum_diproses, container, false);
        binding = FragmentRestoranBelumDiprosesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.rvFragmentRestoranBelumDiproses.setHasFixedSize(true);
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
                            for (int j = 0; j <= arrtemp.length() - 1; j++) {
                                JSONObject tempMenu = arrtemp.getJSONObject(j);
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
                                        tempMenu.getString("status_pesanan")
                                );
                                arrHtrans.add(ht);
                            }

                            if (arrHtrans != null) {
                                binding.rvFragmentRestoranBelumDiproses.setLayoutManager(new LinearLayoutManager(getActivity()));
                                for (int i = 0; i < arrHtrans.size(); i++) {
                                    if(arrHtrans.get(i).getId_restoran().equalsIgnoreCase(idresto) && arrHtrans.get(i).getStatus_pesanan().equalsIgnoreCase("Belum dikonfirmasi")){
                                        HeaderTransaksi ht = new HeaderTransaksi(arrHtrans.get(i).getId_htrans_reservasi(), arrHtrans.get(i).getId_customer(), arrHtrans.get(i).getNama_client(), arrHtrans.get(i).getNomor_telepon_client(), arrHtrans.get(i).getTanggal_reservasi(), arrHtrans.get(i).getJam_reservasi(), arrHtrans.get(i).getJumlah_orang(), arrHtrans.get(i).getNote_transaksi(), arrHtrans.get(i).getTotal_harga(), arrHtrans.get(i).getId_restoran(), arrHtrans.get(i).getStatus_pesanan());
                                        arrHtrans2.add(ht);
                                    }
                                }
                                ListTransaksiAdapter adapter = new ListTransaksiAdapter(arrHtrans2);
                                adapter.setOnItemClickCallback(new ListTransaksiAdapter.OnItemClickCallback() {
                                    @Override
                                    public void onDetailClicked(HeaderTransaksi transaksi) {
                                        Intent i = new Intent(getActivity(), DetailTransaksiBelumDiproses.class);
                                        i.putExtra("transaksi", transaksi);
                                        i.putExtra("id_restoran", idresto);
                                        startActivity(i);
                                    }
                                });
                                binding.rvFragmentRestoranBelumDiproses.setAdapter(adapter);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}