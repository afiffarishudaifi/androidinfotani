package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Adapter.AdapterDesa;
import com.example.myapplication.Adapter.AdapterKomoditas;
import com.example.myapplication.Model.DataDesa;
import com.example.myapplication.Model.DataKomoditas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DataPetaniActivity extends AppCompatActivity implements View.OnClickListener {
    private String URL_DATA_PETANI;
    private EditText ktp, alamat, nohp;
    private TextView tglpanen,nama_desa, nama_komoditas;
    private String dnamaDesa, dnamaKomoditas;
    private String didDesa ="0";
    private String didKomoditas="0";
    private ImageButton imgBtnpetanisimpan, imgBtnpetanikeluar;
    private LinearLayout linearLayoutBtnpetani;
    private ProgressBar loadingPetani, loadingSpinnerDesa, loadingSpinnerKomoditas;


    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    //session deklar
    private SessionManager sessionManager;
    private String mId_user, mUsername, mKtp, URL_FILL_DATA_PETANI;

    //spinner desa
    String temp_id_desa;
    Spinner spinner_desa;
    ProgressDialog pDialog;
    AdapterDesa adapter_desa;
    String URL_DESA;
    List<DataDesa> listDesa = new ArrayList<DataDesa>();

    //spinner komoditas
    String temp_id_komoditas;
    Spinner spinner_komoditas;
    String URL_KOMODITAS;
    AdapterKomoditas adapter_komoditas;
    List<DataKomoditas> listKomoditas = new ArrayList<DataKomoditas>();

    private static final String TAG = DataPetaniActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_petani);

//ambil url dari api.java
        Api dataPetani = new Api();
        URL_DATA_PETANI = dataPetani.getURL_DATA_PETANI();
        URL_DESA = dataPetani.getURL_DESA();
        URL_KOMODITAS = dataPetani.getURL_KOMODITAS();
        URL_FILL_DATA_PETANI = dataPetani.getURL_FILL_DATA_PETANI();

        //kumpulan inisialisasi
        initControl();

        //cek session
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        mKtp = user.get(sessionManager.KTP);
        mId_user = user.get(sessionManager.ID_USER);
        mUsername = user.get(sessionManager.USERNAME);

        //start spinner desa
        spinner_desa = (Spinner) findViewById(R.id.spinner_desa);
        spinner_desa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if(position <= 0){
                    if(!didDesa.equals("0")) {
                        temp_id_desa = didDesa;
                        nama_desa.setText(dnamaDesa);
                    }
                }else {
                    temp_id_desa = listDesa.get(position).getIdDesa();
                    nama_desa.setText(listDesa.get(position).getNamaDesa());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        adapter_desa = new AdapterDesa(DataPetaniActivity.this, listDesa);
        spinner_desa.setAdapter(adapter_desa);
        callDataDesa();
        //end spinner_desa

        //start spinner komoditas
        spinner_komoditas = (Spinner) findViewById(R.id.spinner_komoditas);
        spinner_komoditas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if(position <= 0){
                    if(!didKomoditas.equals("0")) {
                        temp_id_komoditas = didKomoditas;
                        nama_komoditas.setText(dnamaKomoditas);
                    }
                }else {
                    temp_id_komoditas = listKomoditas.get(position).getIdKomoditas();
                    nama_komoditas.setText(listKomoditas.get(position).getNamaKomoditas());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        adapter_komoditas = new AdapterKomoditas(DataPetaniActivity.this, listKomoditas);
        spinner_komoditas.setAdapter(adapter_komoditas);
        callDataKomoditas();
        //end spinner_komoditas
        if(!mKtp.equals("0")){
        fillData();
        }
    }
    private void initControl(){
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        loadingSpinnerDesa = (ProgressBar) findViewById(R.id.loadingSpinnerDesa);
        loadingSpinnerKomoditas = (ProgressBar) findViewById(R.id.loadingSpinnerKomoditas);
        loadingPetani = (ProgressBar) findViewById(R.id.loadingPetani);
        linearLayoutBtnpetani = (LinearLayout)findViewById(R.id.linearLayoutBtnpetani);
        ktp =(EditText) findViewById(R.id.ktp);
        alamat =(EditText) findViewById(R.id.alamat);
        nohp =(EditText) findViewById(R.id.nohp);
        tglpanen =(TextView) findViewById(R.id.tglpanen);
        nama_desa = (TextView) findViewById(R.id.nama_desa);
        nama_komoditas = (TextView) findViewById(R.id.nama_komoditas);
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
            case R.id.tglpanen:
                showDateDialog();
                break;
        }
    }

