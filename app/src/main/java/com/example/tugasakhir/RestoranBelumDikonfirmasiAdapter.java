package com.example.tugasakhir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tugasakhir.databinding.ItemRvCustomerPesananSayaBinding;

import java.util.ArrayList;

public class RestoranBelumDikonfirmasiAdapter extends RecyclerView.Adapter<RestoranBelumDikonfirmasiAdapter.ViewHolder> {

    ArrayList<Restoran> listResto;
    Context ctx;
    OnItemClickedCallback onItemClickedCallback;

    public RestoranBelumDikonfirmasiAdapter(ArrayList<Restoran> listResto, Context ctx) {
        this.listResto = listResto;
        this.ctx = ctx;
    }

    public void setOnItemClickedCallback(OnItemClickedCallback onItemClickedCallback) {
        this.onItemClickedCallback = onItemClickedCallback;
    }

    @NonNull
    @Override
    public RestoranBelumDikonfirmasiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvCustomerPesananSayaBinding binding = ItemRvCustomerPesananSayaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestoranBelumDikonfirmasiAdapter.ViewHolder holder, int position) {
        Restoran r = listResto.get(position);
        holder.bind(r);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickedCallback.onItemClicked(r);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listResto.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemRvCustomerPesananSayaBinding binding;
        public ViewHolder(@NonNull ItemRvCustomerPesananSayaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Restoran restoran){
            binding.textViewTotalHargaPesanan.setText("");
            binding.textViewNamaRestoYangDipesan.setText(restoran.getNama_restoran());
            binding.textViewKeteranganPesanan.setText(restoran.getAlamat_restoran());
            Glide.with(ctx).load("http://reservasirestoran.me/imagesResto/"+ restoran.getNama_restoran().replace(" ", "") + "BagianDepan" +".jpg").into(binding.imageView13);
        }
    }

    public interface OnItemClickedCallback{
        void onItemClicked(Restoran restoran);
    }
}
