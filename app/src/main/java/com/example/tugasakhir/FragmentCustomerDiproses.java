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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCustomerDiproses#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCustomerDiproses extends Fragment {

    FragmentCustomerDiprosesBinding binding;
    ArrayList<PesananSayaClass> arrPsc = new ArrayList<>();
    ArrayList<PesananSayaClass> arrPsc2 = new ArrayList<>();
    ArrayList<PesananSayaClass> arrPsc3 = new ArrayList<>();
    private static final String Param1 = "1";
    private String idcust;

    public FragmentCustomerDiproses() {
        // Required empty public constructor
    }

    public static FragmentCustomerDiproses newInstance(String id_customer) {
        FragmentCustomerDiproses fragment = new FragmentCustomerDiproses();
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
//        return inflater.inflate(R.layout.fragment_customer_diproses, container, false);
        binding = FragmentCustomerDiprosesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.rvFragmentCustomerDiproses.setHasFixedSize(true);
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

                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                            String date = dateFormat.format(c.getTime());

                            if (arrPsc != null) {
                                binding.rvFragmentCustomerDiproses.setLayoutManager(new LinearLayoutManager(getActivity()));
                                for (int i = 0; i < arrPsc.size(); i++) {
                                    if(arrPsc.get(i).getStatus_pesanan().equalsIgnoreCase("Dikonfirmasi") && (Integer.parseInt(arrPsc.get(i).getTanggal_reservasi().substring(6)) >= Integer.parseInt(date.substring(6,10))) && (Integer.parseInt(arrPsc.get(i).getTanggal_reservasi().substring(3,5)) >= Integer.parseInt(date.substring(3,5)))){
                                        PesananSayaClass p = new PesananSayaClass(arrPsc.get(i).getId_htrans(), arrPsc.get(i).getNama_restoran(), arrPsc.get(i).getTanggal_reservasi(), arrPsc.get(i).getJam_reservasi(), arrPsc.get(i).getTotal_harga(), arrPsc.get(i).getStatus_pesanan());
                                        arrPsc2.add(p);
                                    }
                                }
                                PesananSayaAdapter adapter = new PesananSayaAdapter(arrPsc2, getActivity());
                                binding.rvFragmentCustomerDiproses.setAdapter(adapter);
                                adapter.setOnItemClickCallback(new PesananSayaAdapter.OnItemClickCallback() {
                                    @Override
                                    public void OnItemClicked(PesananSayaClass psc) {
                                        Intent i = new Intent(getActivity(), QRCodeCustomer.class);
                                        i.putExtra("datapesanan", psc);
                                        startActivity(i);
                                    }
                                });
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void PesananSelesai(String id_htrans){
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
//                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
                params.put("function", "PesananSelesai");
                params.put("id_htrans", id_htrans);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}