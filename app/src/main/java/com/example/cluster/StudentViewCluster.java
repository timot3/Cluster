package com.example.cluster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class StudentViewCluster extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_cluster);
        Intent intent = getIntent();
        String title = intent.getStringExtra("Cluster");
        setTitle(title);
    }
}