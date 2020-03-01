package com.parveen.example.mainfarmerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class home extends AppCompatActivity {
    public ImageButton mUpload,mPosts,mFeedback,mProfile;

    public TextView mName,mLogout;
    DatabaseReference dataBaseProfile;
    String id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mUpload = findViewById(R.id.upload);
        mPosts = findViewById(R.id.posts);
        mFeedback = findViewById(R.id.feedback);
        mProfile = findViewById(R.id.profile);
        mLogout = findViewById(R.id.Logout);
        mName = findViewById(R.id.textView);

        mLogout.setPaintFlags(mLogout.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        dataBaseProfile = FirebaseDatabase.getInstance().getReference("Farmer").child("Profile");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        id = currentUser.getUid();
        dataBaseProfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if(id.equals(snapshot.child("id").getValue().toString()))
                    {

                        String name = snapshot.child("name").getValue().toString();
                        mName.setText("Hello, "+name);


                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(home.this, "You clicked on Uploads!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(home.this, upload.class);
                startActivity(intent);
            }
        });
        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(home.this, "You clicked on Profile!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(home.this, mainProfile.class);
                startActivity(intent);
            }
        });
        mPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(home.this, "You clicked on Posts!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(home.this, posts.class);
                startActivity(intent);
            }
        });
        mFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(home.this, "You clicked on Feedback!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(home.this, feedback.class);
                startActivity(intent);
            }
        });
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i=new Intent(home.this,MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

    }
}
