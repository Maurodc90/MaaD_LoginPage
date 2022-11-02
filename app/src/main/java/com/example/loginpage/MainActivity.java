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

public class MainActivity extends AppCompatActivity {

    TextView username, password, forgetpass, registerUser;
    Button loginBtn;
    FirebaseAuth mAuth;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        forgetpass = findViewById(R.id.forgetpass);
        registerUser = findViewById(R.id.new_user);
        loginBtn = findViewById(R.id.loginbtn);
        mAuth = FirebaseAuth.getInstance();






        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }


        });

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterUser.class);
                startActivity(intent);
            }
        });


    }
    private void userLogin() {
        String emailtotext = username.getText().toString().trim();
        String passwordtotext = password.getText().toString().trim();
        if (emailtotext.isEmpty()){
            username.setError("Please provide an email address");
            username.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailtotext).matches()){
            username.setError("Please provide a correct email address");
            username.requestFocus();
            return;
        }
        if (passwordtotext.isEmpty()){
            password.setError("Please insert your password");
            password.requestFocus();
            return;
        }
        if (password.length() < 6 ){
            password.setError("The password need to be minimum 6 char");
            password.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(emailtotext, passwordtotext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Login correctly", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, menuApp.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(MainActivity.this, "Failed to login, please check your credential", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Failed to login, please check your credential", Toast.LENGTH_LONG).show();
            }
        });




    }


}