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

public class DataPetaniActivity extends AppCompatActivity implements View.OnClickListener {
    private String URL_DATA_PETANI, mId_user, mUsername;
    private EditText ktp, alamat, desa, nohp, komoditas, tglpanen;
    private ImageButton imgBtnpetanisimpan, imgBtnpetanikeluar;
    private LinearLayout linearLayoutBtnpetani;
    private ProgressBar loadingPetani;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_petani);

        Api dataPetani = new Api();
        URL_DATA_PETANI = dataPetani.getURL_DATA_PETANI();

        initControl();
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        mId_user = user.get(sessionManager.ID_USER);
        mUsername = user.get(sessionManager.USERNAME);
    }
    private void initControl(){
        loadingPetani = (ProgressBar) findViewById(R.id.loadingPetani);
        linearLayoutBtnpetani = (LinearLayout)findViewById(R.id.linearLayoutBtnpetani);
        ktp =(EditText) findViewById(R.id.ktp);
        alamat =(EditText) findViewById(R.id.alamat);
        desa =(EditText) findViewById(R.id.spinner_desa);
        nohp =(EditText) findViewById(R.id.nohp);
        komoditas =(EditText) findViewById(R.id.komoditas);
        tglpanen =(EditText) findViewById(R.id.tglpanen);
        imgBtnpetanisimpan = (ImageButton) findViewById(R.id.imgBtnpetanisimpan);
        imgBtnpetanisimpan.setOnClickListener(this);
        imgBtnpetanikeluar = (ImageButton) findViewById(R.id.imgBtnpetanikeluar);
        imgBtnpetanikeluar.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBtnpetanisimpan:
                simpan();
                break;
            case R.id.imgBtnpetanikeluar:
                Intent kembali_ke_home = new Intent(DataPetaniActivity.this, MainActivity.class);
                startActivity(kembali_ke_home);
                finish();
                break;
        }
    }

    private void simpan() {
        linearLayoutBtnpetani.setVisibility(View.GONE);
        loadingPetani.setVisibility(View.VISIBLE);
        final String id_user = this.mId_user;
        final String username = this.mUsername;
        final String ktp =  this.ktp.getText().toString().trim();
        final String alamat =  this.alamat.getText().toString().trim();
        final String desa = this.desa.getText().toString().trim();
        final String nohp = this.nohp.getText().toString().trim();
        final String komoditas = this.komoditas.getText().toString().trim();
        final String tglpanen = this.tglpanen.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DATA_PETANI ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(DataPetaniActivity.this, "Pesan : " + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            loadingPetani.setVisibility(View.GONE);
                            linearLayoutBtnpetani.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DataPetaniActivity.this, "Pesan : " + e.toString(), Toast.LENGTH_SHORT).show();
                            System.out.println(e.toString());
                            loadingPetani.setVisibility(View.GONE);
                            linearLayoutBtnpetani.setVisibility(View.VISIBLE);
                        }

                        startActivity(new Intent(DataPetaniActivity.this, MainActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DataPetaniActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        System.out.println(error.toString());
                        loadingPetani.setVisibility(View.GONE);
                        linearLayoutBtnpetani.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //mengirim data ke controller
                Map<String, String> map = new HashMap<>();
                map.put("id_user",id_user);
                map.put("username", username);
                map.put("ktp", ktp);
                map.put("alamat", alamat);
                map.put("desa", desa);
                map.put("nohp",nohp);
                map.put("komoditas", komoditas);
                map.put("tglpanen", tglpanen);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
