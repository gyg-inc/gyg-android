package com.capstonegyg.gyg.UI.ViewGyg;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ViewGygFirebaseAdapter extends FirebaseRecyclerAdapter<ViewGygData, ViewGygViewHolder> {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    //Constructor
    public ViewGygFirebaseAdapter(Class<ViewGygData> modelClass, int modelLayout, Class<ViewGygViewHolder> viewHolderClass, Query query) {
        super(modelClass, modelLayout, viewHolderClass, query);
    }

    //Inject the model data into its respective viewholder/layout widget.
    @Override
    protected void populateViewHolder(ViewGygViewHolder viewHolder, ViewGygData model, int position) {
        //Set the name of job. Pass in data
        viewHolder.setGygName(model.gygName);
        //Set the poster name. Pass in data
        viewHolder.setGygPosterName(model.gygPosterName);
        viewHolder.setGygFee(model.gygFee, model.gygTime);
        viewHolder.setGygLocation(model.gygLocation);
    }

    @Override
    public ViewGygViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGygViewHolder viewHolder =  super.onCreateViewHolder(parent, viewType);

        viewHolder.setOnClickListener(new ViewGygViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(view.getContext(), "Item clicked at " + position, Toast.LENGTH_SHORT).show();
                //DatabaseReference mDatabaseReference = firebaseDatabase.getReference().child("gygs").;
            }
        });

        return viewHolder;
    }
}
