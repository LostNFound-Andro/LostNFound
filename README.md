# LostNFound
Android application for Lost and Found.

The app provides google sign in, post found items, browse feed at the moment. Further changes will be updated here.
1. The app only allows nitc email id for login.

2. Once logged in the email is stored in userEmail variable which is static, which implies you can access logged in users email in any activity or fragment using MainActivity.userName variable.

3. To access its layout properties from Fragment.java, use the following code:

    myFragmentView = inflater.inflate(R.layout.fragment_post, container, false);

4. Instead of findViewById directly, use myFragmentView.findViewById (only in fragments).

5. To use the context in any Fragment, use getActivity(); instead of this or Context context.

#INSTRUCTION FOR THE TEAM

To add any functionality, add it in the existing fragments.

To add a new menu item (like Profile, Feed, Post, Subscribe etc) use the res/menu/activity_feed_drawer.xml

To add a new instance/page/activity create a new blank fragment with factory methods, call backs and create layout checked.
  - For this fragment to be triggered, create a menu item, and make necessary changes to the FeedActivity.java
  - 
  
Previous Browse.java and postFind.java Activities have been migrated to FeedFragment.java and PostActivity.java respectively.

MainActivity, loginActivity, signUpActivity have no useful code, hence not included any where.


#Important

This is the new default branch. To make any changes, create a new branch and do not pull or merge unless discussed.
