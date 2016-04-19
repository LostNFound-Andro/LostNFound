package com.example.sel.lostfound;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
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
import java.util.List;
import java.util.Map;

/**
 * Created by achu on 09-04-2016.
 */
public class PostAdapter extends ArrayAdapter<Posts> {

    List<Posts> list;
    Context context;
    private String objectString;

    public PostAdapter(Context context, int resource, List<Posts> posts) {
        super(context, resource,posts);
        this.context =context;
        this.list = posts;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;
        row = convertView;
        final PostHolder postHolder;


        if (row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout,parent,false);
            postHolder = new PostHolder();
            postHolder.tx_title = (TextView) row.findViewById(R.id.tx_title);
            postHolder.tx_categoryid = (TextView) row.findViewById(R.id.tx_category);
            postHolder.tx_date = (TextView) row.findViewById(R.id.tx_date);
            postHolder.tx_description = (TextView) row.findViewById(R.id.tx_description);
            postHolder.tx_emailid = (TextView) row.findViewById(R.id.tx_email);
            postHolder.tx_time = (TextView) row.findViewById(R.id.tx_time);
            postHolder.tx_location = (TextView) row.findViewById(R.id.tx_location);

            row.setTag(postHolder);
        }
        else
        {
            postHolder = (PostHolder)row.getTag();
        }

        final Posts posts = (Posts) this.getItem(position);

        if(posts.getCount() >=3) {
            RelativeLayout relativeLayout = (RelativeLayout) row.findViewById(R.id.row);
            relativeLayout.setBackgroundColor(0xFFFC8F8F);
        }

        postHolder.tx_title.setText(posts.getTitle());
        postHolder.tx_description.setText(posts.getDescription());
        postHolder.tx_categoryid.setText(posts.getCategoryid());
        postHolder.tx_emailid.setText(posts.getEmailid().replaceFirst("@nitc.ac.in",""));
        postHolder.tx_time.setText(posts.getTime());
        postHolder.tx_date.setText(posts.getDate());
        postHolder.tx_location.setText(posts.getLocation());

        Button contact_button = (Button) row.findViewById(R.id.contact_button);
        contact_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = String.valueOf(postHolder.tx_emailid.getText());

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { s });
                intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                v.getContext().startActivity(Intent.createChooser(intent, ""));

//                Toast.makeText(context,s, Toast.LENGTH_LONG).show();
            }
        });

        Button report_button = (Button) row.findViewById(R.id.report_button);
        report_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AsyncTask<String, Void, String>() {

                    @Override
                    protected String doInBackground(String... params) {
                        String email = MainActivity.userEmail;
                        String checkUrl = "http://52.38.30.3/report.php";
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
                            data.put("post_id",posts.getPostid());
                            data.put("email", email);

                            bufferedWriter.write(getQuery(data));
                            bufferedWriter.flush();
                            bufferedWriter.close();
                            OS.close();

                            DataInputStream dis = new DataInputStream(httpURLConnection.getInputStream());
                            StringBuilder stringBuilder = new StringBuilder();
                            String line;

                            do {
                                line = dis.readLine();
                                stringBuilder.append(line);

                            } while (line != null);


                            objectString = stringBuilder.toString();

                            httpURLConnection.connect();

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        return objectString;
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            String status = jsonObject.optString("status").toString();
                            Toast.makeText(context,status,Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e("check",result);
                    }
                }.execute();


//                Toast.makeText(context,s, Toast.LENGTH_LONG).show();
            }
        });

        return row;
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

    static class PostHolder{
        TextView  tx_title,tx_description,tx_categoryid,tx_emailid,tx_time,tx_date,tx_location ;
    }
}
