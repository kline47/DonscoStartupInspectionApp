package com.example.startupinspection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Page responsible for collecting user ID, current date, truck #, and department
 *
 * @author Kyle Kline
 */

public class userStartupInfo extends AppCompatActivity {

    public static final String TEXT_NAME = "com.example.startupinspection.TEXT_NAME";

    String name, date, truck, dept;
    EditText user_name, user_date, user_truck, user_dept;
    //TextView nameText, dateText, truckText, deptText;
    DatabaseReference testRef;
    Button addInfo;
    Users user;
    long maxID, maxInc;
    Long timeStampLong;
    private String timeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_startup_info);

        //initialize variable values
        user_name = (EditText) findViewById(R.id.userName);
        user_date = (EditText) findViewById(R.id.userDate);
        user_truck = (EditText) findViewById(R.id.userTruck);
        user_dept = (EditText) findViewById(R.id.userDept);
        addInfo = (Button) findViewById(R.id.addDataBtn);

        user = new Users();
        testRef = FirebaseDatabase.getInstance().getReference().child("Users");
        //Most likely unnecessary/useless but kept in case later useful/necessary
        testRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    maxID = snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //stores user data into new database subsection given user input
        addInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = user_name.getText().toString();
                date = user_date.getText().toString();
                truck = user_truck.getText().toString();
                dept = user_dept.getText().toString();
                timeStampLong = System.currentTimeMillis()/1000;
                timeStamp = timeStampLong.toString();

                user.setName(name);
                user.setDate(date);
                user.setTruck(truck);
                user.setDept(dept);

                testRef.child(timeStamp).setValue(user);
                Toast.makeText(userStartupInfo.this, "data inserted successfully", Toast.LENGTH_LONG).show();

                finishToHomeScreen();
                startInspection();
            }
        });

    }

    private void finishToHomeScreen() {
        finish();
    }

    //transfers user timestamp to next activity for database storage
    private void startInspection() {
        Intent intent = new Intent(userStartupInfo.this, InspectionActivity.class);
        intent.putExtra(TEXT_NAME, timeStamp);
        startActivity(intent);
    }
}