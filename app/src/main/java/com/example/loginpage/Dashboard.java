package com.example.loginpage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginpage.Class.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {


    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    User recordUser;
    Uri path;



    Button profileBtn, logoutBtn, adminBtn;
    ImageView restBtn, sFoodBtn, catBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        profileBtn = findViewById(R.id.dashboard_profile_btn);
        logoutBtn = findViewById(R.id.dashboard_logOut_btn);

        restBtn = findViewById(R.id.dashboard_restaurantsBtn);
        sFoodBtn = findViewById(R.id.dashboard_streetfoodBtn);
        catBtn = findViewById(R.id.dashboard_cateringBtn);
        adminBtn = findViewById(R.id.dashboard_adminBTN);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("User");
        userID = user.getUid();



        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);
                if (userprofile.isAdmin()){
                   adminBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Profile.class);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user= FirebaseAuth.getInstance().getCurrentUser();
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Dashboard.this, "Good Bye", Toast.LENGTH_SHORT).show();
                Intent logout_intent = new Intent(Dashboard.this, Login.class);
                startActivity(logout_intent);

            }
        });

        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toAdmin = new Intent(Dashboard.this, AdminPanel.class);
                startActivity(toAdmin);
            }
        });





    } // onCreate
} // app compact activity