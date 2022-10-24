package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class admin_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);


        TextView restRew1 = findViewById(R.id.restRew1);
        TextView restRew2 = findViewById(R.id.restRew2);
        ImageView rest1 = findViewById(R.id.rest1);
        Button btn_logOut = findViewById(R.id.btn_logOut);


        btn_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_login.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}