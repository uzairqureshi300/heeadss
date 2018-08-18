package com.app.heads.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;



import static android.content.Context.MODE_PRIVATE;


public class UserSession {
    private final String role_id = "role_id";
    private final String role_name = "role_name";
    private final String user_id = "user_id";
    private final String region_id = "region_id";
    private final String username = "username";
    private final String email = "email";
    private final String usertype = "usertype";


    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;
    private Context mContext;
    private SharedPreferences info_sp;
    SharedPreferences.Editor info_editor;



    public UserSession(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        info_sp = context.getSharedPreferences("Userdata", MODE_PRIVATE);

        this.mContext = context;
    }


    private static UserSession instance;

    public static UserSession getInstance(Context context) {
        if (instance == null) {
            instance = new UserSession(context);
        }
        return instance;
    }

    public String getRole_id()
    {
        return sp.getString(role_id, "0");
    }

    public boolean setRole_id(String status) {
        spEditor = sp.edit();
        spEditor.putString(role_id, status);
        spEditor.apply();
        return true;
    }
    public boolean setRole_name(String status) {
        spEditor = sp.edit();
        spEditor.putString(role_name , status);
        spEditor.apply();
        return true;
    }
    public String getRole_name() {
        return sp.getString(role_name, "0");
    }

    public boolean setUserid(String status) {
        spEditor = sp.edit();
        spEditor.putString(user_id , status);
        spEditor.apply();
        return true;
    }
    public String getUser_id() {
        return sp.getString(user_id, "0");
    }

    public boolean setRegionId(String status) {
        spEditor = sp.edit();
        spEditor.putString(region_id , status);
        spEditor.apply();
        return true;
    }
    public String getRegionId() {
        return sp.getString(region_id , "0");
    }
    public boolean setUsername(String status) {
        spEditor = sp.edit();
        spEditor.putString(username , status);
        spEditor.apply();
        return true;
    }
    public String getUsername() {
        return sp.getString(username, "0");
    }

    public boolean setEmail(String status) {
        spEditor = sp.edit();
        spEditor.putString(email , status);
        spEditor.apply();
        return true;
    }
    public String getEmail() {
        return sp.getString(email , "0");
    }

    public boolean setUserType(String status) {
        spEditor = sp.edit();
        spEditor.putString(usertype , status);
        spEditor.apply();
        return true;
    }
    public String getUserType() {
        return sp.getString(usertype, "0");
    }

    public boolean DestroySession(){
        spEditor.remove(role_id);
        spEditor.remove(role_name);
        spEditor.remove(user_id);
        spEditor.remove(region_id);
        spEditor.remove(username);
        spEditor.remove(email);
        spEditor.remove(usertype);



        return true;
    }
}