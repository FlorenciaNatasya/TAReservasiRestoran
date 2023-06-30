package com.example.tugasakhir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tugasakhir.databinding.ItemRvDashboardCustomerBinding;

import java.util.ArrayList;

public class DashboardCustomerAdapter extends RecyclerView.Adapter<DashboardCustomerAdapter.ViewHolder> {

    ArrayList<VoucherRestoran> listVouch;
    Context ctx;
    OnItemClickCallback onItemClickCallback;

    public DashboardCustomerAdapter(ArrayList<VoucherRestoran> listVouch, Context ctx) {
        this.listVouch = listVouch;
        this.ctx = ctx;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public DashboardCustomerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvDashboardCustomerBinding binding = ItemRvDashboardCustomerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardCustomerAdapter.ViewHolder holder, int position) {
        VoucherRestoran v = listVouch.get(position);
        holder.bind(v);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.OnItemClicked(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listVouch.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemRvDashboardCustomerBinding binding;
        public ViewHolder(@NonNull ItemRvDashboardCustomerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(VoucherRestoran vr){
            Glide.with(ctx).load("https://reservasirestoran.me/imagesResto/"+ "Banner"+ vr.getKode_voucher() +".jpg").into(binding.imageView20);
        }
    }

    public interface OnItemClickCallback{
        void OnItemClicked(VoucherRestoran vr);
    }
}
