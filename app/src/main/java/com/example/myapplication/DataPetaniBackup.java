//package com.example.myapplication;
//
//import android.app.ProgressDialog;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.VolleyLog;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.example.myapplication.Adapter.AdapterDesa;
//import com.example.myapplication.Model.DataDesa;
//import com.example.myapplication.Util.AppController;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class DataPetaniBackup extends AppCompatActivity {
//    TextView txt_hasil;
//    Spinner spinner_desa;
//    ProgressDialog pDialog;
//    AdapterDesa adapter;
//    String URL_DESA;
//    List<DataDesa> listDesa = new ArrayList<DataDesa>();
//
//    private static final String TAG = DataPetaniBackup.class.getSimpleName();
//
//    public static final String TAG_ID_DESA = "ID_DESA";
//    public static final String TAG_NAMA_DESA = "NAMA_DESA";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_data_petani);
//
//        Api desa = new Api();
//        URL_DESA = desa.getURL_DESA();
//
//        txt_hasil = (TextView) findViewById(R.id.spHasil);
//        spinner_desa = (Spinner) findViewById(R.id.spinner_desa);
//
//        spinner_desa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                // TODO Auto-generated method stub
//                txt_hasil.setText("NAMA DESA : " + listDesa.get(position).getNamaDesa());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // TODO Auto-generated method stub
//
//            }
//        });
//
//        adapter = new AdapterDesa(DataPetaniBackup.this, listDesa);
//        spinner_desa.setAdapter(adapter);
//
//        callData();
//
//    }
//    private void callData() {
//        listDesa.clear();
//
//        pDialog = new ProgressDialog(DataPetaniBackup.this);
//        pDialog.setCancelable(false);
//        pDialog.setMessage("Loading...");
//        showDialog();
//
//        // Creating volley request obj
//        JsonArrayRequest jArr = new JsonArrayRequest(URL_DESA,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Log.e(TAG, response.toString());
//
//                        // Parsing json
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//                                JSONObject obj = response.getJSONObject(i);
//
//                                DataDesa item = new DataDesa();
//
//                                item.setIdDesa(obj.getString(TAG_ID_DESA));
//                                item.setNamaDesa(obj.getString(TAG_NAMA_DESA));
//
//                                listDesa.add(item);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        // notifying list adapter about data changes
//                        // so that it renders the list view with updated data
////                        adapter.notifyDataSetChanged();
//
//                        hideDialog();
//                    }
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.e(TAG, "Error: " + error.getMessage());
//                Toast.makeText(DataPetaniBackup.this, error.getMessage(), Toast.LENGTH_LONG).show();
//                hideDialog();
//            }
//        });
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(jArr);
//    }
//
//    private void showDialog() {
//        if (!pDialog.isShowing())
//            pDialog.show();
//    }
//
//    private void hideDialog() {
//        if (pDialog.isShowing())
//            pDialog.dismiss();
//    }
//}
