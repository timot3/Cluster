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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ReplyDailyPoll extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_daily_poll);
        Intent thisScreen = getIntent();
        setTitle(thisScreen.getStringExtra("title"));
    }

    public void onSubmit(View view) {
        String reply = ((EditText) findViewById(R.id.replyField)).getText().toString();


        if (!(reply.isEmpty())) {
            FirebaseFirestore.getInstance().collection("clusters")
                    .document(getIntent().getStringExtra("clusterID"))
                    .collection("dailypolls")
                    .document(getIntent().getStringExtra("pollID")).update("replies",
                    FieldValue.arrayUnion(reply)).addOnCompleteListener(task -> {
                        if (task.isComplete()) {
                            Toast.makeText(this, "Reply to " + getIntent().getStringExtra("title")
                                    + " Successful", Toast.LENGTH_LONG).show();
                        }
                    });

            finish();
        }
    }
}