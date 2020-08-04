package com.example.startupinspection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

/**
 * This is the main login page showed after initial app startup
 * User login info added/stored on Firebase server
 * Can only continue with startup procedure by providing valid  login
 *
 * @author Kyle Kline
 */

public class LoginPage extends AppCompatActivity {

    private FirebaseAuth myAuth;
    private TextView loginText, togglePW;
    private EditText emailTBox, pwTBox;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //initialize variable values
        loginText = (TextView) findViewById(R.id.LoginAcctText);
        emailTBox = (EditText) findViewById(R.id.emailBox);
        pwTBox = (EditText) findViewById(R.id.pwBox);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        togglePW = (TextView) findViewById(R.id.toggleView);

        //Makes SHOW/HIDE text box disappear
        togglePW.setVisibility(View.GONE);
        pwTBox.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        //Changes text box properties when text is entered into password field
        pwTBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (pwTBox.getText().length() > 0) {
                    //SHOW/HIDE box appears only when text is present
                    togglePW.setVisibility(View.VISIBLE);
                } else {
                    togglePW.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //makes SHOW/HIDE box clickable and able to show/hide the password
        togglePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (togglePW.getText() == "SHOW") {
                    togglePW.setText("HIDE");
                    pwTBox.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    pwTBox.setSelection(pwTBox.length());
                } else {
                    togglePW.setText("SHOW");
                    pwTBox.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pwTBox.setSelection(pwTBox.length());
                }
            }
        });

        myAuth = FirebaseAuth.getInstance();

        //Checks user info to ensure valid credentials entered
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailTBox.getText().toString().trim();
                String pw = pwTBox.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    emailTBox.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(pw)) {
                    pwTBox.setError("Password is required");
                    return;
                }

                myAuth.signInWithEmailAndPassword(email, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startInspection();
                            finish();
                        } else {
                            Toast.makeText(LoginPage.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    private void startInspection() {
        Intent intent = new Intent(LoginPage.this, userStartupInfo.class);
        startActivity(intent);
    }
}