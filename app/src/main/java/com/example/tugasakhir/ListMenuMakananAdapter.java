package com.example.tugasakhir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tugasakhir.databinding.ItemRvMenuMakananBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ListMenuMakananAdapter extends RecyclerView.Adapter<ListMenuMakananAdapter.ViewHolder> {

    ArrayList<MenuMakanan> listMenu;
    Context ctx;
    OnItemClickCallback onItemClickCallback;
    String idresto;

    public ListMenuMakananAdapter(ArrayList<MenuMakanan> listMenu, Context ctx, String idresto) {
        this.listMenu = listMenu;
        this.ctx = ctx;
        this.idresto = idresto;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ListMenuMakananAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvMenuMakananBinding binding = ItemRvMenuMakananBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ListMenuMakananAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMenuMakananAdapter.ViewHolder holder, int position) {
        MenuMakanan menu = listMenu.get(position);
        holder.bind(menu);
    }

    @Override
    public int getItemCount() {
        return listMenu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemRvMenuMakananBinding binding;
        public ViewHolder(@NonNull ItemRvMenuMakananBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(MenuMakanan menu){
            binding.textViewNamaMenu.setText(menu.getNama_menu());
            binding.textViewDeskripsiMenu.setText(menu.getDeskripsi_menu());
            Glide.with(ctx).load("https://github.com/FlorenciaNatasya/BackendTA/imagesMenu/"+ menu.getNama_menu().replace(" ", "") + idresto +".jpg").into(binding.imageViewMenuMakanan);
            binding.textViewHargaMenu.setText(formatRupiah(Integer.parseInt(menu.getHarga_menu())));

            binding.buttonEditMenuMakanan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickCallback.OnEditClicked(menu);
                }
            });

            binding.buttonHapusMenuMakanan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickCallback.OnDeleteClicked(menu);
                }
            });
        }
    }

    public interface OnItemClickCallback{
        void OnEditClicked(MenuMakanan menu);
        void OnDeleteClicked(MenuMakanan menu);
    }

    public String formatRupiah(int number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}
