package com.capstonegyg.gyg.UI.MyGygsScreen.GygHits;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

/**
 * Created by Isuru Nanayakkara on 4/23/18.
 */

public class GygHitsActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView interestedUser;
    private FirebaseDatabase firebaseDatabase;

    private Button acceptWorker, declineWorker;
    private String gygKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gyg_hit_screen);

        firebaseDatabase = FirebaseDatabase.getInstance();

        interestedUser = findViewById(R.id.userAccepted);
        acceptWorker = findViewById(R.id.accept_worker);
        declineWorker = findViewById(R.id.decline_worker);

        acceptWorker.setOnClickListener(this);
        declineWorker.setOnClickListener(this);

        gygKey = getIntent().getExtras().getString("GYG_KEY");
        setData(gygKey);
    }

    void setData(String gygKey) {
        if(gygKey != null) {
            DatabaseReference ref = firebaseDatabase.getReference().child("gygs").child(gygKey);

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    PostGygData postGygData = dataSnapshot.getValue(PostGygData.class);

                    DatabaseReference userRef = firebaseDatabase.getReference()
                            .child("users")
                            .child(postGygData.gygWorkerName)
                            .child("display_name");

                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Object userRef = dataSnapshot.getValue();
                            if(userRef != null) {
                                interestedUser.setText(dataSnapshot.getValue().toString() + " is interested");
                            }
                            else
                                interestedUser.setText("No hits yet");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        DatabaseReference ref = firebaseDatabase.getReference().child("gygs").child(gygKey);

        switch (v.getId()) {
            case R.id.decline_worker:
                ref.child("gygWorkerName").setValue("");
                Toast.makeText(getApplicationContext(), "User declined", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.accept_gyg_button:
                //Do notification
                break;
        }
    }
}
