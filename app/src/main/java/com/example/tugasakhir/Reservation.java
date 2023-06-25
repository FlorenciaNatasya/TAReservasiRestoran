package com.example.tugasakhir;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Reservation implements Parcelable {
    String id_customer;
    String nama_client;
    String nomor_telepon;
    String tanggal_reservasi;
    String jam_reservasi;
    String jumlah_orang;
    String note_transaksi;

    public Reservation(String id_customer, String nama_client, String nomor_telepon, String tanggal_reservasi, String jam_reservasi, String jumlah_orang, String note_transaksi) {
        this.id_customer = id_customer;
        this.nama_client = nama_client;
        this.nomor_telepon = nomor_telepon;
        this.tanggal_reservasi = tanggal_reservasi;
        this.jam_reservasi = jam_reservasi;
        this.jumlah_orang = jumlah_orang;
        this.note_transaksi = note_transaksi;
    }


    protected Reservation(Parcel in) {
        id_customer = in.readString();
        nama_client = in.readString();
        nomor_telepon = in.readString();
        tanggal_reservasi = in.readString();
        jam_reservasi = in.readString();
        jumlah_orang = in.readString();
        note_transaksi = in.readString();
    }

    public static final Creator<Reservation> CREATOR = new Creator<Reservation>() {
        @Override
        public Reservation createFromParcel(Parcel in) {
            return new Reservation(in);
        }

        @Override
        public Reservation[] newArray(int size) {
            return new Reservation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
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

    public String getNomor_telepon() {
        return nomor_telepon;
    }

    public void setNomor_telepon(String nomor_telepon) {
        this.nomor_telepon = nomor_telepon;
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

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id_customer);
        parcel.writeString(nama_client);
        parcel.writeString(nomor_telepon);
        parcel.writeString(tanggal_reservasi);
        parcel.writeString(jam_reservasi);
        parcel.writeString(jumlah_orang);
        parcel.writeString(note_transaksi);
    }
}
