package com.parveen.example.phoneauth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class edit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        findViewById(R.id.button_mainProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_mainProfile =new Intent(mainProfile.this,edit.class);
                startActivity(intent_mainProfile);
            }
        });
        findViewById(R.id.btn_editProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                

                Intent intent_editProfile =new Intent(edit.this,mainProfile.class);
                startActivity(intent_editProfile);
            }
        });
    }
}
