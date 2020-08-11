package com.example.cluster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;


public class StudentViewCluster extends AppCompatActivity {
    private ImageView loadingGif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_cluster);
        Intent intent = getIntent();
        String title = intent.getStringExtra("Cluster");
        setTitle(title);

        //Use Glide library for custom loading animation
        //loadingGif = (ImageView) findViewById(R.id.student_view_loading_gif);
        //Glide.with(this).load(R.drawable.tabletennis).into(loadingGif);


        String[] options = {"Daily Polls", "Live Polls"};

        ListView listView = findViewById(R.id.listOptions);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                options));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent nextPage;
                if (position == 0) {
                    //Daily polling
                    nextPage = new Intent(StudentViewCluster.this, DailyPolls.class);
                }
                else {
                    //Live polling
                    nextPage = new Intent(StudentViewCluster.this, LivePoll.class);
                }
                nextPage.putExtra("ID", intent.getStringExtra("ID"));
                startActivity(nextPage);
            }
        });
    }
}