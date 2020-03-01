package com.parveen.example.mainfarmerapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import praveen.postDetails.postDetails;
import praveen.rv.rvAdapter;

import static android.widget.Toast.LENGTH_SHORT;

public class posts extends AppCompatActivity {
    DatabaseReference db,db_delete;
    rvAdapter rvAdapter;
    private List<postDetails> postDetails = new ArrayList<>();
    String id,id_delete;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        Intent intent_delete = getIntent();
        id_delete = intent_delete.getStringExtra("id_delete");

        if(id_delete != null) {

            db_delete = FirebaseDatabase.getInstance().getReference("Uploads").child(id_delete);
            db_delete.removeValue();
            Toast.makeText(this, "Item deleted.", LENGTH_SHORT).show();
        }


        imageView = (ImageView) findViewById(R.id.img);
        db = FirebaseDatabase.getInstance().getReference("Uploads");
        final RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        id = currentUser.getUid();
        rv.setHasFixedSize(true);
        rv.setItemViewCacheSize(150);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rvAdapter = new rvAdapter(posts.this, postDetails);
        rv.setAdapter(rvAdapter);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postDetails.clear();
               //else {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //if(!snapshot.getKey().equals("Location")) {

                        String uid = snapshot.child("farmerId").getValue(String.class);
                        if (uid.equals(id)) {

                            String s = snapshot.child("quantity").getValue(String.class);
                            String str = snapshot.child("price").getValue(String.class);
                            String img = snapshot.child("imgurl").getValue(String.class);
                            String id = snapshot.child("id").getValue(String.class);
                            String title = snapshot.child("title").getValue(String.class);
                            postDetails postDetails1 = new postDetails(s, str, img, title, id);
                            postDetails.add(postDetails1);


                        }

                   }
                if (postDetails.isEmpty()) {

                    Toast.makeText(posts.this, "NO posts available", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(posts.this, home.class);
                    startActivity(intent);

                }

                rvAdapter.notifyDataSetChanged();
                }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
}
