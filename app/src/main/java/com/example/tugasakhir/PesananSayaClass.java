package com.example.tugasakhir;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class PesananSayaClass implements Parcelable {
    String id_htrans;
    String nama_restoran;
    String tanggal_reservasi;
    String jam_reservasi;
    String total_harga;
    String status_pesanan;

    public PesananSayaClass(String id_htrans, String nama_restoran, String tanggal_reservasi, String jam_reservasi, String total_harga, String status_pesanan) {
        this.id_htrans = id_htrans;
        this.nama_restoran = nama_restoran;
        this.tanggal_reservasi = tanggal_reservasi;
        this.jam_reservasi = jam_reservasi;
        this.total_harga = total_harga;
        this.status_pesanan = status_pesanan;
    }

    protected PesananSayaClass(Parcel in) {
        id_htrans = in.readString();
        nama_restoran = in.readString();
        tanggal_reservasi = in.readString();
        jam_reservasi = in.readString();
        total_harga = in.readString();
        status_pesanan = in.readString();
    }

    public static final Creator<PesananSayaClass> CREATOR = new Creator<PesananSayaClass>() {
        @Override
        public PesananSayaClass createFromParcel(Parcel in) {
            return new PesananSayaClass(in);
        }

        @Override
        public PesananSayaClass[] newArray(int size) {
            return new PesananSayaClass[size];
        }
    };

    public String getId_htrans() {
        return id_htrans;
    }

    public void setId_htrans(String id_htrans) {
        this.id_htrans = id_htrans;
    }

    public String getNama_restoran() {
        return nama_restoran;
    }

    public void setNama_restoran(String nama_restoran) {
        this.nama_restoran = nama_restoran;
    }

    public String getTanggal_reservasi() {
        return tanggal_reservasi;
    }

    public void setTanggal_reservasi(String tanggal_reservasi) {
        this.tanggal_reservasi = tanggal_reservasi;
    }

    public String getJam_reservasi() {
        return jam_reservasi;
    }

    public void setJam_reservasi(String jam_reservasi) {
        this.jam_reservasi = jam_reservasi;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(String total_harga) {
        this.total_harga = total_harga;
    }

    public String getStatus_pesanan() {
        return status_pesanan;
    }

    public void setStatus_pesanan(String status_pesanan) {
        this.status_pesanan = status_pesanan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id_htrans);
        parcel.writeString(nama_restoran);
        parcel.writeString(tanggal_reservasi);
        parcel.writeString(jam_reservasi);
        parcel.writeString(total_harga);
        parcel.writeString(status_pesanan);
    }
}
