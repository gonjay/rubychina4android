package org.rubychina.app.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mac on 14-2-2.
 */
public class TopicReply extends Topic {

    private static final String REPLY_BRIEF_TEMPLATE = "%d楼，%s";

    private static final String REPLY_FRONT_TEMPLATE = "#%d楼 @%s";

    public String getReplyBrief(int position) {
        return String.format(REPLY_BRIEF_TEMPLATE, position + 1, getCreated_at());
    }

    public String getReplyFront(int position) {
        return String.format(REPLY_FRONT_TEMPLATE, position + 1, this.user.login);
    }

    public String getCreated_at() {

        if (created_at == null) return "";

        Date date = new Date();
        Date now = new Date();

        try {
            date = CREATE_AT_DATE_FORMAT.parse(created_at);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 获得时间差的秒数
        long between = Math.abs((now.getTime() - date.getTime()) / 1000);

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
