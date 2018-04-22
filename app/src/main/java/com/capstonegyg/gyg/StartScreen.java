package com.capstonegyg.gyg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.capstonegyg.gyg.UI.Authentication.AuthenticationActivity;
import com.capstonegyg.gyg.UI.MyGygs.MyGygsActivity;
import com.capstonegyg.gyg.UI.PostGyg.PostGygActivity;
import com.capstonegyg.gyg.UI.PostGyg.newLocation;
import com.capstonegyg.gyg.UI.Profile.GeneralProfileActivity;
import com.capstonegyg.gyg.UI.Profile.ProfileActivity;
import com.capstonegyg.gyg.UI.ViewGygScreen.ViewGyg.ViewGygActivity;
import com.google.firebase.auth.FirebaseAuth;

public class StartScreen extends AppCompatActivity {

    private Button viewGygs;
    private Button postGygs;
    private Button myGygs;
    private Button profile;
    private Button emergencyExit;
    private Button generalProfile;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        mAuth = FirebaseAuth.getInstance();

        viewGygs = findViewById(R.id.view_gyg_button);
        postGygs = findViewById(R.id.post_gyg_button);
        myGygs = findViewById(R.id.mygygs_button);
        profile = findViewById(R.id.profile_button);
        emergencyExit = findViewById(R.id.emergency_exit);
        generalProfile = findViewById(R.id.external_profile);


        newLocation l = new newLocation(this);
        l.getLocation();


        viewGygs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartScreen.this, ViewGygActivity.class);
                startActivity(i);
            }
        });


        postGygs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent p = new Intent(StartScreen.this, PostGygActivity.class);
                startActivity(p);
            }
        });

        myGygs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent p = new Intent(StartScreen.this, MyGygsActivity.class);
                startActivity(p);
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent p = new Intent(StartScreen.this, ProfileActivity.class);
                startActivity(p);
            }
        });

        emergencyExit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mAuth.signOut();
                Intent p = new Intent(StartScreen.this, AuthenticationActivity.class);
                startActivity(p);
            }
        });

        generalProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent p = new Intent(StartScreen.this, GeneralProfileActivity.class);
                startActivity(p);
            }
        });
    }
}
