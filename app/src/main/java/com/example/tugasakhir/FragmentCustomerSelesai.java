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
import com.example.tugasakhir.databinding.FragmentCustomerDiprosesBinding;
import com.example.tugasakhir.databinding.FragmentCustomerSelesaiBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCustomerSelesai#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCustomerSelesai extends Fragment {

    FragmentCustomerSelesaiBinding binding;
    ArrayList<PesananSayaClass> arrPsc = new ArrayList<>();
    ArrayList<PesananSayaClass> arrPsc2 = new ArrayList<>();
    private static final String Param1 = "1";
    private String idcust;

    public FragmentCustomerSelesai() {
        // Required empty public constructor
    }

    public static FragmentCustomerSelesai newInstance(String id_customer) {
        FragmentCustomerSelesai fragment = new FragmentCustomerSelesai();
        Bundle args = new Bundle();
        args.putString(Param1, id_customer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idcust = getArguments().getString(Param1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_customer_selesai, container, false);
        binding = FragmentCustomerSelesaiBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.rvFragmentCustomerSelesai.setHasFixedSize(true);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://10.0.2.2/TA-service/master.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONObject jsonobject = new JSONObject(response);
                            JSONArray arrtemp = jsonobject.getJSONArray("datapesanan");
                            for (int j = 0; j <= arrtemp.length() - 1; j++) {
                                JSONObject tempMenu = arrtemp.getJSONObject(j);
                                PesananSayaClass psc = new PesananSayaClass(
                                        tempMenu.getString("id_htrans_reservasi"),
                                        tempMenu.getString("nama_restoran"),
                                        tempMenu.getString("tanggal_reservasi"),
                                        tempMenu.getString("jam_reservasi"),
                                        tempMenu.getString("total_harga"),
                                        tempMenu.getString("status_pesanan")
                                );
                                arrPsc.add(psc);
                            }

                            if (arrPsc != null) {
                                binding.rvFragmentCustomerSelesai.setLayoutManager(new LinearLayoutManager(getActivity()));
                                for (int i = 0; i < arrPsc.size(); i++) {
                                    if(arrPsc.get(i).getStatus_pesanan().equalsIgnoreCase("Selesai")){
                                        PesananSayaClass p = new PesananSayaClass(arrPsc.get(i).getId_htrans(), arrPsc.get(i).getNama_restoran(), arrPsc.get(i).getTanggal_reservasi(), arrPsc.get(i).getJam_reservasi(), arrPsc.get(i).getTotal_harga(), arrPsc.get(i).getStatus_pesanan());
                                        arrPsc2.add(p);
                                    }
                                }
                                PesananSayaAdapter adapter = new PesananSayaAdapter(arrPsc2, getActivity());
                                adapter.setOnItemClickCallback(new PesananSayaAdapter.OnItemClickCallback() {
                                    @Override
                                    public void OnItemClicked(PesananSayaClass psc) {
                                        Intent i = new Intent(getActivity(), RatingReview.class);
                                        i.putExtra("datapesanan", psc);
                                        i.putExtra("id_customer", idcust);
                                        startActivity(i);
                                    }
                                });
                                binding.rvFragmentCustomerSelesai.setAdapter(adapter);
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
                params.put("function", "PesananSaya");
                params.put("id_cust", "1");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}