package org.rubychina.app.model;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.rubychina.app.utils.ApiUtils;
import org.rubychina.app.utils.UserUtils;

/**
 * Created by mac on 14-2-11.
 */
public class Notification {

    public String id;
    public String created_at;
    public String updated_at;
    public boolean read;
    public Mention mention;
    public Mention reply;

    public void delete(){
        ApiUtils.delete(String.format(ApiUtils.NOTIFICATION_DELETE, id, UserUtils.getUserToken()), new AsyncHttpResponseHandler());
    }

    public Mention getMention(){
        return mention == null ? reply : mention;
    }

    public class Mention {
        public String id;
        public String title;
        public String created_at;
        public String updated_at;
        public String replied_at;
        public String replies_count;
        public String node_name;
        public String node_id;
        public String last_reply_user_id;
        public String last_reply_user_login;
        public String body;
        public String body_html;
        public String deleted_at;
        public String topic_id;
        public User user;

        public String getTitle(){
            if (topic_id != null) return  String.format("%s 回复了你 %s ",user.login, title);
            return String.format("%s 在 %s 提及到了你",user.name, title );
        }

        public String getID() {
            return topic_id == null ? id : topic_id;
        }
    }

}
