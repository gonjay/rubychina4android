package org.rubychina.app.model;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mac on 14-1-28.
 */
public class Topic {

    public String id;
    public String title;
    public String created_at;
    public String replied_at;
    public String replies_count;
    public String node_name;
    public String node_id;
    public String last_reply_user_id;
    public String last_reply_user_login;
    public String body;
    public String body_html;
    public String hits;

    public User user;

    public static final DateFormat CREATE_AT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    private static final DateFormat LAST_REPLY_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    private static final String DETAIL_TEMPLATE = "%s %s于%s发布%n最后由 %s %s次阅读";

    private static final String DETAIL_WITHOUT_REPLY_TEMPLATE = "%s %s于%s发布%n%s次阅读";

    private static final String LAST_REPLY_TEMPLATE = "%s于%s回复";

    public Topic getInstance(String response){
        return new Gson().fromJson(response, Topic.class);
    }

    public String getDetail(){
        if (last_reply_user_login != null) {
            return String.format(DETAIL_TEMPLATE, node_name, user.login, getCreated_at(), getLastReply(), hits);
        }
        // 没有回复时回复者部分不显示
        else {
            return String.format(DETAIL_WITHOUT_REPLY_TEMPLATE, node_name, user.login, getCreated_at(), hits);
        }
    }

    public String getCreated_at() {
        return getShowTimeString(created_at, CREATE_AT_DATE_FORMAT);
    }

    public String getLastReply() {
        if (last_reply_user_login != null) {
            return String.format(LAST_REPLY_TEMPLATE, last_reply_user_login, getShowTimeString(replied_at, LAST_REPLY_FORMAT));
        }
        // 没有回复时返回空
        else {
            return "";
        }
    }

    public String getShowTimeString(String showTime, DateFormat dateFormat) {
        Date date = new Date();
        Date nowTime = new Date();
        try {
            date = dateFormat.parse(showTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 获得时间差的秒数
        long between = Math.abs((nowTime.getTime() - date.getTime()) / 1000);

        long day = between / (24 * 3600);

        long hour = between % (24 * 3600) / 3600;

        long minute = between % 3600 / 60;

        if (day > 0) {
            return day + "天前";
        } else if (hour > 0) {
            return hour + "小时前";
        } else if (minute > 0) {
            return minute + "分钟前";
        } else {
            return between + "秒前";
        }
    }
}
