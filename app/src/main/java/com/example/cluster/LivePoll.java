package com.example.cluster;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class LivePoll extends FragmentActivity {



    Handler handler = new Handler();
    Fragment fragment;
    static int display;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_poll);
        setTitle("Live Poll");
        display = R.id.fragmentContainer;
        fragment = new WaitingFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment).commit();

        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                FirebaseFirestore.getInstance().collection("clusters")
                        .document(getIntent().getStringExtra("ID"))
                        .collection("livepolls")
                        .document("live").get().addOnCompleteListener(task -> {
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                                .beginTransaction();
                    if (task.isComplete()) {
                        DocumentSnapshot snapshot = task.getResult();
                        String question = snapshot.getString("question");
                        List<String> emails = (List<String>) snapshot.get("emails");
                        boolean answered = false;

                        for (String s : emails)
                            if (s.equals(userEmail))
                                answered = true;

                        if (!(question.isEmpty()) && !answered) {
                            //Add fragment
                            try {
                                Bundle bundle = new Bundle();
                                QuestionFragment questionFragment = new QuestionFragment();
                                questionFragment.setArguments(bundle);
                                bundle.putString("ID", getIntent().getStringExtra("ID"));
                                fragmentTransaction.replace(R.id.fragmentContainer,
                                        questionFragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else
                            handler.postDelayed(this, 1000);
                    }
                });
            }

        }, 1000);
    }




}