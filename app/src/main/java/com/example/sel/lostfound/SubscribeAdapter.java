package com.example.sel.lostfound;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
 * Created by achu on 14-04-2016.
 */
public class SubscribeAdapter extends ArrayAdapter<Category> {

    List<Category> list;
    Context context;

    public SubscribeAdapter(Context context, int resource, List<Category> objects) {
        super(context, resource, objects);

        this.context =context;
        this.list = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;
        row = convertView;
        final SubscribeHolder subscribeHolder;


        if (row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout2,parent,false);
            subscribeHolder = new SubscribeHolder();
            subscribeHolder.tx_cat = (TextView) row.findViewById(R.id.tx_cat);


            row.setTag(subscribeHolder);
        }
        else
        {
            subscribeHolder = (SubscribeHolder) row.getTag();
        }

        final Category category = (Category) this.getItem(position);

        subscribeHolder.tx_cat.setText(category.getCategory());


        Button unsub_button = (Button) row.findViewById(R.id.unsub_button);
        unsub_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Toast.makeText(context,category.getCid(), Toast.LENGTH_LONG).show();

                Toast.makeText(context,"Unsubscribe",Toast.LENGTH_LONG).show();

                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        String email = MainActivity.userEmail;
                        String checkUrl = "http://52.38.30.3/deletesubscription.php";
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
                            data.put("email", email);
                            data.put("catid",category.getCid());
                            bufferedWriter.write(getQuery(data));
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
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute();
            }
        });

       // list.remove(position);
       // notifyDataSetChanged();

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

    static class SubscribeHolder{
        TextView tx_cat ;
    }
}
