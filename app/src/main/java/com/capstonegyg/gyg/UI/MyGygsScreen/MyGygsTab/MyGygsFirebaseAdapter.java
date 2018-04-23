package com.capstonegyg.gyg.UI.MyGygsScreen.MyGygsTab;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MyGygsFirebaseAdapter extends FirebaseRecyclerAdapter <MyGygsData, MyGygsViewHolder> {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public String gygKey;

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
        viewHolder.setGygFee(model.gygFee, model.gygTime);
        viewHolder.setGygLocation(model.gygLocation);
        viewHolder.setGygKey(model.gygKey);
        viewHolder.setGygWorkerName(model.gygWorkerName);
        gygKey = model.gygKey;
    }

    @Override
    public MyGygsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyGygsViewHolder viewHolder =  super.onCreateViewHolder(parent, viewType);

        viewHolder.setOnClickListener(new MyGygsViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(view.getContext(), "Item clicked at " + position + gygKey, Toast.LENGTH_SHORT).show();
            }
        });

        return viewHolder;
    }
}
