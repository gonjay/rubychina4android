package org.rubychina.app.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by mac on 14-1-29.
 */
public class ApiUtils {

    public static final String LOGIN = "http://ruby-china.org/account/sign_in.json";

    public static final String TOPICS = "http://ruby-china.org/api/v2/topics.json?page=1&per_page=20";

    public static AsyncHttpClient client = new AsyncHttpClient();

    public static void post(String url, ApiParams params,
                            AsyncHttpResponseHandler handler) {
        client.post(url, params, handler);
    }

    public static void get(String url, ApiParams params, AsyncHttpResponseHandler handler){
        client.get(url, params, handler);
    }
}
