package com.capstonegyg.gyg.UI.MyGygsScreen.GygHits;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.capstonegyg.gyg.R;
import com.capstonegyg.gyg.UI.PostGyg.PostGygData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Isuru Nanayakkara on 4/23/18.
 */

public class GygHitsActivity extends AppCompatActivity {

    private TextView interestedUser;
    private FirebaseDatabase firebaseDatabase;

    private Button acceptWorker, declineWorker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gyg_hit_layout);

        firebaseDatabase = FirebaseDatabase.getInstance();

        interestedUser = findViewById(R.id.userAccepted);
        acceptWorker = findViewById(R.id.accept_worker);

        setData(getIntent().getExtras().getString("GYG_KEY"));
    }

    void setData(String gygKey) {
        if(gygKey != null) {
            DatabaseReference ref = firebaseDatabase.getReference().child("gygs").child(gygKey);

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    PostGygData postGygData = dataSnapshot.getValue(PostGygData.class);
                    interestedUser.setText("User "+ postGygData.gygWorkerName + " is interested");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
