package org.rubychina.app.model;

import com.google.gson.Gson;

/**
 * Created by mac on 14-3-3.
 */
public class Message {
    public String count;
    public String content;
    public String title;
    public String content_path;

    public Message getInstance(String message) {
        return new Gson().fromJson(message, Message.class);
    }
}
