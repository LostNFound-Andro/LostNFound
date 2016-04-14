package com.example.sel.lostfound;

/**
 * Created by achu on 12-04-2016.
 */
public class Category {
    private String category;
    private String cid;

    public Category(String category, String cid) {
        this.category = category;
        this.cid = cid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
