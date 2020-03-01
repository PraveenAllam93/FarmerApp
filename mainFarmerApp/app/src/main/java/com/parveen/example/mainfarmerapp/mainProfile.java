package com.parveen.example.mainfarmerapp;





import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class mainProfile extends AppCompatActivity {

    TextView AadharNo,Name,Address,Mobile,Logout;
    ImageView Image;
    public String id="";
    DatabaseReference dataBaseProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile);

        dataBaseProfile = FirebaseDatabase.getInstance().getReference("Farmer").child("Profile");
        AadharNo = findViewById(R.id.AadharText);
        Name = findViewById(R.id.nameText);
        Address = findViewById(R.id.addressText);
        Logout = findViewById(R.id.actionLogout);
        Mobile = findViewById(R.id.mobileText);
        Image = findViewById(R.id.iv_mainProfile);
        //Intent i = getIntent();
        //id = i.getStringExtra("id");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Logout.setPaintFlags(Logout.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        id = currentUser.getUid();
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        dataBaseProfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    String d = snapshot.child("id").getValue(String.class);
                    if(id.equals(d))
                    {
                        String aadhar = snapshot.child("aadhar").getValue(String.class);
                        String address = snapshot.child("address").getValue(String.class);
                        String mobile = snapshot.child("mobile").getValue(String.class);
                        String name = snapshot.child("name").getValue(String.class);
                        StorageReference storageRef = storage.getReferenceFromUrl("gs://marsmahesh-158f1.appspot.com/farmerAadhar/" + mobile + ".jpg");
                        //StorageReference imagesRef = storageRef.child( "farmerAadhar/" + mobile + ".jpg");
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).fit().into(Image);
                            }
                        });
                        Address.setText(address);
                        Mobile.setText(mobile);
                        AadharNo.setText(aadhar);
                        Name.setText(name);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i=new Intent(mainProfile.this,MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        findViewById(R.id.button_mainProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainProfile.this, editProfile.class);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();

            }
        });
    }
}
