package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class PengaturanActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView fotoUser;
    private ImageButton btnSimpan, btnKeluar;
    private EditText passLama, passBaru, passBaruKonf;
    private Button btnFoto;
    private ProgressBar loadingPengaturan;
    private LinearLayout linearLayoutBtnPengaturan;
    SessionManager sessionManager;

    private String URL_UBAH_PENGATURAN;

    private String mId_user, mUsername, mFoto_user, mKtp, URL_FOTO;

    private Bitmap bitmap;
    final int CODE_GALLERY_REQUEST = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        initControl();
        Api pengaturan = new Api();
        URL_UBAH_PENGATURAN = pengaturan.getURL_UBAH_PENGATURAN();

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        mId_user = user.get(sessionManager.ID_USER);
        mUsername = user.get(sessionManager.USERNAME);
        mFoto_user = user.get(sessionManager.FOTO_USER);
        mKtp = user.get(sessionManager.KTP);
        URL_FOTO = "http://192.168.43.171/ciinfotani/img/user/"+mFoto_user;

        loadFoto();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pimgBtnsimpan:
                ubah();
                break;
            case R.id.pimgBtnKeluar:
                Intent toMain = new Intent(PengaturanActivity.this, MainActivity.class);
                startActivity(toMain);
                finish();
                break;
            case R.id.pBtnFoto:
                ActivityCompat.requestPermissions(
                        PengaturanActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CODE_GALLERY_REQUEST
                );
                break;
        }
    }

    private void initControl(){
        fotoUser = (ImageView) findViewById(R.id.pFotouser);
        passBaru = (EditText) findViewById(R.id.pPasswordbaru);
        passBaruKonf = (EditText) findViewById(R.id.pPasswordbarukonf);
        passLama = (EditText) findViewById(R.id.pPasswordlama);
        btnKeluar = (ImageButton) findViewById(R.id.pimgBtnKeluar);
        btnKeluar.setOnClickListener(this);
        btnSimpan = (ImageButton) findViewById(R.id.pimgBtnsimpan);
        btnSimpan.setOnClickListener(this);
        btnFoto = (Button) findViewById(R.id.pBtnFoto);
        btnFoto.setOnClickListener(this);
        loadingPengaturan = (ProgressBar)findViewById(R.id.loading);
        linearLayoutBtnPengaturan = (LinearLayout)findViewById(R.id.linearLayoutBtn);
    }

    private void loadFoto(){
        Glide.with(PengaturanActivity.this)
                // LOAD URL DARI INTERNET
                .load(URL_FOTO)
                // LOAD GAMBAR AWAL SEBELUM GAMBAR UTAMA MUNCUL, BISA DARI LOKAL DAN INTERNET
                .placeholder(R.drawable.ic_person_white_24dp)
                //. LOAD GAMBAR SAAT TERJADI KESALAHAN MEMUAT GMBR UTAMA
                .error(R.drawable.ic_person_white_24dp)
                .into(fotoUser);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CODE_GALLERY_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), CODE_GALLERY_REQUEST);
            } else {
                Toast.makeText(getApplicationContext(), "Kamu tidak memiliki akses", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null){
            Uri filePath = data.getData();
            try {
                //mengambil gambar dari Gallery
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                //menampilkan gambar ke imageview
                fotoUser.setImageBitmap(getResizedBitmap(bitmap,360));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    //resize gambar
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void ubah(){

        final String iduser =  this.mId_user.trim();
        final String passlama = this.passLama.getText().toString().trim();
        final String passbaru = this.passBaru.getText().toString().trim();
        final String passbarukonf = this.passBaruKonf.getText().toString().trim();
//        final String fotolama = this.mFoto_user.trim();
//        final String username = this.mUsername.trim();
//        final String imageData = imageToString(bitmap);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UBAH_PENGATURAN ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            if(success.equals("1")) {
                                Toast.makeText(PengaturanActivity.this, "Pesan : " + message, Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(PengaturanActivity.this, "Pesan : " + message, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PengaturanActivity.this, "Pesan : " + e.toString(), Toast.LENGTH_SHORT).show();
                            System.out.println(e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PengaturanActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        System.out.println(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //mengirim data ke controller
                Map<String, String> map = new HashMap<>();
                map.put("iduser", iduser); //dari session
                map.put("passlama", passlama);
                map.put("passbaru", passbaru);
                map.put("passbarukonf", passbarukonf);
//                map.put("fotolama", fotolama); //dari session
//                map.put("user", username); //dari session
//                map.put("foto", imageData);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
