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
        setContentView(R.layout.view_gyg_screen);

        viewGygsRecycler = findViewById(R.id.view_gyg_recycler_view);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = firebaseDatabase.getReference().child("view_gygs");

        mAdapter = new ViewGygFirebaseAdapter(ViewGygData.class, R.layout.gyg_list_layout, ViewGygViewHolder.class, mDatabaseReference);

        viewGygsRecycler.setLayoutManager(new LinearLayoutManager(this));
        viewGygsRecycler.setAdapter(mAdapter);
    }
}
