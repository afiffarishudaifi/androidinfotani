package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

public class KonfirmasiPesananActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView idPK, namaPK, komoPK, jmlPK, biayPK, tglPK;
    private String idPanen;
    private Button konfirmasi, tolak;
    private String URL_KONF_PESANAN, URL_TOLAK_PESANAN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_pesanan);

        Api konfPesanan = new Api();
        URL_KONF_PESANAN = konfPesanan.getURL_PESAN_KONF();
        URL_TOLAK_PESANAN = konfPesanan.getURL_PESAN_TOLAK();

        initControls();

    }
    private void initControls(){
        idPK = (TextView) findViewById(R.id.idPesanK);
        namaPK = (TextView) findViewById(R.id.namaPerusahaanK);
        komoPK = (TextView) findViewById(R.id.komoditasPesanK);
        jmlPK = (TextView) findViewById(R.id.jmlPesanK);
        biayPK = (TextView) findViewById(R.id.biayaPesanK);
        tglPK = (TextView) findViewById(R.id.tglPesanK);
        konfirmasi = (Button) findViewById(R.id.btnKonfPesan);
        konfirmasi.setOnClickListener(this);
        tolak = (Button) findViewById(R.id.btnTolakPesan);
        tolak.setOnClickListener(this);

        idPK.setText(getIntent().getExtras().getString("idPK"));
        namaPK.setText(getIntent().getExtras().getString("namaPK"));
        komoPK.setText(getIntent().getExtras().getString("komoditasPK"));
        jmlPK.setText(getIntent().getExtras().getString("jmlPK"));
        biayPK.setText(getIntent().getExtras().getString("biayaPK"));
        tglPK.setText(getIntent().getExtras().getString("tglPK"));
        idPanen = getIntent().getExtras().getString("idPanenK");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnKonfPesan:
                konfirmasi();
                break;
            case R.id.btnTolakPesan:
                tolak();
                break;
        }
    }

    private void konfirmasi(){
        final String idPeK = this.idPK.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_KONF_PESANAN ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            if(success.equals("1")) {
                                Toast.makeText(KonfirmasiPesananActivity.this, "Pesan : " + message +
                                        "\nKeluar Lalu Masuk Kembali Untuk Memperbarui Data!", Toast.LENGTH_LONG).show();
//                                Intent intent = new Intent(KonfirmasiPesananActivity.this, RiwayatPesanActivity.class);
//                                intent.putExtra("kondisi","1");
//                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(KonfirmasiPesananActivity.this, "Pesan : " + message, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(KonfirmasiPesananActivity.this, "Pesan : " + e.toString(), Toast.LENGTH_SHORT).show();
                            System.out.println(e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(KonfirmasiPesananActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        System.out.println(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //mengirim data ke controller
                Map<String, String> map = new HashMap<>();
                map.put("idpesan", idPeK);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void tolak(){
        final String idPeK = this.idPK.getText().toString().trim();
        final String jmlPeK = this.jmlPK.getText().toString().trim();
        final String idPaK = idPanen.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TOLAK_PESANAN ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            if(success.equals("1")) {
                                Toast.makeText(KonfirmasiPesananActivity.this, "Pesan : " + message +
                                        "\nKeluar Lalu Masuk Kembali Untuk Memperbarui Data!", Toast.LENGTH_LONG).show();
//                                Intent intent = new Intent(KonfirmasiPesananActivity.this, RiwayatPesanActivity.class);
//                                intent.putExtra("kondisi","1");
//                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(KonfirmasiPesananActivity.this, "Pesan : " + message, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(KonfirmasiPesananActivity.this, "Pesan : " + e.toString(), Toast.LENGTH_SHORT).show();
                            System.out.println(e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(KonfirmasiPesananActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        System.out.println(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //mengirim data ke controller
                Map<String, String> map = new HashMap<>();
                map.put("idpesan", idPeK);
                map.put("idpanen", idPaK);
                map.put("jmlpesan", jmlPeK);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
