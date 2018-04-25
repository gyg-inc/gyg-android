package com.capstonegyg.gyg.UI.MyGygsScreen.GygHits;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.capstonegyg.gyg.R;
import com.capstonegyg.gyg.UI.PostGyg.PostGygData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Isuru Nanayakkara on 4/23/18.
 */

public class GygHitsActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView interestedUser;
    private FirebaseDatabase firebaseDatabase;
    private CircleImageView hitProfileImage;

    private Button acceptWorker, declineWorker;
    private String gygKey;

    private TextView skillsLabel, skill1, skill2, skill3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gyg_hit_screen);

        //Get Firebase ref
        firebaseDatabase = FirebaseDatabase.getInstance();

        //TextViews
        interestedUser = findViewById(R.id.userAccepted);
        skillsLabel = findViewById(R.id.skills_label);
        skill1 = findViewById(R.id.skill1);
        skill2 = findViewById(R.id.skill2);
        skill3 = findViewById(R.id.skill3);

        //Interested user profile picture
        hitProfileImage = findViewById(R.id.hit_profile_image);

        //Buttons
        acceptWorker = findViewById(R.id.accept_worker);
        declineWorker = findViewById(R.id.decline_worker);

        //Listen to buttons
        acceptWorker.setOnClickListener(this);
        declineWorker.setOnClickListener(this);

        //Grab and store the GygKey for future use
        gygKey = getIntent().getExtras().getString("GYG_KEY");
        //Load data
        setData(gygKey);
    }

    void setData(String gygKey) {
        if(gygKey != null) {
            final DatabaseReference gygRef = firebaseDatabase.getReference().child("gygs").child(gygKey);
            final DatabaseReference userRef = firebaseDatabase.getReference().child("users");

            gygRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    PostGygData postGygData = dataSnapshot.getValue(PostGygData.class);

                    if(postGygData != null && !postGygData.gygWorkerName.equals("")) {
                        DatabaseReference workerRef = userRef.child(postGygData.gygWorkerName);

                        workerRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                //First make everything Visible
                                hitProfileImage.setVisibility(View.VISIBLE);
                                skillsLabel.setVisibility(View.VISIBLE);
                                acceptWorker.setVisibility(View.VISIBLE);
                                declineWorker.setVisibility(View.VISIBLE);
                                skill1.setVisibility(View.VISIBLE);
                                skill2.setVisibility(View.VISIBLE);
                                skill3.setVisibility(View.VISIBLE);

                                //Set profile pic
                                Glide.with(hitProfileImage.getContext())
                                        .load(Objects.requireNonNull(dataSnapshot.child("pic_ref")
                                                .getValue())).override(155, 155)
                                        .into(hitProfileImage);

                                //Set worker name
                                interestedUser.setText(dataSnapshot.child("display_name").getValue().toString() + " is interested");
                                //Set skill 1
                                skill1.setText(dataSnapshot.child("skills").child("skill0").getValue().toString());
                                //Set skill 2
                                skill2.setText(dataSnapshot.child("skills").child("skill1").getValue().toString());
                                //Set skill 3
                                skill3.setText(dataSnapshot.child("skills").child("skill2").getValue().toString());

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    else {
                        //Make everything dissappear
                        hitProfileImage.setVisibility(View.GONE);
                        skillsLabel.setVisibility(View.GONE);
                        acceptWorker.setVisibility(View.GONE);
                        declineWorker.setVisibility(View.GONE);

                        //Set worker name
                        interestedUser.setText("No Hits Yet");
                    }

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
                ref.child("gygAcceptedDate").setValue("1");
                Toast.makeText(getApplicationContext(), "User accepted", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}
