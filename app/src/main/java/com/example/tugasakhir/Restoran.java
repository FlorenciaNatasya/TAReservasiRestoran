package com.example.tugasakhir;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Restoran implements Parcelable {
    String id_restoran;
    String username_restoran;
    String password_restoran;
    String nama_restoran;
    String alamat_restoran;
    String daerah_restoran;
    String email_restoran;
    String status_restoran;

    public Restoran(String username_restoran, String password_restoran, String nama_restoran, String alamat_restoran, String daerah_restoran, String email_restoran, String status_restoran) {
        this.username_restoran = username_restoran;
        this.password_restoran = password_restoran;
        this.nama_restoran = nama_restoran;
        this.alamat_restoran = alamat_restoran;
        this.daerah_restoran = daerah_restoran;
        this.email_restoran = email_restoran;
        this.status_restoran = status_restoran;
    }

    public Restoran(String id_restoran, String username_restoran, String password_restoran, String nama_restoran, String alamat_restoran, String daerah_restoran, String email_restoran, String status_restoran) {
        this.id_restoran = id_restoran;
        this.username_restoran = username_restoran;
        this.password_restoran = password_restoran;
        this.nama_restoran = nama_restoran;
        this.alamat_restoran = alamat_restoran;
        this.daerah_restoran = daerah_restoran;
        this.email_restoran = email_restoran;
        this.status_restoran = status_restoran;
    }

    public Restoran(String id_restoran, String nama_restoran, String alamat_restoran, String daerah_restoran) {
        this.id_restoran = id_restoran;
        this.nama_restoran = nama_restoran;
        this.alamat_restoran = alamat_restoran;
        this.daerah_restoran = daerah_restoran;
    }

    protected Restoran(Parcel in) {
        id_restoran = in.readString();
        username_restoran = in.readString();
        password_restoran = in.readString();
        nama_restoran = in.readString();
        alamat_restoran = in.readString();
        daerah_restoran = in.readString();
        email_restoran = in.readString();
        status_restoran = in.readString();
    }

    public static final Creator<Restoran> CREATOR = new Creator<Restoran>() {
        @Override
        public Restoran createFromParcel(Parcel in) {
            return new Restoran(in);
        }

        @Override
        public Restoran[] newArray(int size) {
            return new Restoran[size];
        }
    };

    public String getId_restoran() {
        return id_restoran;
    }

    public void setId_restoran(String id_restoran) {
        this.id_restoran = id_restoran;
    }

    public String getUsername_restoran() {
        return username_restoran;
    }

    public void setUsername_restoran(String username_restoran) {
        this.username_restoran = username_restoran;
    }

    public String getPassword_restoran() {
        return password_restoran;
    }

    public void setPassword_restoran(String password_restoran) {
        this.password_restoran = password_restoran;
    }

    public String getNama_restoran() {
        return nama_restoran;
    }

    public void setNama_restoran(String nama_restoran) {
        this.nama_restoran = nama_restoran;
    }

    public String getAlamat_restoran() {
        return alamat_restoran;
    }

    public void setAlamat_restoran(String alamat_restoran) {
        this.alamat_restoran = alamat_restoran;
    }

    public String getDaerah_restoran() {
        return daerah_restoran;
    }

    public void setDaerah_restoran(String daerah_restoran) {
        this.daerah_restoran = daerah_restoran;
    }

    public String getEmail_restoran() {
        return email_restoran;
    }

    public void setEmail_restoran(String email_restoran) {
        this.email_restoran = email_restoran;
    }

    public String getStatus_restoran() {
        return status_restoran;
    }

    public void setStatus_restoran(String status_restoran) {
        this.status_restoran = status_restoran;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id_restoran);
        parcel.writeString(username_restoran);
        parcel.writeString(password_restoran);
        parcel.writeString(nama_restoran);
        parcel.writeString(alamat_restoran);
        parcel.writeString(daerah_restoran);
        parcel.writeString(email_restoran);
        parcel.writeString(status_restoran);
    }
}
