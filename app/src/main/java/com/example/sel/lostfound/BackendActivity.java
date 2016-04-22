/*!\package Java used
*/
package com.example.sel.lostfound;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Rohit G on 3/31/2016.
 */

/** 
 * \class Backend Activity class
 * \brief class implementing gmail authentication
*/
public class BackendActivity extends AsyncTask<String,Void,String>{
    Context ctx; /*!< Context variable */
    //! A constructor
    BackendActivity(Context ctx)
    {
        this.ctx = ctx;
    }


    @Override
    protected void onPreExecute() {

    }
    
    /**
     * 	\fn  protected String doInBackground(String... params)
    *	\brief function calling php file to check email .establishes connection with webserver and validates the email which has been input
    * @param params is a string parameter
    */
    @Override
    protected String doInBackground(String... params) {
        String method = params[0];
        if (method.equals("checkEmail")) {
            String email = params[1]; /*! Stores input email to the string variable */
            String checkUrl = "http://52.38.30.3/check.php"; /*! url of webserver pointing to check.php */
            try {
                URL url = new URL(checkUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                ContentValues data = new ContentValues();
                data.put("email", email);	/*! adds email to the database */
                bufferedWriter.write(getQuery(data)); /*! @see getQuery(params) */
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    /**
     * 	\fn  protected void onPostExecute(String result)
     *  \brief display function when email has already been registered
    */
    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        if(result.equals("Email already registered!")) {
            Toast.makeText(ctx, "Google sign in", Toast.LENGTH_LONG).show();
        }
    }
    /**
     * \fn String getQuery(ContentValues params)
    * 	\brief Function to send a request to web server
    */
    private String getQuery(ContentValues params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, Object> entry : params.valueSet())
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
        }

        return result.toString();
    }
}
