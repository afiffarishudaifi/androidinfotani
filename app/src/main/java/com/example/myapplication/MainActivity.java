package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView username, id_user;
    private ImageButton btnDatapetani, btnFormpanen, btnLappanen,
            btnLappesan, btnRiwayatpesan, btnFormtanya;
    private ImageView fotoUser;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        initControl();

        HashMap<String, String> user = sessionManager.getUserDetail();
//        String mId_user = user.get(sessionManager.ID_USER);
        String mUsername = user.get(sessionManager.USERNAME);
        String mFoto_user = user.get(sessionManager.FOTO_USER);
        String URL_FOTO = "http://192.168.43.171/ciinfotani/img/user/"+mFoto_user;

        //set nama dari session
        username.setText(mUsername);
        //set foto
        Glide.with(MainActivity.this)
                // LOAD URL DARI INTERNET
                .load(URL_FOTO)
                // LOAD GAMBAR AWAL SEBELUM GAMBAR UTAMA MUNCUL, BISA DARI LOKAL DAN INTERNET
                .placeholder(R.drawable.ic_person_white_24dp)
                //. LOAD GAMBAR SAAT TERJADI KESALAHAN MEMUAT GMBR UTAMA
                .error(R.drawable.ic_person_white_24dp)
                .into(fotoUser);
    }

    private void initControl() {
        fotoUser = (ImageView) findViewById(R.id.foto_user);
        username = (TextView) findViewById(R.id.namaPengguna);
//        btnLogout = (Button) findViewById(R.id.);
//        btnLogout.setOnClickListener(this);
        btnDatapetani = (ImageButton) findViewById(R.id.btnDatapetani);
        btnDatapetani.setOnClickListener(this);
        btnFormpanen = (ImageButton) findViewById(R.id.btnFormpanen);
        btnFormpanen.setOnClickListener(this);
        btnLappanen = (ImageButton) findViewById(R.id.btnLappanen);
        btnLappanen.setOnClickListener(this);
        btnLappesan = (ImageButton) findViewById(R.id.btnLappesan);
        btnLappesan.setOnClickListener(this);
        btnRiwayatpesan = (ImageButton) findViewById(R.id.btnRiwayatpesan);
        btnRiwayatpesan.setOnClickListener(this);
        btnFormtanya = (ImageButton) findViewById(R.id.btnTanya);
        btnFormtanya.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btnLogout:
//                sessionManager.logout();
//                break;
            case R.id.btnDatapetani:
                Intent dataPetani = new Intent(MainActivity.this, DataPetaniActivity.class);
                startActivity(dataPetani);
                break;
            case R.id.btnFormpanen:
                Intent formPanen = new Intent(MainActivity.this, FormPanenActivity.class);
                startActivity(formPanen);
                break;
            case R.id.btnLappanen:
                Intent lapPanen = new Intent(MainActivity.this, LapPanenActivity.class);
                startActivity(lapPanen);
                break;
            case R.id.btnLappesan:
                Intent lapPesan = new Intent(MainActivity.this, LapPesanActivity.class);
                startActivity(lapPesan);
                break;
            case R.id.btnRiwayatpesan:
                Intent riwayatPesan = new Intent(MainActivity.this, RiwayatPesanActivity.class);
                startActivity(riwayatPesan);
                break;
            case R.id.btnTanya:
                Intent formTanya = new Intent(MainActivity.this, FormTanyaActivity.class);
                startActivity(formTanya);
                break;
        }
    }
}
