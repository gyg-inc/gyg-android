package com.capstonegyg.gyg.UI.GygTabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Isuru Nanayakkara on 3/31/2018.
 */

public class DefaultFrag extends Fragment {
    public static DefaultFrag newInstance(int position) {

        Bundle args = new Bundle();

        DefaultFrag fragment = new DefaultFrag();
        fragment.setArguments(args);
        return fragment;
    }
}
