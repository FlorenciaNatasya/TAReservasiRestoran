package com.example.tugasakhir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tugasakhir.databinding.ItemRvCustomerPesananSayaBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PesananSayaAdapter extends RecyclerView.Adapter<PesananSayaAdapter.ViewHolder> {

    ArrayList<PesananSayaClass> ps ;
    Context ctx;
    OnItemClickCallback onItemClickCallback;

    public PesananSayaAdapter(ArrayList<PesananSayaClass> ps, Context ctx) {
        this.ps = ps;
        this.ctx = ctx;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public PesananSayaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvCustomerPesananSayaBinding binding = ItemRvCustomerPesananSayaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PesananSayaAdapter.ViewHolder holder, int position) {
        PesananSayaClass psc = ps.get(position);
        holder.bind(psc);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.OnItemClicked(psc);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemRvCustomerPesananSayaBinding binding;
        public ViewHolder(@NonNull ItemRvCustomerPesananSayaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(PesananSayaClass psc){
            Glide.with(ctx).load("http://10.0.2.2/TA-service/imagesResto/"+ psc.getNama_restoran().replace(" ", "") + "BagianDepan" +".jpg").into(binding.imageView13);
            binding.textViewNamaRestoYangDipesan.setText(psc.getNama_restoran());
            if(psc.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("01")){
                binding.textViewKeteranganPesanan.setText("Reservasi pada tanggal " + psc.getTanggal_reservasi().substring(0,2) + " Januari " + psc.getTanggal_reservasi().substring(6) + " pukul " + psc.getJam_reservasi());
            }
            else if(psc.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("02")){
                binding.textViewKeteranganPesanan.setText("Reservasi pada tanggal " + psc.getTanggal_reservasi().substring(0,2) + " Februari " + psc.getTanggal_reservasi().substring(6) + " pukul " + psc.getJam_reservasi());
            }
            else if(psc.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("03")){
                binding.textViewKeteranganPesanan.setText("Reservasi pada tanggal " + psc.getTanggal_reservasi().substring(0,2) + " Maret " + psc.getTanggal_reservasi().substring(6) + " pukul " + psc.getJam_reservasi());
            }
            else if(psc.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("04")){
                binding.textViewKeteranganPesanan.setText("Reservasi pada tanggal " + psc.getTanggal_reservasi().substring(0,2) + " April " + psc.getTanggal_reservasi().substring(6) + " pukul " + psc.getJam_reservasi());
            }
            else if(psc.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("05")){
                binding.textViewKeteranganPesanan.setText("Reservasi pada tanggal " + psc.getTanggal_reservasi().substring(0,2) + " Mei " + psc.getTanggal_reservasi().substring(6) + " pukul " + psc.getJam_reservasi());
            }
            else if(psc.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("06")){
                binding.textViewKeteranganPesanan.setText("Reservasi pada tanggal " + psc.getTanggal_reservasi().substring(0,2) + " Juni " + psc.getTanggal_reservasi().substring(6) + " pukul " + psc.getJam_reservasi());
            }
            else if(psc.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("07")){
                binding.textViewKeteranganPesanan.setText("Reservasi pada tanggal " + psc.getTanggal_reservasi().substring(0,2) + " Juli " + psc.getTanggal_reservasi().substring(6) + " pukul " + psc.getJam_reservasi());
            }
            else if(psc.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("08")){
                binding.textViewKeteranganPesanan.setText("Reservasi pada tanggal " + psc.getTanggal_reservasi().substring(0,2) + " Agustus " + psc.getTanggal_reservasi().substring(6) + " pukul " + psc.getJam_reservasi());
            }
            else if(psc.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("09")){
                binding.textViewKeteranganPesanan.setText("Reservasi pada tanggal " + psc.getTanggal_reservasi().substring(0,2) + " September " + psc.getTanggal_reservasi().substring(6) + " pukul " + psc.getJam_reservasi());
            }
            else if(psc.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("10")){
                binding.textViewKeteranganPesanan.setText("Reservasi pada tanggal " + psc.getTanggal_reservasi().substring(0,2) + " Oktober " + psc.getTanggal_reservasi().substring(6) + " pukul " + psc.getJam_reservasi());
            }
            else if(psc.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("11")){
                binding.textViewKeteranganPesanan.setText("Reservasi pada tanggal " + psc.getTanggal_reservasi().substring(0,2) + " November " + psc.getTanggal_reservasi().substring(6) + " pukul " + psc.getJam_reservasi());
            }
            else if(psc.getTanggal_reservasi().substring(3,5).equalsIgnoreCase("12")){
                binding.textViewKeteranganPesanan.setText("Reservasi pada tanggal " + psc.getTanggal_reservasi().substring(0,2) + " Desember " + psc.getTanggal_reservasi().substring(6) + " pukul " + psc.getJam_reservasi());
            }

            binding.textViewTotalHargaPesanan.setText(formatRupiah(Integer.parseInt(psc.getTotal_harga())));
        }
    }

    public interface OnItemClickCallback{
        void OnItemClicked(PesananSayaClass psc);
    }

    public String formatRupiah(int number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}
