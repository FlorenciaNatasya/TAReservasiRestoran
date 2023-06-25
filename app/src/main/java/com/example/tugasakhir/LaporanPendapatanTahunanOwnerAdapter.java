package com.example.tugasakhir;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasakhir.databinding.ItemLaporanPendapatanRestoranBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class LaporanPendapatanTahunanOwnerAdapter extends RecyclerView.Adapter<LaporanPendapatanTahunanOwnerAdapter.ViewHolder> {

    ArrayList<HeaderTransaksi> arrHT;
    OnItemClickCallback onItemClickCallback;

    public LaporanPendapatanTahunanOwnerAdapter(ArrayList<HeaderTransaksi> arrHT) {
        this.arrHT = arrHT;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public LaporanPendapatanTahunanOwnerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLaporanPendapatanRestoranBinding binding = ItemLaporanPendapatanRestoranBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LaporanPendapatanTahunanOwnerAdapter.ViewHolder holder, int position) {
        HeaderTransaksi ht = arrHT.get(position);
        holder.bind(ht);
    }

    @Override
    public int getItemCount() {
        return arrHT.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemLaporanPendapatanRestoranBinding binding;
        public ViewHolder(@NonNull ItemLaporanPendapatanRestoranBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(HeaderTransaksi ht){
            binding.textViewNTrans.setText(ht.getId_htrans_reservasi());
            binding.textViewTanggalTransaksi.setText(ht.getTanggal_reservasi().substring(6));
            binding.textViewTotalHargaTransaksi.setText(formatRupiah(Integer.parseInt(ht.getTotal_harga())*25/100));
        }
    }

    public String formatRupiah(int number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

    public interface OnItemClickCallback{
        void onDetailClicked(HeaderTransaksi ht);
    }
}
