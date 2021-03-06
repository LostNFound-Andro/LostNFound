package com.example.sel.lostfound;

/**
 * This program takes input posttype,title,description,string,categoryid,postid,date,location and displays it.
 */


/**
 * \class UserPost
 * 	\brief contains posttype,title,description,string,categoryid,postid,date,location variables and fuctions to set values to each variables and functions to abstract values from each variables.
 * 	\package com.example.sel.lostfound
 */

public class UserPost {
/**
* \var posttype
*	\brief Type of the post.There are two post types : found post and lost post 
*/  
    private String posttype;
/**
* \var title
*	\brief variable for title of the post
*/
    private String title ;	

/**
* \var description
*	\brief	description of the post which gives information about post
*/  
  private String description;	 

/**
* \var categoryid
*	\brief identifaction key for respective category of post
*/
    private String categoryid ;

/**
* \var postid
*	\brief primary(identifaction) key for postid
*/
    private String postid ; 
/**
* \var time
*	\brief time of the lost/found post in format hh:mm
*/
    private String time ;

/**
* \var date
*	\brief date of post in format dd/mm/yy
*/ 
   private String date ;
/**
* \var location
*	\brief variable for location, provides where admin(person who posted the post) lost or found the object 
*/
    private String location ; 


/**
* \fn public UserPost(String posttype,String title, String description, String categoryid, String postid, String time, String date, String location) 			
*	\brief	posttype,title,description,categoryid,postid,time,date,location, this argumets gets copied to respective current class instance variables.
	\param posttype
	\param title
	\param description
	\param categoryid
	\param postid
	\param time
	\param date
	\param location 
*/
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

/**
* \fn public String getPosttype() 
*	\brief returns post type
*/
    public String getPosttype() {
        return posttype;
    }
/**
* \fn public void setPosttype(String posttype)
*	\brief sets value of current class instance variable posttype to posttype
*/
    public void setPosttype(String posttype) {
        this.posttype = posttype;
    }
/**
* \fn public String getTitle()
*	\brief returns title of the post
*/
    public String getTitle() {
        return title;
    }
/**
* \fn public void setTitle(String title)
*	\brief sets value of current class instance variable posttype to posttype
*/
    public void setTitle(String title) {
        this.title = title;
    }
/**
* \fn public String getDescription()
*	\brief returns description of post
*/
    public String getDescription() {
        return description;
    }
/**
* \fn public void setDescription(String description)
*	\brief sets value of current class instance variable description to description
*/
    public void setDescription(String description) {
        this.description = description;
    }
/**
* \fn public String getCategoryid()
*	\brief returns identification key of category
*/
    public String getCategoryid() {
        return categoryid;
    }
/**
* \fn public void setCategoryid(String categoryid)
*	\brief	sets value of current class instance variable categoryid to categoryid as in the argument
*/
    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }
/**
* \fn public String getPostid()
*	\brief returns value of identification key of the post 
*/
    public String getPostid() {
        return postid;
    }
/**
* \fn public void setPostid(String postid)
*	 \brief sets value of current class instance variable postid to postid as in the argument
*/
    public void setPostid(String postid) {
        this.postid = postid;
    }
/**
* \fn public String getTime() 
*	\brief returns value of lost/found time
*/
    public String getTime() {
        return time;
    }
/**
* \fn public void setTime(String time)  
*	\brief sets value of current class instance variable time to time as in the argument
*/
    public void setTime(String time) {
        this.time = time;
    }
/**
* \fn public String getDate()
*	\brief returns value of lost/found date
*/
    public String getDate() {
        return date;
    }
/**
* \fn public void setDate(String date) 
*	\brief sets value of current class instance variable date to date as in the argument
*/
    public void setDate(String date) {
        this.date = date;
    }
/**
* \fn public String getLocation() 
*	\brief returns location of the post
*/
    public String getLocation() {
        return location;
    }
/**
* \fn public void setLocation(String location)
*	\brief sets value of current class instance variable location to location as in the argument
*/
    public void setLocation(String location) {
        this.location = location;
    }
}
