package com.capstonegyg.gyg.UI.MyGygs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.capstonegyg.gyg.R;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Jonathan Luetze on 3/24/18.
 *
 *  My Gygs shows all the Gygs the user has accepted, posted, completed
 *
 */

public class MyGygsActivity extends AppCompatActivity{

    private MyGygsFirebaseAdapter mAdapter;
    private RecyclerView myGygsRecycler;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This sets the master view for the ViewGygActivity screen
        setContentView(R.layout.my_gyg_base);

        //The recycler view that is populated
        myGygsRecycler = findViewById(R.id.my_gyg_recycler_view);

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
        mAdapter = new MyGygsFirebaseAdapter(MyGygsData.class, R.layout.mygygs_screen, MyGygsViewHolder.class, mDatabaseReference);

        //Set the layout manager. (Important) Defines how layout works.
        myGygsRecycler.setLayoutManager(new LinearLayoutManager(this));
        //Link the recycler view with the adapter
        myGygsRecycler.setAdapter(mAdapter);
    }
}
