package com.example.myapplication.Model;

public class DataKomoditas {
    private String idKomoditas, namaKomoditas;

    public DataKomoditas() {
    }

    public DataKomoditas(String idKomoditas, String namaKomoditas) {
        this.idKomoditas = idKomoditas;
        this.namaKomoditas = namaKomoditas;
    }
    public String getIdKomoditas() {
        return idKomoditas;
    }

    public void setIdKomoditas(String idKomoditas) {
        this.idKomoditas = idKomoditas;
    }

    public String getNamaKomoditas() {
        return namaKomoditas;
    }

    public void setNamaKomoditas(String namaKomoditas) {
        this.namaKomoditas = namaKomoditas;
    }
}
