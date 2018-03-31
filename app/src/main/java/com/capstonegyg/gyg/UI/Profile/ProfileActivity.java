package com.capstonegyg.gyg.UI.Profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.capstonegyg.gyg.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileActivity extends AppCompatActivity {

    private ProfileFirebaseAdapter pAdapter;
    private DatabaseReference pDatabaseReference;

    //FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference myRef = database.getReference().child("user_profile");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);

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
