package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView username = findViewById(R.id.username);
        TextView password = findViewById(R.id.password);
        TextView forgetpass = findViewById(R.id.forgetpass);
        TextView registerUser = findViewById(R.id.new_user);

        Button loginBtn = findViewById(R.id.loginbtn);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtotext = username.getText().toString();
                if (Patterns.EMAIL_ADDRESS.matcher(emailtotext).matches()){
                    if(username.getText().toString().equals("ad@min.com") && password.getText().toString().equals("admin")){
                        Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, admin_login.class);
                        startActivity(intent);
                    } else if (username.getText().toString().equals("us@er.com") && password.getText().toString().equals("user")){
                        Intent intent = new Intent(MainActivity.this, menuApp.class);
                        startActivity(intent);
                    }

                    else {
                        Toast.makeText(MainActivity.this, "Wrong Email/Password", Toast.LENGTH_SHORT).show();
                    }
                }
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
}