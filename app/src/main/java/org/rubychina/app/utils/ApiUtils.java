package org.rubychina.app.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by mac on 14-1-29.
 */
public class ApiUtils {

    public static final String HOST = "http://ruby-china.org/api/v2/";
//    public static final String HOST = "http://192.168.1.102:3000/api/v2/";

    public static final String SIGN_IN = "http://ruby-china.org/account/sign_in.json";

    public static final String TOPICS = HOST + "topics.json";
    public static final String TOPIC_REPLY = HOST + "topics/";
    public static final String USER_PROFILE = HOST + "users/";
    public static final String TOPIC_VIEW = HOST + "topics/";
    public static final String TOPIC_NEW = HOST + "topics.json";
    public static final String NODES = HOST + "nodes.json";
    public static final String NODE_URL = HOST + "topics/node/%s.json";

    public static AsyncHttpClient client = new AsyncHttpClient();

    public static void post(String url, ApiParams params,
                            AsyncHttpResponseHandler handler) {
        client.post(url, params, handler);
    }

    public static void get(String url, ApiParams params, AsyncHttpResponseHandler handler){
        client.get(url, params, handler);
    }

    /*
    *****可用的URL列表*******
        Method	URL
        GET	    http://ruby-china.org/api/topics.json
        GET	    http://ruby-china.org/api/topics/node/:id.json
        POST	http://ruby-china.org/api/topics.json
        GET	    http://ruby-china.org/api/topics/:id.json
        POST	http://ruby-china.org/api/topics/:id/replies.json
        POST	http://ruby-china.org/api/topics/:id/follow.json
        POST	http://ruby-china.org/api/topics/:id/unfollow.json
        POST	http://ruby-china.org/api/topics/:id/favorite.json
        GET	    http://ruby-china.org/api/nodes.json
        PUT	    http://ruby-china.org/api/user/favorite/:user/:topic.json
        GET	    http://ruby-china.org/api/users.json
        GET	    http://ruby-china.org/api/users/temp_access_token.json
        GET	    http://ruby-china.org/api/users/:user.json
        GET	    http://ruby-china.org/api/users/:user/topics.json
        GET	    http://ruby-china.org/api/users/:user/topics/favorite.json
        GET	    http://ruby-china.org/api/sites.json

    */
}
