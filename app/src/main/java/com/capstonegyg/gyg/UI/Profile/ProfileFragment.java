package com.capstonegyg.gyg.UI.Profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.capstonegyg.gyg.R;
import com.capstonegyg.gyg.UI.Authentication.AuthenticationActivity;
import com.capstonegyg.gyg.UI.Home.HomeActivity;
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

import static android.app.Activity.RESULT_OK;

/**
 * Created by libpub on 4/22/2018.
 */

public class ProfileFragment extends Fragment {
    private String UID, email;
    private static final int RC_PHOTO_PICKER = 2;
    private static final int RC_BANNER_PICKER = 3;

    private TextView displayName;
    private TextView emailDisplay;
    private TextView displayedSkills;
    private TextView skill1;
    private TextView skill2;
    private TextView skill3;
    private CircleImageView profilePic;
    private ImageView bannerPic;
    private FloatingActionButton updatePic;
    private FloatingActionButton updateBannerPic;

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    private FirebaseStorage mStor;
    private StorageReference mProStorRef;
    private StorageReference mBanStorRef;

    private Context context;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        fragment.setHasOptionsMenu(true);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_screen_2, container, false);

        context = getContext();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        //if (user != null) {
            UID = user.getUid();
            email = user.getEmail();
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
                    showToast("Signed Out");
                }
            });

            //Views
            displayName = view.findViewById(R.id.text_displayed_name);
            emailDisplay = view.findViewById(R.id.text_email_address);
            displayedSkills = view.findViewById(R.id.text_displayed_skills);
            skill1 = view.findViewById(R.id.text_skill_1);
            skill2 = view.findViewById(R.id.text_skill_2);
            skill3 = view.findViewById(R.id.text_skill_3);
            profilePic = view.findViewById(R.id.profile_image);
            bannerPic = view.findViewById(R.id.image_banner);

            //Buttons
            updatePic = view.findViewById(R.id.button_edit_profile_pic);
            updateBannerPic = view.findViewById(R.id.button_edit_banner_pic);

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

        //}
       /* else
        {
            Intent p = new Intent(getActivity(), AuthenticationActivity.class);
            startActivity(p);
        }
        */
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profile_settings_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.profile_settings) {
            Intent i = new Intent(getActivity(), Settings.class);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();

            //Get a reference to store file at profile_pics/<FILENAME>
            StorageReference photoRef = mProStorRef.child(selectedImageUri.getLastPathSegment());

            //Upload file to Firebase Storage
            photoRef.putFile(selectedImageUri)
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // When the image has successfully uploaded, we get its download URL
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();

                            // Update the database with this reference
                            ref.child("pic_ref").setValue(downloadUrl.toString());
                        }
                    });
        }
        if (requestCode == RC_BANNER_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();

            //Get a reference to store file at profile_pics/<FILENAME>
            StorageReference photoRef = mBanStorRef.child(selectedImageUri.getLastPathSegment());

            //Upload file to Firebase Storage
            photoRef.putFile(selectedImageUri)
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // When the image has successfully uploaded, we get its download URL
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();

                            // Update the database with this reference
                            ref.child("banner_ref").setValue(downloadUrl.toString());
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

        if(isAdded()) {
            Glide.with(this).load(Objects.requireNonNull(dataSnapshot.child("pic_ref").getValue())).override(155, 155).into(profilePic);
            Glide.with(this).load(Objects.requireNonNull(dataSnapshot.child("banner_ref").getValue())).centerCrop().into(bannerPic);
        }
    }

    void yesNoPopUp(String title, String message) {
        AlertDialog.Builder builder;

        builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                mAuth.signOut();
                Intent i = new Intent(getActivity(), AuthenticationActivity.class);
                startActivity(i);
                getActivity().finish();
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
        Toast.makeText(getContext(), message,Toast.LENGTH_SHORT).show();
    }
}
