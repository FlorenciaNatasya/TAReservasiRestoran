package com.example.tugasakhir;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasakhir.databinding.ItemRvClaimVoucherBinding;

import java.util.ArrayList;

public class ClaimVoucherAdapter extends RecyclerView.Adapter<ClaimVoucherAdapter.ViewHolder> {

    ArrayList<VoucherRestoran> arrVouch;
    OnItemClickCallback onItemClickCallback;

    public ClaimVoucherAdapter(ArrayList<VoucherRestoran> arrVouch) {
        this.arrVouch = arrVouch;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ClaimVoucherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvClaimVoucherBinding binding = ItemRvClaimVoucherBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ClaimVoucherAdapter.ViewHolder holder, int position) {
        VoucherRestoran v = arrVouch.get(position);
        holder.bind(v);
    }

    @Override
    public int getItemCount() {
        return arrVouch.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemRvClaimVoucherBinding binding;
        public ViewHolder(@NonNull ItemRvClaimVoucherBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(VoucherRestoran vr){
            if(vr.getJenis_potongan().equals("Nominal")){
                binding.textView70.setText("Diskon Rp " + vr.getMaksimal_potongan());
                binding.textViewMinimBelanja.setText("Min. Pembelian Rp " + vr.getMinimum_pembelian());
            }
            else{
                binding.textView70.setText("Diskon " + vr.getJumlah_diskon() + "%");
                binding.textViewMinimBelanja.setText("Min. Pembelian Rp " + vr.getMinimum_pembelian() + ", Max. Potongan Rp " + vr.getMaksimal_potongan());
            }

            binding.buttonClaimVoucher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickCallback.OnClaimClicked(vr);
                }
            });
        }
    }

    public interface OnItemClickCallback{
        void OnClaimClicked(VoucherRestoran vr);
    }
}
