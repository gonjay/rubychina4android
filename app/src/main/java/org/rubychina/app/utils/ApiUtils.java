package org.rubychina.app.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by mac on 14-1-29.
 */
public class ApiUtils {

    public static final String HOST = "http://ruby-china.org/";
    public static final String FAYE_SERVER = "ws://ruby-china.org:8080/faye";
//    public static final String HOST = "http://192.168.1.102:3000/";

    public static final String API = HOST + "api/v2/";
    public static final String API0 = HOST + "api/";

    public static final String SIGN_IN = HOST + "account/sign_in.json";

    public static final String TOPICS = API + "topics.json";
    public static final String TOPIC_REPLY = API + "topics/%s/replies.json";
    public static final String USER_PROFILE = API + "users/%s.json";
    public static final String TOPIC_VIEW = API + "topics/%s.json";
    public static final String TOPIC_NEW = API + "topics.json";
    public static final String NODES = API + "nodes.json";
    public static final String NODE_URL = API + "topics/node/%s.json";
    public static final String NOTIFICATIONS = API + "notifications.json";
    public static final String NOTIFICATION_DELETE = API + "notifications/%s.json?token=%s";
    public static final String USER_PROFILE_TOPICS = API + "users/%s/topics.json";
    public static final String USER_PROFILE_TOPICS_FAVORITE = API + "users/%s/topics/favorite.json";
    public static final String USER_TEMP_ACCESS_TOKEN = API0 + "users/temp_access_token.json";//Need to use old api to get temp_access_token
    public static final String TOP_USERS = API + "users.json";
    public static final String WIKI = HOST + "wiki";
    public static final String SIGN_UP = HOST + "account/sign_up";

    public static final String FAYE_CHANNEL = "/notifications_count/%s";

    public static AsyncHttpClient client = new AsyncHttpClient();

    public static void post(String url, ApiParams params,
                            AsyncHttpResponseHandler handler) {
        client.post(url, params, handler);
    }

    public static void get(String url, ApiParams params, AsyncHttpResponseHandler handler){
        client.get(url, params, handler);
    }

    public static void delete(String url, AsyncHttpResponseHandler handler){
        client.delete(url, handler);
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
