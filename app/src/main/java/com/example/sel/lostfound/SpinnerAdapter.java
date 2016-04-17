package com.example.sel.lostfound;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by achu on 12-04-2016.
 */
public class SpinnerAdapter extends ArrayAdapter<Category> {

    private Context context;
    private List<Category> list;

    public int getCount(){
        return list.size();
    }

    public Category getItem(int position){
        return list.get(position);
    }

    public long getItemId(int position){
        return position;
    }


    public SpinnerAdapter(Context context, int resource, List<Category> objects) {
        super(context, resource, objects);
        this.context=context;
        this.list=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
//        label.setTextColor(Color.BLACK);
        label.setText(list.get(position).getCategory());
        label.setTextSize(24);
        return label;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
//        label.setTextColor(Color.BLACK);
        label.setText(list.get(position).getCategory());
        label.setTextSize(24);
        return label;
    }
}
