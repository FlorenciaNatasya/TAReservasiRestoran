package com.example.tugasakhir;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasakhir.databinding.ItemRvPilihFasilitasBinding;

import java.util.ArrayList;

public class PilihFasilitasAdapter extends RecyclerView.Adapter<PilihFasilitasAdapter.ViewHolder> {

    ArrayList<FasilitasYangDipinjamClass> listFas;
    static OnItemClickCallback onItemClickCallback;

    public PilihFasilitasAdapter(ArrayList<FasilitasYangDipinjamClass> listFas) {
        this.listFas = listFas;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public PilihFasilitasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvPilihFasilitasBinding binding = ItemRvPilihFasilitasBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PilihFasilitasAdapter.ViewHolder holder, int position) {
        FasilitasYangDipinjamClass fas = listFas.get(position);
        holder.bind(fas);
    }

    @Override
    public int getItemCount() {
        return listFas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemRvPilihFasilitasBinding binding;
        public ViewHolder(@NonNull ItemRvPilihFasilitasBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(FasilitasYangDipinjamClass fas){
            binding.textView40.setText(fas.getJmlh() + "x " + fas.getNamfas());
            binding.imageView18.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickCallback.OnDeleteClicked(fas);
                }
            });
        }
    }

    public interface OnItemClickCallback{
        void OnDeleteClicked(FasilitasYangDipinjamClass fas);
    }
}
