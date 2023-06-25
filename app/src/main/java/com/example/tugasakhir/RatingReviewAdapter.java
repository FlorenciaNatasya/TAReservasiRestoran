package com.example.tugasakhir;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasakhir.databinding.ItemRvRatingReviewBinding;

import java.util.ArrayList;

public class RatingReviewAdapter extends RecyclerView.Adapter<RatingReviewAdapter.ViewHolder> {

    ArrayList<RatingReviewClass> listRR;

    public RatingReviewAdapter(ArrayList<RatingReviewClass> listRR) {
        this.listRR = listRR;
    }

    @NonNull
    @Override
    public RatingReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvRatingReviewBinding binding = ItemRvRatingReviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingReviewAdapter.ViewHolder holder, int position) {
        RatingReviewClass rrc = listRR.get(position);
        holder.bind(rrc);
    }

    @Override
    public int getItemCount() {
        return listRR.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemRvRatingReviewBinding binding;
        public ViewHolder(@NonNull ItemRvRatingReviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(RatingReviewClass rrc){
            binding.textViewBintangResto.setText(rrc.getJumlah_bintang());
            binding.textViewReviewResto.setText(rrc.getReview());
        }
    }
}
