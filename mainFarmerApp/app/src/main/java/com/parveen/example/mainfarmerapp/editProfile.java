package com.parveen.example.mainfarmerapp;






import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
//import android.widget.ImageButton;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

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


import praveen.profile.pro;

public class editProfile extends AppCompatActivity {

    EditText AadharNo,Name,Address,Mobile;
    //ImageButton image = findViewById(R.id.imageButton);
    DatabaseReference  dataBaseProfile;
    int flag =0;
    ImageView Image_editProfile;
    String id="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        dataBaseProfile = FirebaseDatabase.getInstance().getReference("Farmer").child("Profile");
        AadharNo = findViewById(R.id.AadharNo_Profile);
        Name = findViewById(R.id.Name_profile);
        Address = findViewById(R.id.address_profile);
       Mobile = findViewById(R.id.mobileNumber);
       Image_editProfile = findViewById(R.id.iv_profile);
         FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        id = currentUser.getUid();
        final FirebaseStorage storage = FirebaseStorage.getInstance();

        dataBaseProfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if(id.equals(snapshot.child("id").getValue().toString()))
                    {
                        String aadhar = snapshot.child("aadhar").getValue().toString();
                        String address = snapshot.child("address").getValue().toString();
                        String mobile = snapshot.child("mobile").getValue().toString();
                        String name = snapshot.child("name").getValue().toString();
                        //String imgurl_profile = snapshot.child("image").getValue().toString();
                        Address.setText(address);
                        Mobile.setText(mobile);
                        AadharNo.setText(aadhar);
                        Name.setText(name);
                        StorageReference storageRef = storage.getReferenceFromUrl("gs://marsmahesh-158f1.appspot.com/farmerAadhar/" + mobile + ".jpg");

                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).fit().into(Image_editProfile);
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        findViewById(R.id.button_profile).setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        }));


//        findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent image = new Intent(Intent.ACTION_GET_CONTENT);
//                image.setType("image/*");
//                startActivityForResult(Intent.createChooser(image,"pick an image"),1);
//            }
//        });

    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(resultCode == RESULT_OK && requestCode == 1){
//            ImageView imageView=findViewById(R.id.iv_profile);
//            try {
//                InputStream inputStream = getContentResolver().openInputStream(data.getData());
//
//                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
//
//                imageView.setImageBitmap(bitmap);
//
//            }catch (FileNotFoundException e){
//                e.printStackTrace();
//            }
//        }
//        else
//        {
//            ImageView editImage=findViewById(R.id.iv_profile);
//            Bitmap bitmap= (Bitmap)data.getExtras().get("data");
//            editImage.setImageBitmap(bitmap);
//
//
//        }
//
//    }

    public void addUser(){
        String mainAddress = Address.getText().toString().trim();
        String Aadhar = AadharNo.getText().toString().trim();
        String EditName = Name.getText().toString().trim();
        String mobileNo = Mobile.getText().toString().trim();
        if (EditName.isEmpty() || Aadhar.isEmpty() || Aadhar.length() != 12 || mainAddress.isEmpty()) {
            Toast.makeText(this,"Enter valid data.",Toast.LENGTH_LONG).show();
        } else {
            flag = 1;

            Intent i = getIntent();
            String id = i.getStringExtra("id");
            //String id = dataBaseProfile.push().getKey();
            dataBaseProfile = FirebaseDatabase.getInstance().getReference("Farmer").child("Profile");

            dataBaseProfile.child(id).child("aadhar").setValue(Aadhar);
            dataBaseProfile.child(id).child("address").setValue(mainAddress);
            dataBaseProfile.child(id).child("mobile").setValue(mobileNo);
            dataBaseProfile.child(id).child("name").setValue(EditName);
            Toast.makeText(this,"Data Edited.",Toast.LENGTH_LONG).show();
            
            //pro profile = new pro(id,EditName,mainAddress,Aadhar,mobileNo,);

            //dataBaseProfile.child(id).setValue(profile);


            Intent intent = new Intent(editProfile.this, home.class);
            startActivity(intent);
            finish();

        }






    }



}

