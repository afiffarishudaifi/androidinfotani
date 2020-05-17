package com.example.myapplication.Model;

public class ModelPanen {
    private int no;
    private String komoditas;
    private String hasil;
    private String sisa;
    private String tglPanen;
    private String harga;

    public int getNo() {
        return no;
    }

    public String getKomoditas() {
        return komoditas;
    }

    public String getHasil() {
        return hasil;
    }

    public String getSisa() {
        return sisa;
    }

    public String getTglPanen() {
        return tglPanen;
    }

    public String getHarga() {
        return harga;
    }

    public ModelPanen(int no, String kom, String has, String sis, String tgl, String har){
        this.no = no;
        this.komoditas = kom;
        this.hasil = has;
        this.sisa = sis;
        this.tglPanen = tgl;
        this.harga = har;
    }

}