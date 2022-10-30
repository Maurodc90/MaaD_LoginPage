package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity {


    // 1.  Create a database reference object
    FirebaseAuth mAuth;







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

        mAuth = FirebaseAuth.getInstance();



        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtotext = new_mail.getText().toString();
                // The user registers on the site, a message is displayed, user is redirect on the login page
                if(!TextUtils.isEmpty(new_username.getText().toString())  && !TextUtils.isEmpty(new_surname.getText().toString())  && !TextUtils.isEmpty(new_mail.getText().toString()) && !TextUtils.isEmpty(new_pass.getText().toString())&& !TextUtils.isEmpty(conf_pass.getText().toString())){ // if all the text are full
                    if (Patterns.EMAIL_ADDRESS.matcher(emailtotext).matches()) { // if the email was written in a correct format
                        if (new_pass.getText().toString().equals(conf_pass.getText().toString())) { // if the passwords matches

                            mAuth.createUserWithEmailAndPassword(new_mail.getText().toString(),new_pass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    User user = new User(new_username.getText().toString(), new_surname.getText().toString());
                                    FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(RegisterUser.this, "User Registered Successfully", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(RegisterUser.this, MainActivity.class);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(RegisterUser.this, "Failed to complete", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                            });







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