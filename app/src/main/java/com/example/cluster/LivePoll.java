package com.example.cluster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LivePoll extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_poll);
        setTitle("Live Poll");
    }
}