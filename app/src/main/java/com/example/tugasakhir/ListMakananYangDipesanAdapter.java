package com.example.tugasakhir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tugasakhir.databinding.ItemRvMakananDipesanBinding;
import com.example.tugasakhir.databinding.ItemRvPilihFasilitasBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ListMakananYangDipesanAdapter extends RecyclerView.Adapter<ListMakananYangDipesanAdapter.ViewHolder> {

    ArrayList<DetailTransaksi> arrDtrans;

    public ListMakananYangDipesanAdapter(ArrayList<DetailTransaksi> arrDtrans) {
        this.arrDtrans = arrDtrans;
    }

    @NonNull
    @Override
    public ListMakananYangDipesanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvPilihFasilitasBinding binding = ItemRvPilihFasilitasBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMakananYangDipesanAdapter.ViewHolder holder, int position) {
        DetailTransaksi dt = arrDtrans.get(position);
        holder.bind(dt);
    }

    @Override
    public int getItemCount() {
        return arrDtrans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemRvPilihFasilitasBinding binding;
        public ViewHolder(@NonNull ItemRvPilihFasilitasBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(DetailTransaksi dt){
            binding.textView40.setText(dt.getJumlah_makanan() + "x " + dt.getNama_makanan());
            binding.imageView18.setVisibility(View.INVISIBLE);
        }
    }

    public String formatRupiah(int number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

}
