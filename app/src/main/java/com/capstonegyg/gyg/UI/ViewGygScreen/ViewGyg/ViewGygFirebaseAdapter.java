package com.capstonegyg.gyg.UI.ViewGygScreen.ViewGyg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.capstonegyg.gyg.UI.ViewGygScreen.ViewDetailedGyg.ViewDetailedGygActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ViewGygFirebaseAdapter extends FirebaseRecyclerAdapter<ViewGygData, ViewGygViewHolder> {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersReference;

    //Constructor
    public ViewGygFirebaseAdapter(Class<ViewGygData> modelClass, int modelLayout, Class<ViewGygViewHolder> viewHolderClass, Query query) {
        super(modelClass, modelLayout, viewHolderClass, query);
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    //Inject the model data into its respective viewholder/layout widget.
    @Override
    protected void populateViewHolder(final ViewGygViewHolder viewHolder, final ViewGygData model, int position) {
        //Get reference to user that posted
        usersReference = firebaseDatabase.getReference().child("users").child(model.gygPosterName);
        //At child node get
        usersReference.child("display_name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Poster name
                String name = (String) dataSnapshot.getValue();
                viewHolder.setGygPosterName(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Errors
            }
        });

        //Set the name of job. Pass in data
        viewHolder.setGygName(model.gygName);
        viewHolder.setGygFee(model.gygFee, model.gygTime);
        viewHolder.setGygLocation(model.gygLocation);
        viewHolder.setGygCategory(model.gygCategory);
        viewHolder.setGygDescription(model.gygDescription);
        viewHolder.setGygTime(model.gygTime);
    }

    @Override
    public ViewGygViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGygViewHolder viewHolder =  super.onCreateViewHolder(parent, viewType);

        viewHolder.setOnClickListener(new ViewGygViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, Bundle data) {
                //Create Intent
                Intent i = new Intent(view.getContext(), ViewDetailedGygActivity.class);
                //Pass along the data
                i.putExtra("GYG_DATA", data);
                //Get context to start ViewDetailedGygActivity
                view.getContext().startActivity(i);
            }
        });

        return viewHolder;
    }



}
