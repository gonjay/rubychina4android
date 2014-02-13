package org.rubychina.app.model;

/**
 * Created by mac on 14-2-13.
 */
public class Author extends User {

    public static User getUser1(){
        User me = new User();
        me.login = "assyer";me.name = "GonJay";me.bio = "混个熟脸...";
        me.avatar_url = "http://ruby-china.org/avatar/13986ecfd5a4465e209d85c64968cab8.png?s=240";
        return me;
    }

    public static User getUser2(){
        User me = new User();
        me.login = "ywjno";me.name = "ywjno";me.bio = "Contributor of RubyChina for Android";
        me.avatar_url = "http://ruby-china.org/avatar/b75f97b497b215815971e19fdbaafa40.png?s=240";
        return me;
    }
}
