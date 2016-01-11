package ru.byters.bcgithubusers.model;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private int id;
    private String avatar_url;
    private String login;
    private int followers;
    private int following;

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getLogin() {
        return login;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
