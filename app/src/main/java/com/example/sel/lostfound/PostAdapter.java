package com.example.sel.lostfound;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by achu on 09-04-2016.
 */
public class PostAdapter extends ArrayAdapter<Posts> {

    List<Posts> list;
    Context context;

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

        Posts posts = (Posts) this.getItem(position);

        postHolder.tx_title.setText(posts.getTitle());
        postHolder.tx_description.setText(posts.getDescription());
        postHolder.tx_categoryid.setText(posts.getCategoryid());
        postHolder.tx_emailid.setText(posts.getEmailid());
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

        return row;
    }

    static class PostHolder{
        TextView  tx_title,tx_description,tx_categoryid,tx_emailid,tx_time,tx_date,tx_location ;
    }
}
