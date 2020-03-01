package com.parveen.example.mainfarmerapp;



import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.provider.Settings;
import android.view.View;
import android.content.*;
import android.widget.*;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

import java.io.IOException;
import java.util.*;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import praveen.posts.uploadPosts;
//import praveen.posts.uploadPosts;

public class upload extends AppCompatActivity {
    private Button upload;
    private EditText price, quantity;
    private String quantityDetails,priceDetails,ImgUrl1,title_edit,id_edit,id_delete;
    private String Price, Quantity, ImgUrl, title , id, farmerid;
    private List<String> list = new ArrayList<String>();
    private List<String> IdList = new ArrayList<String>();
    private List<String> imglist = new ArrayList<String>();
    private DatabaseReference db, dataBaseUpload,db_edit,db_delete;
    //private Context context;
    private AutoCompleteTextView acTextView;
    private ImageView imageView;
    ArrayAdapter<String> adapter;
    int pos;
    int pos1;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        Intent intent = getIntent();
        id_edit = intent.getStringExtra("id");
        if (ContextCompat.checkSelfPermission(upload.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            acessLocation();
        } else {
            Dexter.withActivity(upload.this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(new PermissionListener() {
                        @Override

                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            acessLocation();
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            if (response.isPermanentlyDenied()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(upload.this);
                                builder.setTitle("Permission Denied")
                                        .setMessage("permission to access device location is permanently denied you need to go to setting to allow the permissions")
                                        .setNegativeButton("cancel", null)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent();
                                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                intent.setData(Uri.fromParts("package", getPackageName(), null));

                                            }
                                        })
                                        .show();
                            } else {
//                                Snackbar snackbar = Snackbar.make(linearLayout, "You are Offline", Snackbar.LENGTH_SHORT).setAction("Action", null);
//                                View sbView = snackbar.getView();
//                                sbView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//                                snackbar.show();
                                Toast.makeText(getApplicationContext(), "unable to get Data", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();
        }


        adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, list);
        acTextView = (AutoCompleteTextView) findViewById(R.id.search_bar);
        acTextView.setThreshold(1);
        upload = (Button) findViewById(R.id.button);
        price = (EditText) findViewById(R.id.price);
        quantity = (EditText) findViewById(R.id.quantity);
        imageView = (ImageView) findViewById(R.id.img);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(upload.this);
        db = FirebaseDatabase.getInstance().getReference("Items");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String s = snapshot.child("name").getValue(String.class);
                    String str = snapshot.child("imgurl").getValue(String.class);
                    list.add("" + s);
                    imglist.add("" + str);
                    acTextView.setAdapter(adapter);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        if (id_edit != null) {
            Intent extraIntent = getIntent();
            quantityDetails = extraIntent.getStringExtra("quantity");
            priceDetails = extraIntent.getStringExtra("price");
            title_edit = extraIntent.getStringExtra("title");

            price.setText(priceDetails);
            quantity.setText(quantityDetails);

            acTextView.setText(title_edit);
            //acTextView.setEditable(false);
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String s = snapshot.child("name").getValue().toString();
                        if(s.equals(title_edit)) {
                            ImgUrl1 = snapshot.child("imgurl").getValue().toString();
                            Picasso.get().load(ImgUrl1).fit().into(imageView);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });


        }




        //"items" is the name of table i.e categories where each child have two subchilds name and imgurl
        dataBaseUpload = FirebaseDatabase.getInstance().getReference("Uploads");


        //pos1 = IdList.indexOf(id_edit);
        acTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                title = parent.getItemAtPosition(position).toString();
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String s = snapshot.child("name").getValue().toString();
                            if(s.equals(title)) {
                                ImgUrl = snapshot.child("imgurl").getValue().toString();
                                Picasso.get().load(ImgUrl).fit().into(imageView);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Price = price.getText().toString().trim();
                Quantity = quantity.getText().toString().trim();
                if (Price.isEmpty() || Quantity.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter Valid Data", Toast.LENGTH_LONG).show();
                } else {
                    if(mLastKnownLocation!=null){
                        uploadPosts();
                        //uploadLocation(id);
                    }else{
                        acessLocation();
                    }

                }
            }
        });
    }
    public void uploadPosts() {
        farmerid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (id_edit != null) {


            // uploadPosts posts = new uploadPosts(id_edit, title_edit, Quantity, Price, farmerid, ImgUrl1);
            if(title == null) {
                title = title_edit;
            }
            else
            {
                ImgUrl1 = ImgUrl;
            }
            dataBaseUpload.child(id_edit).child("title").setValue(title);
            dataBaseUpload.child(id_edit).child("imgurl").setValue(ImgUrl1);
            dataBaseUpload.child(id_edit).child("quantity").setValue(Quantity);
            dataBaseUpload.child(id_edit).child("price").setValue(Price);
            // dataBaseUpload.child(id_edit).child("title").setValue(title_edit);

            //uploadLocation(id_edit);
            startActivity(new Intent(upload.this,home.class));
            finish();





            Toast.makeText(this, "Data Edited.", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(upload.this, home.class);
            startActivity(i);
            finish();
        } else {
            id = dataBaseUpload.push().getKey();
            uploadPosts posts = new uploadPosts(id, title, Quantity, Price, farmerid, ImgUrl);
            dataBaseUpload.child(id).setValue(posts);
            uploadLocation(id);


            Toast.makeText(this, "Data Added.", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(upload.this, home.class);
            startActivity(i);
            finish();
        }
    }
    private void acessLocation() {
        if (ActivityCompat.checkSelfPermission(upload.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //check if gps is enabled or not and then request user to enable it
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(upload.this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(upload.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                getDeviceLocation();
            }
        });

        task.addOnFailureListener(upload.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(upload.this, 51);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 51) {
            if (resultCode == RESULT_OK) {
                getDeviceLocation();
            }
        }
    }
    private void getDeviceLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();
                                /*Geocoder geocoder;
                                List<Address> addresses;
                                geocoder = new Geocoder(MapActivity.this, Locale.getDefault());
                                try {
                                    addresses = geocoder.getFromLocation(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude(), 1);
                                    System.out.println(" --------------------       "+addresses);
                                    String add = " "+addresses;
                                    String[] strings = add.split("\\[");
                                    for(String a: strings){
                                        if(a.startsWith("0:")){
                                            System.out.println("======"+a);
                                            String[] res = a.split("]");
                                            addressfinal = res[0].split(":");
                                        }
                                    }
                                    tv.setText( ""+addressfinal[1]);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }*/


                        } else {
                            Toast.makeText(upload.this, "unable to get last location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void uploadLocation(String iid){
        if(mLastKnownLocation!=null){
            GeoFire geoFire = new GeoFire(dataBaseUpload.child(iid));
            geoFire.setLocation("location", new GeoLocation(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), new
                    GeoFire.CompletionListener() {
                        @Override
                        public void onComplete(String key, DatabaseError error) {
                            Toast.makeText(upload.this, "Location Updated", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            acessLocation();
        }
    }
}

