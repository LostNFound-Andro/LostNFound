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

public class FeedActivity extends AppCompatActivity
        implements ProfileFragment.OnFragmentInteractionListener,
        FeedFragment.OnFragmentInteractionListener,
        InfoFragment.OnFragmentInteractionListener,
        PostFragment.OnFragmentInteractionListener,
        SubscribeFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView = null;
    Toolbar toolbar = null;

    TextView nameText,emailText;

    protected static String ptype = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        //home screen content (showing profile initially)
        ProfileFragment profileFragment = new ProfileFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, profileFragment);
        fragmentTransaction.commit();

        Toast.makeText(this,"Signed in as "+ MainActivity.userEmail, Toast.LENGTH_LONG).show();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        nav_user.setText(MainActivity.userName);
        nav_email.setText(MainActivity.userEmail);
        if(MainActivity.userImage != null) {
            new DownloadImageTask(nav_image)
                    .execute(MainActivity.userImage.toString());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.feed, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            ProfileFragment profileFragment = new ProfileFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, profileFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_feed) {
            FeedFragment feedFragment = new FeedFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, feedFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_post) {
            PostFragment postFragment = new PostFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, postFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_subscribe) {
            SubscribeFragment subscribeFragment = new SubscribeFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, subscribeFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_info) {
            InfoFragment infoFragment = new InfoFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, infoFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_signout) {
            startActivity(new Intent(this,MainActivity.class));
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

