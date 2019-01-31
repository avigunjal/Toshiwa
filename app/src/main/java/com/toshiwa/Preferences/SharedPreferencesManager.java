package com.toshiwa.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private static final String isLogin = "false";
    private static final String empid = "empid";
    private static final String name = "name";
    private static final String mobile = "mobile";
    private static final String password = "password";
    private static final String isAdmin = "admin";

    private SharedPreferencesManager() {}

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("user", Context.MODE_PRIVATE);
    }

    public static String getEmpid(Context context) {
        return getSharedPreferences(context).getString(empid , null);
    }

    public static void setEmpid(Context context, String newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(empid , newValue);
        editor.commit();
    }

    public static void setIsLogin(Context context, Boolean newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(isLogin , newValue);
        editor.commit();
    }

    public static Boolean getIsLogin(Context context) {
        return getSharedPreferences(context).getBoolean(isLogin , false);
    }

    public static void setIsAdmin(Context context, Boolean newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(isAdmin , newValue);
        editor.commit();
    }

    public static Boolean getIsAdmin(Context context) {
        return getSharedPreferences(context).getBoolean(isAdmin , false);
    }

    public static void setName(Context context, String newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(name , newValue);
        editor.commit();
    }

    public static String getName(Context context) {
        return getSharedPreferences(context).getString(name , null);
    }

    public static void setMobile(Context context, String newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(mobile , newValue);
        editor.commit();
    }

    public static String getMobile(Context context) {
        return getSharedPreferences(context).getString(mobile , null);
    }

    public static String getPassword(Context context) {
        return getSharedPreferences(context).getString(password , null);
    }

    public static void setPassword(Context context, String newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(password , newValue);
        editor.commit();
    }
}
