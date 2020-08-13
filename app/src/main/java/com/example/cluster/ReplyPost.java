package com.example.cluster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReplyPost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_post);
        Intent thisPage = getIntent();
        setTitle("Reply to " + thisPage.getStringExtra("title"));

        TextView titleView = (TextView) findViewById(R.id.titleView);
        titleView.setText(thisPage.getStringExtra("title"));
    }


    public void onClick(View view) {
        EditText reply = (EditText) findViewById(R.id.repsonseText);

        if (reply.getText().toString().isEmpty())
            Toast.makeText(this, "No message written", Toast.LENGTH_LONG).show();
        else {
            FirebaseFirestore.getInstance().collection("community")
                    .document(getIntent().getStringExtra("clusterID"))
                    .collection("posts")
                    .document(getIntent().getStringExtra("postID")).update("replies",
                    FieldValue.arrayUnion(reply.getText().toString())).addOnCompleteListener(task -> {
                        if (task.isComplete()) {
                            Toast.makeText(ReplyPost.this, "Reply sent",
                                    Toast.LENGTH_LONG).show();
                            setResult(500);
                            finish();
                        }
                    });

        }
    }
}