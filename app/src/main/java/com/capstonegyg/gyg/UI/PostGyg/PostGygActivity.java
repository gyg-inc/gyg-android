package com.capstonegyg.gyg.UI.PostGyg;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.AlertDialog;
import android.os.Handler;
import android.widget.Toast;
import java.util.Date;

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

public class PostGygActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    int year;
    int month;
    int day;

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


        /* Setting the background color for the pay section */

        ImageView payBackground = findViewById(R.id.pay_background);
        payBackground.setBackgroundColor(Color.rgb(220,220, 220));


        /* Gyg Deadline Date Picker Information */

        Button date = findViewById(R.id.date_button);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");


            }
        });


        /* Declaring and setting Submit button to send info to Firebase */

        Button Submit = findViewById(R.id.submit_gyg);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                // Declaring Firebase object
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference postDBR = database.getReference();

                /* Getting and formatting user input */

                EditText gygName        = findViewById(R.id.gyg_title);
                EditText gygCategory    = findViewById(R.id.gyg_category);
                EditText gygLocation    = findViewById(R.id.gyg_area);
                EditText gygDescription = findViewById(R.id.gyg_description);

                EditText edt = findViewById(R.id.gyg_pay);
                double gygFee = Double.parseDouble(edt.getText().toString());

                Spinner s = findViewById(R.id.time_spinner);
                String gygTime = s.getSelectedItem().toString();

                String gygPosterName = "testName";
                String gygPostedDate = new Date().toString();
                String gygEndDate = "gygEndDate";

                // TO DO: Get slider input to see if volunteering is on or off
                // change spinner and gyg_pay to reflect choice
                // Check for no input or missing input and set fields accordingly (throw error, mark empty fields red)
                // Finish Deadline date with DatePickerFragment class


                /* Creating class object and sending it to Firebase */

                PostGygData gyg = new PostGygData(format(gygName), format(gygCategory), format(gygLocation), gygFee,
                        format(gygDescription), gygTime, gygPosterName, gygPostedDate, gygEndDate);
                postDBR.child("gygs").push().setValue(gyg);


                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(PostGygActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(PostGygActivity.this);
                }
                builder.setTitle("Post Gyg");
                builder.setMessage("Are you sure you want to post this Gyg?");


                /* If user definitely wants to post the gyg */
                builder.setPositiveButton("yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                            AlertDialog.Builder newB = new AlertDialog.Builder(PostGygActivity.this);
                            newB.setMessage("Posted Successfully");

                            newB.create();
                            newB.show();

                            /* Handler delays message from disappearing */

                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable() {
                                public void run() {
                                    finish();
                                }
                            }, 3000);
                        }
                    });

                /* If user doesn't want to post the gyg */
                builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });


                builder.create();
                builder.show();
            }

            /* Function to format the input */

            private String format(EditText E) {
                return E.getText().toString();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        this.year = year;
        this.month = month;
        this.day = day;

        String dateString = "Deadline: "+ month + "/" + day + "/" + year;

        TextView V = findViewById(R.id.display_date);
        V.setText(dateString);

        Button W = findViewById(R.id.date_button);
        W.setText(R.string.change_deadline);

        V.setTextSize(18);
        V.setAllCaps(true);

    }
}
