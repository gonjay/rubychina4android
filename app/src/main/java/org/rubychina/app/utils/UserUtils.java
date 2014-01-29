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

    public static String loadTopic() {
        SharedPreferences pref = getAppContext().getSharedPreferences(
                "topic", 0);
        return pref.getString("topic_cache", "");
    }

    public static boolean cacheTopic(String responce) {
        SharedPreferences pref = getAppContext().getSharedPreferences(
                "topic", 0);
        return pref.edit().putString("topic_cache", responce).commit();
    }
}
