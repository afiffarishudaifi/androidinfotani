package com.example.myapplication.Model;

public class PanenModel {
    private String no;
    private String komoditas;
    private String tgl;
    private String hasilawal;
    private String hasilsisa;
    private String harga;

    public PanenModel(String no, String komoditas, String tgl, String hasilawal, String hasilsisa, String harga) {
        this.no = no;
        this.komoditas = komoditas;
        this.tgl = tgl;
        this.hasilawal = hasilawal;
        this.hasilsisa = hasilsisa;
        this.harga = harga;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getKomoditas() {
        return komoditas;
    }

    public void setKomoditas(String komoditas) {
        this.komoditas = komoditas;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public String getHasilawal() {
        return hasilawal;
    }

    public void setHasilawal(String hasilawal) {
        this.hasilawal = hasilawal;
    }

    public String getHasilsisa() {
        return hasilsisa;
    }

    public void setHasilsisa(String hasilsisa) {
        this.hasilsisa = hasilsisa;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