//proses insert/update data
    private void simpan() {
        linearLayoutBtnpetani.setVisibility(View.GONE);
        loadingPetani.setVisibility(View.VISIBLE);
        final String id_user = this.mId_user;
        final String username = this.mUsername;
        final String ktp =  this.ktp.getText().toString().trim();
        final String alamat =  this.alamat.getText().toString().trim();
        final String desa = this.temp_id_desa;
        final String nohp = this.nohp.getText().toString().trim();
        final String komoditas = this.temp_id_komoditas;
        final String tglpanen = this.tglpanen.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DATA_PETANI ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(DataPetaniActivity.this, "Pesan : " + jsonObject.getString("message")+"\nMungkin anda harus masuk kembali", Toast.LENGTH_SHORT).show();
                            sessionManager.loginKembali();
                            }else {
                                Toast.makeText(DataPetaniActivity.this, "Pesan : " + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }loadingPetani.setVisibility(View.GONE);
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


    //load data desa
    private void callDataDesa() {
        listDesa.clear();
        showloadingDesa();
        // Creating volley request obj
        JsonArrayRequest jArr = new JsonArrayRequest(URL_DESA,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);

                                DataDesa item = new DataDesa();

                                item.setIdDesa(obj.getString("ID_DESA"));
                                item.setNamaDesa(obj.getString("NAMA_DESA"));

                                listDesa.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(DataPetaniActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter_desa.notifyDataSetChanged();

                        hideloadingDesa();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(DataPetaniActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                hideloadingDesa();
            }
        });
        // Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jArr);
    }


    //load data desa
    private void callDataKomoditas() {
        listKomoditas.clear();
        showloadingKomoditas();
        // Creating volley request obj
        JsonArrayRequest jArrK = new JsonArrayRequest(URL_KOMODITAS,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);

                                DataKomoditas item = new DataKomoditas();

                                item.setIdKomoditas(obj.getString("ID_KOMODITAS"));
                                item.setNamaKomoditas(obj.getString("NAMA_KOMODITAS"));

                                listKomoditas.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(DataPetaniActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter_komoditas.notifyDataSetChanged();

                        hideloadingKomoditas();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(DataPetaniActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                hideloadingKomoditas();
            }
        });
        // Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jArrK);
    }

    private void showloadingDesa(){
        linearLayoutBtnpetani.setVisibility(View.GONE);
        spinner_desa.setVisibility(View.GONE);
        loadingPetani.setVisibility(View.VISIBLE);
        loadingSpinnerDesa.setVisibility(View.VISIBLE);
    }
    private void hideloadingDesa(){
        loadingPetani.setVisibility(View.GONE);
        loadingSpinnerDesa.setVisibility(View.GONE);
        linearLayoutBtnpetani.setVisibility(View.VISIBLE);
        spinner_desa.setVisibility(View.VISIBLE);
    }
    private void showloadingKomoditas(){
        linearLayoutBtnpetani.setVisibility(View.GONE);
        spinner_komoditas.setVisibility(View.GONE);
        loadingPetani.setVisibility(View.VISIBLE);
        loadingSpinnerKomoditas.setVisibility(View.VISIBLE);
    }
    private void hideloadingKomoditas(){
        loadingPetani.setVisibility(View.GONE);
        loadingSpinnerKomoditas.setVisibility(View.GONE);
        linearLayoutBtnpetani.setVisibility(View.VISIBLE);
        spinner_komoditas.setVisibility(View.VISIBLE);
    }

    private void showDateDialog(){
        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();
        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                tglpanen.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }

    private void fillData(){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_FILL_DATA_PETANI,
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
                                        String dKtp = object.getString("ktp").trim();
                                        String dAlamat = object.getString("alamat").trim();
                                        String dDesa = object.getString("desa").trim();
                                        String dNohp = object.getString("nohp").trim();
                                        String dKomoditas = object.getString("komoditas").trim();
                                        String dTglpanen = object.getString("tglpanen").trim();
                                        String dnDesa = object.getString("nama_desa").trim();
                                        String dnKomoditas = object.getString("nama_komoditas").trim();

                                        dnamaDesa = dnDesa;
                                        dnamaKomoditas = dnKomoditas;
                                        didDesa = dDesa;
                                        didKomoditas = dKomoditas;
                                        ktp.setText(dKtp);
                                        alamat.setText(dAlamat);
                                        nohp.setText(dNohp);
                                        tglpanen.setText(dTglpanen);

                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(DataPetaniActivity.this,
                                        "Error!. " +e.toString(),
                                        Toast.LENGTH_SHORT).show();
                                System.out.println(e.toString());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(DataPetaniActivity.this,
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
