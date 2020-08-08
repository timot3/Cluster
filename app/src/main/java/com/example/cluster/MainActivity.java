package com.example.cluster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {


    /**
     * LOGIN SCREEN
     * @param savedInstanceState
     */


    private FirebaseUser mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSignInClick(View v) {

    }
}