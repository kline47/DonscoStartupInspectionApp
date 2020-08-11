package com.example.startupinspection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.SimpleTimeZone;

/**
 * Page responsible for collecting user ID, current date, truck #, and department
 *
 * @author Kyle Kline
 */

public class userStartupInfo extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{

    public static final String TEXT_NAME = "com.example.startupinspection.TEXT_NAME";

    String name, date, truck, dept, currDate, currTime;
    EditText user_name, user_truck, user_dept;
    //TextView nameText, dateText, truckText, deptText;
    DatabaseReference testRef;
    Button addInfo, datePick;
    Users user;
    long maxID, maxInc;
    Long timeStampLong;
    private String timeStamp;
    private static final String TAG = "userStartupInfo";
    private TextView user_date;
    private DatePickerDialog.OnDateSetListener uDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_startup_info);

        //initialize variable values
        user_name = (EditText) findViewById(R.id.userName);
        datePick = (Button) findViewById(R.id.userDate);
        user_date = (TextView) findViewById(R.id.displayDate);
        user_truck = (EditText) findViewById(R.id.userTruck);
        user_dept = (EditText) findViewById(R.id.userDept);
        addInfo = (Button) findViewById(R.id.addDataBtn);

        datePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        Calendar cal = Calendar.getInstance();
        currDate = DateFormat.getDateInstance().format(cal.getTime());
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        currTime = format.format(cal.getTime());

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
                //date = user_date.getText().toString();
                truck = user_truck.getText().toString();
                dept = user_dept.getText().toString();

                user.setName(name);
                user.setDate(currDate);
                user.setTruck(truck);
                user.setDept(dept);

                testRef.child(currTime).setValue(user);
                Toast.makeText(userStartupInfo.this, "data inserted successfully", Toast.LENGTH_LONG).show();

                finishToHomeScreen();
                startInspection();
            }
        });

    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        month++;
        date = "month/day/year: " + month + '/' + day + "/" + year;
        datePick.setText(date);
    }

    private void finishToHomeScreen() {
        finish();
    }

    //transfers user timestamp to next activity for database storage
    private void startInspection() {
        Intent intent = new Intent(userStartupInfo.this, InspectionActivity.class);
        intent.putExtra(TEXT_NAME, currTime);
        startActivity(intent);
    }

}