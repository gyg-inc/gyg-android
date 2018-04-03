package com.capstonegyg.gyg.UI.ViewGyg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.capstonegyg.gyg.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ipnanayakkara on 3/5/2018.
 *
 * setContentView(R.layout.view_gyg_screen);
 *      This sets the master view for the ViewGygActivity screen
 *
 */

public class ViewGygActivity extends AppCompatActivity {

    private ViewGygFirebaseAdapter mAdapter;
    private RecyclerView viewGygsRecycler;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This sets the master view for the ViewGygActivity screen
        setContentView(R.layout.view_gyg_screen);

        //The recycler view that is populated
        viewGygsRecycler = findViewById(R.id.view_gyg_recycler_view);

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
        viewGygsRecycler.setLayoutManager(new LinearLayoutManager(this));
        //Link the recycler view with the adapter
        viewGygsRecycler.setAdapter(mAdapter);
    }
}
