package com.capstonegyg.gyg.UI.MyGygs;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.capstonegyg.gyg.UI.ViewGyg.ViewGygData;
import com.capstonegyg.gyg.UI.ViewGyg.ViewGygViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MyGygsFirebaseAdapter extends FirebaseRecyclerAdapter <MyGygsData, MyGygsViewHolder> {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    //Constructor
    public MyGygsFirebaseAdapter(Class<MyGygsData> modelClass, int modelLayout, Class<MyGygsViewHolder> viewHolderClass, Query query) {
        super(modelClass, modelLayout, viewHolderClass, query);
    }

    //Inject the model data into its respective viewholder/layout widget.
    @Override
    protected void populateViewHolder(MyGygsViewHolder viewHolder, MyGygsData model, int position) {
        //Set the name of job. Pass in data
        viewHolder.setGygName(model.gygName);
        //Set the poster name. Pass in data
        viewHolder.setGygPosterName(model.gygPosterName);
        viewHolder.setGygFee(model.gygFee, model.gygTime);
        viewHolder.setGygLocation(model.gygLocation);
    }

    @Override
    public MyGygsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyGygsViewHolder viewHolder =  super.onCreateViewHolder(parent, viewType);

        viewHolder.setOnClickListener(new MyGygsViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(view.getContext(), "Item clicked at " + position, Toast.LENGTH_SHORT).show();
                //DatabaseReference mDatabaseReference = firebaseDatabase.getReference().child("gygs").;
            }
        });

        return viewHolder;
    }
}
