package org.rubychina.app.model;

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

    private static final String DETAIL_TEMPLATE = "%s %s %s%n最后由 %s %s次阅读";

    private static final String CREATE_AT_TEMPLATE = "%s于%d%s前创建";

    private static final String LAST_REPLY_TEMPLATE = "%s于%d%s前回复";

    public String getDetail(){
        return String.format(DETAIL_TEMPLATE, node_name, user.login, getCreated_at(), getLastReply(), hits);
    }

    public String getCreated_at(){
        return getShowTimeFormatString(created_at, CREATE_AT_DATE_FORMAT, CREATE_AT_TEMPLATE);
    }

    public String getLastReply(){
        return getShowTimeFormatString(replied_at, LAST_REPLY_FORMAT, LAST_REPLY_TEMPLATE);
    }

    private String getShowTimeFormatString(String showTime, DateFormat dateFormat, String stringTemplate) {

        if (showTime == null) return "";

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
            return String.format(stringTemplate, last_reply_user_login, day, "天");
        } else if (hour > 0) {
            return String.format(stringTemplate, last_reply_user_login, hour, "小时");
        } else if (minute > 0) {
            return String.format(stringTemplate, last_reply_user_login, minute, "分钟");
        } else {
            return String.format(stringTemplate, last_reply_user_login, between, "秒");
        }
    }
}
