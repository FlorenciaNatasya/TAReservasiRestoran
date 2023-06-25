package com.example.tugasakhir;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasakhir.databinding.ItemRestoranListTransaksiBinding;

import java.util.ArrayList;

public class ListTransaksiAdapter extends RecyclerView.Adapter<ListTransaksiAdapter.ViewHolder> {

    ArrayList<HeaderTransaksi> arrHTrans;
    OnItemClickCallback onItemClickCallback;

    public ListTransaksiAdapter(ArrayList<HeaderTransaksi> arrHTrans) {
        this.arrHTrans = arrHTrans;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ListTransaksiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRestoranListTransaksiBinding binding = ItemRestoranListTransaksiBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListTransaksiAdapter.ViewHolder holder, int position) {
        HeaderTransaksi ht = arrHTrans.get(position);
        holder.bind(ht);
    }

    @Override
    public int getItemCount() {
        return arrHTrans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemRestoranListTransaksiBinding binding;
        public ViewHolder(@NonNull ItemRestoranListTransaksiBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(HeaderTransaksi transaksi){
            if(transaksi.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("01")){
                binding.textViewTglReservasi.setText(transaksi.getTanggal_reservasi().substring(0,2) + " Januari " + transaksi.getTanggal_reservasi().substring(6));
            }
            else if(transaksi.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("02")){
                binding.textViewTglReservasi.setText(transaksi.getTanggal_reservasi().substring(0,2) + " Februari " + transaksi.getTanggal_reservasi().substring(6));
            }
            else if(transaksi.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("03")){
                binding.textViewTglReservasi.setText(transaksi.getTanggal_reservasi().substring(0,2) + " Maret " + transaksi.getTanggal_reservasi().substring(6));
            }
            else if(transaksi.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("04")){
                binding.textViewTglReservasi.setText(transaksi.getTanggal_reservasi().substring(0,2) + " April " + transaksi.getTanggal_reservasi().substring(6));
            }
            else if(transaksi.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("05")){
                binding.textViewTglReservasi.setText(transaksi.getTanggal_reservasi().substring(0,2) + " Mei " + transaksi.getTanggal_reservasi().substring(6));
            }
            else if(transaksi.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("06")){
                binding.textViewTglReservasi.setText(transaksi.getTanggal_reservasi().substring(0,2) + " Juni " + transaksi.getTanggal_reservasi().substring(6));
            }
            else if(transaksi.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("07")){
                binding.textViewTglReservasi.setText(transaksi.getTanggal_reservasi().substring(0,2) + " Juli " + transaksi.getTanggal_reservasi().substring(6));
            }
            else if(transaksi.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("08")){
                binding.textViewTglReservasi.setText(transaksi.getTanggal_reservasi().substring(0,2) + " Agustus " + transaksi.getTanggal_reservasi().substring(6));
            }
            else if(transaksi.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("09")){
                binding.textViewTglReservasi.setText(transaksi.getTanggal_reservasi().substring(0,2) + " September " + transaksi.getTanggal_reservasi().substring(6));
            }
            else if(transaksi.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("10")){
                binding.textViewTglReservasi.setText(transaksi.getTanggal_reservasi().substring(0,2) + " Oktober " + transaksi.getTanggal_reservasi().substring(6));
            }
            else if(transaksi.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("11")){
                binding.textViewTglReservasi.setText(transaksi.getTanggal_reservasi().substring(0,2) + " November " + transaksi.getTanggal_reservasi().substring(6));
            }
            else if(transaksi.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("12")){
                binding.textViewTglReservasi.setText(transaksi.getTanggal_reservasi().substring(0,2) + " Desember " + transaksi.getTanggal_reservasi().substring(6));
            }

            binding.textViewJamReservasinya.setText(transaksi.getJam_reservasi());
            binding.textViewNamaPemesan.setText(transaksi.getNama_client());
            binding.textViewJumlahSeatDipesan.setText(transaksi.getJumlah_orang() + " orang");
            binding.buttonDetailPesananCustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickCallback.onDetailClicked(transaksi);
                }
            });
        }
    }

    public interface OnItemClickCallback{
        void onDetailClicked(HeaderTransaksi transaksi);
    }
}
