package org.rubychina.app.utils;

import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by mac on 14-1-29.
 */
public class ApiParams extends RequestParams {
    public ApiParams with(String key, String value) {
        put(key, value);
        return this;
    }

    public ApiParams withToken() {
        put("token", UserUtils.getUserToken());
        return this;
    }

    public ApiParams withFile(String key, File file) {
        try {
            put(key, file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return this;
    }
}
