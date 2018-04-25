package com.capstonegyg.gyg.UI.Profile;

import android.content.Intent;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.Switch;
import android.widget.CompoundButton;

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
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

import java.util.Objects;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    private String UID, email;
    private int REQUEST_CODE = 50;

    private EditText oldPass;
    private EditText newPass;
    private EditText displayName;
    private EditText skill1;
    private EditText skill2;
    private EditText skill3;
    private TextView emailDisplay;
    private Switch swtch;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference ref;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_settings_screen);
        ActionBar actionBar = this.getSupportActionBar();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        UID = user.getUid();
        email = user.getEmail();
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

        // Shawn's thing

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(" https://gyg.herokuapp.com/client_token", new TextHttpResponseHandler() {
            public String clientToken;

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String clientToken) {
                this.clientToken = clientToken;
            }
        });

        // End Shawn's thing

        //Views
        oldPass = findViewById(R.id.text_oldpw);
        newPass = findViewById(R.id.text_newpw);
        displayName = findViewById(R.id.text_name);
        skill1 = findViewById(R.id.text_skill1);
        skill2 = findViewById(R.id.text_skill2);
        skill3 = findViewById(R.id.text_skill3);
        emailDisplay = findViewById(R.id.text_email_address);

        //Buttons
        findViewById(R.id.button_submit).setOnClickListener(this);
        findViewById(R.id.button_update_pw).setOnClickListener(this);
        findViewById(R.id.button_pay_update).setOnClickListener(this);

        //Switch
        swtch = findViewById(R.id.switch_email);

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        emailDisplay.setText("("+email+")");

        swtch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ref.child("show_email").setValue(true);
                else
                    ref.child("show_email").setValue(false);
            }
        });

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
        DatabaseReference postDBR = FirebaseDatabase.getInstance().getReference();
          if (i == R.id.button_update_pw) {
            if (user != null) {
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
            if (displayName.getText().length() + skill1.getText().length() + skill2.getText().length() + skill3.getText().length() == 0)
                showToast("Enter the values you wish to update.");
            else {
                if (displayName.getText().length() >= 6)
                    postDBR.child("users")
                            .child(UID)
                            .child("display_name")
                            .setValue(displayName.getText().toString());
                else if (displayName.getText().length() < 6 && displayName.getText().length() > 0)
                    showToast("Display names must be at least 6 characters");
                postDBR.child("users")
                        .child(UID)
                        .child("skills")
                        .child("skill0")
                        .setValue(skill1.getText().toString());
                postDBR.child("users")
                        .child(UID)
                        .child("skills")
                        .child("skill1")
                        .setValue(skill2.getText().toString());
                postDBR.child("users")
                        .child(UID)
                        .child("skills")
                        .child("skill2")
                        .setValue(skill3.getText().toString());
                showToast("Profile Updated Successfully!");
            }
        }
        if (i == R.id.button_pay_update) {
            onBraintreeSubmit(v);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                // use the result to update your UI and send the payment method nonce to your server
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
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
        skill1.setText(Objects.requireNonNull(dataSnapshot.child("skills").child("skill0").getValue()).toString());
        skill2.setText(Objects.requireNonNull(dataSnapshot.child("skills").child("skill1").getValue()).toString());
        skill3.setText(Objects.requireNonNull(dataSnapshot.child("skills").child("skill2").getValue()).toString());
        if ((boolean) dataSnapshot.child("show_email").getValue())
            swtch.setChecked(true);
        else
            swtch.setChecked(false);
    }

    public void onBraintreeSubmit(View v) {
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken("eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiJiNDZmZmYzNmE4ZWMxZDY3Zjg3NmRhNTVmYWU3MDY4YzcwMmU1NDZmYjhkY2FkMDk0OTc3YzdjYjZkYTdjOTBmfGNyZWF0ZWRfYXQ9MjAxOC0wMy0zMVQxOToyMDoxNy41MTgwMTA0NDMrMDAwMFx1MDAyNm1lcmNoYW50X2lkPTM0OHBrOWNnZjNiZ3l3MmJcdTAwMjZwdWJsaWNfa2V5PTJuMjQ3ZHY4OWJxOXZtcHIiLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvMzQ4cGs5Y2dmM2JneXcyYi9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJjaGFsbGVuZ2VzIjpbXSwiZW52aXJvbm1lbnQiOiJzYW5kYm94IiwiY2xpZW50QXBpVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbTo0NDMvbWVyY2hhbnRzLzM0OHBrOWNnZjNiZ3l3MmIvY2xpZW50X2FwaSIsImFzc2V0c1VybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tIiwiYXV0aFVybCI6Imh0dHBzOi8vYXV0aC52ZW5tby5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tIiwiYW5hbHl0aWNzIjp7InVybCI6Imh0dHBzOi8vY2xpZW50LWFuYWx5dGljcy5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tLzM0OHBrOWNnZjNiZ3l3MmIifSwidGhyZWVEU2VjdXJlRW5hYmxlZCI6dHJ1ZSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoiQWNtZSBXaWRnZXRzLCBMdGQuIChTYW5kYm94KSIsImNsaWVudElkIjpudWxsLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjp0cnVlLCJlbnZpcm9ubWVudCI6Im9mZmxpbmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJtYXN0ZXJjbGllbnQzIiwiYmlsbGluZ0FncmVlbWVudHNFbmFibGVkIjp0cnVlLCJtZXJjaGFudEFjY291bnRJZCI6ImFjbWV3aWRnZXRzbHRkc2FuZGJveCIsImN1cnJlbmN5SXNvQ29kZSI6IlVTRCJ9LCJtZXJjaGFudElkIjoiMzQ4cGs5Y2dmM2JneXcyYiIsInZlbm1vIjoib2ZmIn0=");
        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
    }

    public void showToast(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
