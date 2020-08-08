package com.example.cluster.ui.clusters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.cluster.Lobby;
import com.example.cluster.R;

public class ClustersFragment extends Fragment {

    private ClustersViewModel clustersViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        clustersViewModel =
                ViewModelProviders.of(this).get(ClustersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_clusters, container, false);

        //Values that we will get from firebase user
        String[] values = {"PHYS 214", "CS 125", "eSports 360 (Owner)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_list_item_1, values);

        ListView listView = (ListView) root.findViewById(R.id.lstMain);
        listView.setAdapter(adapter);

        return root;
    }
}