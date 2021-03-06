package com.example.sel.lostfound;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * \class MainActivity
* 		\brief implementing authentication of email id and log-in
**/
public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    EditText nitc_email;	/*!< variable storing NITC email*/
    String email;
    Button proceedButton,continueButton,changeSignIn; /*! \var Button proceedButton,continueButton,changeSignIn
                                                            \brief variables defined as buttons */
    com.google.android.gms.common.SignInButton signinButton;
    TextView connectivityText; /*! \var TextView connectivityText \brief display connectivity message */

    private static final String TAG = "SignInActivity"; /*! \var private static final String TAG */
    private static Intent CHK_SIGN_IN; /* \var private static Intent CHK_SIGN_IN  */
    protected static String userName; /* \var protected static String userName  */
    protected static String userEmail; /* \var protected static String userName */
    protected static Uri userImage; /* \var protected static Uri userImage */
    private static final int RC_SIGN_IN = 9001; /* \var private static final int RC_SIGN_IN */
    private static final String emailPattern = "[a-z]+(\\_?[a-z0-9]{9})@nitc.ac\\.in$";	/*! \var private static final String emailPattern \brief pattern to check if @nitc.ac.in */


    private TextView mStatusTextView; /* \var private TextView mStatusTextView */
    private GoogleApiClient mGoogleApiClient; /* \var private GoogleApiClient mGoogleApiClient */
    private boolean signedIn; /* \var private boolean signedIn */
    /**
     * \fn public void isSignedIn()
     * \brief function to sign in via google
     **/
    public void isSignedIn() {
        if (CHK_SIGN_IN != null) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(CHK_SIGN_IN);
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                proceedButton.setVisibility(View.INVISIBLE);
                signinButton.setVisibility(View.INVISIBLE);
                mStatusTextView.setText("Signed in as " + userName);
                continueButton.setVisibility(View.VISIBLE);
                changeSignIn.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    /**
     * \fn protected void onCreate(Bundle savedInstanceState)
     * \brief function implementing log in feature
     **/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nitc_email = (EditText) findViewById(R.id.emailText);
        mStatusTextView = (TextView) findViewById(R.id.status);
        proceedButton = (Button) findViewById(R.id.proceedButton);
        signinButton = (com.google.android.gms.common.SignInButton) findViewById(R.id.sign_in_button);
        continueButton = (Button)findViewById(R.id.continueButton);
        continueButton.setVisibility(View.INVISIBLE);
        changeSignIn = (Button)findViewById(R.id.changeSignIn);
        changeSignIn.setVisibility(View.INVISIBLE);
        connectivityText = (TextView) findViewById(R.id.textConnectivity);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            connectivityText.setVisibility(View.INVISIBLE);
        } else {
            proceedButton.setEnabled(false);
            signinButton.setEnabled(false);

        }
        changeSignIn.setOnClickListener(this);
        signinButton.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        isSignedIn();

    }
    /**
     * \fn public  void continueSignIn(View view)
     * \brief function to sign in into same account as previously signed in
     **/
    public  void continueSignIn(View view){
        startActivity(new Intent(this,FeedActivity.class));
        finish();
    }
    /**
     * \fn public void proceed(View view)
     * \brief function to proceed login after validating the email id entered
     **/
    public void proceed(View view) {
        email = nitc_email.getText().toString().trim();
        String method = "checkEmail";
        if(email.matches(emailPattern))
        {
            Toast.makeText(getApplicationContext(),"valid email address", Toast.LENGTH_LONG).show();
            BackendActivity backendActivity = new BackendActivity(this);
            backendActivity.execute(method, email);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    /**
     * \fn public void onClick(View v)
     **/
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.changeSignIn:
                revokeAccess();
                signOut();
                break;

        }
    }
    /**
     * \fn private void signIn()
     **/
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    /**
     * \fn public void onConnectionFailed(ConnectionResult connectionResult)
     * \brief function on entering email id other than nitc email id
     **/
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,"Please use nitc email id to continue.Fail",Toast.LENGTH_LONG).show();
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    /**
     * \fn public void onActivityResult(int requestCode, int resultCode, Intent data)
     * \brief Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...)
     **/
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CHK_SIGN_IN = data;
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    /**
     * \fn private void handleSignInResult(GoogleSignInResult result)
     * \brief function after successful login
     **/
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            userName = acct.getDisplayName();
            userEmail = acct.getEmail();
            userImage = acct.getPhotoUrl();
            if (userEmail.matches(emailPattern))
            {
                finish();
                startActivity(new Intent(this, FeedActivity.class));
            }
            else
            {
                revokeAccess();
                signOut();
            }
        } else {
            // Signed out, show unauthenticated UI.
            mStatusTextView.setText("Failed to sign in. Try again!");
        }
    }
   /**
    * \fn private void signOut()
    * \brief function for sign out
    **/
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        //proceedButton.setVisibility(View.VISIBLE);
                        signinButton.setVisibility(View.VISIBLE);
                        continueButton.setVisibility(View.INVISIBLE);
                        changeSignIn.setVisibility(View.INVISIBLE);
                        mStatusTextView.setText("Please use NITC email to continue");
                        // [END_EXCLUDE]
                    }
                });
    }
    /**
     * \fn private void revokeAccess()
     **/
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        mStatusTextView.setText("signed out");
                        // [END_EXCLUDE]
                    }
                });
    }

}
