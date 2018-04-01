package com.capstonegyg.gyg.UI.GygTabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.capstonegyg.gyg.R;

/**
 * Created by hp-pc on 3/31/2018.
 */

public class GygTabActivity extends AppCompatActivity {
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gyg_tab_screen);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = findViewById(R.id.gyg_tab_view_pager);
        viewPager.setAdapter(new GygPagerAdapter(getSupportFragmentManager(), this));

        tabLayout.setupWithViewPager(viewPager);
    }
}
