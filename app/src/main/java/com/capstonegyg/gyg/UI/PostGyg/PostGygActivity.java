package com.capstonegyg.gyg.UI.PostGyg;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
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
    String gygEndDate;
    Boolean  gygVolunteer;

    SwitchCompat sw;

    ArrayList<String> times;

    Spinner timeSpinner;

    EditText gygName;
    EditText gygCategory;
    EditText gygLocation;
    EditText gygDescription;

    double gygFee;

    String gygTime;
    String gygPosterName;
    String gygPostedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_gyg_screen);

        //Set views
        sw = findViewById(R.id.switch2);
        timeSpinner = findViewById(R.id.time_spinner);

        gygEndDate = "NONE";

        /* Initialize data */

        initTimeList();

        initTimeSpinner();

        initDatePicker();

        initVolunteerSwitch();

        setDesign();

        /* Declaring and setting Submit button to send info to Firebase */
        Button Submit = findViewById(R.id.submit_gyg);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Pop-Up Box to verify that the User wants to post the Gyg */
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(PostGygActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(PostGygActivity.this);
                }
                builder.setTitle("Post Gyg");
                builder.setMessage("Are you sure you want to post this Gyg?");

                /* If user definitely wants to post the gyg, get data and send it to Firebase */
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        getInput();

                        // TO DO:
                        // change gyg_pay to reflect choice of volunteering or not (e.g. 0)
                        // Check for no input or missing input and set fields accordingly (throw error, mark empty fields red)
                        // option to add picture for a gyg

                        pushToFirebase();

                        /* Posted Successfully Message */
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
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.create();
                builder.show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        this.year = year;
        this.month = month;
        this.day = day;

        String dateString = "Deadline: "+ month + "/" + day + "/" + year; // creates displayed date

        this.gygEndDate = month + "/" + day + "/" + year; // saves date that's pushed to Firebase

        TextView V = findViewById(R.id.display_date);
        V.setText(dateString);

        Button W = findViewById(R.id.date_button);
        W.setText(R.string.change_deadline);

        V.setTextSize(18);
        V.setAllCaps(true);
    }

    //Private methods
    void initTimeList() {
        /* Declaring and filling Arraylist and Spinner for time */
        times = new ArrayList<>();
        times.add("Hourly");
        times.add("Daily");
        times.add("Weekly");
        times.add("Monthly");
        times.add("Yearly");
    }

    void initTimeSpinner() {
        ArrayAdapter<String> timeSpinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_dropdown_item, times);
        timeSpinner.setAdapter(timeSpinnerArrayAdapter);
    }

    void setDesign() {
        /* Setting the background color for the pay section */
        ImageView payBackground = findViewById(R.id.pay_background);
        payBackground.setBackgroundColor(Color.rgb(220,220, 220));
    }

    void initDatePicker() {
        /* Gyg Deadline Date Picker Information */
        Button date = findViewById(R.id.date_button);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });
    }

    void initVolunteerSwitch() {
        this.gygVolunteer = false;

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked) {gygVolunteer = true;}
            }
        });
    }


    void getInput() {
        /* Getting and formatting user input */
        gygName        = findViewById(R.id.gyg_title);
        gygCategory    = findViewById(R.id.gyg_category);
        gygLocation    = findViewById(R.id.gyg_area);
        gygDescription = findViewById(R.id.gyg_description);

        EditText edt = findViewById(R.id.gyg_pay);
        gygFee = Double.parseDouble(edt.getText().toString());

        //Spinner s = findViewById(R.id.time_spinner);
        gygTime = timeSpinner.getSelectedItem().toString();

        gygPosterName = "testName";
        gygPostedDate = new Date().toString();
    }

    void pushToFirebase() {
        // Declaring Firebase object
        final FirebaseDatabase[] database = {FirebaseDatabase.getInstance()};
        DatabaseReference postDBR = database[0].getReference();

        /* Formatting and Pushing of data */
        PostGygData gyg = new PostGygData(format(gygName), format(gygCategory), format(gygLocation), gygFee,
                format(gygDescription), gygTime, gygPosterName, gygPostedDate, gygEndDate, gygVolunteer);
        postDBR.child("gygs").push().setValue(gyg);
    }

    /* Function to format the input */
    private String format(EditText E) {
        return E.getText().toString();
    }

}
