package com.example.cluster;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ResponsesFragment extends Fragment {

    Handler handler = new Handler();
    ListView listViewLive;
    String clusterID;

    public ResponsesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_responses, container, false);

        Intent thisScreen = getActivity().getIntent();
        clusterID = thisScreen.getStringExtra("ID");

        ListView listViewDaily = (ListView) root.findViewById(R.id.dailyList);
        listViewLive = (ListView) root.findViewById(R.id.liveList);

        FirebaseFirestore.getInstance().collection("clusters").document(clusterID)
                .collection("dailypolls").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<String> dailyPolls = new ArrayList<>();
                        ArrayList<String> dailyPollID = new ArrayList<>();
                        if (task.isComplete()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                String dailyTitle = documentSnapshot.getString("question");
                                String dailyID = documentSnapshot.getId();
                                if (dailyTitle != null) {
                                    dailyPolls.add(dailyTitle);
                                    dailyPollID.add(dailyID);
                                }
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                                    android.R.layout.simple_list_item_1, dailyPolls);
                            listViewDaily.setAdapter(adapter);

                            listViewDaily.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                }
                            });
                        }
                    }
                });

        // Replace this with Live Polling results
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    FirebaseFirestore.getInstance().collection("clusters").document(clusterID)
                            .collection("livepolls").document("live").get()
                            .addOnCompleteListener(task -> {
                                if (task.isComplete()) {
                                    ArrayList<String> livePolls = new ArrayList<>();
                                    DocumentSnapshot snapshot = task.getResult();

                                    List<String> replies = (List<String>) snapshot.get("replies");
                                    for (String reply : replies) {
                                        if (reply != null) {
                                            livePolls.add(reply);
                                        }
                                    }

                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                                            android.R.layout.simple_list_item_1, livePolls);
                                    listViewLive.setAdapter(adapter);
                                    Log.wtf("Bruh", "reloading");
                                }

                            });
                    android.os.SystemClock.sleep(3000);
                }
            }
        }).start();
        return root;
    }



}
