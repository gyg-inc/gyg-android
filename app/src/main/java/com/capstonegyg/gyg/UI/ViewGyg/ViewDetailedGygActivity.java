/*
 * Written by Jonathan Luetze
 *
 * Shows the details of a gyg. Corresponds to gyg_details_screen.xml
 */


package com.capstonegyg.gyg.UI.ViewGyg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.capstonegyg.gyg.R;

public class ViewDetailedGygActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gyg_details_screen);
    }

    public void SetGygData(String gygName, String gygCategory, String gygLocation, Double gygFee, String gygDescription, String gygTime, String gygPosterName) {

        TextView fee = (TextView) findViewById(R.id.detail_fee);
        fee.setText("$" + String.valueOf(gygFee) + "/"+ gygTime);

        TextView name = (TextView) findViewById(R.id.detail_name);
        name.setText(gygName);

        TextView cat = (TextView) findViewById(R.id.detail_category);
        cat.setText(gygCategory);

        TextView loc = (TextView) findViewById(R.id.detail_location);
        loc.setText(gygLocation);

        TextView desc = (TextView) findViewById(R.id.detail_description);
        desc.setText(gygDescription);

        TextView pname = (TextView) findViewById(R.id.detail_jobposter);
        pname.setText(gygPosterName);

    }
}
