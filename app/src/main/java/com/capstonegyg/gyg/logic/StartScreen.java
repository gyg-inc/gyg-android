package com.capstonegyg.gyg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.capstonegyg.gyg.UI.ViewGyg.ViewGygActivity;

public class StartScreen extends AppCompatActivity {

    private Button viewGygs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        // Jonathan Luetze

        viewGygs = findViewById(R.id.view_gyg_button);

        viewGygs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(this, ViewGygActivity.class);
                startActivity(i);
            }
        });
    }
}
