package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Edit_Profile extends AppCompatActivity {

    private CircleImageView profileImageView;
    private EditText firstNameEditText, surnameEditText;
    private TextView  closeTextBtn, saveTextBtn;
    private String checker = "";
    private FirebaseUser userDB;
    private DatabaseReference refDB;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        firstNameEditText = (EditText) findViewById(R.id.settings_first_name);
        surnameEditText = (EditText) findViewById(R.id.settings_surname);
        userDB = FirebaseAuth.getInstance().getCurrentUser();
        refDB = FirebaseDatabase.getInstance().getReference("User");
        userID = userDB.getUid();

        closeTextBtn = (TextView) findViewById(R.id.close_settings_btn);
        saveTextBtn = (TextView) findViewById(R.id.update_account_settings_btn);


        userInfoDisplay(firstNameEditText, surnameEditText);

        closeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        saveTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(checker.equals("clicked"))
                {
                    userInfoSaved();
                }
                else
                {
                    updateOnlyUserInfo();
                }

            }
        });


    }

    private void updateOnlyUserInfo()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("fn", firstNameEditText.getText().toString());
        userMap.put("sn", surnameEditText.getText().toString());
        refDB.child(userID).updateChildren(userMap);


        startActivity(new Intent(Edit_Profile.this, Profile.class));
        Toast.makeText(Edit_Profile.this, "Profile info updated successfully.", Toast.LENGTH_SHORT).show();
        finish();
    }



    private void userInfoSaved()
    {
        if(TextUtils.isEmpty(firstNameEditText.getText().toString()))
        {
            Toast.makeText(this, "Name is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(surnameEditText.getText().toString()))
        {
            Toast.makeText(this, "Surname is mandatory.", Toast.LENGTH_SHORT).show();
        }

    }



    private void userInfoDisplay(EditText profileImageView, EditText fullNameEditText)
    {
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {


                    String fnames = snapshot.child("fn").getValue().toString();
                    String surnames = snapshot.child("sn").getValue().toString();




                    firstNameEditText.setText(fnames);
                    surnameEditText.setText(surnames);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}