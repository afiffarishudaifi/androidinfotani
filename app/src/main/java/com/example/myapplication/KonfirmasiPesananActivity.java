package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class KonfirmasiPesananActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView idPK, namaPK, komoPK, jmlPK, biayPK, tglPK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_pesanan);

        initControls();

    }
    private void initControls(){
        idPK = (TextView) findViewById(R.id.idPesanK);
        namaPK = (TextView) findViewById(R.id.namaPerusahaanK);
        komoPK = (TextView) findViewById(R.id.komoditasPesanK);
        jmlPK = (TextView) findViewById(R.id.jmlPesanK);
        biayPK = (TextView) findViewById(R.id.biayaPesanK);
        tglPK = (TextView) findViewById(R.id.tglPesanK);

        idPK.setText(getIntent().getExtras().getString("idPK"));
        namaPK.setText(getIntent().getExtras().getString("namaPK"));
        komoPK.setText(getIntent().getExtras().getString("komoditasPK"));
        jmlPK.setText(getIntent().getExtras().getString("jmlPK"));
        biayPK.setText(getIntent().getExtras().getString("biayaPK"));
        tglPK.setText(getIntent().getExtras().getString("tglPK"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBtnKonfPesan:
                konfirmasi();
                break;
            case R.id.imgBtnTolakPesan:
                tolak();
                break;
        }
    }

    private void konfirmasi(){
        //json untuk konfirmasi pesanan (mengubah ID_STATUS_PESAN) menjadi 2;
        //api belum ada;
        //layout untuk konfirmasi pesanan belum fix (tombol simpan diganti konfirmasi)
        //tombol keluar diganti tolak;
        //background form belum sesuai;
    }
    private void tolak(){
        //json untuk menghapus data pesanan;
        //api belum ada;
    }
}
