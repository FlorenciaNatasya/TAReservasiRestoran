package com.example.tugasakhir;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class DetailTransaksi implements Parcelable {
    String nama_makanan;
    String jumlah_makanan;
    String harga_makanan;

    public DetailTransaksi(String nama_makanan, String jumlah_makanan, String harga_makanan) {
        this.nama_makanan = nama_makanan;
        this.jumlah_makanan = jumlah_makanan;
        this.harga_makanan = harga_makanan;
    }

    public DetailTransaksi(String nama_makanan, String jumlah_makanan) {
        this.nama_makanan = nama_makanan;
        this.jumlah_makanan = jumlah_makanan;
    }

    protected DetailTransaksi(Parcel in) {
        nama_makanan = in.readString();
        jumlah_makanan = in.readString();
        harga_makanan = in.readString();
    }

    public static final Creator<DetailTransaksi> CREATOR = new Creator<DetailTransaksi>() {
        @Override
        public DetailTransaksi createFromParcel(Parcel in) {
            return new DetailTransaksi(in);
        }

        @Override
        public DetailTransaksi[] newArray(int size) {
            return new DetailTransaksi[size];
        }
    };

    public String getNama_makanan() {
        return nama_makanan;
    }

    public void setNama_makanan(String nama_makanan) {
        this.nama_makanan = nama_makanan;
    }

    public String getJumlah_makanan() {
        return jumlah_makanan;
    }

    public void setJumlah_makanan(String jumlah_makanan) {
        this.jumlah_makanan = jumlah_makanan;
    }

    public String getHarga_makanan() {
        return harga_makanan;
    }

    public void setHarga_makanan(String harga_makanan) {
        this.harga_makanan = harga_makanan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(nama_makanan);
        parcel.writeString(jumlah_makanan);
        parcel.writeString(harga_makanan);
    }
}
