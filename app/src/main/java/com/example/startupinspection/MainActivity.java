package com.example.startupinspection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Shows the user the main homepage, asking to start inspection
 * Leads to login page
 *
 * @author Kyle Kline
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "UserStartupInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize variable values
        Button btnStartInspection = findViewById(R.id.inspection_btn);
        btnStartInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //leads to login page
                startInspection();
            }
        });
    }

    private void startInspection() {
        Intent intent = new Intent(MainActivity.this, LoginPage.class);
        startActivity(intent);
    }

}