package com.example.tugasakhir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tugasakhir.databinding.ItemRvMenuMakananBinding;
import com.example.tugasakhir.databinding.ItemRvPilihMakananBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PilihMakananAdapter extends RecyclerView.Adapter<PilihMakananAdapter.ViewHolder> {

    ArrayList<MenuMakanan> listPilihMakanan;
    Context ctx;
    OnItemClickCallback onItemClickCallback;
    int jmlh;
    int total = 0;
    int totalharga= 0;

    public PilihMakananAdapter(ArrayList<MenuMakanan> listPilihMakanan, Context ctx) {
        this.listPilihMakanan = listPilihMakanan;
        this.ctx = ctx;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public PilihMakananAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvPilihMakananBinding binding = ItemRvPilihMakananBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PilihMakananAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PilihMakananAdapter.ViewHolder holder, int position) {
        MenuMakanan m = listPilihMakanan.get(position);
        holder.bind(m);
    }

    @Override
    public int getItemCount() {
        return listPilihMakanan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemRvPilihMakananBinding binding;
        public ViewHolder(@NonNull ItemRvPilihMakananBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(MenuMakanan menu){
            binding.tvNamaMenu.setText(menu.getNama_menu());
            binding.tvDescMenu.setText(menu.getDeskripsi_menu());
            Glide.with(ctx).load("https://reservasirestoran.me/imagesMenu/"+ menu.getNama_menu().replace(" ", "") +".jpg").into(binding.imageViewMenu);
            binding.tvHargaMenu.setText(formatRupiah(Integer.parseInt(menu.getHarga_menu())));

            binding.buttonPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jmlh = Integer.parseInt(binding.tvJumlahMakananYangDipilih.getText().toString());
                    jmlh+=1;
                    binding.tvJumlahMakananYangDipilih.setText(String.valueOf(jmlh));
                    total+=1;
                    totalharga += Integer.parseInt(menu.getHarga_menu());

                    onItemClickCallback.OnPlusClicked(menu, binding.tvJumlahMakananYangDipilih.getText().toString());
                }
            });

            binding.buttonMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jmlh = Integer.parseInt(binding.tvJumlahMakananYangDipilih.getText().toString());
                    if(jmlh==0){
                        binding.tvJumlahMakananYangDipilih.setText("0");
                    }
                    else{
                        jmlh-=1;
                        binding.tvJumlahMakananYangDipilih.setText(String.valueOf(jmlh));
                    }
                    if(total == 0){
                        total = 0;
                        totalharga = 0;
                    }
                    else {
                        total-=1;
                        totalharga -= Integer.parseInt(menu.getHarga_menu());
                    }
                    onItemClickCallback.OnMinusClicked(menu, binding.tvJumlahMakananYangDipilih.getText().toString());
                }
            });
        }
    }

    public interface OnItemClickCallback{
        void OnPlusClicked(MenuMakanan menu, String jmlh);
        void OnMinusClicked(MenuMakanan menu, String jmlh);
    }

    public String formatRupiah(int number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

    public int TotalItem(){
        return total;
    }

    public String TotalHarga(){
        int tadmin = (totalharga * 25 / 100) + totalharga;
        return String.valueOf(tadmin);
    }

    public String BiayaAdmin(){
        int tadmin = totalharga * 25 / 100;
        return String.valueOf(formatRupiah(tadmin));
    }

    public String TotalPesanan(){
        return String.valueOf(totalharga);
    }
}
