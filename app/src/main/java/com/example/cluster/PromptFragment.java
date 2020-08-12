package com.example.cluster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PromptFragment extends Fragment {

    public PromptFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_prompt, container, false);


        Intent thisScreen = getActivity().getIntent();
        String clusterID = thisScreen.getStringExtra("ID");

        EditText questionText = (EditText) root.findViewById(R.id.questionView);
        Button submit = (Button) root.findViewById(R.id.submitButton);

        submit.setOnClickListener(v -> {
            FirebaseFirestore.getInstance().collection("clusters")
                    .document(clusterID).collection("livepolls")
                    .document("live").update("question",
                    questionText.getText().toString());
            Toast.makeText(getContext(), "Question Posted", Toast.LENGTH_LONG).show();
        });

        return root;
    }
}
