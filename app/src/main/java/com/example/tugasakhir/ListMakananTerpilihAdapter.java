package com.example.tugasakhir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tugasakhir.databinding.ItemRvMakananDipesanBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ListMakananTerpilihAdapter extends RecyclerView.Adapter<ListMakananTerpilihAdapter.ViewHolder> {

    ArrayList<DetailTransaksi> arrDtrans;
    Context ctx;

    public ListMakananTerpilihAdapter(ArrayList<DetailTransaksi> arrDtrans, Context ctx) {
        this.arrDtrans = arrDtrans;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ListMakananTerpilihAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvMakananDipesanBinding binding = ItemRvMakananDipesanBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMakananTerpilihAdapter.ViewHolder holder, int position) {
        DetailTransaksi dt = arrDtrans.get(position);
        holder.bind(dt);
    }

    @Override
    public int getItemCount() {
        return arrDtrans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemRvMakananDipesanBinding binding;
        public ViewHolder(@NonNull ItemRvMakananDipesanBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(DetailTransaksi dt){
            Glide.with(ctx).load("http://10.0.2.2/TA-service/imagesMenu/"+ dt.getNama_makanan().replace(" ", "") + ".jpg").into(binding.imageViewMakananYangDipesan);
            binding.textViewJumlahDanNamaMakanan.setText(dt.getJumlah_makanan() + "x " + dt.getNama_makanan());
            int total = Integer.parseInt(dt.getHarga_makanan()) * Integer.parseInt(dt.getJumlah_makanan());
            binding.textViewJumlahHarga.setText(formatRupiah(total));
        }
    }

    public String formatRupiah(int number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}
