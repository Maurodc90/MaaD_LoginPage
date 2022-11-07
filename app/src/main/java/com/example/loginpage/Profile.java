package com.example.loginpage;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginpage.Class.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {


    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;


    TextView profile_name, profile_surname, profile_greetings;
    ImageView profile_picture;
    Button logoutBtn, editDetailsBtn;
    Uri path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        logoutBtn = findViewById(R.id.profile_logoutButton);
        editDetailsBtn = findViewById(R.id.profile_edit_detailsBtn);
        profile_name = findViewById(R.id.profile_name_display);
        profile_surname = findViewById(R.id.profile_surname_display);
        profile_greetings = findViewById(R.id.profile_greetings);
        profile_picture = findViewById(R.id.profile_user_picture);



        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Profile.this, "Good Bye", Toast.LENGTH_SHORT).show();
                Intent logout_intent = new Intent(Profile.this, Login.class);
                startActivity(logout_intent);
            }
        });



        editDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit_intent = new Intent(Profile.this, Edit_Profile.class);
                startActivity(edit_intent);
            }
        });



        user= FirebaseAuth.getInstance().getCurrentUser(); // from firebase take the current user from authentication
        reference = FirebaseDatabase.getInstance().getReference("User"); // match that with the realtime database
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null){
                    String name = userProfile.getFn();
                    String surname = userProfile.getSn();

                    if (userProfile.isCritique() == false){
                        profile_greetings.setText("Welcome " + name + "!");
                    } else {
                        profile_greetings.setText("Welcome " + name + ", you are a food critique!");
                    }
                    profile_name.setText(name);
                    profile_surname.setText(surname);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Something Wrong happen", Toast.LENGTH_LONG);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data); // check if the code requested is indeed 101, or some other code
        if (requestCode == 101 && resultCode == RESULT_OK && data.getData() != null){
            path = data.getData(); // save the URI in a variable called path so you can use in all the class
            Picasso.get().load(data.getData()).fit().into(profile_picture);
        }
    }

    private String getExtension(Uri uri){ // this method strip out the name of the file and get only the extension without the dot (png, jpg....)
        ContentResolver resolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(resolver.getType(uri));

    }

}