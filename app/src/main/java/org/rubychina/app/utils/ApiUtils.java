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
    public static final String TOPIC_REPLY = "http://ruby-china.org/api/v2/topics/%s/replies.json";
    public static final String USER_PROFILE = "http://ruby-china.org/api/v2/users/%s.json";
    public static final String USER_PROFILE_TOPICS = "http://ruby-china.org/api/v2/users/%s/topics.json";
    public static final String USER_PROFILE_TOPICS_FAVORITE = "http://ruby-china.org/api/v2/users/%s/topics/favorite.json";
    public static final String TOPIC_VIEW = "http://ruby-china.org/api/v2/topics/%s.json";
    public static final String TOPIC_NEW = "http://ruby-china.org/api/v2/topics.json";
    public static final String NODES = "http://ruby-china.org/api/v2/nodes.json";
    public static final String NODE_URL = "http://ruby-china.org/api/topics/node/%s.json";

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
