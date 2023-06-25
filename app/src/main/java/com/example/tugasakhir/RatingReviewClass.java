package com.example.tugasakhir;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class RatingReviewClass implements Parcelable {
    String jumlah_bintang;
    String review;
    String kategori_pilihan;
    String id_restoran;
    String id_customer;

    public RatingReviewClass(String jumlah_bintang, String review, String kategori_pilihan, String id_restoran, String id_customer) {
        this.jumlah_bintang = jumlah_bintang;
        this.review = review;
        this.kategori_pilihan = kategori_pilihan;
        this.id_restoran = id_restoran;
        this.id_customer = id_customer;
    }

    public RatingReviewClass(String jumlah_bintang, String id_restoran) {
        this.jumlah_bintang = jumlah_bintang;
        this.id_restoran = id_restoran;
    }

    protected RatingReviewClass(Parcel in) {
        jumlah_bintang = in.readString();
        review = in.readString();
        kategori_pilihan = in.readString();
        id_restoran = in.readString();
        id_customer = in.readString();
    }

    public static final Creator<RatingReviewClass> CREATOR = new Creator<RatingReviewClass>() {
        @Override
        public RatingReviewClass createFromParcel(Parcel in) {
            return new RatingReviewClass(in);
        }

        @Override
        public RatingReviewClass[] newArray(int size) {
            return new RatingReviewClass[size];
        }
    };

    public String getJumlah_bintang() {
        return jumlah_bintang;
    }

    public void setJumlah_bintang(String jumlah_bintang) {
        this.jumlah_bintang = jumlah_bintang;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getKategori_pilihan() {
        return kategori_pilihan;
    }

    public void setKategori_pilihan(String kategori_pilihan) {
        this.kategori_pilihan = kategori_pilihan;
    }

    public String getId_restoran() {
        return id_restoran;
    }

    public void setId_restoran(String id_restoran) {
        this.id_restoran = id_restoran;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(jumlah_bintang);
        parcel.writeString(review);
        parcel.writeString(kategori_pilihan);
        parcel.writeString(id_restoran);
        parcel.writeString(id_customer);
    }
}
