package com.capstonegyg.gyg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.capstonegyg.gyg.UI.PostGyg.PostGygActivity;
import com.capstonegyg.gyg.UI.ViewGyg.ViewGygActivity;

public class StartScreen extends AppCompatActivity {

    private Button viewGygs;
    private Button listGygs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        viewGygs = findViewById(R.id.view_gyg_button);

        viewGygs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartScreen.this, ViewGygActivity.class);
                startActivity(i);
            }
        });

        listGygs = findViewById(R.id.list_gyg_button);
        listGygs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent p = new Intent(StartScreen.this, PostGygActivity.class);
                startActivity(p);
            }
        });
    }
}
