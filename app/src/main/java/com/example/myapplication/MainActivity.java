package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView username, id_user, foto_user;
    private Button btnLogout;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        initControl();

        HashMap<String, String> user = sessionManager.getUserDetail();
        String mId_user = user.get(sessionManager.ID_USER);
        String mUsername = user.get(sessionManager.USERNAME);
        String mFoto_user = user.get(sessionManager.FOTO_USER);

        id_user.setText(mId_user);
        username.setText(mUsername);
        foto_user.setText(mFoto_user);
    }

    private void initControl() {

        username = (TextView) findViewById(R.id.mainUsername);
        id_user = (TextView) findViewById(R.id.id_user);
        foto_user = (TextView) findViewById(R.id.foto_user);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogout:
                sessionManager.logout();
                break;
        }
    }
}
