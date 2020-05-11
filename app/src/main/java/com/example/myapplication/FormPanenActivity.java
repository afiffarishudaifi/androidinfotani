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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FormPanenActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView ktp, tglPanen, komoditas;
    private EditText hasil, harga;
    private ImageButton imgBtnSimpanPanen, imgBtnKeluarPanen;
    private LinearLayout linearLayoutBtnPanen;
    private ProgressBar loadingPanen;
    private String tempKomoditasID;

    //session deklar
    private SessionManager sessionManager;
    private String mKtp, mId_user;

    private String URL_PANEN, URL_FILL_DATA_PANEN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_panen);

        Api dataPanen = new Api();
        URL_PANEN = dataPanen.getURL_PANEN();
        URL_FILL_DATA_PANEN = dataPanen.getURL_FILL_DATA_PANEN();

        //cek session
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        mKtp = user.get(sessionManager.KTP);
        mId_user = user.get(sessionManager.ID_USER);

        initControl();
        ktp.setText(mKtp);

        if(!mKtp.equals("0")){
            fillData();
        }
    }

    private void initControl(){
        ktp = (TextView) findViewById(R.id.ktpPanen);
        tglPanen = (TextView) findViewById(R.id.tglpanenPanen);
        komoditas = (TextView) findViewById(R.id.komoditasPanen);
        hasil = (EditText) findViewById(R.id.hasilPanen);
        harga = (EditText) findViewById(R.id.hargaPanen);
        imgBtnSimpanPanen = (ImageButton) findViewById(R.id.imgBtnsimpanPanen);
        imgBtnSimpanPanen.setOnClickListener(this);
        imgBtnKeluarPanen = (ImageButton) findViewById(R.id.imgBtnKeluarPanen);
        imgBtnKeluarPanen.setOnClickListener(this);
        linearLayoutBtnPanen = (LinearLayout) findViewById(R.id.linearLayoutBtnPanen);
        loadingPanen = (ProgressBar) findViewById(R.id.loadingPanen);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBtnsimpanPanen:
                simpan();
                break;
            case R.id.imgBtnKeluarPanen:
                Intent kembali = new Intent(FormPanenActivity.this, MainActivity.class);
                startActivity(kembali);
                finish();
                break;
        }
    }

    //proses insert/update data
    private void simpan() {
        linearLayoutBtnPanen.setVisibility(View.GONE);
        loadingPanen.setVisibility(View.VISIBLE);

        final String ktp =  this.ktp.getText().toString().trim();
        final String komoditas = this.tempKomoditasID.trim();
        final String tglpanen = this.tglPanen.getText().toString().trim();
        final String hasil = this.hasil.getText().toString().trim();
        final String harga = this.harga.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PANEN ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            if(success.equals("1")) {
                                Toast.makeText(FormPanenActivity.this, "Pesan : " + message, Toast.LENGTH_SHORT).show();
                                loadingPanen.setVisibility(View.GONE);
                                linearLayoutBtnPanen.setVisibility(View.VISIBLE);
                                startActivity(new Intent(FormPanenActivity.this, MainActivity.class));
                            }else{
                                Toast.makeText(FormPanenActivity.this, "Pesan : " + message, Toast.LENGTH_SHORT).show();
                                loadingPanen.setVisibility(View.GONE);
                                linearLayoutBtnPanen.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(FormPanenActivity.this, "Pesan : " + e.toString(), Toast.LENGTH_SHORT).show();
                            System.out.println(e.toString());
                            loadingPanen.setVisibility(View.GONE);
                            linearLayoutBtnPanen.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FormPanenActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        System.out.println(error.toString());
                        loadingPanen.setVisibility(View.GONE);
                        linearLayoutBtnPanen.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //mengirim data ke controller
                Map<String, String> map = new HashMap<>();
                map.put("ktp", ktp);
                map.put("komoditas", komoditas);
                map.put("tglpanen", tglpanen);
                map.put("hasil", hasil);
                map.put("harga", harga);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void fillData(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_FILL_DATA_PANEN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if (success.equals("1")) {
                                for(int i = 0; i < jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String fillKtp = object.getString("ktp").trim();
                                    String fillKomoditas = object.getString("nama_komoditas").trim();
                                    String fillTglpanen = object.getString("tglpanen").trim();
                                    tempKomoditasID = object.getString("id_komoditas").trim();
                                    ktp.setText(fillKtp);
                                    komoditas.setText(fillKomoditas);
                                    tglPanen.setText(fillTglpanen);
                                }
                            } else{
                                for(int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    Toast.makeText(FormPanenActivity.this,
                                            "Error!. " + jsonObject.getString("message"),
                                            Toast.LENGTH_SHORT).show();
                                    System.out.println(jsonObject.getString("message"));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(FormPanenActivity.this,
                                    "Error!. " +e.toString(),
                                    Toast.LENGTH_SHORT).show();
                            System.out.println(e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FormPanenActivity.this,
                                "Error!. " +error.toString(),
                                Toast.LENGTH_SHORT).show();
                        System.out.println(error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("id_user", mId_user);
                params.put("ktp", mKtp);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



}
