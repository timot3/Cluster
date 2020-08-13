package com.example.cluster;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

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
    }

}