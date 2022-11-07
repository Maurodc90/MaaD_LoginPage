package com.example.loginpage;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Edit_Profile extends AppCompatActivity {

    TextView edit_name, edit_surname;
    Button edit_submit, edit_goBack;
    Uri path;
    ImageView profile_picture;

    FirebaseAuth mAuth;
    FirebaseDatabase dBref;
    StorageReference sRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        edit_name = findViewById(R.id.edit_user_name);
        edit_surname = findViewById(R.id.edit_user_surname);
        edit_submit = findViewById(R.id.edit_submitBtn);
        edit_goBack = findViewById(R.id.edit_goBackBtn);
        profile_picture = findViewById(R.id.edit_profile_image);

        mAuth = FirebaseAuth.getInstance();
        sRef = FirebaseStorage.getInstance().getReference("profile_pictures");

        edit_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Edit_Profile.this, Profile.class);
                startActivity(intent);
            }
        });


        edit_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(edit_name.getText().toString()) && !TextUtils.isEmpty(edit_surname.getText().toString())){
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("User");
                    String key = dbRef.push().getKey();
                        StorageReference reference = sRef.child(key+"."+getExtension(path));
                        reference.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Download Url on Success
                                        String url = uri.toString();
                                        Intent submit_intent = new Intent(Edit_Profile.this, Profile.class);
                                        startActivity(submit_intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Download Url on Success
                                        reference.delete();
                                    }
                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                // Loading
                            }
                        });

                } else {
                    Toast.makeText(Edit_Profile.this, "Please be sure to provide both new Name and new surname", Toast.LENGTH_LONG).show();
                }
            }
        });

        profile_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // You can use intent to move between activity, app, or functionality of the phone
                Intent i = new Intent(); // constructor to open new intent
                i.setType("image/*"); // telling the intent to open only images types of files
                i.setAction(Intent.ACTION_GET_CONTENT); // bring element in your app
                startActivityForResult(i,101); // request code is a code create by us to have a reference regarding the intent that the system is on
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