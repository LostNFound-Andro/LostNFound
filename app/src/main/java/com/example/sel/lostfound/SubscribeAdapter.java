package com.example.sel.lostfound;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

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

        Category category = (Category) this.getItem(position);

        subscribeHolder.tx_cat.setText(category.getCategory());


        Button unsub_button = (Button) row.findViewById(R.id.unsub_button);
        unsub_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

//                Toast.makeText(context,s, Toast.LENGTH_LONG).show();
            }
        });

        return row;
    }

    static class SubscribeHolder{
        TextView tx_cat ;
    }
}
