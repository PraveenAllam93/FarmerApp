package com.parveen.example.mainfarmerapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        String toS="mars79859@gmail.com";
        String subS="Farmer's issue";

        Intent email=new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL,toS);
        email.putExtra(Intent.EXTRA_EMAIL,new String[]{toS});
        email.putExtra(Intent.EXTRA_SUBJECT,subS);


        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email,"choose app to send mail"));
        finish();
    }
}
