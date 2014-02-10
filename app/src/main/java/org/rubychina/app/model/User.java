package org.rubychina.app.model;

/**
 * Created by mac on 14-1-28.
 */
public class User {

    public String avatar_url;
    public String login;
    public String id;
    public String email;
    public String private_token;
    public String name;
    public String location;
    public String company;
    public String twitter;
    public String website;
    public String bio;
    public String tagline;
    public String gravatar_hash;
    public String github_url;

    private static final String POP_BRIEF = "第 %s 位会员\n%s";

    public String getPop(){
        return String.format(POP_BRIEF, id, tagline);
    }

    public String getName(){
        if (name.length() < 1 ) return login;
        return name;
    }

    public String getContactText(){
        if (this.email != null) return email;
        if (this.website != null) return website;
        return "";
    }

    public String getBigAvatar(int size){
        if (this.avatar_url.contains("?")){
            return avatar_url + "&s=" + size;
        } else {
            return avatar_url + "?&s=" + size;
        }
    }

    public String getTwitter() {
        return "https://twitter.com/" + twitter;
    }
}
