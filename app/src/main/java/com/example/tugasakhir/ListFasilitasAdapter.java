package com.example.tugasakhir;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasakhir.databinding.ItemRvFasilitasBinding;

import java.util.ArrayList;

public class ListFasilitasAdapter extends RecyclerView.Adapter<ListFasilitasAdapter.ViewHolder> {

    ArrayList<FasilitasRestoran> listFas;
    OnItemClickCallback onItemClickCallback;

    public ListFasilitasAdapter(ArrayList<FasilitasRestoran> listFas) {
        this.listFas = listFas;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ListFasilitasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvFasilitasBinding binding = ItemRvFasilitasBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFasilitasAdapter.ViewHolder holder, int position) {
        FasilitasRestoran fr = listFas.get(position);
        holder.bind(fr);
    }

    @Override
    public int getItemCount() {
        return listFas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemRvFasilitasBinding binding;
        public ViewHolder(@NonNull ItemRvFasilitasBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(FasilitasRestoran fr){
            binding.textViewNamaFasilitas.setText(fr.getNama_fasilitas());
            binding.textViewJumlahFasilitas.setText("Jumlah : " + fr.getJumlah_fasilitas());
            binding.textViewStatusFasilitas.setText("Status : " + fr.getStatus_fasilitas());

            binding.buttonUbahFasilitas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickCallback.OnEditClicked(fr);
                }
            });

            binding.buttonHapusFasilitas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickCallback.OnDeleteClicked(fr);
                }
            });
        }
    }

    public interface OnItemClickCallback{
        void OnEditClicked(FasilitasRestoran fr);
        void OnDeleteClicked(FasilitasRestoran fr);
    }
}
