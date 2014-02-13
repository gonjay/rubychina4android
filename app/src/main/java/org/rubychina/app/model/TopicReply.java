package org.rubychina.app.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mac on 14-2-2.
 */
public class TopicReply extends Topic {

    private static final String REPLY_BRIEF_TEMPLATE = "%d楼，%s";

    private static final String REPLY_FRONT_TEMPLATE = "#%d楼 @%s ";

    public String getReplyBrief(int position) {
        return String.format(REPLY_BRIEF_TEMPLATE, position + 1, getShowTimeString(created_at, CREATE_AT_DATE_FORMAT));
    }

    public String getReplyFront(int position) {
        return String.format(REPLY_FRONT_TEMPLATE, position + 1, this.user.login);
    }
}
