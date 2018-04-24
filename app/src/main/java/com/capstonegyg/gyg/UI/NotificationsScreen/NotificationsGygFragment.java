package com.capstonegyg.gyg.UI.NotificationsScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;

import com.capstonegyg.gyg.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Objects;

/**
 * Created by Isuru Nanayakkara on 4/22/2018.
 */

public class NotificationsGygFragment extends Fragment {
    private RecyclerView notificationsRecycler;
    String gygWorkerName;
    
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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userDBR = database.getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        //Query queryRef = userDBR.child("gygs").orderByChild("gygPosterName").equalTo(mAuth.getCurrentUser().getUid());
        Query queryRef = userDBR.getRef().child("gygs")
                        .child(mAuth.getCurrentUser().getUid())
                        .child("my_gygs").child("gygWorkerName");

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Iterable<DataSnapshot> matchSnapShot = dataSnapshot.getChildren();
                for (DataSnapshot match : matchSnapShot) {

                    if(match.getValue() != "") {
                        gygWorkerName = match.toString();
                        String notify = gygWorkerName + " picked up your gyg";
                    }
                }
                NotificationsGygFragment.this.notify();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        notificationsRecycler = view.findViewById(R.id.notifications_recycler);

        return view;
    }


}
