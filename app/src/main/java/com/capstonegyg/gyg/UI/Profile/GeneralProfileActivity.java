package com.capstonegyg.gyg.UI.Profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.capstonegyg.gyg.R;
import com.capstonegyg.gyg.UI.Authentication.AuthenticationActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class GeneralProfileActivity extends AppCompatActivity {

    private String UID, email;

    private TextView displayName;
    private TextView emailDisplay;
    private TextView displayedSkills;
    private TextView skill1;
    private TextView skill2;
    private TextView skill3;
    private CircleImageView profilePic;
    private ImageView bannerPic;

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_profile_settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.report_user) {
            yesNoPopUp("Report User", "Report this user for misconduct?");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_profile_screen);
//        mAuth = FirebaseAuth.getInstance();
//        // Needs to get whatever user is being viewed here instead.
//        user = mAuth.getCurrentUser();

        //Get UID that is passed in.
        UID = getIntent().getExtras().getString("POSTER_UID");
        //Get ref to child
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
        displayedSkills = findViewById(R.id.text_displayed_skills);
        skill1 = findViewById(R.id.text_skill_1);
        skill2 = findViewById(R.id.text_skill_2);
        skill3 = findViewById(R.id.text_skill_3);
        profilePic = findViewById(R.id.profile_image);
        bannerPic = findViewById(R.id.image_banner);

        //Buttons
        //None yet I guess.
    }

    public void show_data(DataSnapshot dataSnapshot) {
        displayName.setText(Objects.requireNonNull(dataSnapshot.child("display_name").getValue()).toString());
        if ((boolean) Objects.requireNonNull(dataSnapshot.child("show_email").getValue()))
            emailDisplay.setText(email);
        else
            emailDisplay.setText("[E-Mail not shared]");
        String skStr1 = Objects.requireNonNull(dataSnapshot.child("skills").child("skill0").getValue()).toString();
        String skStr2 = Objects.requireNonNull(dataSnapshot.child("skills").child("skill1").getValue()).toString();
        String skStr3 = Objects.requireNonNull(dataSnapshot.child("skills").child("skill2").getValue()).toString();
        if (skStr1.length() == 0 && skStr2.length() == 0 && skStr3.length() == 0) {
            displayedSkills.setText("");
            skill1.setText("");
            skill2.setText("[No Displayed Skills]");
            skill3.setText("");
        }
        else
        {
            displayedSkills.setText("Displayed Skills");
            skill1.setText(skStr1);
            skill2.setText(skStr2);
            skill3.setText(skStr3);
        }
        Glide.with(this).load(Objects.requireNonNull(dataSnapshot.child("pic_ref").getValue())).override(155, 155).into(profilePic);
        Glide.with(this).load(Objects.requireNonNull(dataSnapshot.child("banner_ref").getValue())).centerCrop().into(bannerPic);
    }


      void yesNoPopUp(String title, String message) {
            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(GeneralProfileActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            builder.setTitle(title);
            builder.setMessage(message);

            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    showToast("Coming Soon! (Eventually)");
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.create();
            builder.show();
        }


    public void showToast(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
