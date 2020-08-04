package com.example.startupinspection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Cycles through all OSHA startup questions and inspections, giving the user the choice of
 * satisfactory or unsatisfactory, then leads to the next activity, which provides a space in case
 * of comments/concerns
 *
 * @author Kyle Kline
 */

public class InspectionActivity extends AppCompatActivity {

    private TextView textViewQuestion;
    private RadioGroup btnGroup;
    private RadioButton btn1;
    private RadioButton btn2;
    private Button btnConfirmNext;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;
    private boolean answered;
    DatabaseReference testRef;
    Users user;
    long maxID, maxInc;
    private String question;
    private String name;
    //String name, date, dept, truck;
    private String timeStamp;
    public static final String TEXT_NAME2 = "com.example.startupinspection.TEXT_NAME";

    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);

        //initialize variable values
        textViewQuestion = findViewById(R.id.text_view_question);
        btnGroup = findViewById(R.id.satUnsat);
        btn1 = findViewById(R.id.satis);
        btn2 = findViewById(R.id.unsatis);
        btnConfirmNext = findViewById(R.id.btn_next);

        //importing username for question/answer entry into database
        Intent intent = getIntent();
        timeStamp = intent.getStringExtra(userStartupInfo.TEXT_NAME);

        InspectionDBHelper dbHelper = new InspectionDBHelper(this);
        questionList = dbHelper.getAllQuestions();
        Collections.shuffle(questionList);
        questionCountTotal = questionList.size();
        showNextQuestion();

        user = new Users();
        testRef = FirebaseDatabase.getInstance().getReference().child("Users");
        //Perhaps useless but decided not to remove in case later necessary
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

        //almost definitely useless
        maxInc = maxID+1;

        //begins cycling through questions
        btnConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToUserInfo();
                showNextQuestion();
            }
        });
    }

    //reports user answer to each question and stores it in database section created in previous activity
    private void addToUserInfo() {
        int ansNum = checkAnswer();
        String satUnsat = "";
        if (ansNum == 1) {
            satUnsat = "Satisfactory";
        } else {
            satUnsat = "Unsatisfactory";
        }
        user.setCheck(satUnsat);
        testRef.child(timeStamp).child(currentQuestion.getQuestion()).setValue(user);
        Toast.makeText(InspectionActivity.this, "data inserted successfully", Toast.LENGTH_LONG).show();
    }

    private void showNextQuestion() {
        btnGroup.clearCheck();
        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);
            textViewQuestion.setText(currentQuestion.getQuestion());
            btn1.setText(currentQuestion.getOption1());
            btn2.setText(currentQuestion.getOption2());
            questionCounter++;
            answered = false;
        } else {
            finishQuestions();
            advanceToComments();
        }
    }

    //returns which answer has been checked (possible to be neither)
    private int checkAnswer() {
        answered = true;
        RadioButton btnSelected = findViewById(btnGroup.getCheckedRadioButtonId());
        int ans = btnGroup.indexOfChild(btnSelected) + 1;
        return ans;

    }

    //finishes cycling through questions and advances to comments
    private void advanceToComments() {
        Intent intent = new Intent(InspectionActivity.this, CommentTab.class);
        intent.putExtra(TEXT_NAME2, timeStamp);
        startActivity(intent);
    }



    private void finishQuestions() {
        finish();
    }
}