package com.teamnamenotfoundexception.hoteller.Login;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.FirebaseDatabase;
import com.teamnamenotfoundexception.hoteller.Activities.MainActivity;
import com.teamnamenotfoundexception.hoteller.Database.CartManager;
import com.teamnamenotfoundexception.hoteller.Database.DishRepository;
import com.teamnamenotfoundexception.hoteller.R;
import com.teamnamenotfoundexception.hoteller.TutorialActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText email, pass;
    private Button signIn, signUp;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        signIn = findViewById(R.id.lloginBtn);
        signUp = findViewById(R.id.lsignUpButton);
        progressBar = findViewById(R.id.progress);

        mAuth = FirebaseAuth.getInstance();

        //Set up event listeners for signUp and singIn
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginButtonClicked(v);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignUpButtonClicked(v);
            }
        });
    }


    public void onLoginButtonClicked(View v) {

        // First check if the device is connected to internet
        if (!isNetworkAvailableAndConnected()) {
            Toast.makeText(getApplicationContext(), "Network is not connected!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Disable buttons temporarily
        signIn.setEnabled(false);
        signUp.setEnabled(false);

        // Get the email and password
        String emailText = email.getText().toString();
        String passText = pass.getText().toString();

        // Show progress bar
        progressBar.setVisibility(View.VISIBLE);

        // Check if user has entered the required fields
        if (emailText.isEmpty() || passText.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Fill all things up!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
            signIn.setEnabled(true);
            signUp.setEnabled(true);
            return;
        }

        // Firebase signIn
        mAuth.signInWithEmailAndPassword(emailText, passText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Invalid Credentials, try again",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            signIn.setEnabled(true);
                            signUp.setEnabled(true);
                        } else {
                            // Login successful, open MainActivity
                            startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            progressBar.setVisibility(View.INVISIBLE);
                            finish();
                        }
                    }
                });
    }

    // Check if the device is connected to the Internet
    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        return isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();
    }

    public void onSignUpButtonClicked(View v) {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}