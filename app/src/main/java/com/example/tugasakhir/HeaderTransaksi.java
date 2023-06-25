package com.example.tugasakhir;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class HeaderTransaksi implements Parcelable {
    String id_htrans_reservasi;
    String id_customer;
    String nama_client;
    String nomor_telepon_client;
    String tanggal_reservasi;
    String jam_reservasi;
    String jumlah_orang;
    String note_transaksi;
    String total_harga;
    String id_restoran;
    String status_pesanan;

    public HeaderTransaksi(String id_htrans_reservasi, String id_customer, String nama_client, String nomor_telepon_client, String tanggal_reservasi, String jam_reservasi, String jumlah_orang, String note_transaksi, String total_harga, String id_restoran, String status_pesanan) {
        this.id_htrans_reservasi = id_htrans_reservasi;
        this.id_customer = id_customer;
        this.nama_client = nama_client;
        this.nomor_telepon_client = nomor_telepon_client;
        this.tanggal_reservasi = tanggal_reservasi;
        this.jam_reservasi = jam_reservasi;
        this.jumlah_orang = jumlah_orang;
        this.note_transaksi = note_transaksi;
        this.total_harga = total_harga;
        this.id_restoran = id_restoran;
        this.status_pesanan = status_pesanan;
    }

    protected HeaderTransaksi(Parcel in) {
        id_htrans_reservasi = in.readString();
        id_customer = in.readString();
        nama_client = in.readString();
        nomor_telepon_client = in.readString();
        tanggal_reservasi = in.readString();
        jam_reservasi = in.readString();
        jumlah_orang = in.readString();
        note_transaksi = in.readString();
        total_harga = in.readString();
        id_restoran = in.readString();
        status_pesanan = in.readString();
    }

    public static final Creator<HeaderTransaksi> CREATOR = new Creator<HeaderTransaksi>() {
        @Override
        public HeaderTransaksi createFromParcel(Parcel in) {
            return new HeaderTransaksi(in);
        }

        @Override
        public HeaderTransaksi[] newArray(int size) {
            return new HeaderTransaksi[size];
        }
    };

    public String getId_htrans_reservasi() {
        return id_htrans_reservasi;
    }

    public void setId_htrans_reservasi(String id_htrans_reservasi) {
        this.id_htrans_reservasi = id_htrans_reservasi;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public String getNama_client() {
        return nama_client;
    }

    public void setNama_client(String nama_client) {
        this.nama_client = nama_client;
    }

    public String getNomor_telepon_client() {
        return nomor_telepon_client;
    }

    public void setNomor_telepon_client(String nomor_telepon_client) {
        this.nomor_telepon_client = nomor_telepon_client;
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

    public String getJumlah_orang() {
        return jumlah_orang;
    }

    public void setJumlah_orang(String jumlah_orang) {
        this.jumlah_orang = jumlah_orang;
    }

    public String getNote_transaksi() {
        return note_transaksi;
    }

    public void setNote_transaksi(String note_transaksi) {
        this.note_transaksi = note_transaksi;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(String total_harga) {
        this.total_harga = total_harga;
    }

    public String getId_restoran() {
        return id_restoran;
    }

    public void setId_restoran(String id_restoran) {
        this.id_restoran = id_restoran;
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
        parcel.writeString(id_htrans_reservasi);
        parcel.writeString(id_customer);
        parcel.writeString(nama_client);
        parcel.writeString(nomor_telepon_client);
        parcel.writeString(tanggal_reservasi);
        parcel.writeString(jam_reservasi);
        parcel.writeString(jumlah_orang);
        parcel.writeString(note_transaksi);
        parcel.writeString(total_harga);
        parcel.writeString(id_restoran);
        parcel.writeString(status_pesanan);
    }
}
