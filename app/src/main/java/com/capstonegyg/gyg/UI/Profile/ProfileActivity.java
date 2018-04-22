package com.capstonegyg.gyg.UI.Profile;

import java.util.*;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private String UID, email;
    private static final int RC_PHOTO_PICKER = 2;
    private static final int RC_BANNER_PICKER = 3;

    private TextView displayName;
    private TextView emailDisplay;
    private TextView skill1;
    private TextView skill2;
    private TextView skill3;
    private CircleImageView profilePic;
    private ImageView bannerPic;
    private FloatingActionButton updatePic;
    private FloatingActionButton updateBannerPic;

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private DatabaseReference postDBR;
    private DatabaseReference ref;
    private FirebaseStorage mStor;
    private StorageReference mProStorRef;
    private StorageReference mBanStorRef;

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
            yesNoPopUp("Sign Out", "Are you sure you want to sign out?");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user != null) {
            UID = user.getUid();
            email = user.getEmail();
            postDBR = FirebaseDatabase.getInstance().getReference().child("users").child(UID);
            mStor = FirebaseStorage.getInstance();
            mProStorRef = mStor.getReference().child("profile_pics");
            mBanStorRef = mStor.getReference().child("banner_pics");
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
            skill1 = findViewById(R.id.text_skill_1);
            skill2 = findViewById(R.id.text_skill_2);
            skill3 = findViewById(R.id.text_skill_3);
            profilePic = findViewById(R.id.profile_image);
            bannerPic = findViewById(R.id.image_banner);

            //Buttons
            updatePic = findViewById(R.id.button_edit_profile_pic);
            updateBannerPic = findViewById(R.id.button_edit_banner_pic);

            // ImagePickerButton shows an image picker to upload a image for a message
            updatePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/jpeg");
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
                }
            });

            // ImagePickerButton shows an image picker to upload a image for a message
            updateBannerPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/jpeg");
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_BANNER_PICKER);
                }
            });

        }
        else
            {
            Intent p = new Intent(ProfileActivity.this, AuthenticationActivity.class);
            startActivity(p);
            }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();

            //Get a reference to store file at profile_pics/<FILENAME>
            StorageReference photoRef = mProStorRef.child(selectedImageUri.getLastPathSegment());

            //Upload file to Firebase Storage
            photoRef.putFile(selectedImageUri)
                    .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // When the image has successfully uploaded, we get its download URL
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();

                        // Update the database with this reference
                        postDBR.child("pic_ref").setValue(downloadUrl.toString());
                        }
                    });
        }
        if (requestCode == RC_BANNER_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();

            //Get a reference to store file at profile_pics/<FILENAME>
            StorageReference photoRef = mBanStorRef.child(selectedImageUri.getLastPathSegment());

            //Upload file to Firebase Storage
            photoRef.putFile(selectedImageUri)
                    .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // When the image has successfully uploaded, we get its download URL
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();

                            // Update the database with this reference
                            postDBR.child("banner_ref").setValue(downloadUrl.toString());
                        }
                    });
        }
    }

    public void show_data(DataSnapshot dataSnapshot) {
        displayName.setText(Objects.requireNonNull(dataSnapshot.child("display_name").getValue()).toString());
        if ((boolean) Objects.requireNonNull(dataSnapshot.child("show_email").getValue()))
            emailDisplay.setText(email);
        else
            emailDisplay.setText("[E-Mail not shared]");
        skill1.setText(Objects.requireNonNull(dataSnapshot.child("skills").child("skill0").getValue()).toString());
        skill2.setText(Objects.requireNonNull(dataSnapshot.child("skills").child("skill1").getValue()).toString());
        skill3.setText(Objects.requireNonNull(dataSnapshot.child("skills").child("skill2").getValue()).toString());
        Glide.with(this).load(Objects.requireNonNull(dataSnapshot.child("pic_ref").getValue())).override(155, 155).into(profilePic);
        if (!Objects.requireNonNull(dataSnapshot.child("pic_ref").getValue()).equals(""))
            Glide.with(this).load(Objects.requireNonNull(dataSnapshot.child("banner_ref").getValue())).centerCrop().into(bannerPic);
    }

    void yesNoPopUp(String title, String message) {
        AlertDialog.Builder builder;

        builder = new AlertDialog.Builder(ProfileActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                mAuth.signOut();
                Intent i = new Intent(ProfileActivity.this, AuthenticationActivity.class);
                startActivity(i);
                finish();
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
