package com.capstonegyg.gyg.UI.NotificationsScreen;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;

import com.capstonegyg.gyg.R;
import com.capstonegyg.gyg.UI.PostGyg.PostGygActivity;
import com.capstonegyg.gyg.UI.ViewGygScreen.ViewGyg.ViewGygData;
import com.capstonegyg.gyg.UI.ViewGygScreen.ViewGyg.ViewGygFirebaseAdapter;
import com.capstonegyg.gyg.UI.ViewGygScreen.ViewGyg.ViewGygFragment;
import com.capstonegyg.gyg.UI.ViewGygScreen.ViewGyg.ViewGygViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

/**
 * Created by Isuru Nanayakkara on 4/22/2018.
 */

public class NotificationsGygFragment extends Fragment {
    private NotificationFirebaseAdapter mAdapter;
    private RecyclerView notificationsRecycler;
    private DatabaseReference mDatabaseReference;
    private LinearLayoutManager linearLayoutManager;

    //private FloatingActionButton postGyg;

    public static NotificationsGygFragment newInstance() {
        return new NotificationsGygFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notifications_screen, container, false);

        //The recycler view that is populated
        notificationsRecycler = view.findViewById(R.id.view_gyg_recycler_view);
        //postGyg = view.findViewById(R.id.post_gyg_fab);

//        postGyg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), PostGygActivity.class);
//                startActivity(i);
//            }
//        });

        //Get the reference to the whole database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //Find the specific Firebase node.
        mDatabaseReference = firebaseDatabase.getReference().child("gygs");

        /*
            @arg1 - The "schema" file that defines data.
            @arg2 - The individual layout that is populated.
            @arg3 - The class that injects data into the gyg_list_layout
            @arg4 - The database reference. Holds actual data.
         */
        mAdapter = new NotificationFirebaseAdapter(NotificationsData.class, R.layout.gyg_list_layout, NotificationViewHolder.class, mDatabaseReference);

        //Init layout manager
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        //Reverse
        linearLayoutManager.setReverseLayout(true);
        //Stack from top
        linearLayoutManager.setStackFromEnd(true);
        //Set the layout manager. (Important) Defines how layout works.
        notificationsRecycler.setLayoutManager(linearLayoutManager);
        //Link the recycler view with the adapter
       notificationsRecycler.setAdapter(mAdapter);

        return view;
    }
}
