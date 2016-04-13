package com.example.sel.lostfound;

/**
 * Created by achu on 09-04-2016.
 */
public class Posts {

    private String title ;
    private String description;
    private String categoryid ;
    private String emailid ;
    private String time ;
    private String date ;
    private String location ;

    public Posts(String title, String categoryid, String date, String description, String emailid, String location, String time) {
        this.title = title;
        this.categoryid = categoryid;
        this.date = date;
        this.description = description;
        this.emailid = emailid;
        this.location = location;
        this.time = time;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
