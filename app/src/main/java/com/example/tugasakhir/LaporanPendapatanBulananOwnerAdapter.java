package com.example.tugasakhir;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasakhir.databinding.ItemLaporanPendapatanRestoranBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class LaporanPendapatanBulananOwnerAdapter extends RecyclerView.Adapter<LaporanPendapatanBulananOwnerAdapter.ViewHolder> {

    ArrayList<HeaderTransaksi> arrHT;
    OnItemClickCallback onItemClickCallback;

    public LaporanPendapatanBulananOwnerAdapter(ArrayList<HeaderTransaksi> arrHT) {
        this.arrHT = arrHT;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public LaporanPendapatanBulananOwnerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLaporanPendapatanRestoranBinding binding = ItemLaporanPendapatanRestoranBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LaporanPendapatanBulananOwnerAdapter.ViewHolder holder, int position) {
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
            if(ht.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("01")){
                binding.textViewTanggalTransaksi.setText(" Januari " + ht.getTanggal_reservasi().substring(6));
            }
            else if(ht.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("02")){
                binding.textViewTanggalTransaksi.setText(" Februari " + ht.getTanggal_reservasi().substring(6));
            }
            else if(ht.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("03")){
                binding.textViewTanggalTransaksi.setText(" Maret " + ht.getTanggal_reservasi().substring(6));
            }
            else if(ht.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("04")){
                binding.textViewTanggalTransaksi.setText(" April " + ht.getTanggal_reservasi().substring(6));
            }
            else if(ht.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("05")){
                binding.textViewTanggalTransaksi.setText(" Mei " + ht.getTanggal_reservasi().substring(6));
            }
            else if(ht.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("06")){
                binding.textViewTanggalTransaksi.setText(" Juni " + ht.getTanggal_reservasi().substring(6));
            }
            else if(ht.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("07")){
                binding.textViewTanggalTransaksi.setText(" Juli " + ht.getTanggal_reservasi().substring(6));
            }
            else if(ht.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("08")){
                binding.textViewTanggalTransaksi.setText(" Agustus " + ht.getTanggal_reservasi().substring(6));
            }
            else if(ht.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("09")){
                binding.textViewTanggalTransaksi.setText(" September " + ht.getTanggal_reservasi().substring(6));
            }
            else if(ht.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("10")){
                binding.textViewTanggalTransaksi.setText(" Oktober " + ht.getTanggal_reservasi().substring(6));
            }
            else if(ht.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("11")){
                binding.textViewTanggalTransaksi.setText(" November " + ht.getTanggal_reservasi().substring(6));
            }
            else if(ht.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("12")){
                binding.textViewTanggalTransaksi.setText(" Desember " + ht.getTanggal_reservasi().substring(6));
            }

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
