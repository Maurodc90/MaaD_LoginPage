package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    TextView login_email, login_password;
    Button loginBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();
        login_email = findViewById(R.id.login_email_input);
        login_password = findViewById(R.id.login_password_input);
        loginBtn = findViewById(R.id.login_btn);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }

    private void userLogin() {
        String emailtotext = login_email.getText().toString().trim();
        String passwordtotext = login_password.getText().toString().trim();
        if(!emailtotext.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(emailtotext).matches()){
            login_email.setError("Please provide a valid email address");
            login_email.requestFocus();
            return;
        }
        if (passwordtotext.isEmpty() && login_password.length() < 6) {
            login_password.setError("Please insert your password");
            login_password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(emailtotext, passwordtotext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "Login correctly", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login.this, RegisterUser.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(Login.this, "Failed to login, please check your credential", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, "Failed to login, please check your credential", Toast.LENGTH_LONG).show();
            }
        });

    }
}