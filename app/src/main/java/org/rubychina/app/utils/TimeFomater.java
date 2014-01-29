package org.rubychina.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by mac on 14-1-29.
 */
public class TimeFomater {

    public static CharSequence getListTime(String created_at) {
        Calendar calendar = Calendar.getInstance();
        Date date = null;
        SimpleDateFormat srcDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
        SimpleDateFormat dstDateFormat = new SimpleDateFormat("MMMM dd yyyy", Locale.US);
        try {
            date = srcDateFormat.parse(created_at);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dstDateFormat.format(date);
    }

    public static String format(String replied_at){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date date = new Date();
        Date now = new Date();
        try {
            date = df.parse(replied_at);
        } catch (ParseException e) {
            System.out.println("erro : " + replied_at);
//            e.printStackTrace();
        }

        long between = (now.getTime() - date.getTime())/1000;

        long day1=between/(24*3600);

        long hour1=between%(24*3600)/3600;

        long minute1=between%3600/60;

        long second1=between%60/60;

        if (day1 > 0){
            return "于" + day1 + "天前回复";
        } else if (hour1 > 0){
            return "于" + hour1 + "小时前回复";
        } else if (minute1 > 0){
            return "于" + minute1 + "分钟前回复";
        } else if (second1 > 0){
            return "于" + second1 + "秒前回复";
        } else {
            return "";
        }
    }
}
