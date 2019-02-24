package com.cokilabs.nfc.utility;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.cokilabs.nfc.model.User;
import com.google.gson.Gson;



@SuppressLint("CommitPrefEdits")
public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    String auth_token;


    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "appSession";
    private static final String KEY_IS_LOGGEDIN = "loggedIn";
    private static final String KEY_LOGIN_BY= "isLoggedIn";
    private static final String KEY_AUTH_TOKEN= "Authorization";

    public static final String LOGIN_FACEBOOK = "byFacebook";
    public static final String LOGIN_GOOGLE = "byGoogle";
    public static final String LOGIN_SYSTEM = "bySystem";


    public SessionManager(Context context) {
//        this.carts = new ArrayList<Cart>();
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public void setLoginBy(String loginBy){
        editor.putString(KEY_LOGIN_BY, loginBy);
        editor.commit();
    }

    public String getLoginBy(){
        return pref.getString(KEY_LOGIN_BY, " ");
    }


    public void saveLogedInProfile(User profile) {
        Gson gson = new Gson();
        String obj = gson.toJson(profile);
        editor.putString("user", obj);
        editor.commit();
    }

    public User getLogedInProfile() {
        Gson gson = new Gson();
        String jsn = pref.getString("user", " ");

        return gson.fromJson(jsn, User.class);
    }

    public String getAuthToken() {
        return pref.getString(KEY_AUTH_TOKEN, "");
    }

    public void setAuthToken(String auth_token) {
        editor.putString(KEY_AUTH_TOKEN, auth_token);
        editor.commit();
    }


}
