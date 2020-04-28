package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class LoginActivity extends AppCompatActivity {
    ImageButton imgBtnmasuk, imgBtnkeluar, imgBtndaftar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initControl();
    }
    private void initControl() {
        imgBtnmasuk = (ImageButton)findViewById(R.id.imgBtnMasuk);
        imgBtnmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //kode login
                Intent index = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(index);
            }
        });

        imgBtnkeluar = (ImageButton)findViewById(R.id.imgBtnKeluar);
        imgBtnkeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgBtndaftar = (ImageButton)findViewById(R.id.imgBtndaftar);
        imgBtndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent index = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(index);
            }
        });
    }
}
