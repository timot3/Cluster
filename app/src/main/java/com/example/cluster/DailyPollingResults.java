package com.example.cluster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class DailyPollingResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_polling_results);
        Intent thisScreen = getIntent();
        setTitle(thisScreen.getStringExtra("title"));
        String clusterID = thisScreen.getStringExtra("clusterID");
        String pollID = thisScreen.getStringExtra("pollID");
        ListView listView = findViewById(R.id.dailyResponsesList);

        FirebaseFirestore.getInstance().collection("clusters")
                .document(clusterID).collection("dailypolls").document(pollID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isComplete()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    List<String> replies = (List<String>) documentSnapshot.get("replies");

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(DailyPollingResults.this,
                            android.R.layout.simple_list_item_1, replies);

                    listView.setAdapter(adapter);
                }
            }
        });
    }
}