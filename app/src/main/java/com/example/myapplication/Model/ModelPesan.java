package com.example.myapplication.Model;

public class ModelPesan {
    private int no;
    private String namaP, komoditasP, tglP, jmlP, biayaP, statusP;

    public ModelPesan(int no, String namaP, String komoditasP, String tglP, String jmlP, String biayaP, String statusP) {
        this.no = no;
        this.namaP = namaP;
        this.komoditasP = komoditasP;
        this.tglP = tglP;
        this.jmlP = jmlP;
        this.biayaP = biayaP;
        this.statusP = statusP;
    }

    public int getNo() {
        return no;
    }

    public String getNamaP() {
        return namaP;
    }

    public String getKomoditasP() {
        return komoditasP;
    }

    public String getTglP() {
        return tglP;
    }

    public String getJmlP() {
        return jmlP;
    }

    public String getBiayaP() {
        return biayaP;
    }

    public String getStatusP() {
        return statusP;
    }
}
