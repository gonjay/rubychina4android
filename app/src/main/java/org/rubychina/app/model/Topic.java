package org.rubychina.app.model;

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
    public String hits;

    public User user;

    public String getDetail(){

        return node_name + " " + user.login + getCreated_at() + "\n" + "最后由 " + getLastReply() + " " + hits + "次阅读";
    }

    public String getCreated_at(){

        if (created_at == null) return "";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date = new Date();
        Date now = new Date();

        try {
            date = df.parse(created_at);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long between = Math.abs((now.getTime() - date.getTime())/1000);

        long day1=between/(24*3600);

        long hour1=between%(24*3600)/3600;

        long minute1=between%3600/60;

        long second1=between%60/60;

        if (day1 > 0){
            return last_reply_user_login + " 于" + day1 + "天前创建";
        } else if (hour1 > 0){
            return last_reply_user_login + " 于" + hour1 + "小时前创建";
        } else if (minute1 > 0){
            return last_reply_user_login + " 于" + minute1 + "分钟前创建";
        } else if (second1 > 0){
            return last_reply_user_login + " 于" + second1 + "秒前创建";
        } else {
            return "?";
        }
    }

    public String getLastReply(){
        if (replied_at == null) return "";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date date = new Date();
        Date now = new Date();

        try {
            date = df.parse(replied_at);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long between = Math.abs((now.getTime() - date.getTime())/1000);

        long day1=between/(24*3600);

        long hour1=between%(24*3600)/3600;

        long minute1=between%3600/60;

        long second1=between%60/60;

        if (day1 > 0){
            return last_reply_user_login + " 于" + day1 + "天前回复";
        } else if (hour1 > 0){
            return last_reply_user_login + " 于" + hour1 + "小时前回复";
        } else if (minute1 > 0){
            return last_reply_user_login + " 于" + minute1 + "分钟前回复";
        } else if (second1 > 0){
            return last_reply_user_login + " 于" + second1 + "秒前回复";
        } else {
           return "?";
        }

    }

}
