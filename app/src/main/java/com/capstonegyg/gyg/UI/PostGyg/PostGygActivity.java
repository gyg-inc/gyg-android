package com.capstonegyg.gyg.UI.PostGyg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.capstonegyg.gyg.R;
import com.capstonegyg.gyg.StartScreen;
import com.capstonegyg.gyg.UI.Profile.ProfileData;
import com.capstonegyg.gyg.UI.Profile.ProfileFirebaseAdapter;
import com.capstonegyg.gyg.UI.Profile.ProfileViewHolder;
import com.capstonegyg.gyg.UI.ViewGyg.ViewGygActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 *  Written by Jonathan Luetze.
 *
 *  PostGyg allows a user to create and post a Gyg to Firebase
 */

public class PostGygActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_gyg_screen);

        /* Declaring and filling Arraylist and Spinner for time */

        ArrayList<String> times = new ArrayList<>();

        times.add("Hourly");
        times.add("Daily");
        times.add("Weekly");
        times.add("Monthly");
        times.add("Yearly");

        Spinner spinnerCountShoes = findViewById(R.id.time_spinner);
        ArrayAdapter<String> spinnerCountShoesArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_dropdown_item, times);
        spinnerCountShoes.setAdapter(spinnerCountShoesArrayAdapter);

        /* Declaring and setting Submit button to send info to Firebase */

        Button Submit = findViewById(R.id.submit_gyg);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Declaring Firebase object

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference postDBR = database.getReference();


                /* Getting and formatting user input */

                EditText edt = findViewById(R.id.gyg_title);
                String gygName = edt.getText().toString();

                edt = findViewById(R.id.gyg_category);
                String gygCategory = edt.getText().toString();

                edt = findViewById(R.id.gyg_area);
                String gygLocation = edt.getText().toString();

                edt = findViewById(R.id.gyg_pay);
                double gygFee = Double.parseDouble(edt.getText().toString());

                edt = findViewById(R.id.gyg_description);
                String gygDescription = edt.getText().toString();

                Spinner s = findViewById(R.id.time_spinner);
                String gygTime = s.getSelectedItem().toString();

                String gygPosterName = "testName";

                /* Creating class object and sending it to Firebase */

                PostGygData gyg = new PostGygData(gygName, gygCategory, gygLocation, gygFee,
                        gygDescription, gygTime, gygPosterName);
                postDBR.child("gygs").push().setValue(gyg);//.child("username2").setValue(gyg);
            }
        });
    }
}
