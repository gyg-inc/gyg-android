package com.capstonegyg.gyg.UI.ViewGyg;

import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

public class ViewGygFirebaseAdapter extends FirebaseRecyclerAdapter<ViewGygData, ViewGygViewHolder> {

    public ViewGygFirebaseAdapter(Class<ViewGygData> modelClass, int modelLayout, Class<ViewGygViewHolder> viewHolderClass, Query query) {
        super(modelClass, modelLayout, viewHolderClass, query);
    }

    @Override
    protected void populateViewHolder(ViewGygViewHolder viewHolder, ViewGygData model, int position) {
        viewHolder.setGygName(model.jobName);
        viewHolder.setGygPosterName(model.jobPosterName);
    }
}
