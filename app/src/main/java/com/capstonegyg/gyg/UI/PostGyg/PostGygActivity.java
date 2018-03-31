package com.capstonegyg.gyg.UI.PostGyg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.capstonegyg.gyg.R;

import java.util.ArrayList;

/**
 * Created by Jonathan on 3/5/2018.
 */

public class PostGygActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_gyg_screen);


        ArrayList<String> times= new ArrayList<>();

        times.add("Hourly");
        times.add("Daily");
        times.add("Weekly");
        times.add("Monthly");
        times.add("Yearly");

        Spinner spinnerCountShoes = findViewById(R.id.time_spinner);
        ArrayAdapter<String> spinnerCountShoesArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_dropdown_item, times);
        spinnerCountShoes.setAdapter(spinnerCountShoesArrayAdapter);
    }





}
