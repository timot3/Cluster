package com.example.cluster.ui.create;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.cluster.R;
import com.example.cluster.ui.join.JoinViewModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateFragment extends Fragment {
    private CreateViewModel createViewModel;
    private FirebaseFunctions functions = FirebaseFunctions.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        createViewModel =
                ViewModelProviders.of(this).get(CreateViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create, container, false);

        createViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });


        Button button = root.findViewById(R.id.createButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String clusterName = ((EditText) root.findViewById(R.id.createField)).getText().toString();
                String code = ((EditText) root.findViewById(R.id.codeField)).getText().toString();
                if (code.isEmpty() || clusterName.isEmpty()) {
                    //Send Invalid here
                    return;
                }

                else {
                    Map<String, Object> data = new HashMap<>();
                    data.put("active", true);
                    data.put("name", clusterName);
                    data.put("joinCode", code);
                    data.put("members", new ArrayList<>());
                    data.put("meetings", new ArrayList<>());
                    data.put("ownerEmail", FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    addToFireBase(data);
                }

            }
        });

        return root;

    }

    private void addToFireBase(Map<String, Object> data) {
        FirebaseFirestore.getInstance().collection("clusters")
                .add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getContext(), "Cluster created", Toast.LENGTH_SHORT).show();
                String ID = documentReference.getId();
                createCommunity(ID);
            }
        })
        .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to create Cluster",
                Toast.LENGTH_SHORT).show());
    }

    private void createCommunity(String ID) {


        FirebaseFirestore.getInstance().collection("community").document(ID)
                .set(new HashMap<>());

        FirebaseFirestore.getInstance().collection("community").document(ID)
                .collection("posts");

        FirebaseFirestore.getInstance().collection("clusters").document(ID)
                .collection("dailypolls").add(new HashMap<>());


        Map<String, Object> emptyData = new HashMap<>();
        emptyData.put("emails", new ArrayList<>());
        emptyData.put("replies", new ArrayList<>());
        emptyData.put("question", new String());
        FirebaseFirestore.getInstance().collection("clusters").document(ID)
                .collection("livepolls").document("live")
                .set(emptyData);
    }
}