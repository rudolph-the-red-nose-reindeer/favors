package edu.upenn.cis350.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class SignupActivity extends Activity{
    private FirebaseAuth auth;
    private String email;
    private String password;
    private EditText emailText;
    private EditText passwordText;
    public static final int ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_signup);
        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        passwordText.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    private void success() {
        Toast.makeText(SignupActivity.this, "Successfully Registered",
                Toast.LENGTH_LONG).show();
        Intent i = new Intent(SignupActivity.this, LoginActivity.class);
        SignupActivity.this.startActivityForResult(i, ID);
        SignupActivity.this.finish();
    }

    private void failure() {
        emailText.setHint("email");
        passwordText.setHint("password");
        emailText.setText("");
        passwordText.setText("");
        Toast.makeText(SignupActivity.this, "Registration Failed",
                Toast.LENGTH_LONG).show();
    }

    private boolean validPennEmail(String s) {
        if (s.length() < 10) {
            return false;
        }
        return s.substring(s.length() - 9).equals("upenn.edu");
    }


    public void onClick(View view) {
        email = emailText.getText().toString();
        password = passwordText.getText().toString();
        if (!validPennEmail(email)) {
            failure();
            return;
        }
        Task<AuthResult> task = auth.createUserWithEmailAndPassword(email, password);
        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    success();
                } else {
                    failure();
                }
            }
        });

    }
}
