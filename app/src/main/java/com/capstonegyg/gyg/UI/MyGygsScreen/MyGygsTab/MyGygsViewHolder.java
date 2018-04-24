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

public class MyGygsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView gygName, gygWorkerName, gygFee, gygLocation;
    private TextView deleteButton, hitsButton, editButton;

    //-------------Click Listeners-------------//

    private MyGygsViewHolder.ClickListener mClickListener;

    //Interface to send callbacks...
    public interface ClickListener {
        void onHitClick(View view, int position);

        void onEditClick(View view, int position);

        void onDeleteClick(View view, int position);
    }

    public void setOnClickListener(MyGygsViewHolder.ClickListener clickListener) {
        mClickListener = clickListener;
    }

    //-------------End Click Listeners-------------//


    public MyGygsViewHolder(View itemView) {
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
        if (gygWorkerName.equals("")) {
            gygWorkerName = "POSTED";
        }
        this.gygWorkerName.setText(gygWorkerName);
    }

    public void setGygFee(Double gygFee, String gygTime) {
        this.gygFee.setText("$" + String.valueOf(gygFee) + "/" + gygTime);
    }

    public void setGygLocation(String gygLocation) {
        this.gygLocation.setText(gygLocation);
    }

    //Listen to all events. Switch through respective one
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete:
                mClickListener.onDeleteClick(itemView, getAdapterPosition());
                break;
            case R.id.hits:
                mClickListener.onHitClick(itemView, getAdapterPosition());
                break;
            case R.id.edit:
                mClickListener.onEditClick(itemView, getAdapterPosition());
                break;
            default:
                mClickListener.onHitClick(itemView, getAdapterPosition());
                break;
        }
    }
}