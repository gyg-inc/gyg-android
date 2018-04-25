/*
 * Written by Jonathan Luetze
 *
 * Shows the details of a gyg. Corresponds to gyg_details_screen.xml
 */


package com.capstonegyg.gyg.UI.ViewGygScreen.ViewDetailedGyg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.capstonegyg.gyg.R;
import com.capstonegyg.gyg.UI.NotificationsScreen.NotificationsData;
import com.capstonegyg.gyg.UI.PostGyg.PostGygData;
import com.capstonegyg.gyg.UI.Profile.GeneralProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ViewDetailedGygActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView fee;
    private TextView name;
    private TextView cat;
    private TextView loc;
    private TextView desc;
    private TextView pname;
    private TextView time;

    private Button seeProfileButton;
    private Button acceptGygButton;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;

    private String gygKey;
    private StringBuilder posterName;
    private StringBuilder posterUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_gyg_details_screen);
        posterName = new StringBuilder();
        posterUid = new StringBuilder();

        //Text views
        fee = findViewById(R.id.detail_fee);
        name = findViewById(R.id.detail_name);
        cat = findViewById(R.id.detail_category);
        loc = findViewById(R.id.detail_location);
        desc = findViewById(R.id.description_data);
        pname = findViewById(R.id.detail_jobposter);

        //Buttons
        seeProfileButton = findViewById(R.id.see_profile_button);
        acceptGygButton = findViewById(R.id.accept_gyg_button);

        //Listen to the button events
        seeProfileButton.setOnClickListener(this);
        acceptGygButton.setOnClickListener(this);


        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        gygKey = getIntent().getExtras().getString("GYG_KEY");
        setGygData(gygKey);
    }

    public void setGygData(String gygKey) {
        DatabaseReference gygReference = firebaseDatabase.getReference().child("gygs").child(gygKey);

        gygReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PostGygData post = dataSnapshot.getValue(PostGygData.class);
                fee.setText("Pay: $" + post.gygFee.toString() + "/" + post.gygTime);
                name.setText(post.gygName);
                cat.setText("Category: " + post.gygCategory);
                loc.setText("Location: " + post.gygLocation);
                desc.setText(post.gygDescription);
                posterUid.append(post.gygPosterName);

                //Set user name
                DatabaseReference posterNameReference = firebaseDatabase.getReference().child("users").child(post.gygPosterName);
                //At child node get user name
                posterNameReference.child("display_name").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Set poster name
                        String name = (String) dataSnapshot.getValue();
                        //Store name for later
                        posterName.append(name);
                        pname.setText("Posted by: " + name);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //Errors
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(ViewDetailedGygActivity.this, "Read Failed. Check Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        //Set user name
        switch (v.getId()) {
            case R.id.see_profile_button :
                Intent i = new Intent(ViewDetailedGygActivity.this, GeneralProfileActivity.class);
                i.putExtra("POSTER_UID", posterUid.toString());
                startActivity(i);
                break;
            case R.id.accept_gyg_button:
                //acceptGyg();
                //acceptGyg2();
                acceptGyg3();
                break;
        }
    }

    public void acceptGyg() {
        //Get authUser
        FirebaseUser thisUser = firebaseAuth.getCurrentUser();
        //Added for future use
        DatabaseReference hitsRef =
                firebaseDatabase.getReference()
                .child("notifications")       //In notifications node
                .child(posterUid.toString())  //For particular user
                .child(gygKey)                //For particular Gyg
                .child("hits");               //Hits array

        //User signed in
        if(thisUser != null) {

            hitsRef
                    .push()
                    .setValue(thisUser.getUid()); //Set this user as interested

            Toast.makeText(getApplicationContext(), "Request Sent", Toast.LENGTH_SHORT).show();
            finish();
        }

        //User not valid
        else {
            Toast.makeText(getApplicationContext(), "Request Failed. Check that you are signed in", Toast.LENGTH_SHORT).show();
        }
    }

    public void acceptGyg2() {
        DatabaseReference notificationsRef = firebaseDatabase.getReference().child("notifications2").child(posterUid.toString());
        FirebaseUser thisUser = firebaseAuth.getCurrentUser();

        if(thisUser != null) {
            NotificationsData notificationsData = new NotificationsData(gygKey, thisUser.getUid());
            notificationsRef.push().setValue(notificationsData);
        }
    }

    public void acceptGyg3() {
        FirebaseUser thisUser = firebaseAuth.getCurrentUser();
        DatabaseReference gygRef = firebaseDatabase.getReference().child("gygs").child(gygKey).child("gygWorkerName");
        if(thisUser != null)
            gygRef.setValue(thisUser.getUid());
        Toast.makeText(getApplicationContext(), "Request Sent", Toast.LENGTH_SHORT).show();
        finish();
    }
}
