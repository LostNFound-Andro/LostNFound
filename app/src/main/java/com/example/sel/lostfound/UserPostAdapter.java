package com.example.sel.lostfound;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
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
import java.util.List;
import java.util.Map;

/**
 * Created by bablu on 4/14/2016.
 */
public class UserPostAdapter extends ArrayAdapter<UserPost>{

    List<UserPost> list;
    Context context;


    public UserPostAdapter(Context context, int resource, List<UserPost> objects) {
        super(context, resource, objects);

        this.context =context;
        this.list = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        final UserPostHolder userPostHolder;


        if (row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.profile_row_layout,parent,false);

            userPostHolder = new UserPostHolder();
            userPostHolder.tx_title = (TextView) row.findViewById(R.id.tx_title);
            userPostHolder.tx_categoryid = (TextView) row.findViewById(R.id.tx_category);
            userPostHolder.tx_date = (TextView) row.findViewById(R.id.tx_date);
            userPostHolder.tx_description = (TextView) row.findViewById(R.id.tx_description);
            userPostHolder.tx_time = (TextView) row.findViewById(R.id.tx_time);
            userPostHolder.tx_location = (TextView) row.findViewById(R.id.tx_location);

            row.setTag(userPostHolder);
        }
        else
        {
            userPostHolder = (UserPostHolder)row.getTag();
        }

        final UserPost userPost = (UserPost) this.getItem(position);

        TextView titleLabel = (TextView)row.findViewById(R.id.titleLabel);
        if(userPost.getPosttype().equals("1") )
        {
            titleLabel.setText("Lost: ");
        }


            userPostHolder.tx_title.setText(userPost.getTitle());
        userPostHolder.tx_description.setText(userPost.getDescription());
        userPostHolder.tx_categoryid.setText(userPost.getCategoryid());
        userPostHolder.tx_time.setText(userPost.getTime());
        userPostHolder.tx_date.setText(userPost.getDate());
        userPostHolder.tx_location.setText(userPost.getLocation());

        Button resolve_button = (Button) row.findViewById(R.id.resolve_button);
        resolve_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            new AlertDialog.Builder(context)
                    .setTitle("Resolve post")
                    .setMessage("Resolving confirms the issue has been solved")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(context, "resolving", Toast.LENGTH_LONG).show();
                            new AsyncTask<Void, Void, Void>() {

                                @Override
                                protected Void doInBackground(Void... params) {
                                    String email = MainActivity.userEmail;
                                    String checkUrl = "http://52.38.30.3/resolve.php";
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
                                        data.put("post_id", userPost.getPostid());

                                        bufferedWriter.write(getQuery(data));
                                        bufferedWriter.flush();
                                        bufferedWriter.close();
                                        OS.close();

                                        InputStream IS = httpURLConnection.getInputStream();
                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                                        String response = "";
                                        String line = "";
                                        while ((line = bufferedReader.readLine()) != null) {
                                            response += line;
                                        }
                                        bufferedReader.close();
                                        IS.close();
                                        httpURLConnection.disconnect();
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    return null;
                                }
                            }.execute();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
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

    static class UserPostHolder{
        TextView tx_title,tx_description,tx_categoryid,tx_time,tx_date,tx_location ;
    }
}
