/*
 * Written by Jonathan Luetze
 *
 * Shows the details of a gyg. Corresponds to gyg_details_screen.xml
 */


package com.capstonegyg.gyg.UI.ViewGygScreen.ViewDetailedGyg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.capstonegyg.gyg.R;

public class ViewDetailedGygActivity extends AppCompatActivity {
    private TextView fee;
    private TextView name;
    private TextView cat;
    private TextView loc;
    private TextView desc;
    private TextView pname;
    private TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gyg_details_screen);

        fee = findViewById(R.id.detail_fee);
        name = findViewById(R.id.detail_name);
        cat = findViewById(R.id.detail_category);
        loc = findViewById(R.id.detail_location);
        desc = findViewById(R.id.detail_description);
        pname = findViewById(R.id.detail_jobposter);

        setGygData(getIntent().getExtras().getBundle("GYG_DATA"));
    }

    public void setGygData(Bundle data) {
        if(data != null) {
            fee.setText(data.getString("GYG_FEE"));
            name.setText(data.getString("GYG_NAME"));
            cat.setText(data.getString("GYG_CATEGORY"));
            loc.setText(data.getString("GYG_LOCATION"));
            desc.setText(data.getString("GYG_DESCRIPTION"));
            pname.setText(data.getString("GYG_POSTER_NAME"));
            //GYG_TIME
        }
    }
}
