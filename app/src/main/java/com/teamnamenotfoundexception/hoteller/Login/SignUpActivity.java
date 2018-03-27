package com.teamnamenotfoundexception.hoteller.Login;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.teamnamenotfoundexception.hoteller.Activities.MainActivity;
import com.teamnamenotfoundexception.hoteller.R;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    private EditText email, pass;
    private Button signUp;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = findViewById(R.id.semail);
        pass = findViewById(R.id.spass);
        signUp = findViewById(R.id.ssignupBtn);
        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.sprogress);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignUpButtonClicked(v);
            }
        });
    }

    public void onSignUpButtonClicked(View v) {

        String email_text = email.getText().toString().trim();
        String pass_text = pass.getText().toString().trim();

        if (!isNetworkAvailableAndConnected()) {
            Toast.makeText(getApplicationContext(), "Network is not connected.", Toast.LENGTH_SHORT).show();
            return;
        } else if (email_text.isEmpty() || pass_text.isEmpty()) {
            Toast.makeText(getApplicationContext(), "All fields are mandatory!", Toast.LENGTH_SHORT).show();
            return;
        } else if (pass_text.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password should be minimum 6 characters.", Toast.LENGTH_SHORT).show();
            return;
        }

        signUp.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        auth.createUserWithEmailAndPassword(email_text, pass_text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Email/password invalid!",
                            Toast.LENGTH_SHORT).show();
                    try {
                        Log.i(TAG, "onComplete: " + task.getResult());
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "use a proper email id", Toast.LENGTH_SHORT).show();
                    } finally {
                        progressBar.setVisibility(View.INVISIBLE);
                        signUp.setEnabled(true);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Successfully signed up!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                }
            }
        });
    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        return isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();
    }
}