package com.example.sel.lostfound;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

/**
 * Created by Rohit G on 3/31/2016.
 */


/**
 * \class FeedActivity
* 		\brief implementing navigation bar.
*/
public class FeedActivity extends AppCompatActivity
        implements ProfileFragment.OnFragmentInteractionListener,
        FeedFragment.OnFragmentInteractionListener,
        InfoFragment.OnFragmentInteractionListener,
        PostFragment.OnFragmentInteractionListener,
        SubscribeFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView = null;  /*!< an instance of navigation view  */
    Toolbar toolbar = null;	/*!< an instance of toolbar */

    TextView nameText,emailText; /* nameText and emailText as instances of TextView */

    protected static String ptype = ""; /*!< variable of type string*/

    /**
     * \fn protected void onCreate(Bundle savedInstanceState)
    *  \brief function defining actions done when the activity is started or created.
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        //!home screen content (showing profile initially)
        ProfileFragment profileFragment = new ProfileFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, profileFragment);
        fragmentTransaction.commit();
	//! display signed in message along with user's email id 
        Toast.makeText(this,"Signed in as "+ MainActivity.userEmail, Toast.LENGTH_LONG).show();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
	//! opening navigation bar
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.nameText); 
        TextView nav_email = (TextView)hView.findViewById(R.id.emailText); 
        de.hdodenhof.circleimageview.CircleImageView nav_image = (de.hdodenhof.circleimageview.CircleImageView)hView.findViewById(R.id.userImage);
        nav_user.setText(MainActivity.userName);	/*!< display user's name on navigation bar*/
        nav_email.setText(MainActivity.userEmail);	/*!< display user's email id on navigation bar*/
        //! to display picture
        if(MainActivity.userImage != null) {
            new DownloadImageTask(nav_image)
                    .execute(MainActivity.userImage.toString());
        }
    }

    @Override
    /**
     *  \fn public void onBackPressed()
    * 	\brief function implementing actions when 'back' is pressed while navigation bar is open
    */
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    /**
     *  \fn public boolean onCreateOptionsMenu(Menu menu)
     *  \brief shows the options in the menu as result of opening the navigation bar
    */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.feed, menu);
        return true;
    }

    /**
     *  \fn public boolean onOptionsItemSelected(MenuItem item) 
     *  \brief Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button
    */
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    /**
     * 	\fn  public boolean onNavigationItemSelected(MenuItem item)
    * 	\brief function implementing actions on selecting a particular row in navigation bar.
    */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId(); /*!< variable id stores item id selected by user */

        if (id == R.id.nav_profile) {
            //!profile page opened as result of selection of 'profile' on navigation bar
            ProfileFragment profileFragment = new ProfileFragment(); 
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, profileFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_feed) {
            //!feed page opened as result of selection of 'feeds' on navigation bar
            FeedFragment feedFragment = new FeedFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, feedFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_post) {
            //!post page opened as result of selection of 'post' on navigation bar
            PostFragment postFragment = new PostFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, postFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_subscribe) {
            //!subscribe page opened as result of selection of 'subscriptions' on navigation bar
            SubscribeFragment subscribeFragment = new SubscribeFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, subscribeFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_info) {
            //!info page opened as result of selection of Info on navigation bar
            InfoFragment infoFragment = new InfoFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, infoFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_signout) {
            //! sign out activity implemented 
            startActivity(new Intent(this,MainActivity.class));
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //! closes the navigation bar
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onProfileFragmentInteraction(String string) {

    }

    @Override
    public void onInfoFragmentInteraction(String string) {

    }

    @Override
    public void onPostFragmentInteraction(String string) {

    }

    @Override
    public void onFeedFragmentInteraction(String string) {

    }

    @Override
    public void onSubscribeFragmentInteraction(String string) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class DownloadImageTask extends AsyncTask<String,Void,Bitmap>{
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap userDp = null;
            try {
                InputStream IS = new java.net.URL(urldisplay).openStream();
                userDp = BitmapFactory.decodeStream(IS);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return userDp;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

