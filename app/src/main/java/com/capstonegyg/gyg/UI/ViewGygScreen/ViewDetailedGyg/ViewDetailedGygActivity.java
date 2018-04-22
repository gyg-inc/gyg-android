/*
 * Written by Jonathan Luetze
 *
 * Shows the details of a gyg. Corresponds to gyg_details_screen.xml
 */


package com.capstonegyg.gyg.UI.ViewGygScreen.ViewDetailedGyg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gyg_details_screen);

        fee = findViewById(R.id.detail_fee);
        name = findViewById(R.id.detail_name);
        cat = findViewById(R.id.detail_category);
        loc = findViewById(R.id.detail_location);
        desc = findViewById(R.id.detail_description);
        pname = findViewById(R.id.detail_jobposter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        setGygData(getIntent().getExtras().getString("GYG_KEY"));
    }

    public void setGygData(String gygKey) {
        DatabaseReference gygReference = firebaseDatabase.getReference().child("gygs").child(gygKey);

        gygReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PostGygData post = dataSnapshot.getValue(PostGygData.class);
                fee.setText(post.gygFee.toString());
                name.setText(post.gygName);
                cat.setText(post.gygCategory);
                loc.setText(post.gygLocation);
                desc.setText(post.gygDescription);
                pname.setText(post.gygName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ViewDetailedGygActivity.this, "Read Failed. Check Connection", Toast.LENGTH_SHORT);
            }
        });
    }
}
