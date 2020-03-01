package com.parveen.example.mainfarmerapp;




import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AlertDialog;
//import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    private EditText editTextMobile;
    private FirebaseAuth mAuth;
   private int STORAGE_PERMISSION_CODE=1,CAMERA_PERMISSION_CODE=1,INTERNET_PERMISSION_CODE=1,ExternalStorage_PERMISSION_CODE=1,LOCATION_PERMISSION_CODE=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
if(currentUser!=null){
    startActivity(new Intent(MainActivity.this, home.class));
        finish();
}




        editTextMobile = findViewById(R.id.editTextMobile);

        findViewById(R.id.Submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile = editTextMobile.getText().toString().trim();

                if (mobile.isEmpty() || mobile.length() < 10) {
                    editTextMobile.setError("Enter a valid mobile number.");
                    editTextMobile.requestFocus();
                    return;
                }

                //Intent main = new Intent(MainActivity.this, newUser.class);
                Intent intent = new Intent(MainActivity.this, otp.class);
                intent.putExtra("mobile", mobile);
                //main.putExtra("mobile", mobile);
//              System.out.println("hey ma " + mobile);
                startActivity(intent);

            }
        });

    }
//    protected void onStart(){
//        super.onStart();
//
//        if (ContextCompat.checkSelfPermission(MainActivity.this,
//                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//        } else
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
//        if (ContextCompat.checkSelfPermission(MainActivity.this,
//                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//        } else
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
//        if (ContextCompat.checkSelfPermission(MainActivity.this,
//                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//        } else
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, LOCATION_PERMISSION_CODE);
//        if (ContextCompat.checkSelfPermission(MainActivity.this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//        } else
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, ExternalStorage_PERMISSION_CODE);
//        if (ContextCompat.checkSelfPermission(MainActivity.this,
//                Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
//        } else
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, INTERNET_PERMISSION_CODE);
////
////        if (mAuth.getCurrentUser() != null) {
////
////            Intent i = new Intent(MainActivity.this, MainActivity.class);
////            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////            startActivity(i);
////        }
//    }

}
