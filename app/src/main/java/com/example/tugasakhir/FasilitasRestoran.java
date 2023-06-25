package com.example.tugasakhir;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class FasilitasRestoran implements Parcelable {
    String id_fasilitas;
    String nama_fasilitas;
    String jumlah_fasilitas;
    String status_fasilitas;
    String id_restoran;

    public FasilitasRestoran(String id_fasilitas, String nama_fasilitas, String jumlah_fasilitas, String status_fasilitas, String id_restoran) {
        this.id_fasilitas = id_fasilitas;
        this.nama_fasilitas = nama_fasilitas;
        this.jumlah_fasilitas = jumlah_fasilitas;
        this.status_fasilitas = status_fasilitas;
        this.id_restoran = id_restoran;
    }

    public FasilitasRestoran(String nama_fasilitas, String jumlah_fasilitas, String status_fasilitas, String id_restoran) {
        this.nama_fasilitas = nama_fasilitas;
        this.jumlah_fasilitas = jumlah_fasilitas;
        this.status_fasilitas = status_fasilitas;
        this.id_restoran = id_restoran;
    }

    protected FasilitasRestoran(Parcel in) {
        id_fasilitas = in.readString();
        nama_fasilitas = in.readString();
        jumlah_fasilitas = in.readString();
        status_fasilitas = in.readString();
        id_restoran = in.readString();
    }

    public static final Creator<FasilitasRestoran> CREATOR = new Creator<FasilitasRestoran>() {
        @Override
        public FasilitasRestoran createFromParcel(Parcel in) {
            return new FasilitasRestoran(in);
        }

        @Override
        public FasilitasRestoran[] newArray(int size) {
            return new FasilitasRestoran[size];
        }
    };

    public String getId_fasilitas() {
        return id_fasilitas;
    }

    public void setId_fasilitas(String id_fasilitas) {
        this.id_fasilitas = id_fasilitas;
    }

    public String getNama_fasilitas() {
        return nama_fasilitas;
    }

    public void setNama_fasilitas(String nama_fasilitas) {
        this.nama_fasilitas = nama_fasilitas;
    }

    public String getJumlah_fasilitas() {
        return jumlah_fasilitas;
    }

    public void setJumlah_fasilitas(String jumlah_fasilitas) {
        this.jumlah_fasilitas = jumlah_fasilitas;
    }

    public String getStatus_fasilitas() {
        return status_fasilitas;
    }

    public void setStatus_fasilitas(String status_fasilitas) {
        this.status_fasilitas = status_fasilitas;
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
        parcel.writeString(id_fasilitas);
        parcel.writeString(nama_fasilitas);
        parcel.writeString(jumlah_fasilitas);
        parcel.writeString(status_fasilitas);
        parcel.writeString(id_restoran);
    }
}
