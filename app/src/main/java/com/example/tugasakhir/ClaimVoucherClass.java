package com.example.tugasakhir;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ClaimVoucherClass implements Parcelable {
    String kode_voucher;
    String id_customer;

    public ClaimVoucherClass(String kode_voucher, String id_customer) {
        this.kode_voucher = kode_voucher;
        this.id_customer = id_customer;
    }

    protected ClaimVoucherClass(Parcel in) {
        kode_voucher = in.readString();
        id_customer = in.readString();
    }

    public static final Creator<ClaimVoucherClass> CREATOR = new Creator<ClaimVoucherClass>() {
        @Override
        public ClaimVoucherClass createFromParcel(Parcel in) {
            return new ClaimVoucherClass(in);
        }

        @Override
        public ClaimVoucherClass[] newArray(int size) {
            return new ClaimVoucherClass[size];
        }
    };

    public String getKode_voucher() {
        return kode_voucher;
    }

    public void setKode_voucher(String kode_voucher) {
        this.kode_voucher = kode_voucher;
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
        parcel.writeString(kode_voucher);
        parcel.writeString(id_customer);
    }
}
