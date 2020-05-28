package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PasswordBaruActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtpass, textkonfpass, id;
    ImageButton btnSimpan, btnKembali;
    String URL_Update;
    LinearLayout linearLayout;
    ProgressBar loadingcari;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_baru);
        Api dataupdate = new Api();
        URL_Update = dataupdate.getURL_UPDATE();

        initcontrol();
        //mengambil data dari inten sebelumnya
        if(getIntent().getExtras() != null){
            //Statement Disini Akan Berjalan Jika Menggunakan Bundle
            Bundle bundle = getIntent().getExtras();
            id.setText(bundle.getString("hasil"));
        }else {
            //Statement Berikut ini Akan Dijalankan Jika Tidak Menggunakan Bundle
            id.setText(getIntent().getStringExtra("hasil"));
        }
    }

    private void initcontrol() {
        txtpass = (EditText)findViewById(R.id.txtpass);
        textkonfpass = (EditText)findViewById(R.id.txtkonfpass);
        btnSimpan = (ImageButton)findViewById(R.id.imgBtnSimpan);
        btnSimpan.setOnClickListener(this);
        btnKembali = (ImageButton)findViewById(R.id.imgBtnkembali);
        btnKembali.setOnClickListener(this);
        linearLayout = (LinearLayout)findViewById(R.id.linearPasswordBaru);
        loadingcari = (ProgressBar)findViewById(R.id.loadingPassBaru);
        id = (EditText)findViewById(R.id.username);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBtnSimpan:
                simpan();
                break;
            case R.id.imgBtnkembali:
                startActivity(new Intent(PasswordBaruActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }

    private void simpan() {
        linearLayout.setVisibility(View.GONE);
        loadingcari.setVisibility(View.VISIBLE);
        final String passbaru = this.txtpass.getText().toString().trim();
        final String konfpass = this.textkonfpass.getText().toString().trim();
        final String id = this.id.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Update,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            if (success.equals("1")) {
                                Toast.makeText(PasswordBaruActivity.this, "Pesan : " +message, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(PasswordBaruActivity.this, LoginActivity.class));
                                finish();
                            } else {
                                Toast.makeText(PasswordBaruActivity.this, "Pesan : " + message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PasswordBaruActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            System.out.println(e.toString());
                            loadingcari.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PasswordBaruActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        System.out.println(error.toString());
                        loadingcari.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("password", passbaru);
                data.put("konfpass", konfpass);
                data.put("id", id);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
