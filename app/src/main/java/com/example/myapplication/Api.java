package com.example.myapplication;

public class Api {
    private String URL_LOGIN = "http://192.168.43.171/ciinfotani/android/loginapi";
    private String URL_REGIST = "http://192.168.43.171/ciinfotani/android/registerapi";
    private String URL_DESA = "http://192.168.43.171/ciinfotani/android/get_desa";
    private String URL_KOMODITAS = "http://192.168.43.171/ciinfotani/android/get_komoditas";
    private String URL_DATA_PETANI = "http://192.168.43.171/ciinfotani/android/data_petaniapi";
    private String URL_FILL_DATA_PETANI = "http://192.168.43.171/ciinfotani/android/fill_data";
    private String URL_PANEN = "http://192.168.43.171/ciinfotani/android/data_panenapi";
    private String URL_FILL_DATA_PANEN = "http://192.168.43.171/ciinfotani/android/fill_data_panen";
    private String URL_CEK_PANEN = "http://192.168.43.171/ciinfotani/android/cek_panen";
    private String URL_LAP_PANEN = "http://192.168.43.171/ciinfotani/android/lap_panen";
    private String URL_LAP_PANEN_TAHUN = "http://192.168.43.171/ciinfotani/android/lap_panenTahun";


    public String getURL_LOGIN() {
        return URL_LOGIN;
    }
    public String getURL_REGIST() {
        return URL_REGIST;
    }
    public String getURL_DESA() {
        return URL_DESA;
    }
    public String getURL_KOMODITAS() { return URL_KOMODITAS; }
    public String getURL_DATA_PETANI() {
        return URL_DATA_PETANI;
    }
    public String getURL_FILL_DATA_PETANI() { return URL_FILL_DATA_PETANI; }
    public String getURL_PANEN() { return URL_PANEN; }
    public String getURL_FILL_DATA_PANEN() { return URL_FILL_DATA_PANEN; }
    public String getURL_CEK_PANEN() { return URL_CEK_PANEN; }
    public String getURL_LAP_PANEN() { return URL_LAP_PANEN; }
    public String getURL_LAP_PANEN_TAHUN() { return URL_LAP_PANEN_TAHUN; }


}
