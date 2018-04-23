package com.capstonegyg.gyg.UI.NotificationsScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capstonegyg.gyg.R;

/**
 * Created by Isuru Nanayakkara on 4/22/2018.
 */

public class NotificationsGygFragment extends Fragment {
    private RecyclerView notificationsRecycler;

    public static NotificationsGygFragment newInstance() {

        Bundle args = new Bundle();

        NotificationsGygFragment fragment = new NotificationsGygFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notifications_screen, container, false);

        notificationsRecycler = view.findViewById(R.id.notifications_recycler);

        return view;
    }
}
