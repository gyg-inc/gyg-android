/*
 * Written by Jonathan Luetze
 *
 * Shows the details of a gyg. Corresponds to gyg_details_screen.xml
 */


package com.capstonegyg.gyg.UI.ViewGygScreen.ViewDetailedGyg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.capstonegyg.gyg.R;
import com.capstonegyg.gyg.UI.PostGyg.PostGygData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewDetailedGygActivity extends AppCompatActivity {
    private TextView fee;
    private TextView name;
    private TextView cat;
    private TextView loc;
    private TextView desc;
    private TextView pname;
    private TextView time;

    private Button seeProfileButton;

    private FirebaseDatabase firebaseDatabase;

    private String gygKey;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gyg_details_screen);

        fee = findViewById(R.id.detail_fee);
        name = findViewById(R.id.detail_name);
        cat = findViewById(R.id.detail_category);
        loc = findViewById(R.id.detail_location);
        desc = findViewById(R.id.description_data);
        pname = findViewById(R.id.detail_jobposter);

        seeProfileButton = findViewById(R.id.see_profile_button);

        seeProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewDetailedGygActivity.this, "Redirect To Profile. Coming Soon.", Toast.LENGTH_SHORT).show();
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
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

                //Set user name
                DatabaseReference posterNameReference = firebaseDatabase.getReference().child("users").child(post.gygPosterName);
                //At child node get user name
                posterNameReference.child("display_name").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Set poster name
                        String name = (String) dataSnapshot.getValue();
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
                Toast.makeText(ViewDetailedGygActivity.this, "Read Failed. Check Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
