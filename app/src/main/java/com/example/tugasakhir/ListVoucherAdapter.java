package com.example.tugasakhir;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasakhir.databinding.ItemRvListVoucherBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ListVoucherAdapter extends RecyclerView.Adapter<ListVoucherAdapter.ViewHolder> {

    ArrayList<VoucherRestoran> listVouch;
    OnItemClickCallback onItemClickCallback;

    public ListVoucherAdapter(ArrayList<VoucherRestoran> listVouch) {
        this.listVouch = listVouch;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ListVoucherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvListVoucherBinding binding = ItemRvListVoucherBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListVoucherAdapter.ViewHolder holder, int position) {
        VoucherRestoran vr = listVouch.get(position);
        holder.bind(vr);
    }

    @Override
    public int getItemCount() {
        return listVouch.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemRvListVoucherBinding binding;
        public ViewHolder(@NonNull ItemRvListVoucherBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(VoucherRestoran vr){
            binding.textViewKodeVoucherResto.setText(vr.getKode_voucher());
            binding.textViewMinPembelian.setText("Min. Pembelian : " + formatRupiah(Integer.parseInt(vr.getMinimum_pembelian())));
            binding.textViewMaxPot.setText("Max. Potongan : " + formatRupiah(Integer.parseInt(vr.getMaksimal_potongan())));
            binding.textViewKuotaVouch.setText("Kuota : " + vr.getKuota_voucher());
            binding.buttonEditVoucher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickCallback.OnEditClicked(vr);
                }
            });
            binding.buttonHapusVoucher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickCallback.OnDeleteClicked(vr);
                }
            });
            binding.textViewTanggalBerlakuVoucher.setText(vr.getTanggal_awal() + " s/d " + vr.getTanggal_akhir());
        }
    }

    public interface OnItemClickCallback{
        void OnEditClicked(VoucherRestoran vr);
        void OnDeleteClicked(VoucherRestoran vr);
    }

    public String formatRupiah(int number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}
