package com.example.cluster;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> clusterNames;

    public MyListAdapter(Activity context, ArrayList<String> clusterNames) {
        super(context, R.layout.row, clusterNames);

        this.context = context;
        this.clusterNames = clusterNames;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.row, null, true);

        TextView textView = (TextView) rowView.findViewById(R.id.clusterName);
        textView.setText(clusterNames.get(position));

        return rowView;
    }
}
