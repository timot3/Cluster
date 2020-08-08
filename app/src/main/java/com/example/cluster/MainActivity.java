package com.example.cluster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {


    /**
     * LOGIN SCREEN
     * @param savedInstanceState
     */


    private FirebaseUser mAuth;
    private FirebaseUser user;
    private FirebaseAuth AuthUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AuthUser = FirebaseAuth.getInstance();
    }

    public void onSignInClick(View v) {
        String email = ((EditText) findViewById(R.id.emailField)).getText().toString();
        String password = ((EditText) findViewById(R.id.PasswordField)).getText().toString();

        AuthUser.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    //If Login is successful
                    if (task.isSuccessful()) {
                        user = AuthUser.getCurrentUser();
                        //Navigate to the next screen
                        Intent i = new Intent(MainActivity.this, Lobby.class);
                        startActivity(i);
                    }

                    //Login is unsuccessful
                    //Do nothing
                });

    }
}