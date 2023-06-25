package com.example.tugasakhir;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Customer implements Parcelable {
    String id;
    String username;
    String password;
    String nama;
    String email;
    String status;

    public Customer(String id, String username, String password, String nama, String email, String status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nama = nama;
        this.email = email;
        this.status = status;
    }

    public Customer(String username, String password, String nama, String email, String status) {
        this.username = username;
        this.password = password;
        this.nama = nama;
        this.email = email;
        this.status = status;
    }

    public Customer(String username, String nama, String email) {
        this.username = username;
        this.nama = nama;
        this.email = email;
    }

    public Customer(String username, String password, String nama, String email) {
        this.username = username;
        this.password = password;
        this.nama = nama;
        this.email = email;
    }

    protected Customer(Parcel in) {
        id = in.readString();
        username = in.readString();
        password = in.readString();
        nama = in.readString();
        email = in.readString();
        status = in.readString();
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(username);
        parcel.writeString(password);
        parcel.writeString(nama);
        parcel.writeString(email);
        parcel.writeString(status);
    }
}
