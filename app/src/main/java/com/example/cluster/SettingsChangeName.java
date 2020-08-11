package com.example.cluster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.w3c.dom.Text;

public class SettingsChangeName extends AppCompatActivity {

    private EditText nameField;
    private Button nameButton;

    private static final String TAG = "SettingsChangeName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_change_name);

        nameField = findViewById(R.id.newNameField);
        nameButton = findViewById(R.id.changeNameButton);

        Intent intent = getIntent();
        String title = intent.getStringExtra("SettingsNameSwitch");
        setTitle(title);

        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newNameString = nameField.getText().toString();
                if (TextUtils.isEmpty(newNameString)) {
                    Toast.makeText(getApplicationContext(), "Enter new name", Toast.LENGTH_LONG).show();
                }
                else {
                    onClickUpdateName(newNameString);
                }
            }
        });
    }

    public void onClickUpdateName(String nName) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(nName)
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Name updated Successfully", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "User profile updated.");
                            finish();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Name update failed", Toast.LENGTH_LONG).show();
                    }
                });
    }

}