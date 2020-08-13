package com.example.cluster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DailyPolls extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_polls);
        setTitle("Daily Polls");

        String clusterID = getIntent().getStringExtra("ID");

        ListView listView = (ListView) findViewById(R.id.dailyList);
        FirebaseFirestore.getInstance().collection("clusters")
                .document(clusterID).collection("dailypolls").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<String> polls = new ArrayList<>();
                        ArrayList<String> pollID = new ArrayList<>();
                        if (task.isComplete()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                String title = documentSnapshot.getString("question");
                                String ID = documentSnapshot.getId();
                                if (title != null) {
                                    polls.add(title);
                                    pollID.add(ID);
                                }
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(DailyPolls.this,
                                    android.R.layout.simple_list_item_1, polls);

                            listView.setAdapter(adapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    //Create new activity
                                }
                            });
                        }
                    }
                });
    }
}