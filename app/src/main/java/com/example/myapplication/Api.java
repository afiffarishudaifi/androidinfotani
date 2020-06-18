package com.example.myapplication;

public class Api {
    private String URL_LOGIN = "http://infotani.mif-project.com/android/loginapi";
    private String URL_REGIST = "http://infotani.mif-project.com/android/registerapi";
    private String URL_DESA = "http://infotani.mif-project.com/android/get_desa";
    private String URL_KOMODITAS = "http://infotani.mif-project.com/android/get_komoditas";
    private String URL_DATA_PETANI = "http://infotani.mif-project.com/android/data_petaniapi";
    private String URL_FILL_DATA_PETANI = "http://infotani.mif-project.com/android/fill_data";
    private String URL_PANEN = "http://infotani.mif-project.com/android/data_panenapi";
    private String URL_FILL_DATA_PANEN = "http://infotani.mif-project.com/android/fill_data_panen";
    private String URL_CEK_PANEN = "http://infotani.mif-project.com/android/cek_panen";
    private String URL_LAP_PANEN = "http://infotani.mif-project.com/android/lap_panen";
    private String URL_LAP_PANEN_TAHUN = "http://infotani.mif-project.com/android/lap_panenTahun";
    private String URL_TANYA = "http://infotani.mif-project.com/android/tanya";
    private String URL_UPDATE = "http://infotani.mif-project.com/android/update_petani";
    private String URL_CARI = "http://infotani.mif-project.com/android/passpetani";
    private String URL_PESAN = "http://infotani.mif-project.com/android/pemesanan";
    private String URL_PESAN_TAHUN = "http://infotani.mif-project.com/android/pemesananTahun";
    private String URL_PESAN_KONF = "http://infotani.mif-project.com/android/konfirmasi_pemesanan";
    private String URL_PESAN_TOLAK = "http://infotani.mif-project.com/android/tolak_pemesanan";
    private String URL_UBAH_PENGATURAN = "http://infotani.mif-project.com/android/pengaturan";
    private String URL_UBAH_FOTO = "http://infotani.mif-project.com/android/update_foto";
    private String URL_UBAH_LENGKAP_PENGATURAN = "http://infotani.mif-project.com/android/pengaturan_danfoto";

    public String getURL_UBAH_LENGKAP_PENGATURAN() {
        return URL_UBAH_LENGKAP_PENGATURAN;
    }

    public String getURL_LOGIN() { return URL_LOGIN; }
    public String getURL_REGIST() { return URL_REGIST; }
    public String getURL_DESA() { return URL_DESA; }
    public String getURL_KOMODITAS() { return URL_KOMODITAS; }
    public String getURL_DATA_PETANI() { return URL_DATA_PETANI; }
    public String getURL_FILL_DATA_PETANI() { return URL_FILL_DATA_PETANI; }
    public String getURL_PANEN() { return URL_PANEN; }
    public String getURL_FILL_DATA_PANEN() { return URL_FILL_DATA_PANEN; }
    public String getURL_CEK_PANEN() { return URL_CEK_PANEN; }
    public String getURL_LAP_PANEN() { return URL_LAP_PANEN; }
    public String getURL_LAP_PANEN_TAHUN() { return URL_LAP_PANEN_TAHUN; }
    public String getURL_TANYA() { return URL_TANYA; }
    public String getURL_CARI() { return URL_CARI; }
    public String getURL_UPDATE() { return URL_UPDATE; }
    public String getURL_PESAN() { return URL_PESAN; }
    public String getURL_PESAN_TAHUN() { return URL_PESAN_TAHUN; }
    public String getURL_PESAN_KONF() { return URL_PESAN_KONF; }
    public String getURL_PESAN_TOLAK(){return URL_PESAN_TOLAK;}
    public String getURL_UBAH_PENGATURAN(){return URL_UBAH_PENGATURAN;}
    public String getURL_UBAH_FOTO(){return URL_UBAH_FOTO;}

}
