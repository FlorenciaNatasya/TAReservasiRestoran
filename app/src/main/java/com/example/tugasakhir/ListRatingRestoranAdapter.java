package com.example.tugasakhir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tugasakhir.databinding.ItemListRestoranBinding;

import java.util.ArrayList;

public class ListRatingRestoranAdapter extends RecyclerView.Adapter<ListRatingRestoranAdapter.ViewHolder> {

    ArrayList<Restoran> listresto;
    ArrayList<RatingReviewClass> listRR;
    Context ctx;

    public ListRatingRestoranAdapter(ArrayList<Restoran> listresto, Context ctx, ArrayList<RatingReviewClass> listRR) {
        this.listresto = listresto;
        this.ctx = ctx;
        this.listRR = listRR;
    }

    @NonNull
    @Override
    public ListRatingRestoranAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListRestoranBinding binding = ItemListRestoranBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListRatingRestoranAdapter.ViewHolder holder, int position) {
        Restoran r = listresto.get(position);
        holder.bind(r);
    }

    @Override
    public int getItemCount() {
        return listresto.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemListRestoranBinding binding;
        public ViewHolder(@NonNull ItemListRestoranBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Restoran resto){
            binding.textViewNamaRestoran.setText(resto.getNama_restoran());
            binding.textViewAlamatRestoran.setText(resto.getAlamat_restoran());
            Glide.with(ctx).load("http://10.0.2.2/TA-service/imagesResto/"+ resto.getNama_restoran().replace(" ", "") + "BagianDepan" +".jpg").into(binding.imageView8);

            float rating = 0;
            int jmlhcustrat = 0;
            boolean ada = false;
            for (int j = 0; j < listRR.size(); j++) {
                if(resto.getId_restoran().equals(listRR.get(j).getId_restoran())){
                    rating += Float.parseFloat(listRR.get(j).getJumlah_bintang());
                    jmlhcustrat++;
                    ada = true;
                }
            }

            if(ada == false){
                rating = 0;
                binding.textViewRatingRestoran.setText(String.format("%.0f", rating));
                //dibuat class id dan angka rating trs nti di sort
            }
            else{
                float hasil = rating / jmlhcustrat;
                String rat = String.format("%.1f", hasil);
                if(rat.substring(rat.length()-1).equals("0")){
                    binding.textViewRatingRestoran.setText(rat.substring(0,1));
                }
                else{
                    binding.textViewRatingRestoran.setText(String.format("%.1f", hasil));
                }
            }

            binding.imageViewProfileRestoran.setVisibility(View.INVISIBLE);
            binding.imageViewChatResto.setVisibility(View.INVISIBLE);
            binding.imageViewVoucherResto.setVisibility(View.INVISIBLE);
            binding.imageViewKalenderBooking.setVisibility(View.INVISIBLE);
        }
    }
}
