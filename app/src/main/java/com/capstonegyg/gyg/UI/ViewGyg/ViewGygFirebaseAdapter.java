package com.capstonegyg.gyg.UI.ViewGyg;

import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

public class ViewGygFirebaseAdapter extends FirebaseRecyclerAdapter<ViewGygData, ViewGygViewHolder> {

    //Constructor
    public ViewGygFirebaseAdapter(Class<ViewGygData> modelClass, int modelLayout, Class<ViewGygViewHolder> viewHolderClass, Query query) {
        super(modelClass, modelLayout, viewHolderClass, query);
    }

    //Inject the model data into its respective viewholder/layout widget.
    @Override
    protected void populateViewHolder(ViewGygViewHolder viewHolder, ViewGygData model, int position) {
        //Set the name of job. Pass in data
        viewHolder.setGygName(model.jobName);
        //Set the poster name. Pass in data
        viewHolder.setGygPosterName(model.jobPosterName);
    }
}
