package com.parveen.example.phoneauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        findViewById(R.id.button_mainProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (TextView)findViewById(R.id.Name_profile);
                setText(R.id.Name_mainProfile)=name;

            }
        });
    }
}
