package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminPanel extends AppCompatActivity {

    Button backBtn, registerCritiqueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        backBtn = findViewById(R.id.admin_backBtn);
        registerCritiqueBtn = findViewById(R.id.admin_regCritiqueBtn);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(AdminPanel.this, Dashboard.class);
                startActivity(goBack);
            }
        });

        registerCritiqueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regCritique = new Intent(AdminPanel.this, RegisterCritique.class);
                startActivity(regCritique);
            }
        });

    }
}