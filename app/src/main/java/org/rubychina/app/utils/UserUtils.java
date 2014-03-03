package org.rubychina.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.rubychina.app.MyApp;

/**
 * Created by mac on 14-1-29.
 */
public class UserUtils {

    public static Context getAppContext() {
        return MyApp.getContext();
    }

    public static String loadTopic(String type) {
        SharedPreferences pref = getAppContext().getSharedPreferences(
                "topic", 0);
        return pref.getString(type, "");
    }

    public static boolean cacheTopic(String responce, String type) {
        SharedPreferences pref = getAppContext().getSharedPreferences(
                "topic", 0);
        return pref.edit().putString(type, responce).commit();
    }

    public static String getUserToken(){
        SharedPreferences pref = getAppContext().getSharedPreferences(
                "user", 0);
        return pref.getString("token", "");
    }

    public static boolean saveUserToken(String s) {
        SharedPreferences pref = getAppContext().getSharedPreferences(
                "user", 0);
        return pref.edit().putString("token", s).commit();
    }

    public static String getUserTempToken(){
        SharedPreferences pref = getAppContext().getSharedPreferences(
                "user", 0);
        return pref.getString("temp_access_token", "");
    }

    public static boolean saveUserTempToken(String s) {
        SharedPreferences pref = getAppContext().getSharedPreferences(
                "user", 0);
        return pref.edit().putString("temp_access_token", s).commit();
    }

    public static String getUserLogin(){
        SharedPreferences pref = getAppContext().getSharedPreferences(
                "user", 0);
        return pref.getString("login", "未登录");
    }

    public static boolean saveUserLogin(String s) {
        SharedPreferences pref = getAppContext().getSharedPreferences(
                "user", 0);
        return pref.edit().putString("login", s).commit();
    }

    public static String getUserEmail(){
        SharedPreferences pref = getAppContext().getSharedPreferences(
                "user", 0);
        return pref.getString("email", "");
    }

    public static boolean saveUserEmail(String s) {
        SharedPreferences pref = getAppContext().getSharedPreferences(
                "user", 0);
        return pref.edit().putString("email", s).commit();
    }

    public static String getUserAvatar(){
        SharedPreferences pref = getAppContext().getSharedPreferences(
                "user", 0);
        return pref.getString("avatar_url", "http://ruby-china.org/avatar/default.png?s=120&d=404");
    }

    public static boolean saveUserAvatar(String s) {
        SharedPreferences pref = getAppContext().getSharedPreferences(
                "user", 0);
        return pref.edit().putString("avatar_url", s).commit();
    }

    public static boolean clearUser() {
        SharedPreferences pref = getAppContext().getSharedPreferences(
                "user", 0);
        return pref.edit().clear().commit();
    }

    public static boolean logined() {
        return getUserToken().length() > 0;
    }
}
