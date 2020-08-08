package com.example.cluster.ui.create;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.cluster.R;
import com.example.cluster.ui.join.JoinViewModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;

public class CreateFragment extends Fragment {
    private CreateViewModel createViewModel;
    private FirebaseFunctions functions;

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
        final String clusterName = ((EditText) root.findViewById(R.id.createField)).getText().toString();
        final String code = ((EditText) root.findViewById(R.id.codeField)).getText().toString();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code.isEmpty() || clusterName.isEmpty()) {
                    //Send Invalid here
                    return;
                }

                else {
                    Map<String, Object> data = new HashMap<>();
                    data.put("active", true);
                    data.put("name", clusterName);
                    data.put("password", code);
                    addCluster(data);
                }
            }
        });

        return root;

    }

    private Task<String> addCluster(Map<String, Object> data) {
        return functions
                .getHttpsCallable("createCluster")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        String result = (String) task.getResult().getData();
                        Log.wtf("YEET", result);
                        return result;
                    }
                });

    }

}