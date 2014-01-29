package org.rubychina.app.utils;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * Created by mac on 14-1-29.
 */
public class JsonUtils {

    public static String getString(JSONObject obj, String key) {
        return getString(obj, key, null);
    }

    public static String getString(JSONObject obj, String key, String dft) {
        if (!obj.has(key))
            return dft;
        try {
            Object value = obj.get(key);
            if (value == null)
                return null;
            String ret = value.toString();
            if ("null".equals(ret))
                return null;
            return ret;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("JsonUtils", "parse error");
            return dft;
        }
    }

    public static int getInt(JSONObject obj, String key) {
        return getInt(obj, key, 0);
    }

    public static int getInt(JSONObject obj, String key, int dft) {
        if (!obj.has(key))
            return dft;
        try {
            int value = obj.getInt(key);
            return value;
        } catch (JSONException e) {
            //e.printStackTrace();
            return dft;
        }
    }
}

