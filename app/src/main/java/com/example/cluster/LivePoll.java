package com.example.cluster;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.atomic.AtomicBoolean;

public class LivePoll extends FragmentActivity {



    Handler handler = new Handler();
    Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_poll);
        setTitle("Live Poll");

        fragment = new WaitingFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment).commit();


        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                FirebaseFirestore.getInstance().collection("clusters")
                        .document(getIntent().getStringExtra("ID"))
                        .collection("livepolls")
                        .document("live").get().addOnCompleteListener(task -> {
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    if (task.isComplete()) {
                        DocumentSnapshot snapshot = task.getResult();
                        String question = snapshot.getString("question");
                        if (!(question.isEmpty())) {
                            //Add fragment
                            try {
                                fragmentTransaction.replace(R.id.fragmentContainer,
                                        new QuestionFragment());
                                fragmentTransaction.commit();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            Log.wtf("Bruh", "This ain't it");
                            handler.postDelayed(this, 1000);
                        }
                    }
                });
            }

        }, 1000);
    }




}