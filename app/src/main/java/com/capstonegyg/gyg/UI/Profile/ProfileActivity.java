package com.capstonegyg.gyg.UI.Profile;

import java.util.*;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.TextView;

import com.capstonegyg.gyg.R;
import com.capstonegyg.gyg.UI.Authentication.AuthenticationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private String UID, email;

    private TextView displayName;
    private TextView emailDisplay;
    private TextView skill1;
    private TextView skill2;
    private TextView skill3;

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.profile_settings) {
            Intent i = new Intent(ProfileActivity.this, Settings.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.sign_out) {
            mAuth.signOut();
            Intent i = new Intent(ProfileActivity.this, AuthenticationActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user != null) {
            UID = user.getUid();
            email = user.getEmail();
            ref = FirebaseDatabase.getInstance().getReference("users").child(UID);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    show_data(dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    showToast("The read failed: " + databaseError.getCode());
                }
            });

            //Views
            displayName = findViewById(R.id.text_displayed_name);
            emailDisplay = findViewById(R.id.text_email_address);
            skill1 = findViewById(R.id.text_skill_1);
            skill2 = findViewById(R.id.text_skill_2);
            skill3 = findViewById(R.id.text_skill_3);
        }
        else
            {
            Intent p = new Intent(ProfileActivity.this, AuthenticationActivity.class);
            startActivity(p);
            }
    }

    public void show_data(DataSnapshot dataSnapshot) {
        displayName.setText(Objects.requireNonNull(dataSnapshot.child("display_name").getValue()).toString());
        if ((boolean) Objects.requireNonNull(dataSnapshot.child("show_email").getValue()))
            emailDisplay.setText(email);
        else
            emailDisplay.setText("[E-Mail not shared]");
        skill1.setText(Objects.requireNonNull(dataSnapshot.child("skills").child("skill0").getValue()).toString());
        skill2.setText(Objects.requireNonNull(dataSnapshot.child("skills").child("skill1").getValue()).toString());
        skill3.setText(Objects.requireNonNull(dataSnapshot.child("skills").child("skill2").getValue()).toString());

    }

    public void showToast(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
