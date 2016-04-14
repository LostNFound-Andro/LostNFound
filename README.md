# LostNFound
Android application for Lost and Found.

The app provides google sign in, post found items, browse feed at the moment. Further changes will be updated here.

1. The app only allows nitc email id for login. User need not enter password, but has to be logged-in in his mobile using nitc-email id.

2. A display picture, if there is any attached to the nitc gmail account, is shown in the navigation drawer.

3. User is given following menu items to browse through

  >Profile

  >Feed

  >Post

  >Subscribe

  >Help & FAQ

4. User can subscribe/unsubscribe among the given categories.
  
5. User can post a lost item, as of now, and browse them through the feed provided they have subscribed the category.

6. User can contact the author of the post by clicking the contact button corresponding to it, which redirects the user to his gmail compose dialog box so that the user can send an email to the author.    

7. Sign out option is available, where user can chose to logout completely and login as a different user.

##INSTRUCTIONS FOR THE TEAM (Internal Documentation)

####Please update your Android Studio to 2.0 to resolve any gradle conflicts.

Once logged in the email is stored in userEmail variable which is static, which implies you can access logged in users email in any activity or fragment using 
> MainActivity.userName variable.

To add any functionality, add it in the existing fragments.

To add a new menu item (like Profile, Feed, Post, Subscribe etc) use the 
> res/menu/activity_feed_drawer.xml

To access its layout properties from Fragment.java, use the following code:
> myFragmentView = inflater.inflate(R.layout.fragment_post, container, false);               //example

Instead of findViewById directly, use myFragmentView.findViewById (only in fragments).

To use the context in any Fragment, use getActivity(); instead of this or Context context.

To add a new instance/page/activity create a new blank fragment with factory methods, call backs and create layout checked.
  - For this fragment to be triggered, create a menu item, and make necessary changes to the FeedActivity.java
  
Previous Browse.java and postFind.java Activities have been migrated to FeedFragment.java and PostFragment.java respectively.

MainActivity, loginActivity, signUpActivity have no useful code, hence not included any where.


#Important

This is the new default branch. To make any changes, create a new branch and do not pull or merge unless discussed.
