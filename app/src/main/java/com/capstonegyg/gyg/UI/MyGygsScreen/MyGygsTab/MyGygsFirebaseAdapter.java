package com.capstonegyg.gyg.UI.MyGygsScreen.MyGygsTab;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.capstonegyg.gyg.UI.MyGygsScreen.GygHits.GygHitsActivity;
import com.capstonegyg.gyg.UI.PostGyg.PostGygActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
        viewHolder.setGygFee(model.gygFee, model.gygTime);
        viewHolder.setGygLocation(model.gygLocation);
        viewHolder.setGygWorkerName(model.gygWorkerName);
    }

    @Override
    public MyGygsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyGygsViewHolder viewHolder =  super.onCreateViewHolder(parent, viewType);

        viewHolder.setOnClickListener(new MyGygsViewHolder.ClickListener() {
            @Override
            public void onHitClick(View view, int position) {
                Intent i = new Intent(view.getContext(), GygHitsActivity.class);
                i.putExtra("GYG_KEY", getRef(position).getKey());
                view.getContext().startActivity(i);
            }

            @Override
            public void onEditClick(View view, int position) {
                Intent i = new Intent(view.getContext(), PostGygActivity.class);
                i.putExtra("GYG_KEY", getRef(position).getKey());
                view.getContext().startActivity(i);
            }

            @Override
            public void onDeleteClick(View view, int position) {
                final String gygKey = getRef(position).getKey();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                FirebaseAuth auth = FirebaseAuth.getInstance();

                //First remove the Gyg
                DatabaseReference gygDBR = database.getReference();
                gygDBR.child("gygs").child(gygKey).removeValue();


                //Then remove the my_gygs entry in users page
                DatabaseReference deleteRef = database
                        .getReference()
                        .child("users")
                        .child(auth.getCurrentUser().getUid())
                        .child("my_gygs");

                //Loop through and filter the gygs
                deleteRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> matchSnapShot = dataSnapshot.getChildren();
                        for (DataSnapshot match : matchSnapShot) {
                            //If this is the gyg we want to delete
                            if(gygKey.equals(match.getValue().toString())) {
                                match.getRef().removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        });

        return viewHolder;
    }
}
