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

import com.airbnb.lottie.LottieAnimationView;
import com.example.loginpage.Class.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity {


    // 1.  Create a firebase auth object
    FirebaseAuth mAuth;

    TextView new_username, new_surname, new_mail, new_pass, conf_pass, alreadyAccount;
    Button registerUser;
    LottieAnimationView progressBar;







    // @SuppressLint("MissingInflatedId") // not sure how much this line is usefule to something
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);


        new_username = findViewById(R.id.inputName);
        new_surname = findViewById(R.id.inputSurname);
        new_mail = findViewById(R.id.inputEmail);
        new_pass = findViewById(R.id.inputPassword);
        conf_pass = findViewById(R.id.inputConfirmPassword);
        alreadyAccount = findViewById(R.id.alredyAccount);
        registerUser = findViewById(R.id.register_btn);
        progressBar = findViewById(R.id.animationView);


        mAuth = FirebaseAuth.getInstance(); // get the instance of firebase (the id?)



        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtotext = new_mail.getText().toString();
                // The user registers on the site, a message is displayed, user is redirect on the login page
                if(!TextUtils.isEmpty(new_username.getText().toString())  && !TextUtils.isEmpty(new_surname.getText().toString())  && !TextUtils.isEmpty(new_mail.getText().toString()) && !TextUtils.isEmpty(new_pass.getText().toString())&& !TextUtils.isEmpty(conf_pass.getText().toString())){ // if all the text are full
                    if (Patterns.EMAIL_ADDRESS.matcher(emailtotext).matches()) { // if the email was written in a correct format
                        if (new_pass.getText().toString().equals(conf_pass.getText().toString())) { // if the passwords matches

                            progressBar.setVisibility(View.VISIBLE);
                            mAuth.createUserWithEmailAndPassword(new_mail.getText().toString(),new_pass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() { // from Firebase create and user with
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    User user = new User(new_username.getText().toString(), new_surname.getText().toString(), false, false);
                                    FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        // FirebaseDatabase.getInstance, is for taking the id auto generated by firebase auth.
                                        // .getReference("User), is the path/folder (in this case user) of the realtime database that we searching for
                                        // .child, is adding the user object to the database
                                        // (FirebaseAuth.getInstance().getCurrentUser(), gibbrish for select the user
                                        // .getUid() Uid is the autogenerated by mAuth and added to the Realtimedatabase
                                        // .setValue(user)
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(RegisterUser.this, "User Registered Successfully", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                                Intent intent = new Intent(RegisterUser.this, Login.class);
                                                startActivity(intent);
                                            } else {
                                                progressBar.setVisibility(View.GONE);
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
                Intent intent = new Intent(RegisterUser.this, Login.class);
                startActivity(intent);
            }
        });


    }


    //


}