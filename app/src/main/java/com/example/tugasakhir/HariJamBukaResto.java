package com.example.tugasakhir;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class HariJamBukaResto implements Parcelable {
    String id_restoran;
    String hari_buka;
    String jam_buka;

    public HariJamBukaResto(String id_restoran, String hari_buka, String jam_buka) {
        this.id_restoran = id_restoran;
        this.hari_buka = hari_buka;
        this.jam_buka = jam_buka;
    }

    public HariJamBukaResto(String hari_buka, String jam_buka) {
        this.hari_buka = hari_buka;
        this.jam_buka = jam_buka;
    }

    protected HariJamBukaResto(Parcel in) {
        id_restoran = in.readString();
        hari_buka = in.readString();
        jam_buka = in.readString();
    }

    public static final Creator<HariJamBukaResto> CREATOR = new Creator<HariJamBukaResto>() {
        @Override
        public HariJamBukaResto createFromParcel(Parcel in) {
            return new HariJamBukaResto(in);
        }

        @Override
        public HariJamBukaResto[] newArray(int size) {
            return new HariJamBukaResto[size];
        }
    };

    public String getId_restoran() {
        return id_restoran;
    }

    public void setId_restoran(String id_restoran) {
        this.id_restoran = id_restoran;
    }

    public String getHari_buka() {
        return hari_buka;
    }

    public void setHari_buka(String hari_buka) {
        this.hari_buka = hari_buka;
    }

    public String getJam_buka() {
        return jam_buka;
    }

    public void setJam_buka(String jam_buka) {
        this.jam_buka = jam_buka;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id_restoran);
        parcel.writeString(hari_buka);
        parcel.writeString(jam_buka);
    }
}
