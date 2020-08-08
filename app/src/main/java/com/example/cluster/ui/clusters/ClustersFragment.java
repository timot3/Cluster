package com.example.cluster.ui.clusters;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.cluster.R;
import com.example.cluster.StudentViewCluster;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ClustersFragment extends Fragment {

    private ClustersViewModel clustersViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        clustersViewModel =
                ViewModelProviders.of(this).get(ClustersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_clusters, container, false);

        //Values that we will get from firebase user
        FirebaseFirestore.getInstance()
                .collection("clusters")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<String> values = new ArrayList<>();
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                            for (int i = 0; i < myListOfDocuments.size(); i++) {
                                String name = myListOfDocuments.get(i).getString("name");
                                values.add(name);
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                    getContext(), android.R.layout.simple_list_item_1, values);

                            //Setting adapter
                            ListView listView = (ListView) root.findViewById(R.id.lstMain);
                            listView.setAdapter(adapter);

                            //Setting items to be clickable
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView <? > arg0, View view, int position, long id) {
                                    Intent i = new Intent(getActivity(), StudentViewCluster.class);
                                    i.putExtra("Cluster", values.get(position));
                                    startActivity(i);
                                }

                            });

                        }
                    }
                });

        String[] clusters = {"PHYS 214", "CS 125", "eSports 360 (Owner)"};

        return root;
    }




}