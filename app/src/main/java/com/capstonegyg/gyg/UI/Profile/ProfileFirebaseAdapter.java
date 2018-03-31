package com.capstonegyg.gyg.UI.Profile;

import android.support.v7.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

/**
 * Created by Shawn on 3/31/18.
 */

public class ProfileFirebaseAdapter extends FirebaseRecyclerAdapter<ProfileData, ProfileViewHolder> {
    //Constructor
    public ProfileFirebaseAdapter(Class<ProfileData> modelClass, int modelLayout, Class<ProfileViewHolder> viewHolderClass, Query query) {
        super(modelClass, modelLayout, viewHolderClass, query);
    }

    //Inject the model data into its respective viewholder/layout widget.
    @Override
    protected void populateViewHolder(ProfileViewHolder viewHolder, ProfileData model, int position) {
        //Set the name of job. Pass in data
        viewHolder.setUserName(model.userName);
        //Set the poster name. Pass in data
        viewHolder.setQRCode(model.qrCode);
    }
}
