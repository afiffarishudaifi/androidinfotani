package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Adapter.AdapterPanen;
import com.example.myapplication.Model.ModelPanen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LapPanenActivity extends AppCompatActivity implements View.OnClickListener{
    private String URL_LAP_PANEN,URL_LAP_PANEN_TAHUN;
    //session deklar
    private SessionManager sessionManager;
    private String mKtp;

    private RequestQueue dQueue;
    private RecyclerView dRecycle;
    private RecyclerView.Adapter dAdapter;
    private RecyclerView.LayoutManager dLayoutManager;

    private int no =0;
    private String  komoditas ,tglPanen,hasil,sisa,harga;
    private TextView jmlHasil, jmlSisa;
    private Button pilih, hapus;
    private Spinner list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lap_panen);

        initControls();
        Api lapPanen = new Api();
        URL_LAP_PANEN = lapPanen.getURL_LAP_PANEN();
        URL_LAP_PANEN_TAHUN = lapPanen.getURL_LAP_PANEN_TAHUN();

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        mKtp = user.get(sessionManager.KTP);

        dQueue = Volley.newRequestQueue(this);
        getPanenList();

    }
    private void initControls(){
        list = (Spinner) findViewById(R.id.spinner_tahun);
        pilih = (Button) findViewById(R.id.pilihLappanen);
        pilih.setOnClickListener(this);
        hapus = (Button) findViewById(R.id.hapusLappanen);
        hapus.setOnClickListener(this);
        jmlHasil = (TextView)findViewById(R.id.jmlAwalpanen);
        jmlSisa = (TextView)findViewById(R.id.jmlSisapanen);
    }
    private void getPanenList(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LAP_PANEN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ArrayList<ModelPanen> panenItems = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if (success.equals("1")) {
                                String jmlhasil = jsonObject.getString("jmlhasil").trim();
                                String jmlsisa = jsonObject.getString("jmlsisa").trim();
                                jmlHasil.setText(jmlhasil);
                                jmlSisa.setText(jmlsisa);
                                no = 0;
                                for(int i = 0; i < jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    komoditas = object.getString("komoditas").trim();
                                    hasil = object.getString("hasil").trim();
                                    sisa = object.getString("sisa").trim();
                                    tglPanen = object.getString("tgl").trim();
                                    harga = object.getString("harga").trim();
                                    no = no;

                                    System.out.println(komoditas);
                                    panenItems.add(new ModelPanen(no,komoditas,hasil,sisa,tglPanen,harga));
                                    no++;
                                }
                                dRecycle = findViewById(R.id.rvTabel);
                                dRecycle.setHasFixedSize(true);
                                dLayoutManager = new LinearLayoutManager(getApplicationContext());
                                dAdapter = new AdapterPanen((panenItems));

                                dRecycle.setLayoutManager(dLayoutManager);
                                dRecycle.setAdapter(dAdapter);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LapPanenActivity.this,
                                    "Error!. " +e.toString(),
                                    Toast.LENGTH_SHORT).show();
                            System.out.println(e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LapPanenActivity.this,
                                "Error!. " +error.toString(),
                                Toast.LENGTH_SHORT).show();
                        System.out.println(error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("ktp", mKtp);
                return params;
            }
        };

        dQueue.add(stringRequest);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pilihLappanen:
                if (!list.getSelectedItem().toString().trim().equals("Pilih Tahun")){
                    filter();
                }
                break;
            case R.id.hapusLappanen:
                list.setSelection(0);
                getPanenList();
                break;
        }
    }

    private void filter(){
        final String tahun = list.getSelectedItem().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LAP_PANEN_TAHUN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ArrayList<ModelPanen> panenItems = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if (success.equals("1")) {
                                String jmlhasil = jsonObject.getString("jmlhasil").trim();
                                String jmlsisa = jsonObject.getString("jmlsisa").trim();
                                jmlHasil.setText(jmlhasil);
                                jmlSisa.setText(jmlsisa);
                                no = 0;
                                for(int i = 0; i < jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    komoditas = object.getString("komoditas").trim();
                                    hasil = object.getString("hasil").trim();
                                    sisa = object.getString("sisa").trim();
                                    tglPanen = object.getString("tgl").trim();
                                    harga = object.getString("harga").trim();
                                    no = no;

                                    System.out.println(komoditas);
                                    panenItems.add(new ModelPanen(no,komoditas,hasil,sisa,tglPanen,harga));
                                    no++;
                                }
                            }else{
                                        String jmlhasil = "-";
                                        String jmlsisa = "-";
                                        jmlHasil.setText(jmlhasil);
                                        jmlSisa.setText(jmlsisa);
                                        no = 0;
                                            komoditas = "Tidak Ada";
                                            hasil = "Tidak Ada";
                                            sisa = "Tidak Ada";
                                            tglPanen = "Tidak Ada";
                                            harga = "Tidak Ada";
                                            no = no;

                                            panenItems.add(new ModelPanen(no,komoditas,hasil,sisa,tglPanen,harga));
                                            no++;
                                    }
                                dRecycle = findViewById(R.id.rvTabel);
                                dRecycle.setHasFixedSize(true);
                                dLayoutManager = new LinearLayoutManager(getApplicationContext());
                                dAdapter = new AdapterPanen((panenItems));

                                dRecycle.setLayoutManager(dLayoutManager);
                                dRecycle.setAdapter(dAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LapPanenActivity.this,
                                    "Error!. " +e.toString(),
                                    Toast.LENGTH_SHORT).show();
                            System.out.println(e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LapPanenActivity.this,
                                "Error!. " +error.toString(),
                                Toast.LENGTH_SHORT).show();
                        System.out.println(error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("ktp", mKtp);
                params.put("tahun",tahun);
                return params;
            }
        };

        dQueue.add(stringRequest);

    }

}
