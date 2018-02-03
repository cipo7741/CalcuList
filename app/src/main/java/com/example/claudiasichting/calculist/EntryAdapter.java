package com.example.claudiasichting.calculist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.claudiasichting.calculist.R;

import java.util.ArrayList;

/**
 * Created by claudiasichting on 03.02.18.
 */

public class EntryAdapter extends ArrayAdapter {

    public EntryAdapter(Context context, ArrayList<Pair<String,String>> entries) {

        super(context, 0, entries);

    }



    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position

        Pair<String,String> entry = (Pair<String, String>) getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.calcu_list_item, parent, false);

        }

        // Lookup view for data population

        TextView tvName = (TextView) convertView.findViewById(R.id.calcu_list_item_key);

        TextView tvHome = (TextView) convertView.findViewById(R.id.calcu_list_item_value);

        // Populate the data into the template view using the data object

        tvName.setText(entry.first);

        tvHome.setText(entry.second);

        // Return the completed view to render on screen

        return convertView;

    }
}