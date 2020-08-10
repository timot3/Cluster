package com.example.cluster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SettingsChangePassword extends AppCompatActivity {
    private EditText newPassword;
    private Button reset;

    private FirebaseAuth fAuth;
    private String userId;

    private static final String TAG = "SettingsChangePassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_change_password);

        newPassword = findViewById(R.id.newPasswordField);
        reset = findViewById(R.id.submitButton);
        fAuth = FirebaseAuth.getInstance();
        userId = Objects.requireNonNull(fAuth.getCurrentUser()).getEmail();

        Intent intent = getIntent();
        String title = intent.getStringExtra("SettingsPasswordSwitch");
        setTitle(title);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password_string = newPassword.getText().toString();
                if (TextUtils.isEmpty(password_string)) {
                    Toast.makeText(getApplicationContext(), "Enter new password",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    onClickUpdatePassword(password_string, v);
                }
            }
        });
    }

    public void onClickUpdatePassword(String nPassword, View v) {

        // Reauthenticate the user with their old password
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(userId, nPassword);
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Log.d(TAG, "User re-authenticated");

                            final EditText reenterPassword = new EditText(v.getContext());
                            final AlertDialog.Builder reLoginDialog = new AlertDialog.Builder(v.getContext());
                            //String reenteredPassword = "";

                            reLoginDialog.setTitle("Enter new password");
                            reLoginDialog.setMessage("Enter your new password- must be 6+ characters");
                            reLoginDialog.setView(reenterPassword);

                            reLoginDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String reenteredPassword = reenterPassword.getText().toString();
                                    // Now set new password
                                    user.updatePassword(reenteredPassword)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "User password updated.");
                                                        Toast.makeText(SettingsChangePassword.this, "Password reset Successful", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    }
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(SettingsChangePassword.this, "Password reset failed", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                            });
                                }
                            });
                            reLoginDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });

                            AlertDialog reLogin = reLoginDialog.create();
                            reLogin.show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SettingsChangePassword.this, "Wrong password", Toast.LENGTH_SHORT).show();
                    }
                });



        // Pop up an alert dialog for their new password


//        try {
//            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//            // Change password
//            user.updatePassword(nPassword)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Log.d(TAG, "User password updated.");
//                            }
//                        }
//                    })
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(SettingsChangePassword.this, "Password reset successfully", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(SettingsChangePassword.this, "Password Reset Failed", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//        } catch (Exception e) {
//
//            Toast.makeText(SettingsChangePassword.this, "Reauthenticating User", Toast.LENGTH_SHORT).show();
//
//            // Reauthenticate user
//            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//            // Make an alert dialog to get their password
//            final EditText reenterPassword = new EditText(v.getContext());
//            final AlertDialog.Builder reLoginDialog = new AlertDialog.Builder(v.getContext());
//            final String[] reenteredPassword = {""};
//
//            reLoginDialog.setTitle("Log in expired");
//            reLoginDialog.setMessage("Enter your password to re-login");
//            reLoginDialog.setView(reenterPassword);
//
//            reLoginDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    reenteredPassword[0] = reenterPassword.getText().toString();
//                }
//            });
//            reLoginDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    // finish()
//                }
//            });
//
//            AlertDialog reLogin = reLoginDialog.create();
//            reLogin.show();
//
//            // Relogin with the credentials from the previous step
//            AuthCredential credential = EmailAuthProvider.getCredential(userId, reenteredPassword[0]);
//            user.reauthenticate(credential)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            Log.d(TAG, "User re-authenticated");
//                        }
//                    });
//        }
    }
}