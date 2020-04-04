package cis350.project.favor_app.ui.register;


import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import cis350.project.favor_app.R;
import cis350.project.favor_app.ui.login.LoginActivity;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private String username;
    private String email;
    private String password;
    private EditText usernameText;
    private EditText emailText;
    private EditText passwordText;
    public static final int ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_register);
        usernameText = findViewById(R.id.register_username);
        emailText = findViewById(R.id.register_email);
        passwordText = findViewById(R.id.register_password);
        passwordText.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);

        final View registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameText.getText().toString();
                email = emailText.getText().toString();
                password = passwordText.getText().toString();
                if (!validPennEmail(email)) {
                    failure();
                    return;
                }
                Log.d("register", "got here 1");

                // try email verification :\
                //auth.sendSignInLinkToEmail(email, getActionCodeSettings())
                //      .addOnCompleteListener(new OnCompleteListener<Void>() {
                //          @Override
                //      public void onComplete(@NonNull Task<Void> task) {
                //              if (task.isSuccessful()) {
                //              Log.d("auth", "Email sent.");
                //          }
                //      }
                //      }
                //  );

                Task<AuthResult> task = auth.createUserWithEmailAndPassword(email, password);
                task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d("register", "got here 2");
                            WebRegisterTask registerTask = new WebRegisterTask();
                            registerTask.execute(username, email, password);
                            try {
                                String res = registerTask.get();
                                Log.d("register", "got here 4");
                                JSONObject resObj = new JSONObject(res);
                                if (resObj.getBoolean("success")) {
                                    Log.d("register", "success");
                                    success();
                                } else {
                                    Log.d("register", "failure");
                                    failure();
                                }
                            } catch (Exception e) {
                                Log.d("Register", e.toString());
                                failure();
                            }
                        } else {

                            Log.d("register", "got here 3");
                            failure();
                        }
                    }
                });
            }
        });;
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

    private ActionCodeSettings getActionCodeSettings() {
        ActionCodeSettings actionCodeSettings =
                ActionCodeSettings.newBuilder()
                        // URL you want to redirect back to. The domain (www.example.com) for this
                        // URL must be whitelisted in the Firebase Console.
                        .setUrl("localhost")
                        // This must be true
                        .setHandleCodeInApp(true)
                        .setIOSBundleId("com.example.ios")
                        .setAndroidPackageName(
                                "cis350.project.favor_app",
                                true, /* installIfNotAvailable */
                                "12"    /* minimumVersion */)
                        .build();

        return actionCodeSettings;
    }
}
