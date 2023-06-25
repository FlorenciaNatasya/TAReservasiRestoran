package com.example.tugasakhir;

public class FasilitasYangDipinjamClass {
    String id_htrans;
    String namfas;
    String jmlh;
    String id_fasilitas;

    public FasilitasYangDipinjamClass(String namfas, String jmlh, String id_fasilitas) {
        this.namfas = namfas;
        this.jmlh = jmlh;
        this.id_fasilitas = id_fasilitas;
    }

    public FasilitasYangDipinjamClass(String id_htrans, String namfas, String jmlh, String id_fasilitas) {
        this.id_htrans = id_htrans;
        this.namfas = namfas;
        this.jmlh = jmlh;
        this.id_fasilitas = id_fasilitas;
    }

    public String getId_htrans() {
        return id_htrans;
    }

    public void setId_htrans(String id_htrans) {
        this.id_htrans = id_htrans;
    }

    public String getNamfas() {
        return namfas;
    }

    public void setNamfas(String namfas) {
        this.namfas = namfas;
    }

    public String getJmlh() {
        return jmlh;
    }

    public void setJmlh(String jmlh) {
        this.jmlh = jmlh;
    }

    public String getId_fasilitas() {
        return id_fasilitas;
    }

    public void setId_fasilitas(String id_fasilitas) {
        this.id_fasilitas = id_fasilitas;
    }
}
