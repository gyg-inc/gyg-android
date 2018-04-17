package com.capstonegyg.gyg.UI.Profile;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import com.google.firebase.database.FirebaseDatabase;

import java.net.PasswordAuthentication;

public class Settings extends AppCompatActivity implements View.OnClickListener {


    private EditText oldPass;
    private EditText newPass;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_settings_screen);
        ActionBar actionBar = this.getSupportActionBar();

        database = FirebaseDatabase.getInstance();

        //Views
        oldPass = findViewById(R.id.text_oldpw);
        newPass = findViewById(R.id.text_newpw);

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
        final String email = user.getEmail();

        if (i == R.id.button_update_pw) {
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

    public void showToast(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }



}
