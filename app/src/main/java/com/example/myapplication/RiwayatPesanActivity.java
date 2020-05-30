package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Adapter.AdapterPanen;
import com.example.myapplication.Adapter.AdapterPesan;
import com.example.myapplication.Model.ModelPanen;
import com.example.myapplication.Model.ModelPesan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RiwayatPesanActivity extends AppCompatActivity implements View.OnClickListener {
    private String URL_RIW_PESAN, URL_RIW_PESAN_TAHUN;
    private String status="1";

    //session deklar
    private SessionManager sessionManager;
    private String mKtp;

    private RequestQueue dQueue;
    private RecyclerView dRecycle;
    private RecyclerView.Adapter dAdapter;
    private RecyclerView.LayoutManager dLayoutManager;

    private int no =0;
    private String  namaP, komoditasP ,tglPesanP,jmlP,biayaP,statusP,idPesan, idPanen;
    private TextView jmlPesan;
    private Button pilih, hapus;
    private Spinner list;
    private ArrayList<ModelPesan> list_a = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_pesan);

        Toast.makeText(RiwayatPesanActivity.this, "Klik Data Yang Ingin Dikonfirmasi/Dihapus!", Toast.LENGTH_SHORT).show();
        initControls();
        Api riwPesan = new Api();
        URL_RIW_PESAN = riwPesan.getURL_PESAN();
        URL_RIW_PESAN_TAHUN = riwPesan.getURL_PESAN_TAHUN();

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        mKtp = user.get(sessionManager.KTP);

        dQueue = Volley.newRequestQueue(this);
        getPesanList();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pilihRiwpesan:
                if (!list.getSelectedItem().toString().trim().equals("Pilih Tahun")){
                    filter();
                }
                break;
            case R.id.hapusRiwpesan:
                list.setSelection(0);
                getPesanList();
                break;
        }
    }
    private void initControls(){
        list = (Spinner) findViewById(R.id.spinner_tahunPesan);
        pilih = (Button) findViewById(R.id.pilihRiwpesan);
        pilih.setOnClickListener(this);
        hapus = (Button) findViewById(R.id.hapusRiwpesan);
        hapus.setOnClickListener(this);
        jmlPesan = (TextView)findViewById(R.id.jmlPesan);
    }

    private void getPesanList(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_RIW_PESAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ArrayList<ModelPesan> pesanItems = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if (success.equals("1")) {
                                String jmlpesan = jsonObject.getString("jmlpesan").trim();
                                String totbiaya = jsonObject.getString("totbiaya").trim();
                                jmlPesan.setText(jmlpesan);
                                no = 0;
                                for(int i = 0; i < jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    namaP = object.getString("nama_perusahaan").trim();
                                    komoditasP = object.getString("komoditas").trim();
                                    tglPesanP = object.getString("tanggal").trim();
                                    jmlP = object.getString("jml_pesan").trim();
                                    biayaP = object.getString("tot_biaya").trim();
                                    statusP = object.getString("status").trim();
                                    idPesan = object.getString("id_pesan").trim();
                                    idPanen = object.getString("id_panen").trim();

                                    System.out.println(komoditasP);
                                    pesanItems.add(new ModelPesan(no,namaP,komoditasP,tglPesanP,jmlP,biayaP,statusP,idPesan,idPanen));
                                    no++;
                                }
                                dRecycle = findViewById(R.id.rvTabelPesan);
                                dRecycle.setHasFixedSize(true);
                                dLayoutManager = new LinearLayoutManager(getApplicationContext());
                                dAdapter = new AdapterPesan((pesanItems));

                                dRecycle.setLayoutManager(dLayoutManager);
                                dRecycle.setAdapter(dAdapter);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RiwayatPesanActivity.this,
                                    "Error!. " +e.toString(),
                                    Toast.LENGTH_SHORT).show();
                            System.out.println(e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RiwayatPesanActivity.this,
                                "Error!. " +error.toString(),
                                Toast.LENGTH_SHORT).show();
                        System.out.println(error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ktp", mKtp);
                params.put("status",status);
                return params;
            }
        };

        dQueue.add(stringRequest);

    }

    private void filter(){
        final String tahun = list.getSelectedItem().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_RIW_PESAN_TAHUN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ArrayList<ModelPesan> pesanItems = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if (success.equals("1")) {
                                String jmlpesan = jsonObject.getString("jmlpesan").trim();
                                String totbiaya = jsonObject.getString("totbiaya").trim();
                                jmlPesan.setText(jmlpesan);
                                no = 0;
                                for(int i = 0; i < jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    namaP = object.getString("nama_perusahaan").trim();
                                    komoditasP = object.getString("komoditas").trim();
                                    tglPesanP = object.getString("tanggal").trim();
                                    jmlP = object.getString("jml_pesan").trim();
                                    biayaP = object.getString("tot_biaya").trim();
                                    statusP = object.getString("status").trim();
                                    idPanen = object.getString("id_panen").trim();

                                    System.out.println(komoditasP);
                                    pesanItems.add(new ModelPesan(no,namaP,komoditasP,tglPesanP,jmlP,biayaP,statusP,idPesan,idPanen));
                                    no++;
                                }
                            }else{
                                String jmlP = "-";
                                String biayaP = "-";
                                jmlPesan.setText(jmlP);
                                no = 0;
//                                pesanItems.add(new ModelPanen(no,komoditas,hasil,sisa,tglPanen,harga));
//                                no++;
                            }
                            dRecycle = findViewById(R.id.rvTabelPesan);
                            dRecycle.setHasFixedSize(true);
                            dLayoutManager = new LinearLayoutManager(getApplicationContext());
                            dAdapter = new AdapterPesan((pesanItems));

                            dRecycle.setLayoutManager(dLayoutManager);
                            dRecycle.setAdapter(dAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RiwayatPesanActivity.this,
                                    "Error!. " +e.toString(),
                                    Toast.LENGTH_SHORT).show();
                            System.out.println(e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RiwayatPesanActivity.this,
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
                params.put("status",status);
                return params;
            }
        };

        dQueue.add(stringRequest);

    }

}
