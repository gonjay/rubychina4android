package org.rubychina.app.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by mac on 14-1-29.
 */
public class ApiUtils {

    public static final String SIGN_IN = "http://ruby-china.org/account/sign_in.json";
    public static final String TOPICS = "http://ruby-china.org/api/v2/topics.json";
    public static final String TOPIC_REPLY = "http://ruby-china.org/api/v2/topics/";
    public static final String USER_PROFILE = "http://ruby-china.org/api/v2/users/";
    public static final String TOPIC_VIEW = "http://ruby-china.org/api/v2/topics/";
    public static final String TOPIC_NEW = "http://ruby-china.org/api/v2/topics.json";
    public static final String NODES = "http://ruby-china.org/api/v2/nodes.json";

    public static AsyncHttpClient client = new AsyncHttpClient();

    public static void post(String url, ApiParams params,
                            AsyncHttpResponseHandler handler) {
        client.post(url, params, handler);
    }

    public static void get(String url, ApiParams params, AsyncHttpResponseHandler handler){
        client.get(url, params, handler);
    }
}
