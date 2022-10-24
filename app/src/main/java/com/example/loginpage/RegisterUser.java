package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterUser extends AppCompatActivity {






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);


        TextView new_username = findViewById(R.id.inputName);
        TextView new_surname = findViewById(R.id.inputSurname);
        TextView new_mail = findViewById(R.id.inputEmail);
        TextView new_pass = findViewById(R.id.inputPassword);
        TextView conf_pass = findViewById(R.id.inputConfirmPassword);
        Button registerUser = findViewById(R.id.btnRegister);
        TextView alreadyAccount = findViewById(R.id.alredyAccount);



        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtotext = new_mail.getText().toString();
                // The user registers on the site, a message is displayed, user is redirect on the login page
                if(!TextUtils.isEmpty(new_username.getText().toString())  && !TextUtils.isEmpty(new_surname.getText().toString())  && !TextUtils.isEmpty(new_mail.getText().toString()) && !TextUtils.isEmpty(new_pass.getText().toString())&& !TextUtils.isEmpty(conf_pass.getText().toString())){ // if all the text are full
                    if (Patterns.EMAIL_ADDRESS.matcher(emailtotext).matches()) { // if the email was written in a correct format
                        if (new_pass.getText().toString().equals(conf_pass.getText().toString())) { // if the passwords matches
                            Toast.makeText(RegisterUser.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterUser.this, MainActivity.class);
                            startActivity(intent);
                        } else { // else password mismatch
                            Toast.makeText(RegisterUser.this, "The password does not match", Toast.LENGTH_SHORT).show();
                        }
                    } else { // else email is not insert correctly
                        Toast.makeText(RegisterUser.this, "Invalid email format please insert right email", Toast.LENGTH_SHORT).show();
                    }
                } else { // if all details are not inserted
                    Toast.makeText(RegisterUser.this, "Please insert your details", Toast.LENGTH_SHORT).show();
                }
            }
        });


        alreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterUser.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }


}