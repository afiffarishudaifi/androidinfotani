package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView username, id_user;
    private ImageButton btnDatapetani, btnFormpanen, btnLappanen,
            btnLappesan, btnRiwayatpesan, btnFormtanya, btnPengaturan, btnLogout;
    private ImageView fotoUser;
    SessionManager sessionManager;

    private String URL_CEK_PANEN;

    private String mId_user, mUsername, mFoto_user, mKtp, URL_FOTO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Api main = new Api();
        URL_CEK_PANEN = main.getURL_CEK_PANEN();

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        initControl();

        HashMap<String, String> user = sessionManager.getUserDetail();
        mId_user = user.get(sessionManager.ID_USER);
        mUsername = user.get(sessionManager.USERNAME);
        mFoto_user = user.get(sessionManager.FOTO_USER);
        mKtp = user.get(sessionManager.KTP);
        URL_FOTO = "http://192.168.43.171/ciinfotani/img/user/"+mFoto_user;

        //set nama dari session
        username.setText(mUsername);
        //set foto
        Glide.with(MainActivity.this)
                // LOAD URL DARI INTERNET
                .load(URL_FOTO)
                // LOAD GAMBAR AWAL SEBELUM GAMBAR UTAMA MUNCUL, BISA DARI LOKAL DAN INTERNET
                .placeholder(R.drawable.ic_person_white_24dp)
                //. LOAD GAMBAR SAAT TERJADI KESALAHAN MEMUAT GMBR UTAMA
                .error(R.drawable.ic_person_white_24dp)
                .into(fotoUser);
//            cek_panen();
    }

    private void initControl() {
        fotoUser = (ImageView) findViewById(R.id.foto_user);
        username = (TextView) findViewById(R.id.namaPengguna);
        btnLogout = (ImageButton) findViewById(R.id.imgBtnLogout);
        btnLogout.setOnClickListener(this);
        btnDatapetani = (ImageButton) findViewById(R.id.btnDatapetani);
        btnDatapetani.setOnClickListener(this);
        btnFormpanen = (ImageButton) findViewById(R.id.btnFormpanen);
        btnFormpanen.setOnClickListener(this);
        btnLappanen = (ImageButton) findViewById(R.id.btnLappanen);
        btnLappanen.setOnClickListener(this);
        btnLappesan = (ImageButton) findViewById(R.id.btnLappesan);
        btnLappesan.setOnClickListener(this);
        btnRiwayatpesan = (ImageButton) findViewById(R.id.btnRiwayatpesan);
        btnRiwayatpesan.setOnClickListener(this);
        btnFormtanya = (ImageButton) findViewById(R.id.btnTanya);
        btnFormtanya.setOnClickListener(this);
        btnPengaturan = (ImageButton)findViewById(R.id.imgBtnpengaturan);
        btnPengaturan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnpengaturan:
                Intent toPengaturan = new Intent(MainActivity.this, PengaturanActivity.class);
                startActivity(toPengaturan);
                break;
            case R.id.imgBtnLogout:
                sessionManager.logout();
                finish();
                break;
            case R.id.btnDatapetani:
                Intent dataPetani = new Intent(MainActivity.this, DataPetaniActivity.class);
                startActivity(dataPetani);
                break;
            case R.id.btnFormpanen:
                Intent formPanen = new Intent(MainActivity.this, FormPanenActivity.class);
                startActivity(formPanen);
                break;
            case R.id.btnLappanen:
                Intent lapPanen = new Intent(MainActivity.this, LapPanenActivity.class);
                startActivity(lapPanen);
                break;
            case R.id.btnLappesan:
                Intent lapPesan = new Intent(MainActivity.this, LapPesanActivity.class);
                startActivity(lapPesan);
                break;
            case R.id.btnRiwayatpesan:
                Intent riwayatPesan = new Intent(MainActivity.this, RiwayatPesanActivity.class);
                startActivity(riwayatPesan);
                break;
            case R.id.btnTanya:
                Intent formTanya = new Intent(MainActivity.this, FormTanyaActivity.class);
                startActivity(formTanya);
                break;
        }
    }

    private void showNotif() {
        String NOTIFICATION_CHANNEL_ID = "channel_androidnotif";
        Context context = this.getApplicationContext();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String channelName = "Android Notif Channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        Intent mIntent = new Intent(MainActivity.this, FormPanenActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("fromnotif", "notif");
        mIntent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID);
        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.logo))
                .setTicker("notif starting")
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setLights(Color.RED, 3000, 3000)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentTitle("Komoditas Anda Telah Panen")
                .setContentText("Klik Disini untuk mengisi data panen!");

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(115, builder.build());
    }

    private void cek_panen() {
        final String ktp =  this.mKtp.trim();
        final String idUser =  this.mId_user.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CEK_PANEN ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            if(success.equals("1")) {
                                String donepanen = jsonObject.getString("donepanen");
                                String donemessage = jsonObject.getString("donemessage");
                                if(donepanen.equals("1")) {
                                    Toast.makeText(MainActivity.this, "Pesan : " + donemessage, Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(MainActivity.this, "Pesan : " + message, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(MainActivity.this, "Pesan : " + donemessage, Toast.LENGTH_LONG).show();
                                    showNotif();
                                }
                            }else{
                                Toast.makeText(MainActivity.this, "Pesan : " + message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Pesan : " + e.toString(), Toast.LENGTH_SHORT).show();
                            System.out.println(e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        System.out.println(error.toString());

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //mengirim data ke controller
                Map<String, String> map = new HashMap<>();
                map.put("ktp", ktp);
                map.put("id_user", idUser);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}

