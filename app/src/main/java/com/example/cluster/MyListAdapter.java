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

        TextView cluster = (TextView) rowView.findViewById(R.id.clusterName);

        String clusterTitle = "";
        if (clusterNames.get(position).contains("(Owner)")) {
            for (int i = 0; i < clusterNames.get(position).length(); i++) {
                if (clusterNames.get(position).charAt(i) == '(') {
                    clusterTitle = clusterNames.get(position).substring(0, i);
                    break;
                }
            }
            cluster.setText(clusterTitle);
        }
        else
            cluster.setText(clusterNames.get(position));

        TextView memberStatus = (TextView) rowView.findViewById(R.id.memberStatus);
        if (clusterNames.get(position).contains("(Owner)"))
            memberStatus.setText("Owner");
        else
            memberStatus.setText("Member");

        return rowView;
    }
}
