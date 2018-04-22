package com.capstonegyg.gyg.UI.ViewGygScreen.ViewGyg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capstonegyg.gyg.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by libpub on 4/22/2018.
 */

public class ViewGygFragment extends Fragment {
    private ViewGygFirebaseAdapter mAdapter;
    private RecyclerView viewGygsRecycler;
    private DatabaseReference mDatabaseReference;

    public static ViewGygFragment newInstance() {
        return new ViewGygFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_gyg_screen, container, false);

        //The recycler view that is populated
        viewGygsRecycler = view.findViewById(R.id.view_gyg_recycler_view);

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

        //Set the layout manager. (Important) Defines how layout works.
        viewGygsRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        //Link the recycler view with the adapter
        viewGygsRecycler.setAdapter(mAdapter);

        return view;
    }

}
