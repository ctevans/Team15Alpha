package com.skilltradiez.skilltraderz;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Stephen on 2015-11-29.
 */
public class SpinnerAdapter<T extends Object> extends ArrayAdapter {
    private Context context;
    private List<T> itemList;
    SpinnerAdapter(Context context, int resourceId, List<T> itemList) {
        super(context, resourceId);
        this.context = context;
        this.itemList = itemList;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    public View getView(int position, View view, ViewGroup parent) {
        T item = itemList.get(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.spinner_item, parent, false);
            viewHolder.item = (TextView) view.findViewById(R.id.item);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        // Populate the data into the template view using the data object
        Typeface myTypeFace = Typeface.createFromAsset(context.getAssets(),
                "fonts/gilsanslight.otf");
        viewHolder.item.setTypeface(myTypeFace);
        viewHolder.item.setText(item.toString());
        return view;
    }

    // Acting As A Struct
    public class ViewHolder {
        TextView item;
    }
}