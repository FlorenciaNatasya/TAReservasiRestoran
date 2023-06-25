package com.example.tugasakhir;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class PilihMakanan implements Parcelable {
    String id_htrans;
    String nama_makanan;
    String jumlah_makanan;

    public PilihMakanan(String id_htrans, String nama_makanan, String jumlah_makanan) {
        this.id_htrans = id_htrans;
        this.nama_makanan = nama_makanan;
        this.jumlah_makanan = jumlah_makanan;
    }

    public PilihMakanan(String nama_makanan, String jumlah_makanan) {
        this.nama_makanan = nama_makanan;
        this.jumlah_makanan = jumlah_makanan;
    }

    protected PilihMakanan(Parcel in) {
        id_htrans = in.readString();
        nama_makanan = in.readString();
        jumlah_makanan = in.readString();
    }

    public static final Creator<PilihMakanan> CREATOR = new Creator<PilihMakanan>() {
        @Override
        public PilihMakanan createFromParcel(Parcel in) {
            return new PilihMakanan(in);
        }

        @Override
        public PilihMakanan[] newArray(int size) {
            return new PilihMakanan[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId_htrans() {
        return id_htrans;
    }

    public void setId_htrans(String id_htrans) {
        this.id_htrans = id_htrans;
    }

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

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id_htrans);
        parcel.writeString(nama_makanan);
        parcel.writeString(jumlah_makanan);
    }
}
