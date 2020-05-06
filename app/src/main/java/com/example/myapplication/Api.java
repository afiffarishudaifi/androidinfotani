package com.example.myapplication;

public class Api {
    String URL_LOGIN = "http://192.168.43.171/ciinfotani/android/loginapi";
    String URL_REGIST = "http://192.168.43.171/ciinfotani/android/registerapi";
    String URL_DESA = "http://192.168.43.171/ciinfotani/android/get_desa";
    String URL_DATA_PETANI = "http://192.168.43.171/ciinfotani/android/data_petaniapi";

    public String getURL_LOGIN() {
        return URL_LOGIN;
    }
    public String getURL_REGIST() {
        return URL_REGIST;
    }
    public String getURL_DESA() {
        return URL_DESA;
    }
    public String getURL_DATA_PETANI() {
        return URL_DATA_PETANI;
    }
}
