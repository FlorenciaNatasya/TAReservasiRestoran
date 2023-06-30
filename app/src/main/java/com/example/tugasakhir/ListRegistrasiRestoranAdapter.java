package com.example.tugasakhir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tugasakhir.databinding.ItemRvListRegistrasiRestoranBinding;

import java.util.ArrayList;

public class ListRegistrasiRestoranAdapter extends RecyclerView.Adapter<ListRegistrasiRestoranAdapter.ViewHolder> {

    ArrayList<Restoran> listResto;
    Context ctx;
    OnItemClickCallback onItemClickCallback;

    public ListRegistrasiRestoranAdapter(ArrayList<Restoran> listResto, Context ctx) {
        this.listResto = listResto;
        this.ctx = ctx;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ListRegistrasiRestoranAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvListRegistrasiRestoranBinding binding = ItemRvListRegistrasiRestoranBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListRegistrasiRestoranAdapter.ViewHolder holder, int position) {
        Restoran r = listResto.get(position);
        holder.bind(r);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.OnItemClicked(r);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listResto.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemRvListRegistrasiRestoranBinding binding;
        public ViewHolder(@NonNull ItemRvListRegistrasiRestoranBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Restoran r){
            Glide.with(ctx).load("https://reservasirestoran.me/imagesResto/"+ r.getNama_restoran().replace(" ", "") + "BagianDepan" +".jpg").into(binding.imageView25);
            binding.textView102.setText(r.getNama_restoran());
            binding.textView103.setText(r.getAlamat_restoran() + ", " + r.getDaerah_restoran());
        }
    }

    public interface OnItemClickCallback{
        void OnItemClicked(Restoran r);
    }
}
