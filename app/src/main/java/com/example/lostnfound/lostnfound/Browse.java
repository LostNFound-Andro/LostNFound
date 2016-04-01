package com.example.lostnfound.lostnfound;

import android.app.Activity;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by simsar on 29/3/16.
 */

public class  Browse extends Activity {

    String postAddress = "http://192.168.40.99/ahamed_b130112cs/se/getpost.php";
    TextView tv;
    TextView jsonParsedOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse);

        tv = (TextView) findViewById(R.id.tv);
        jsonParsedOutput = (TextView) findViewById(R.id.textView4);

        new AsyncTask<Void, Void, Void>(){

            String stringdata;

            @Override
            protected Void doInBackground(Void... params) {
                stringdata = getPost();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                jsonParsedOutput.setText(stringdata);
            }
        }.execute();

    }

    public String getPost() {
        String data = "";
        try {


            URL url = new URL(postAddress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            DataInputStream dis = new DataInputStream(conn.getInputStream());
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            do{
                line = dis.readLine();
                stringBuilder.append(line);

            }while (line != null);

            String output;
            output = stringBuilder.toString();

            try {
                JSONObject jsonRootObject = new JSONObject(output);
                JSONArray jsonArray = jsonRootObject.getJSONArray("postlist");
                //Iterate the jsonArray and print the info of JSONObjects
                for(int i=0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String title = jsonObject.optString("Title").toString();
                    String description = jsonObject.optString("Description").toString();
                    String categoryid = jsonObject.optString("CategoryID").toString();
                    String emailid = jsonObject.optString("EmailID").toString();
                    String time = jsonObject.optString("Time").toString();
                    String date = jsonObject.optString("Date").toString();
                    String location = jsonObject.optString("Location").toString();

                    data += "\nPost"+i+" : \n title= "+ title +" \n description= "+ description +" \n emailid= "+ emailid +" \n\n ";
                }

            } catch (JSONException e) {e.printStackTrace();}

//            Log.d("Out", stringBuilder.toString());
            //tv.setText(output);
            conn.connect();

        }
        catch (Exception e){
            Log.e("Out",e.getMessage()); ;
        }
        return data;
    }

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
