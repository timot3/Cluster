package com.example.cluster.ui.settings;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.cluster.SettingsViewCluster;

import com.example.cluster.R;
import com.example.cluster.ui.settings.SettingsViewModel;


import java.util.Set;

public class Settings extends Fragment {

    private SettingsViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        settingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        //Values that we will get from firebase user
        String[] settings = {"About", "Change Name", "Change Password"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, settings);

        //Setting adapter
        ListView listView = (ListView) root.findViewById(R.id.lstSettings);
        listView.setAdapter(adapter);

        //Setting items to be clickable
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView <? > arg0, View view, int position, long id) {
                Intent i = new Intent(getActivity(), SettingsViewCluster.class);
                i.putExtra("Settings", settings[position]);
                startActivity(i);
            }

        });
        return root;
    }
}