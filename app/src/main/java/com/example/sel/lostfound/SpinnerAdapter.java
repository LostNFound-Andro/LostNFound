package com.example.sel.lostfound;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


 
/** \class SpinnerAdapter  
 * 	\extends ArrayAdapter<Category>
 * 	\brief Displays Categories in dropdown view
 */
public class SpinnerAdapter extends ArrayAdapter<Category> {
    private Context context;
 /**
 *	\var  List<Category> list
 * 	\brief list of categories available in database
 */
    private List<Category> list;
/**
 *	\fn	public int getCount()
 * 		\brief gets the number of total categories available
 */
    public int getCount(){
        return list.size();
    }
/**
 *	\fn  public Category getItem(int position)
 * 	\param position
 * 	\brief gets the category details by its position number in the list
 */
    public Category getItem(int position){
        return list.get(position);
    }
/**
 *	\fn	public long getItemId(int position)
 * 	\brief *** 
 */
    public long getItemId(int position){
        return position;
    }

/**
 *	\fn	 public SpinnerAdapter(Context context, int resource, List<Category> objects)
 */
    public SpinnerAdapter(Context context, int resource, List<Category> objects) {
        super(context, resource, objects);
        this.context=context;
        this.list=objects;
    }

/**
 *	\fn	public View getView(int position, View convertView, ViewGroup parent)
 * 	\brief displays categories in dropdown view
 */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
//        label.setTextColor(Color.BLACK);
        label.setText(list.get(position).getCategory());
        label.setTextSize(24);
        return label;
    }

}
