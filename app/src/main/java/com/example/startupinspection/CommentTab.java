package com.example.startupinspection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Final page asking the user if they have any issues/comments after completing
 * all startup steps
 * Stores default value into Firebase Database if no comments
 *
 * @author Kyle Kline
 */

public class CommentTab extends AppCompatActivity {

    private Button yesComment, noComment, finishComment;
    private TextView anyComments;
    private EditText commentEntry;
    DatabaseReference testRef;
    Users user;
    long maxID;
    private String name, comment, currTime;
    String commentCategory = "Any Comments";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_tab);

        anyComments = (TextView) findViewById(R.id.anyCommentTBox);
        yesComment = (Button) findViewById(R.id.yesCommentBtn);
        noComment = (Button) findViewById(R.id.noCommentBtn);
        commentEntry = (EditText) findViewById(R.id.commentText);
        finishComment = (Button) findViewById(R.id.finishCommentBtn);

        Intent intent = getIntent();
        currTime = intent.getStringExtra(InspectionActivity.TEXT_NAME2);

        user = new Users();
        testRef = FirebaseDatabase.getInstance().getReference().child("Users");
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

        yesComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentEntry.setVisibility(view.VISIBLE);
                finishComment.setVisibility(view.VISIBLE);
            }
        });
        noComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setComment("No Additional Comments");
                testRef.child(currTime).child(commentCategory).setValue(user);
                Toast.makeText(CommentTab.this, "data inserted successfully", Toast.LENGTH_LONG).show();
                finishComments();
            }
        });

        finishComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comment = commentEntry.getText().toString();
                if (comment.equals("")) {
                    comment = "No Additional Comments";
                }
                user.setComment(comment);
                testRef.child(currTime).child(commentCategory).setValue(user);
                Toast.makeText(CommentTab.this, "data inserted successfully", Toast.LENGTH_LONG).show();
                finishComments();
            }
        });
    }

    private void finishComments() {
        finish();
    }
}