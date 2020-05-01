package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton imgBtnmasuk, imgBtnkeluar, imgBtndaftar;
    private EditText etusername, etpassword;

    private ProgressBar loading;
    private String URL_LOGIN;
    private Context mContext;
    private LinearLayout linearLayoutBtn;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        Api login = new Api();
        URL_LOGIN = login.getURL_LOGIN();
        mContext = this;
             initControl();
    }
   private void initControl() {
        loading = (ProgressBar)findViewById(R.id.loading);
        linearLayoutBtn = (LinearLayout)findViewById(R.id.linearLayoutBtn);
       etusername = (EditText) findViewById(R.id.username);
       etpassword = (EditText) findViewById(R.id.password);
        imgBtnmasuk = (ImageButton) findViewById(R.id.imgBtnmasuk);
        imgBtnmasuk.setOnClickListener(this);
        imgBtnkeluar = (ImageButton) findViewById(R.id.imgBtnkeluar);
        imgBtnkeluar.setOnClickListener(this);
        imgBtndaftar = (ImageButton) findViewById(R.id.imgBtndaftar);
        imgBtndaftar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnmasuk:
                String mUsername = etusername.getText().toString().trim();
                String mPassword = etpassword.getText().toString().trim();

                if(!mUsername.isEmpty() || !mPassword.isEmpty()){
                    login(mUsername, mPassword);
                }else{
                    etusername.setError("Tolong Masukkan Nama Pengguna");
                    etpassword.setError("Tolong Masukkan Kata Sandi");
                }
                break;
            case R.id.imgBtnkeluar:
                finish();
                break;
            case R.id.imgBtndaftar:
                startActivity(new Intent(mContext, RegisterActivity.class));
                finish();
                break;
        }
    }

    private void login(final String user, final String pass) {
        loading.setVisibility(View.VISIBLE);
        linearLayoutBtn.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");
                            if (success.equals("1")) {
                                for(int i = 0; i < jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id_user = object.getString("id_user").trim();
                                    String username = object.getString("username").trim();
                                    String foto_user = object.getString("foto_user").trim();

                                    sessionManager.createSession(id_user,username,foto_user);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("id_user",id_user);
                                    intent.putExtra("username",username);
                                    intent.putExtra("foto_user",foto_user);
                                    startActivity(intent);
                                    loading.setVisibility(View.GONE);
                                    linearLayoutBtn.setVisibility(View.VISIBLE);

                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this,
                                    "Error!. " +e.toString(),
                                    Toast.LENGTH_SHORT).show();
                            System.out.println(e.toString());
                            loading.setVisibility(View.GONE);
                            linearLayoutBtn.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,
                                "Error!. " +error.toString(),
                                Toast.LENGTH_SHORT).show();
                        System.out.println(error.toString());
                        loading.setVisibility(View.GONE);
                        linearLayoutBtn.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("username", user);
                params.put("password", pass);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
