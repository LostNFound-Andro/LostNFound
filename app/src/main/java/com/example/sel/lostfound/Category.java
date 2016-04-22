package com.example.sel.lostfound;

/**
 * Created by achu on 12-04-2016.
 */
 
/**
 * \class Category
*	\brief class implementing categories
*/
public class Category {
    private String category;	/*! String variable category */
    private String cid;		/*! Category id */

    /**  \constructor public Category(String category, String cid)
    *   \param category
    *   \param cid
    */
    public Category(String category, String cid) {
        this.category = category;	
        this.cid = cid;
    }
    
    
   /**
    * \fn public String getCid()
    * \brief function to get category id
    */ 
    public String getCid() {
        return cid;
    }
   /**
    * \fn public void setCid(String cid)
    * \brief function to set category id
    */
    public void setCid(String cid) {
        this.cid = cid;
    }
   /** \fn public String getCategory()
    *  \brief function to get category name
    */
    public String getCategory() {
        return category;
    }
  /** 
   * \fn public void setCategory(String category)
   *  \brief function to set category name
   */
    public void setCategory(String category) {
        this.category = category;
    }
}
