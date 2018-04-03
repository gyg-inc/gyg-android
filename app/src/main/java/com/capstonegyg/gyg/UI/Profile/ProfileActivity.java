package com.capstonegyg.gyg.UI.Profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.capstonegyg.gyg.R;
import com.capstonegyg.gyg.StartScreen;
import com.capstonegyg.gyg.UI.ViewGyg.ViewGygActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


// add option to delete gyg from user profile

public class ProfileActivity extends AppCompatActivity {

    private ProfileFirebaseAdapter pAdapter;
    private DatabaseReference pDatabaseReference;
    private Button myGygs;

    //FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference myRef = database.getReference().child("user_profile");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);
        myGygs = findViewById(R.id.profiles_gygs);

        myGygs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, ViewGygActivity.class);
                startActivity(i);
            }
        });

        //Get the reference to the whole database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //Find the specific Firebase node.
        pDatabaseReference = firebaseDatabase.getReference().child("user_profile");

        /*
            @arg1 - The "schema" file that defines data.
            @arg2 - The individual layout that is populated.
            @arg3 - The class that injects data into the gyg_list_layout
            @arg4 - The database reference. Holds actual data.
         */
        pAdapter = new ProfileFirebaseAdapter(ProfileData.class, R.layout.gyg_list_layout, ProfileViewHolder.class, pDatabaseReference);

    }


}
