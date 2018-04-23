package com.capstonegyg.gyg.UI.MyGygsScreen.MyGygsTab;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.capstonegyg.gyg.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MyGygsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView gygName,gygWorkerName, gygFee, gygLocation;
    private TextView deleteButton, hitsButton, editButton;

    String gygKey;

    //-------------Click Listeners-------------//

    private MyGygsViewHolder.ClickListener mClickListener;

    //Interface to send callbacks...
    public interface ClickListener{
        void onHitClick(View view, int position);
        void onEditClick(View view, int position);
    }

    public void setOnClickListener(MyGygsViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

    //-------------End Click Listeners-------------//


    public MyGygsViewHolder(View itemView){
        super(itemView);

        //Info Text views
        gygName = itemView.findViewById(R.id.gyg_name);
        gygWorkerName = itemView.findViewById(R.id.gyg_worker_name);
        gygFee = itemView.findViewById(R.id.gyg_fee);
        gygLocation = itemView.findViewById(R.id.gyg_location);

        //"Button" Text views
        deleteButton = itemView.findViewById(R.id.delete);
        //Hits button
        hitsButton = itemView.findViewById(R.id.hits);
        //Edit button
        editButton = itemView.findViewById(R.id.edit);

        //If the card is clicked
        itemView.setOnClickListener(this);
        //Listen to button events
        deleteButton.setOnClickListener(this);
        hitsButton.setOnClickListener(this);
        editButton.setOnClickListener(this);
    }

    public void setGygName(String gygName) {
        this.gygName.setText(gygName);
    }

    // Sets worker name, indicates POSTED if gyg hasn't been accepted yet
    public void setGygWorkerName(String gygWorkerName) {
        if(gygWorkerName.equals("")) {
            gygWorkerName = "POSTED";
        }
        this.gygWorkerName.setText(gygWorkerName);
    }

    public void setGygFee(Double gygFee, String gygTime) {
        this.gygFee.setText("$" + String.valueOf(gygFee) + "/"+ gygTime);
    }

    public void setGygLocation(String gygLocation) {
        this.gygLocation.setText(gygLocation);
    }

    public void setGygKey(String gygKey) {
        this.gygKey = gygKey;
    }


    //Listen to all events. Switch through respective one
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete:
                doDelete();
                break;
            case R.id.hits:
                doHits();
                break;
            case R.id.edit:
                doEdit();
                break;
            default:
                doHits();
                break;
        }
    }


    //----------------------Helper Methods----------------//

    private void doDelete() {
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

        //Go into my_gygs array
        deleteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> matchSnapShot = dataSnapshot.getChildren();
                //Loop through each gyg
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

    private void doHits() {
        mClickListener.onHitClick(itemView, getAdapterPosition());
    }

    private void doEdit() {
        mClickListener.onEditClick(itemView, getAdapterPosition());
    }
}
