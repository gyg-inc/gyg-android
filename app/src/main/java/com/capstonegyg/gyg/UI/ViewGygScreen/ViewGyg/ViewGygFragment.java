package com.capstonegyg.gyg.UI.ViewGygScreen.ViewGyg;

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

import com.capstonegyg.gyg.R;
import com.capstonegyg.gyg.UI.PostGyg.PostGygActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by Isuru Nanayakkara on 4/22/2018.
 */

public class ViewGygFragment extends Fragment {
    private ViewGygFirebaseAdapter mAdapter;
    private RecyclerView viewGygsRecycler;
    private DatabaseReference mDatabaseReference;
    private LinearLayoutManager linearLayoutManager;

    private FloatingActionButton postGyg;

    public static ViewGygFragment newInstance() {
        return new ViewGygFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_gyg_screen, container, false);

        //The recycler view that is populated
        viewGygsRecycler = view.findViewById(R.id.view_gyg_recycler_view);
        postGyg = view.findViewById(R.id.post_gyg_fab);

        postGyg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PostGygActivity.class);
                startActivity(i);
            }
        });

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
        mAdapter = new ViewGygFirebaseAdapter(ViewGygData.class, R.layout.gyg_list_layout, ViewGygViewHolder.class, mDatabaseReference);

        //Init layout manager
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        //Reverse
        linearLayoutManager.setReverseLayout(true);
        //Stack from top
        linearLayoutManager.setStackFromEnd(true);
        //Set the layout manager. (Important) Defines how layout works.
        viewGygsRecycler.setLayoutManager(linearLayoutManager);
        //Link the recycler view with the adapter
        viewGygsRecycler.setAdapter(mAdapter);

        return view;
    }

}
