package com.example.cluster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


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
        loadingGif = (ImageView) findViewById(R.id.student_view_loading_gif);
        Glide.with(this).load(R.drawable.tabletennis).into(loadingGif);

    }
}