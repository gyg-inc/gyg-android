package com.capstonegyg.gyg.UI.MyGygsScreen.MyGygsTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capstonegyg.gyg.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by Isuru Nanyakkara on 4/22/2018.
 */

public class MyGygsFragment extends Fragment {

    private MyGygsFirebaseAdapter mAdapter;
    private RecyclerView myGygsRecycler;

    public static MyGygsFragment newInstance() {

        Bundle args = new Bundle();

        MyGygsFragment fragment = new MyGygsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_gyg_base, container, false);

        myGygsRecycler = view.findViewById(R.id.my_gyg_recycler_view);

        //Get the reference to the whole database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userDBR = database.getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        /* Finds all the Gygs that were posted by the signed-in user */
        Query queryRef = userDBR.child("gygs").orderByChild("gygPosterName").equalTo(mAuth.getCurrentUser().getUid());

        //   mDatabaseReference = userDBR.child("gygs");
        /*
            @arg1 - The "schema" file that defines data.
            @arg2 - The individual layout that is populated.
            @arg3 - The class that injects data into the gyg_list_layout
            @arg4 - The database reference. Holds actual data.
         */
        mAdapter = new MyGygsFirebaseAdapter(MyGygsData.class, R.layout.mygygs_layout, MyGygsViewHolder.class, queryRef);

        //Set the layout manager. (Important) Defines how layout works.
        myGygsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Link the recycler view with the adapter
        myGygsRecycler.setAdapter(mAdapter);

        return view;
    }
}
