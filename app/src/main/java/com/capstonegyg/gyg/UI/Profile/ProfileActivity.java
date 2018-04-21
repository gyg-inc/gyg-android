package com.capstonegyg.gyg.UI.Profile;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.capstonegyg.gyg.R;
import com.capstonegyg.gyg.StartScreen;
import com.capstonegyg.gyg.UI.Authentication.AuthenticationActivity;
import com.capstonegyg.gyg.UI.ViewGyg.ViewGygActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
*   Change display name, change skillset, change payment info (send to Shawn), change picture, submit to commit changes
*/

// add option to delete gyg from user profile

public class ProfileActivity extends AppCompatActivity {

    private ProfileFirebaseAdapter pAdapter;
    private DatabaseReference pDatabaseReference;
    private Button myGygs;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private TextView userName;
    private FirebaseAuth mAuth;

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
        //FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
             String UID = user.getUid();

             // Name, email address, and profile photo Url
            //String name = user.getDisplayName();
            //Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.


            //userName = findViewById(R.id.profile_name);
            //userName.setText(name);

        }


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
