package com.example.sel.lostfound;

/**
 * Created by achu on 12-04-2016.
 */
 //! Category class
/*!
class implementing categories
*/
public class Category {
    private String category;	/*! String variable category */
    private String cid;		/*! Category id */

    //! A constructor with params category and cid
    public Category(String category, String cid) {
        this.category = category;	
        this.cid = cid;
    }
   //! function to get category id
    public String getCid() {
        return cid;
    }
   //! function to set category id
    public void setCid(String cid) {
        this.cid = cid;
    }
   //! function to get category name
    public String getCategory() {
        return category;
    }
  //! function to set category name
    public void setCategory(String category) {
        this.category = category;
    }
}
