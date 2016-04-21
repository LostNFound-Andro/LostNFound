package com.example.sel.lostfound;

/**
 * 
 */
public class Posts {

/**
* \var	private String title
*	\brief variable for title of the post
*/
    private String title ;
/**
* \var	private String description
*	\brief	description of the post which gives information about post
*/

    private String description;
/**
* \var	private String categoryid
*	\brief identifaction key for respective category of post
*/

    private String categoryid ;
/**
* \var	private String emailid
*	\brief emailid of admin of the post
*/
    private String emailid ;
    
/**
* \var	private String time
*	\brief time of the lost/found post in format hh:mm
*/
    private String time ;
  
/**
* \var	private String date
*	\brief date of post in format dd/mm/yy
*/ 
    private String date ;
/**
* \var	private String location
*	\brief variable for location, provides where admin(person who posted the post) lost or found the object 
*/
   
    private String location ;

/**
* \var	private String postid
*	\brief primary(identifaction) key for postid
*/
    private String postid;
 /**   
* \var	private int count
*	\brief count of flag**
*/

    private int count;


/**
* \fn   public Posts(String postid,String title, String categoryid, String date, String description, String emailid, String location, String time,int count) 			
*	\brief	postid,title,description,categoryid,eamilid,time,date,location,count this argumets gets copied to respective current class instance variables.
	\param postid
	\param title
	\param categoryid
	\param date
	\param description
	\param emailid
	\param location 
	\param time
	\param count
*/
    public Posts(String postid,String title, String categoryid, String date, String description, String emailid, String location, String time,int count) {
        this.postid = postid;
        this.title = title;
        this.categoryid = categoryid;
        this.date = date;
        this.description = description;
        this.emailid = emailid;
        this.location = location;
        this.time = time;
        this.count = count;
    }

/**
* \fn   public int getCount()
* 	\brief returns count of flags
*/
    public int getCount() {
        return count;
    }
/**
* \fn public void setCount(int count)
* 	\param count
*	 \brief sets value of current class instance variable postid to count as in the argument
*/
    public void setCount(int count) {
        this.count = count;
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
*	\param postid
*	 \brief sets value of current class instance variable postid to postid as in the argument
*/
    public void setPostid(String postid) {
        this.postid = postid;
    }
/**
* \fn public String getCategoryid()
*	\brief returns identification key of category
*/
    public String getCategoryid() {
        return categoryid;
    }
/**
* \fn 	public void setCategoryid(String categoryid)
* 	\param categoryid
*	\brief	sets value of current class instance variable categoryid to categoryid as in the argument
*/
    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }
/**
* \fn  public String getDate()
*	\brief returns value of lost/found date
*/
    public String getDate() {
        return date;
    }
/**
* \fn public void setDate(String date) 
*	\param date
*	\brief sets value of current class instance variable date to date as in the argument
*/
    public void setDate(String date) {
        this.date = date;
    }
/**
* \fn public String getDescription()
*	\brief returns description of post
*/
    public String getDescription() {
        return description;
    }
/**
* \fn  public void setDescription(String description)
*	\param description
*	\brief sets value of current class instance variable description to description
*/

    public void setDescription(String description) {
        this.description = description;
    }
/**
* \fn public String getEmailid()
*	\brief returns value of identification key of the post 
*/
    public String getEmailid() {
        return emailid;
    }
/**
* \fn public void setEmailid(String emailid)
*	\param emailid
*	 \brief sets value of current class instance variable postid to emailid as in the argument
*/
    public void setEmailid(String emailid) {
        this.emailid = emailid;
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
*	\param location
*	\brief sets value of current class instance variable location to location as in the argument
*/
    public void setLocation(String location) {
        this.location = location;
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
*	\param time
*	\brief sets value of current class instance variable time to time as in the argument
*/
    public void setTime(String time) {
        this.time = time;
    }
/**
* \fn public String getTitle()
*	\brief returns title of the post
*/
    public String getTitle() {
        return title;
    }
/**
* \fn     public void setTitle(String title)
*	\param title
*	\brief sets value of current class instance variable posttype to posttype
*/
    public void setTitle(String title) {
        this.title = title;
    }
}
