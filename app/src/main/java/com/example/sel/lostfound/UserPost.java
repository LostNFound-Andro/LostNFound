package com.example.sel.lostfound;

/**
 * Created by bablu on 4/14/2016.
 */
public class UserPost {
    private String posttype;
    private String title ;
    private String description;
    private String categoryid ;
    private String postid ;
    private String time ;
    private String date ;
    private String location ;

    public UserPost(String posttype,String title, String description, String categoryid, String postid, String time, String date, String location) {
        this.posttype =posttype;
        this.title = title;
        this.description = description;
        this.categoryid = categoryid;
        this.postid = postid;
        this.time = time;
        this.date = date;
        this.location = location;
    }

    public String getPosttype() {
        return posttype;
    }

    public void setPosttype(String posttype) {
        this.posttype = posttype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
