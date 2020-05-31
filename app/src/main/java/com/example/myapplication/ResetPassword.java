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

public class ResetPassword extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnCari, btnKembali;
    private String URL_CARI;
    private EditText txtnama, txtno;
    ProgressBar loadingcari;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Api datacari = new Api();
        URL_CARI = datacari.getURL_CARI();
        initcontrol();

    }

    private void initcontrol() {
        txtnama = (EditText)findViewById(R.id.txtusername);
        txtno = (EditText)findViewById(R.id.txtnohp);
        btnCari = (ImageButton)findViewById(R.id.imgBtnCari);
        btnCari.setOnClickListener(this);
        btnKembali = (ImageButton)findViewById(R.id.imgBtnkembali);
        btnKembali.setOnClickListener(this);
        loadingcari = (ProgressBar)findViewById(R.id.loadingPassBaru);
        linearLayout = (LinearLayout)findViewById(R.id.linearResetPassword);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBtnCari:
                cari();
                break;
            case R.id.imgBtnkembali:
                startActivity(new Intent(ResetPassword.this, LoginActivity.class));
                finish();
                break;
        }
    }

    private void cari() {
        /*linearLayout.setVisibility(View.GONE);
        loadingcari.setVisibility(View.VISIBLE);*/

        final String username = this.txtnama.getText().toString().trim();
        final String no = this.txtno.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CARI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            String hasil = jsonObject.getString("id");
                            if (success.equals("1")) {
                                Toast.makeText(ResetPassword.this, "Pesan : " + message + "\n" + hasil, Toast.LENGTH_SHORT).show();
                                /*loadingcari.setVisibility(View.GONE);
                                linearLayout.setVisibility(View.VISIBLE);*/
                                Intent sendData1 = new Intent(ResetPassword.this, PasswordBaruActivity.class);
                                sendData1.putExtra("hasil", hasil);
                                startActivity(sendData1);
                            } else {
                                Toast.makeText(ResetPassword.this, "Pesan : " + message, Toast.LENGTH_SHORT).show();
                                /*loadingcari.setVisibility(View.GONE);
                                linearLayout.setVisibility(View.VISIBLE);*/
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ResetPassword.this, "Pesan : " + e.toString(), Toast.LENGTH_SHORT).show();
                            System.out.println(e.toString());
                            /*loadingcari.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);*/
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ResetPassword.this, "Pesan : "+error.toString(), Toast.LENGTH_SHORT).show();
                        System.out.println(error.toString());
                        /*loadingcari.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);*/
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("username", username);
                map.put("nohp", no);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
