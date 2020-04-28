package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class RegisterActivity extends AppCompatActivity {
    ImageButton imgBtnkembali, imgBtnsimpan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initControl();
    }
    public void initControl(){
        imgBtnkembali = (ImageButton)findViewById(R.id.imgBtnkembali);
        imgBtnkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kembali_ke_login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(kembali_ke_login);
            }
        });
        imgBtnsimpan = (ImageButton)findViewById(R.id.imgBtnsimpan);
        imgBtnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //kode proses daftar disini
            }
        });
    }
}
