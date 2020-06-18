package com.example.myapplication;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private ImageButton imgBtnkembali, imgBtnsimpan;
    private ImageView imageUpload;
    private ProgressBar loading;
    private EditText username, password, konfpassword;
    private Button btnUpload;
    private LinearLayout linearLayoutBtnregister;
    private Bitmap bitmap;
    final int CODE_GALLERY_REQUEST = 999;
    String URL_REGISTRASI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText)findViewById(R.id.regUsername);
        password = (EditText)findViewById(R.id.regPassword);
        konfpassword = (EditText)findViewById(R.id.regKonfpassword);
        btnUpload = (Button)findViewById(R.id.btnUpload);
        imageUpload = (ImageView)findViewById(R.id.imageUpload);
        loading = (ProgressBar)findViewById(R.id.loading);
        linearLayoutBtnregister = (LinearLayout)findViewById(R.id.linearLayoutBtnRegister);

        Api registrasi = new Api();
        URL_REGISTRASI = registrasi.getURL_REGIST();

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        RegisterActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CODE_GALLERY_REQUEST
                );
            }
        });
        initControl();
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
                imageUpload.setImageBitmap(getResizedBitmap(bitmap,360));
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

    public void initControl() {
        imgBtnkembali = (ImageButton)findViewById(R.id.imgBtnkembali);
        imgBtnkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kembali_ke_login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(kembali_ke_login);
                finish();
            }
        });


        imgBtnsimpan = (ImageButton)findViewById(R.id.imgBtnsimpan);
        imgBtnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //kode proses daftar disini
                if(bitmap ==null){
                    Toast.makeText(RegisterActivity.this, "Foto Harus diisi!" , Toast.LENGTH_SHORT).show();
                }else{
                    simpan();
                }
            }
        });
    }

    private void simpan() {
        linearLayoutBtnregister.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        final String user =  this.username.getText().toString().trim();
        final String pass =  this.password.getText().toString().trim();
        final String konfirmasipass = this.konfpassword.getText().toString().trim();
        final String imageData = imageToString(bitmap);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTRASI ,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Toast.makeText(RegisterActivity.this, "Pesan : " + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                linearLayoutBtnregister.setVisibility(View.VISIBLE);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(RegisterActivity.this, "Pesan : " + e.toString(), Toast.LENGTH_SHORT).show();
                                System.out.println(e.toString());
                                loading.setVisibility(View.GONE);
                                linearLayoutBtnregister.setVisibility(View.VISIBLE);
                            }

                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                            System.out.println(error.toString());
                            loading.setVisibility(View.GONE);
                            linearLayoutBtnregister.setVisibility(View.VISIBLE);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    //mengirim data ke controller
                    Map<String, String> map = new HashMap<>();
                    map.put("username", user);
                    map.put("password", pass);
                    map.put("passwordconf", konfirmasipass);
                    map.put("foto", imageData);
                    return map;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
    }
}
