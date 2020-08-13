package com.example.cluster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class PostPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_page);
        Intent thisPage = getIntent();
        setTitle(thisPage.getStringExtra("title"));

        TextView title = (TextView) findViewById(R.id.titleView);
        title.setText(thisPage.getStringExtra("title"));
        ListView listView = (ListView) findViewById(R.id.replyList);
        String clusterID = thisPage.getStringExtra("clusterID");
        String postID = thisPage.getStringExtra("postID");
        TextView questionView = findViewById(R.id.contextView);

        FirebaseFirestore.getInstance().collection("community").document(clusterID)
                .collection("posts").document(postID).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isComplete()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    List<String> replies = (List<String>) documentSnapshot.get("replies");
                    String question = documentSnapshot.getString("question");

                    if (replies != null && question != null) {

                        //Create custom list
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(PostPage.this,
                                android.R.layout.simple_list_item_1, replies);
                        listView.setAdapter(adapter);

                        questionView.setText(question);
                    }
                }
            }
        });
    }

    public void createReply(View view) {
        //Create reply activity
    }

}