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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse);

        tv = (TextView) findViewById(R.id.tv);

        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                getPost();
                return null;
            }
        }.execute();

    }

    public void getPost() {
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
//            JSONObject jsonObject = new JSONObject(output);
//            JSONArray jsonArray = jsonObject.getJSONArray("");
//            Log.d("Out", stringBuilder.toString());
            tv.setText(output);
            conn.connect();
        }
        catch (Exception e){
            Log.e("Out",e.getMessage()); ;
        }
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
