package org.rubychina.app.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mac on 14-2-2.
 */
public class TopicReply extends Topic {

    public String getReplyBrief(int position){

        return (position + 1) + "楼，" + getCreated_at();
    }

    public String getReplyFront(int position){
        return "#" + (position + 1)  + "楼 " + "@" + this.user.login;
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
            return day1 + "天前";
        } else if (hour1 > 0){
            return hour1 + "小时前";
        } else if (minute1 > 0){
            return minute1 + "分钟前";
        } else if (second1 > 0){
            return second1 + "秒前";
        } else {
            return "?";
        }
    }
}
