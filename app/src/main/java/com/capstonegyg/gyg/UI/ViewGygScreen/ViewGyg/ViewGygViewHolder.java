package com.capstonegyg.gyg.UI.ViewGygScreen.ViewGyg;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.capstonegyg.gyg.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Isuru Nanayakkara on 3/24/2018.
 */

public class ViewGygViewHolder extends RecyclerView.ViewHolder {

    TextView gygName, gygPosterName, gygFee, gygLocation;

    //---------------------------------Click Listeners----------------------------------//

    private ViewGygViewHolder.ClickListener mClickListener;

    //Interface to send callbacks...
    public interface ClickListener{
        public void onItemClick(View view, int position);
    }

    public void setOnClickListener(ViewGygViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

    //------------------------------End Click Listeners--------------------------------//

    public ViewGygViewHolder(View itemView) {
        super(itemView);

        gygName = itemView.findViewById(R.id.gyg_name);
        gygPosterName = itemView.findViewById(R.id.gyg_poster_name);
        gygFee = itemView.findViewById(R.id.gyg_fee);
        gygLocation = itemView.findViewById(R.id.gyg_location);

        //dataEntries = new ArrayList<>(3);

        //Add a click listener to Android
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });
    }

    public void setGygName(String gygName) {
        this.gygName.setText(gygName);
    }

    public void setGygPosterName(String gygPosterName) {
        this.gygPosterName.setText(gygPosterName);
    }

    public void setGygFee(Double gygFee, String gygTime) {
        this.gygFee.setText("$" + String.valueOf(gygFee) + "/"+ gygTime);
    }

    public void setGygLocation(String gygLocation) {
        this.gygLocation.setText(gygLocation);
    }
}
