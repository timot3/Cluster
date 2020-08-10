package com.example.cluster.ui.clusters;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.cluster.Lobby;
import com.example.cluster.R;
import com.example.cluster.StudentViewCluster;
import com.example.cluster.TeacherView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.concurrent.ExecutionException;

public class ClustersFragment extends Fragment {

    private ClustersViewModel clustersViewModel;
    private String userEmail;

    private ListView listView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        clustersViewModel =
                ViewModelProviders.of(this).get(ClustersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_clusters, container, false);

        listView = (ListView) root.findViewById(R.id.lstMain);
        userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        new MyTask().execute();

        //Values that we will get from firebase user
        String[] clusters = {"PHYS 214", "CS 125", "eSports 360 (Owner)"};


        /**
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, clusters);

        //Setting adapter
        ListView listView = (ListView) root.findViewById(R.id.lstMain);
        listView.setAdapter(adapter);

        //Setting items to be clickable
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView //<? > arg0, View view, int position, long id) {
                Intent i = new Intent(getActivity(), StudentViewCluster.class);
                i.putExtra("Cluster", clusters[position]);
                startActivity(i);
            }

        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_list_item_1, clusters);

        ListView listView = (ListView) root.findViewById(R.id.lstMain);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                Intent i = new Intent(getActivity(), StudentViewCluster.class);
                i.putExtra("Cluster", clusters[position]);
                startActivity(i);
            }

        });
        view = root;
         **/

        /**
        FirebaseFirestore.getInstance().collection("clusters")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<String> list = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    String name = documentSnapshot.getString("name");
                    list.add(name);
                }
                ListView listView = root.findViewById(R.id.lstMain);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, list);

                listView.setAdapter(adapter);

                listView.setOnItemClickListener((parent, view, position, id) -> {
                    Intent i = new Intent(getActivity(), StudentViewCluster.class);
                    i.putExtra("Cluster", parent.getItemIdAtPosition(position));
                    startActivity(i);
                });
            }
        });
         **/

        return root;
    }

    class SortAlphabetical implements Comparator<String> {
        public int compare(String a, String b) {
            String lower_a = a.toLowerCase();
            String lower_b = b.toLowerCase();
            return lower_a.compareTo(lower_b);
        }
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(Void... voids) {
            FirebaseFirestore.getInstance()
                    .collection("clusters")
                    .get()
                    .addOnCompleteListener(task -> {
                        ArrayList<String> list = new ArrayList<>();
                        if (task.isComplete()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                //Look if user owns the cluster
                                String clusterName = documentSnapshot.getString("name");
                                String ownerEmail = documentSnapshot.getString("ownerEmail");
                                List<String> members = (List<String>) documentSnapshot.get("members");
                                boolean isMember = false;
                                //First check if its a owner
                                if (ownerEmail.equals(userEmail)) {
                                    list.add(clusterName + " (Owner)");
                                    continue;
                                }
                                //Then member
                                for (String m : members) {
                                    if (m.equals(userEmail)) {
                                        isMember = true;
                                        break;
                                    }
                                }

                                //Yes add to list, otherwise don't add it
                                if (isMember)
                                    list.add(clusterName);
                            }

                            //Find a way to sort the list so that the Owners are first, then
                            // the members
                            this.sortList(list);
                            // Sort before adapter

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_list_item_1, list);

                            listView.setAdapter(adapter);

                            listView.setOnItemClickListener((parent, view, position, id) -> {

                                //This is where we can choose what view works
                                Intent i;
                                if (list.get(position).contains("Owner")) {
                                    i =  new Intent(getActivity(), TeacherView.class);
                                } else {
                                    i = new Intent(getActivity(), StudentViewCluster.class);
                                }
                                i.putExtra("Cluster", list.get(position));
                                startActivity(i);
                            });
                        }
                    });
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void sortList(List<String> list) {
            // Partition, then sort each
            List<String> owners = list.stream().filter(s -> s.endsWith("(Owner)")).collect(Collectors.toList());
            List<String> members = list.stream().filter(s-> !(s.endsWith("(Owner)"))).collect(Collectors.toList());

            Comparator<String> sortAlphabetical = new SortAlphabetical();
            owners.sort(sortAlphabetical);
            members.sort(sortAlphabetical);

            list.clear();
            list.addAll(owners);
            list.addAll(members);
        }
    }

}