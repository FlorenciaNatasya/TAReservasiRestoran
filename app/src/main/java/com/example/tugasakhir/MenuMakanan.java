package com.example.tugasakhir;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MenuMakanan implements Parcelable {
    String id_menu;
    String nama_menu;
    String kategori_menu;
    String status_menu;
    String deskripsi_menu;
    String harga_menu;
    String id_restoran;

    public MenuMakanan(String id_menu, String nama_menu, String kategori_menu, String status_menu, String deskripsi_menu, String harga_menu) {
        this.id_menu = id_menu;
        this.nama_menu = nama_menu;
        this.kategori_menu = kategori_menu;
        this.status_menu = status_menu;
        this.deskripsi_menu = deskripsi_menu;
        this.harga_menu = harga_menu;
    }

    public MenuMakanan(String nama_menu, String kategori_menu, String status_menu, String deskripsi_menu, String harga_menu) {
        this.nama_menu = nama_menu;
        this.kategori_menu = kategori_menu;
        this.status_menu = status_menu;
        this.deskripsi_menu = deskripsi_menu;
        this.harga_menu = harga_menu;
    }

    public MenuMakanan(String harga_menu, String id_restoran) {
        this.harga_menu = harga_menu;
        this.id_restoran = id_restoran;
    }

    protected MenuMakanan(Parcel in) {
        id_menu = in.readString();
        nama_menu = in.readString();
        kategori_menu = in.readString();
        status_menu = in.readString();
        deskripsi_menu = in.readString();
        harga_menu = in.readString();
        id_restoran = in.readString();
    }

    public static final Creator<MenuMakanan> CREATOR = new Creator<MenuMakanan>() {
        @Override
        public MenuMakanan createFromParcel(Parcel in) {
            return new MenuMakanan(in);
        }

        @Override
        public MenuMakanan[] newArray(int size) {
            return new MenuMakanan[size];
        }
    };

    public String getId_menu() {
        return id_menu;
    }

    public void setId_menu(String id_menu) {
        this.id_menu = id_menu;
    }

    public String getNama_menu() {
        return nama_menu;
    }

    public void setNama_menu(String nama_menu) {
        this.nama_menu = nama_menu;
    }

    public String getKategori_menu() {
        return kategori_menu;
    }

    public void setKategori_menu(String kategori_menu) {
        this.kategori_menu = kategori_menu;
    }

    public String getStatus_menu() {
        return status_menu;
    }

    public void setStatus_menu(String status_menu) {
        this.status_menu = status_menu;
    }

    public String getDeskripsi_menu() {
        return deskripsi_menu;
    }

    public void setDeskripsi_menu(String deskripsi_menu) {
        this.deskripsi_menu = deskripsi_menu;
    }

    public String getHarga_menu() {
        return harga_menu;
    }

    public void setHarga_menu(String harga_menu) {
        this.harga_menu = harga_menu;
    }

    public String getId_restoran() {
        return id_restoran;
    }

    public void setId_restoran(String id_restoran) {
        this.id_restoran = id_restoran;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id_menu);
        parcel.writeString(nama_menu);
        parcel.writeString(kategori_menu);
        parcel.writeString(status_menu);
        parcel.writeString(deskripsi_menu);
        parcel.writeString(harga_menu);
        parcel.writeString(id_restoran);
    }
}
