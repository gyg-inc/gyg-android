package com.capstonegyg.gyg.UI.Home;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.capstonegyg.gyg.R;
import com.capstonegyg.gyg.UI.MyGygs.MyGygsFragment;
import com.capstonegyg.gyg.UI.NotificationsScreen.NotificationsGygFragment;
import com.capstonegyg.gyg.UI.Profile.ProfileFragment;
import com.capstonegyg.gyg.UI.ViewGygScreen.ViewGyg.ViewGygFragment;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private FrameLayout hostContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        //Get the bottom navigation and container
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        hostContainer = findViewById(R.id.content_frame);

        //Get fragment manager
        fragmentManager = getSupportFragmentManager();


        //Home is the first thing a user sees
        bottomNavigationView.setSelectedItemId(R.id.action_home_tab);
        //Initially load view gygs
        loadFragment(R.id.action_home_tab);
        //Set tab listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                loadFragment(item.getItemId());
                return true;
            }
        });
    }

    void loadFragment(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.id.action_profile_tab:
                fragment = ProfileFragment.newInstance();
                break;
            case R.id.action_home_tab:
                fragment = ViewGygFragment.newInstance();
                break;
            case R.id.action_post_tab:
                fragment = MyGygsFragment.newInstance();
                break;
            case R.id.action_notifications:
                fragment = NotificationsGygFragment.newInstance();
                break;
        }

        if (fragment == null)
            return;

        fragmentManager
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

}
