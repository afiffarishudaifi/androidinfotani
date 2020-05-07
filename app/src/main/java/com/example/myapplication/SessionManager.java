package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String KTP = "KTP";
    public static final String USERNAME = "USERNAME";
    public static final String ID_USER = "ID_USER";
    public static final String FOTO_USER = "FOTO_USER";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String ktp, String id_user, String username, String foto_user){
        editor.putBoolean(LOGIN,true);
        editor.putString(KTP,ktp);
        editor.putString(USERNAME,username);
        editor.putString(ID_USER,id_user);
        editor.putString(FOTO_USER,foto_user);
        editor.apply();
    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN,false);
    }
    public void checkLogin(){
        if(!this.isLoggin()){
            Intent kembaliLogin = new Intent(context, LoginActivity.class);
            context.startActivity(kembaliLogin);
            ((MainActivity)context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(KTP,sharedPreferences.getString(KTP,null));
        user.put(ID_USER,sharedPreferences.getString(ID_USER,null));
        user.put(USERNAME,sharedPreferences.getString(USERNAME,null));
        user.put(FOTO_USER,sharedPreferences.getString(FOTO_USER,null));

        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent kembaliLogin = new Intent(context, LoginActivity.class);
        context.startActivity(kembaliLogin);
        ((MainActivity)context).finish();
    }
    //dipakai saat input data petani pertama kali
    public void loginKembali(){
        editor.clear();
        editor.commit();
        Intent kembaliLogin = new Intent(context, LoginActivity.class);
        context.startActivity(kembaliLogin);
        ((DataPetaniActivity)context).finish();
    }
}
