package com.example.tugasakhir;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class VoucherRestoran implements Parcelable {
    String id_voucher;
    String kode_voucher;
    String jenis_voucher;
    String jenis_potongan;
    String jumlah_diskon;
    String kuota_voucher;
    String minimum_pembelian;
    String maksimal_potongan;
    String tanggal_awal;
    String tanggal_akhir;
    String status_voucher;
    String id_restoran;

    public VoucherRestoran(String id_voucher, String kode_voucher, String jenis_voucher, String jenis_potongan, String jumlah_diskon, String kuota_voucher, String minimum_pembelian, String maksimal_potongan, String tanggal_awal, String tanggal_akhir, String status_voucher, String id_restoran) {
        this.id_voucher = id_voucher;
        this.kode_voucher = kode_voucher;
        this.jenis_voucher = jenis_voucher;
        this.jenis_potongan = jenis_potongan;
        this.jumlah_diskon = jumlah_diskon;
        this.kuota_voucher = kuota_voucher;
        this.minimum_pembelian = minimum_pembelian;
        this.maksimal_potongan = maksimal_potongan;
        this.tanggal_awal = tanggal_awal;
        this.tanggal_akhir = tanggal_akhir;
        this.status_voucher = status_voucher;
        this.id_restoran = id_restoran;
    }

    public VoucherRestoran(String kode_voucher, String jenis_voucher, String jenis_potongan, String jumlah_diskon, String kuota_voucher, String minimum_pembelian, String maksimal_potongan, String tanggal_awal, String tanggal_akhir, String status_voucher, String id_restoran) {
        this.kode_voucher = kode_voucher;
        this.jenis_voucher = jenis_voucher;
        this.jenis_potongan = jenis_potongan;
        this.jumlah_diskon = jumlah_diskon;
        this.kuota_voucher = kuota_voucher;
        this.minimum_pembelian = minimum_pembelian;
        this.maksimal_potongan = maksimal_potongan;
        this.tanggal_awal = tanggal_awal;
        this.tanggal_akhir = tanggal_akhir;
        this.status_voucher = status_voucher;
        this.id_restoran = id_restoran;
    }

    public VoucherRestoran(String kode_voucher, String jenis_voucher, String jenis_potongan, String jumlah_diskon, String kuota_voucher, String minimum_pembelian, String maksimal_potongan, String tanggal_awal, String tanggal_akhir) {
        this.kode_voucher = kode_voucher;
        this.jenis_voucher = jenis_voucher;
        this.jenis_potongan = jenis_potongan;
        this.jumlah_diskon = jumlah_diskon;
        this.kuota_voucher = kuota_voucher;
        this.minimum_pembelian = minimum_pembelian;
        this.maksimal_potongan = maksimal_potongan;
        this.tanggal_awal = tanggal_awal;
        this.tanggal_akhir = tanggal_akhir;
    }

    protected VoucherRestoran(Parcel in) {
        id_voucher = in.readString();
        kode_voucher = in.readString();
        jenis_voucher = in.readString();
        jenis_potongan = in.readString();
        jumlah_diskon = in.readString();
        kuota_voucher = in.readString();
        minimum_pembelian = in.readString();
        maksimal_potongan = in.readString();
        status_voucher = in.readString();
        id_restoran = in.readString();
    }

    public static final Creator<VoucherRestoran> CREATOR = new Creator<VoucherRestoran>() {
        @Override
        public VoucherRestoran createFromParcel(Parcel in) {
            return new VoucherRestoran(in);
        }

        @Override
        public VoucherRestoran[] newArray(int size) {
            return new VoucherRestoran[size];
        }
    };

    public String getId_voucher() {
        return id_voucher;
    }

    public void setId_voucher(String id_voucher) {
        this.id_voucher = id_voucher;
    }

    public String getKode_voucher() {
        return kode_voucher;
    }

    public void setKode_voucher(String kode_voucher) {
        this.kode_voucher = kode_voucher;
    }

    public String getJenis_voucher() {
        return jenis_voucher;
    }

    public void setJenis_voucher(String jenis_voucher) {
        this.jenis_voucher = jenis_voucher;
    }

    public String getJenis_potongan() {
        return jenis_potongan;
    }

    public void setJenis_potongan(String jenis_potongan) {
        this.jenis_potongan = jenis_potongan;
    }

    public String getJumlah_diskon() {
        return jumlah_diskon;
    }

    public void setJumlah_diskon(String jumlah_diskon) {
        this.jumlah_diskon = jumlah_diskon;
    }

    public String getKuota_voucher() {
        return kuota_voucher;
    }

    public void setKuota_voucher(String kuota_voucher) {
        this.kuota_voucher = kuota_voucher;
    }

    public String getMinimum_pembelian() {
        return minimum_pembelian;
    }

    public void setMinimum_pembelian(String minimum_pembelian) {
        this.minimum_pembelian = minimum_pembelian;
    }

    public String getMaksimal_potongan() {
        return maksimal_potongan;
    }

    public void setMaksimal_potongan(String maksimal_potongan) {
        this.maksimal_potongan = maksimal_potongan;
    }

    public String getTanggal_awal() {
        return tanggal_awal;
    }

    public void setTanggal_awal(String tanggal_awal) {
        this.tanggal_awal = tanggal_awal;
    }

    public String getTanggal_akhir() {
        return tanggal_akhir;
    }

    public void setTanggal_akhir(String tanggal_akhir) {
        this.tanggal_akhir = tanggal_akhir;
    }

    public String getStatus_voucher() {
        return status_voucher;
    }

    public void setStatus_voucher(String status_voucher) {
        this.status_voucher = status_voucher;
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
        parcel.writeString(id_voucher);
        parcel.writeString(kode_voucher);
        parcel.writeString(jenis_voucher);
        parcel.writeString(jenis_potongan);
        parcel.writeString(jumlah_diskon);
        parcel.writeString(kuota_voucher);
        parcel.writeString(minimum_pembelian);
        parcel.writeString(maksimal_potongan);
        parcel.writeString(tanggal_awal);
        parcel.writeString(tanggal_akhir);
        parcel.writeString(status_voucher);
        parcel.writeString(id_restoran);
    }
}
