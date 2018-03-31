package com.capstonegyg.gyg.UI.GygTabs;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Isuru Nanayakkara on 3/31/2018.
 */

public class GygPagerAdapter extends FragmentStatePagerAdapter{
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Post Gyg", "View Gyg" };
    private Context mContext;

    public GygPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                //return StatsTabFragment.newInstance(position);
            case 1:
                //return GroupTabFragment.newInstance(position);
            default:
                return DefaultFrag.newInstance(position);
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
