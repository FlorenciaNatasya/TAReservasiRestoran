package com.example.tugasakhir;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasakhir.databinding.ItemRvPilihFasilitasBinding;

import java.util.ArrayList;

public class KalenderReservasiAdapter extends RecyclerView.Adapter<KalenderReservasiAdapter.ViewHolder> {

    ArrayList<HeaderTransaksi> listHT;
    ArrayList<String> jmlh;

    public KalenderReservasiAdapter(ArrayList<HeaderTransaksi> listHT, ArrayList<String> jmlh) {
        this.listHT = listHT;
        this.jmlh = jmlh;
    }

    @NonNull
    @Override
    public KalenderReservasiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvPilihFasilitasBinding binding = ItemRvPilihFasilitasBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull KalenderReservasiAdapter.ViewHolder holder, int position) {
        HeaderTransaksi ht = listHT.get(position);
        String s = jmlh.get(position);
        holder.bind(ht, s);
    }

    @Override
    public int getItemCount() {
        return listHT.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemRvPilihFasilitasBinding binding;
        public ViewHolder(@NonNull ItemRvPilihFasilitasBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(HeaderTransaksi ht, String s){
            binding.imageView18.setEnabled(false);
            binding.imageView18.setVisibility(View.INVISIBLE);
            binding.textView40.setText(ht.getJam_reservasi() + " menangani " + s + " pesanan");
        }
    }
}
