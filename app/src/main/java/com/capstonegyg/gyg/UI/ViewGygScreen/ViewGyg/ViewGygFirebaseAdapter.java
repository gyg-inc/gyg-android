package com.capstonegyg.gyg.UI.ViewGygScreen.ViewGyg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.capstonegyg.gyg.R;
import com.capstonegyg.gyg.UI.PostGyg.PostGygData;
import com.capstonegyg.gyg.UI.ViewGygScreen.ViewDetailedGyg.ViewDetailedGygActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ViewGygFirebaseAdapter extends FirebaseRecyclerAdapter<ViewGygData, ViewGygViewHolder> {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersReference;
    private FirebaseAuth firebaseAuth;

    private final int SOMEONE_ELSE = 0;
    private final int THIS_USER = 1;

    //Constructor
    public ViewGygFirebaseAdapter(Class<ViewGygData> modelClass, int modelLayout, Class<ViewGygViewHolder> viewHolderClass, Query query) {
        super(modelClass, modelLayout, viewHolderClass, query);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public ViewGygViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGygViewHolder viewHolder =  super.onCreateViewHolder(parent, viewType);

        viewHolder.setOnClickListener(new ViewGygViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String gygKey = getRef(position).getKey();
                //Log.d("GYG_KEY", gygKey);
                Intent i = new Intent(view.getContext(), ViewDetailedGygActivity.class);
                //Pass along the data
                i.putExtra("GYG_KEY", gygKey);
                //Get context to start ViewDetailedGygActivity
                view.getContext().startActivity(i);
            }
        });

        return viewHolder;
    }

    //Inject the model data into its respective viewholder/layout widget.
    @Override
    protected void populateViewHolder(final ViewGygViewHolder viewHolder, final ViewGygData model, int position) {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //If user not null and not current user
        if(user != null && !model.gygPosterName.equals(user.getUid())) {

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

            //Set profile picture
            usersReference.child("pic_ref").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Poster picture
                    String pictureStringRef = (String) dataSnapshot.getValue();
                    viewHolder.setGygPosterFace(pictureStringRef);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Errors
                }
            });

            //Set the gyg data
            viewHolder.setGygName(model.gygName);
            viewHolder.setGygFee(model.gygFee, model.gygTime);
            viewHolder.setGygLocation(model.gygLocation);

            viewHolder.setVisibility(View.VISIBLE);
        }
        //Unauthenticated or posted by user
        else {
            viewHolder.setVisibility(View.GONE);
        }
    }
}
