package com.example.cluster.ui.join;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firestore.v1.WriteResult;


import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JoinFragment extends Fragment {

    private JoinViewModel joinViewModel;
    private Button joinCluster;
    private EditText joinIdField;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        joinViewModel =
                ViewModelProviders.of(this).get(JoinViewModel.class);
        View root = inflater.inflate(R.layout.fragment_join, container, false);
        //final TextView textView = root.findViewById(R.id.text_d);
        joinViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        joinCluster = (Button) root.findViewById(R.id.JoinButton);
        joinIdField = (EditText) root.findViewById(R.id.JoinField);

        joinCluster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String joinCode = joinIdField.getText().toString();
                if (TextUtils.isEmpty(joinCode)) {
                    Toast.makeText(getContext(), "Enter Join Code", Toast.LENGTH_LONG).show();
                }
                else {
                    String user_email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    verifyUser(joinCode, user_email);
                }
            }
        });

        return root;
    }

    protected void verifyUser(String joinCode, String user_email) {
        // Read firebase
        FirebaseFirestore.getInstance()
                .collection("clusters")
                .get()
                .addOnCompleteListener(task -> {
                   if (task.isComplete()) {
                        boolean verified = false;
                        boolean isOwner = false;
                        QueryDocumentSnapshot cluster = null;

                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            String clusterCode = documentSnapshot.getString("joinCode");
                            String ownerEmail = documentSnapshot.getString("ownerEmail");

                            if (joinCode.equals(clusterCode)) {
                                verified = true;
                                if (user_email.equals(ownerEmail)) { isOwner = true; }
                                cluster = documentSnapshot;
                                break;
                            }
                        }

                        if (verified && isOwner) {
                            // Reject on basis that they are owner
                            Toast.makeText(getContext(), "You are the owner of this cluster!", Toast.LENGTH_LONG).show();
                        } else if (!verified) {
                            // Reject on basis that they have the wrong code
                            Toast.makeText(getContext(), "Wrong code", Toast.LENGTH_LONG).show();
                            joinIdField.getText().clear();
                        } else {
                            // Write to firebase, successful join
                            Toast.makeText(getContext(), "Successfully Joined!", Toast.LENGTH_LONG).show();

                            Map<String, List<String>> update = new HashMap<>();
                            List<String> updateArr = (List<String>) cluster.get("members");
                            updateArr.add(user_email);
                            update.put("members", updateArr);

                            FirebaseFirestore.getInstance().collection("clusters").document(cluster.getId())
                                    .set(update, SetOptions.merge());
                        }
                   }
                });
    }
}