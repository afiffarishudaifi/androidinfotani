package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

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


public class FormTanyaActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nama, email, pesan;
    private ImageButton imgBtnSimpan, imgBtnKembali;
    private ProgressBar loadingTanya;
    private LinearLayout linearLayoutbtnpesan;

    private SessionManager sessionManager;
    private String mKtp, mId_User;

    private String URL_TANYA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_tanya);
        initControl();
        
        Api datatanya = new Api();
        URL_TANYA = datatanya.getURL_TANYA();
        
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String,String> user = sessionManager.getUserDetail();
        mKtp = user.get(sessionManager.KTP);
        mId_User = user.get(sessionManager.ID_USER);

    }

    private void initControl() {
        nama = (EditText)findViewById(R.id.namatanya);
        email = (EditText)findViewById(R.id.emailtanya);
        pesan = (EditText)findViewById(R.id.pesantanya);
        imgBtnSimpan = (ImageButton)findViewById(R.id.imgBtnsimpanpesan);
        imgBtnSimpan.setOnClickListener(this);
        imgBtnKembali = (ImageButton)findViewById(R.id.imgBtnkeluarpesan);
        imgBtnKembali.setOnClickListener(this);
        linearLayoutbtnpesan = (LinearLayout)findViewById(R.id.linearLayoutBtnTanya);
        loadingTanya = (ProgressBar)findViewById(R.id.loadingTanya);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBtnsimpanpesan:
                simpan();
                break;
            case R.id.imgBtnkeluarpesan:
                Intent kembali = new Intent(FormTanyaActivity.this, MainActivity.class);
                startActivity(kembali);
                break;
        }
    }

    private void simpan() {
        final String txtnama = this.nama.getText().toString().trim();
        final String txtemail = this.email.getText().toString().trim();
        final String txtpesan = this.pesan.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TANYA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            if (success.equals("1")) {
                                Toast.makeText(FormTanyaActivity.this, "Pesan : " + message, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(FormTanyaActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(FormTanyaActivity.this, "Pesan : " + message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(FormTanyaActivity.this, "Pesan : " + e.toString(), Toast.LENGTH_SHORT).show();
                            System.out.println(e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FormTanyaActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        System.out.println(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("nama", txtnama);
                map.put("email", txtemail);
                map.put("pesan", txtpesan);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
