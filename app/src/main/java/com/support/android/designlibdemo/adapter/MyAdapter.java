package com.support.android.designlibdemo.adapter;

/**
 * Created by Print Secure on 5/5/2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.support.android.designlibdemo.R;
import com.support.android.designlibdemo.model.Model;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<Model> {

    private final Context context;
    private final ArrayList<Model> modelsArrayList;

    public MyAdapter(Context context, ArrayList<Model> modelsArrayList) {

        super(context, R.layout.target_item, modelsArrayList);

        this.context = context;
        this.modelsArrayList = modelsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater

        View rowView = null;
        if(!modelsArrayList.get(position).isGroupHeader()){
            rowView = inflater.inflate(R.layout.target_item, parent, false);

            // 3. Get icon,title & counter views from the rowView

            TextView titleView = (TextView) rowView.findViewById(R.id.item_title);
            TextView nomView = (TextView) rowView.findViewById(R.id.item_nom);
            TextView counterView = (TextView) rowView.findViewById(R.id.item_counter);

            // 4. Set the text for textView

            titleView.setText(modelsArrayList.get(position).getTitle());
            nomView.setText(modelsArrayList.get(position).getNom());
            counterView.setText(modelsArrayList.get(position).getCounter());
        }
        else{
            rowView = inflater.inflate(R.layout.group_header_item, parent, false);
            TextView titleView = (TextView) rowView.findViewById(R.id.header);
            titleView.setText(modelsArrayList.get(position).getTitle());
            rowView.setOnClickListener(null);

        }

        // 5. retrn rowView
        return rowView;
    }
}