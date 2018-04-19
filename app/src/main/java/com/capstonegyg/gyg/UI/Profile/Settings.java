package com.capstonegyg.gyg.UI.Profile;

import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.capstonegyg.gyg.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    private String UID;

    private EditText oldPass;
    private EditText newPass;
    private EditText displayName;
    private EditText skill1;
    private EditText skill2;
    private EditText skill3;

    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_settings_screen);
        ActionBar actionBar = this.getSupportActionBar();
        mAuth = FirebaseAuth.getInstance();
        UID = mAuth.getCurrentUser().getUid();
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
        oldPass = findViewById(R.id.text_oldpw);
        newPass = findViewById(R.id.text_newpw);
        displayName = findViewById(R.id.text_name);
        skill1 = findViewById(R.id.text_skill1);
        skill2 = findViewById(R.id.text_skill2);
        skill3 = findViewById(R.id.text_skill3);

        //Buttons
        findViewById(R.id.button_submit).setOnClickListener(this);
        findViewById(R.id.button_update_pw).setOnClickListener(this);

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the ProfileActivity
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        final String email;

        if (i == R.id.button_update_pw) {
            if (user != null) {
                email = user.getEmail();
            String oldpw = oldPass.getText().toString();
            final String newpw = newPass.getText().toString();
            if (oldpw.length() > 0 && newpw.length() > 0 && newpw.length() >= 6 && !oldpw.equals(newpw))
                update_pw(user, email, oldpw, newpw);
            else if (oldpw.length() > 0 && newpw.length() > 0 && newpw.length() >= 6 && oldpw.equals(newpw))
                showToast("That's the same password!");
            else if (oldpw.length() > 0 && newpw.length() > 0 && newpw.length() < 6)
                showToast("Passwords must be at least 6 characters long.");
            else
                showToast("Please enter both current and new password.");
            }
        }
        if (i == R.id.button_submit) {
            DatabaseReference postDBR = FirebaseDatabase.getInstance().getReference();
            if (displayName.getText().length() + skill1.getText().length() + skill2.getText().length() + skill3.getText().length() == 0)
                showToast("Enter the values you wish to update.");
            else {
                if (displayName.getText().length() >= 6)
                    postDBR.child("users")
                            .child(UID)
                            .child("display_name")
                            .setValue(displayName.getText().toString());
                else if (displayName.getText().length() < 6 && displayName.getText().length() > 0)
                    showToast("Diaplay names must be at least 6 characters");
                if (skill1.getText().length() > 0)
                    postDBR.child("users")
                            .child(UID)
                            .child("skills")
                            .child("skill0")
                            .setValue(skill1.getText().toString());
                if (skill2.getText().length() > 0)
                    postDBR.child("users")
                            .child(UID)
                            .child("skills")
                            .child("skill1")
                            .setValue(skill2.getText().toString());
                if (skill3.getText().length() > 0)
                    postDBR.child("users")
                            .child(UID)
                            .child("skills")
                            .child("skill2")
                            .setValue(skill3.getText().toString());
                showToast("Profile Updated Successfully!");
            }
        }
    }

    public void update_pw(final FirebaseUser user, String email, String oldpass, final String newpass) {
        AuthCredential credential = EmailAuthProvider.getCredential(email,oldpass);
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    user.updatePassword(newpass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(!task.isSuccessful()){
                                showToast("Something went wrong. Please try again later");
                            }else {
                                showToast("Password Successfully Updated!");
                            }
                        }
                    });
                }else {
                    showToast("Incorrect Current Password!");
                }
            }
        });
    }

    public void show_data(DataSnapshot dataSnapshot) {
        displayName.setText(Objects.requireNonNull(dataSnapshot.child("display_name").getValue()).toString());
        skill1.setHint(Objects.requireNonNull(dataSnapshot.child("skills").child("skill0").getValue()).toString());
        skill2.setHint(Objects.requireNonNull(dataSnapshot.child("skills").child("skill1").getValue()).toString());
        skill3.setHint(Objects.requireNonNull(dataSnapshot.child("skills").child("skill2").getValue()).toString());
    }

    public void showToast(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
