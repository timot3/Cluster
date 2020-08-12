package com.example.cluster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.List;

public class DiscussionBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_board);
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("title"));
        ListView listView = (ListView) findViewById(R.id.displayList);

        String clusterID = intent.getStringExtra("clusterID");
        FirebaseFirestore.getInstance().collection("community")
                .document(clusterID).collection("posts").get()
                .addOnCompleteListener(task -> {
                    if (task.isComplete()) {
                        List<String> postsTitles = new ArrayList<>();
                        List<String> postID = new ArrayList<>();
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            postsTitles.add(documentSnapshot.getString("title"));
                            postID.add(documentSnapshot.getId());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(DiscussionBoard.this,
                                android.R.layout.simple_list_item_1, postsTitles);

                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //Add intent here and pass ID
                                Intent i = new Intent(DiscussionBoard.this, PostPage.class);
                                i.putExtra("clusterID", clusterID);
                                i.putExtra("postID", postID.get(position));
                                i.putExtra("title", postsTitles.get(position));
                                startActivity(i);
                            }
                        });
                    }
                });
    }

    public void onCreatePosts(View view) {
        Intent nextScreen = new Intent(this, CreateNewPost.class);
        nextScreen.putExtra("clusterID", getIntent().getStringExtra("clusterID"));
        startActivityForResult(nextScreen, 500);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 500)
            this.recreate();
    }

}