package com.capstonegyg.gyg.UI.MyGygsScreen.MyGygsTab;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.capstonegyg.gyg.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyGygsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView gygName,gygWorkerName, gygFee, gygLocation;
    private Button deleteButton, hitsButton, editButton;
    //private int visibility;

    String gygKey;

    //-------------Click Listeners-------------//

    private MyGygsViewHolder.ClickListener mClickListener;

    //Interface to send callbacks...
    public interface ClickListener{
        public void onItemClick(View view, int position);
    }

    public void setOnClickListener(MyGygsViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

    //-------------End Click Listeners-------------//


    public MyGygsViewHolder(View itemView){
        super(itemView);

        //Text views
        gygName = itemView.findViewById(R.id.gyg_name);
        gygWorkerName = itemView.findViewById(R.id.gyg_worker_name);
        gygFee = itemView.findViewById(R.id.gyg_fee);
        gygLocation = itemView.findViewById(R.id.gyg_location);

        // Add a click listener to Android
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bundle dataBundle = new Bundle();
//                dataBundle.putString("GYG_NAME", gygName.getText().toString());
//                dataBundle.putString("GYG_FEE", gygFee.getText().toString());
//                dataBundle.putString("GYG_LOCATION", gygLocation.getText().toString());
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });

        // Delete Gyg if user chooses to do so
        TextView delete = itemView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference userDBR = database.getReference();

                userDBR.child("gygs").child(gygKey).removeValue();
            }
        });

        //temView.setVisibility(visibility);
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

//    public void setVisibility(int visibility) {
//        this.visibility = visibility;
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hits:

                break;
        }
    }
}
