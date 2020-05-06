package com.example.myapplication.Model;

public class DataDesa {
    private String idDesa, namaDesa;

    public DataDesa() {
    }

    public DataDesa(String idDesa, String namaDesa) {
        this.idDesa = idDesa;
        this.namaDesa = namaDesa;
    }
    public String getIdDesa() {
        return idDesa;
    }

    public void setIdDesa(String idDesa) {
        this.idDesa = idDesa;
    }

    public String getNamaDesa() {
        return namaDesa;
    }

    public void setNamaDesa(String namaDesa) {
        this.namaDesa = namaDesa;
    }
}
