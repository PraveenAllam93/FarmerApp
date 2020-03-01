package com.parveen.example.mainfarmerapp;






import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import praveen.profile.pro;

public class newUser extends AppCompatActivity {

    private EditText AadharNo, Name, Address;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_CAMERA = 0;
    private int a, b;
    public ImageView mImageView;
    Bitmap map;
    private String Status = "";
    Uri mImageUri;
    public StorageTask mUploadTask;
    DatabaseReference dataBaseProfile, mDatabaseRef;
    public String Mobile1, id, imgurl;
    public Button Done;
    private AlertDialog alertDialog;
    public ProgressDialog progressDialog;
    public int flag = 0;
    private StorageReference mAadhar;
    ImageView Image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        Mobile1 = getIntent().getStringExtra("mobile");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        //mAadhar = storageRef.child( "farmerAadhar/"+System.currentTimeMillis() + ".jpg");
        mImageView = findViewById(R.id.iv_newProfile);
        AadharNo = findViewById(R.id.AadharNo_newProfile);
        Name = findViewById(R.id.Name_newProfile);
        Address = findViewById(R.id.address_newProfile);
        Done = findViewById(R.id.button_newProfile);
        Image = findViewById(R.id.iv_newProfile);

        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();;
            }
        });


        dataBaseProfile = FirebaseDatabase.getInstance().getReference("Farmer").child("Profile");
        Done.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(newUser.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                }
                else
                    uploadFile();
                //addUser();
            }
        }));


    }

    public void addUser() {

        if(validate()) {
            String mainAddress = Address.getText().toString().trim();
            String Aadhar = AadharNo.getText().toString().trim();
            String EditName = Name.getText().toString().trim();

//            FirebaseAuth mAuth = FirebaseAuth.getInstance();
//            FirebaseUser currentUser = mAuth.getCurrentUser();
            id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            pro profile = new pro(id, EditName, Mobile1,mainAddress,Aadhar);
            dataBaseProfile.child(id).setValue(profile);
            flag = 1;
            if (flag == 1) {
                Intent intent = new Intent(newUser.this, home.class);
                startActivity(intent);
                finish();

            }

        }
    }

    private void uploadFile() {
        if (a == 1) {
            uploadImage(map);
        } else if (b == 2) {
            UploadImageGallery();
        } else {
            Toast.makeText(newUser.this, "Select any one of the method.", Toast.LENGTH_SHORT).show();
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST) {
                mImageUri = data.getData();
                Picasso.get().load(mImageUri).fit().centerCrop().into(Image);
            } else if (requestCode == REQUEST_CAMERA) {
                Bundle bundle = data.getExtras();
                map = (Bitmap) bundle.get("data");
                mImageView.setImageBitmap(map);
            }
        }
    }
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(newUser.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    a=1;
                    camera();
                } else if (items[item].equals("Choose from Library")) {
                    openFileChooser();
                    b=2;
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void camera() {
        Intent intt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intt,REQUEST_CAMERA);
    }
    public void uploadImage(Bitmap bitmap) {
        if(bitmap!=null) {
            if (validate()) {
//                progressDialog.setMessage("Uploading Your Photo....");
 //               progressDialog.show();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReferenceFromUrl("gs://marsmahesh-158f1.appspot.com/farmerAadhar/" + Mobile1 + ".jpg");
                final UploadTask uploadTask = storageRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
              //          progressDialog.dismiss();
                        Toast.makeText(newUser.this, "Connection Failed!", Toast.LENGTH_SHORT).show();

                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //imgurl = taskSnapshot.getStorage().getDownloadUrl().toString();
                        addUser();;
                    }
                });
            } else {
                Toast.makeText(newUser.this, "Enter All the details", Toast.LENGTH_LONG).show();
                //progressDialog.dismiss();
            }

        }
    }
    public void UploadImageGallery(){

        if(validate()) {
            if (mImageUri != null) {
              //  progressDialog.setMessage("Uploading Your Book....");
              //  progressDialog.show();
//                StorageReference fileReference = mAadhar.child( "farmerAadhar/"+ System.currentTimeMillis()
//                       + "." + getFileExtension(mImageUri));
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReferenceFromUrl("gs://marsmahesh-158f1.appspot.com/farmerAadhar/" + Mobile1 + ".jpg");
                mUploadTask = storageRef.putFile(mImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Toast.makeText(newUser.this, "Upload successful", Toast.LENGTH_LONG).show();
//                                Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();
//                                imgurl = downloadUri.getResult().toString();
                                addUser();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(newUser.this, "Connection Failed!", Toast.LENGTH_SHORT).show();
                               // progressDialog.dismiss();-
                            }
                        });
            } else {
                Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Enter all the details", Toast.LENGTH_SHORT).show();

        }
    }

    private boolean validate() {
        boolean result = true;
        String mainAddress = Address.getText().toString().trim();
        String Aadhar = AadharNo.getText().toString().trim();
        String EditName = Name.getText().toString().trim();

        if(EditName.isEmpty() || Aadhar.isEmpty() || Aadhar.length() != 12 || mainAddress.isEmpty()) {
            Toast.makeText(this, "Enter valid data.", Toast.LENGTH_LONG).show();
            result = false;
        }else{
            result =true;
        }
        return result;

    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }



}