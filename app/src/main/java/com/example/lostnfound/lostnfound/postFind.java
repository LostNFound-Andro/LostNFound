package com.example.lostnfound.lostnfound;

import android.app.Activity;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
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
 * Created by simsar on 28/3/16.
 */
public class  postFind extends Activity {

    String postAddress = "http://192.168.40.99/ahamed_b130112cs/se/addpost.php";
    TextView txtTitle;
    TextView txtDesc;
    TextView txtLoc;
    CheckBox cbCalc;
    CheckBox cbMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postfind);


        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtDesc = (TextView) findViewById(R.id.txtDesc);
        txtLoc = (TextView) findViewById(R.id.txtLoc);
        cbCalc = (CheckBox) findViewById(R.id.cbCalc);
        cbMoney = (CheckBox) findViewById(R.id.cbMoney);

    }


    String getCategory(){
        if(cbCalc.isChecked()){
            return "Calc";
        }
        else if(cbMoney.isChecked()){
            return "Money";
        }

        return "Nothing selected";
    }

    public void postFields(View v){

        final String title = txtTitle.getText().toString();
        final String desc = txtDesc.getText().toString();
        final String loc = txtLoc.getText().toString();
        final String time = "time0";
        final String date = "date0";
        final String email ="emailejsljf";
        final String cat = getCategory();



        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "done" , Toast.LENGTH_LONG).show();
            }

            @Override
            protected Void doInBackground(Void... parames) {

                try

                {


                    URL url = new URL(postAddress);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    ContentValues params = new ContentValues();

                    params.put("title", title);
                    params.put("desc", desc);
                    params.put("loc", loc);
                    params.put("time", time);
                    params.put("date", date);
                    params.put("email", email);
                    params.put("cat", cat);


                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(getQuery(params));
                    writer.flush();
                    writer.close();
                    os.close();

                    DataInputStream dis = new DataInputStream(conn.getInputStream());
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    do {
                        line = dis.readLine();
                        stringBuilder.append(line);

                    } while (line != null);

                    String output;
                    output = stringBuilder.toString();
                    JSONObject jsonObject = new JSONObject(output);
                    JSONArray jsonArray = jsonObject.getJSONArray("");
                    Log.d("Out", stringBuilder.toString());
                    conn.connect();
                }

                catch(
                        Exception e
                        )

                {
                    Log.e("Error", e.getMessage()
                    );
                }

                return null;
            }
        }.execute();


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
