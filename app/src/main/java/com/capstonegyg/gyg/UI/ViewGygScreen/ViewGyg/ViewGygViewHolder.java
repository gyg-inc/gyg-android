package com.capstonegyg.gyg.UI.ViewGygScreen.ViewGyg;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.capstonegyg.gyg.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Isuru Nanayakkara on 3/24/2018.
 */

public class ViewGygViewHolder extends RecyclerView.ViewHolder {

    private LinearLayout parentView;
    private TextView gygName, gygPosterName, gygFee, gygLocation;
    private CircleImageView gygPosterFace;

    //---------------------------------Click Listeners----------------------------------//

    private ViewGygViewHolder.ClickListener mClickListener;

    //Interface to send callbacks...
    public interface ClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnClickListener(ViewGygViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

    //------------------------------End Click Listeners--------------------------------//

    public ViewGygViewHolder(View itemView) {
        super(itemView);

        parentView = itemView.findViewById(R.id.gyg_list_layout_container);
        gygName = itemView.findViewById(R.id.gyg_name);
        gygPosterName = itemView.findViewById(R.id.gyg_poster_name);
        gygFee = itemView.findViewById(R.id.gyg_fee);
        gygLocation = itemView.findViewById(R.id.gyg_location);
        gygPosterFace = itemView.findViewById(R.id.poster_face);

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

    public void setGygPosterFace(String uri) {
        Glide
                .with(gygPosterFace.getContext())
                .load(uri)
                //.override(75, 75)
                .into(gygPosterFace);
    }

    //Set the viewholder visibility
    public void setVisibility(int visibility) {
        RecyclerView.LayoutParams param = (RecyclerView.LayoutParams)itemView.getLayoutParams();
        if (visibility == View.VISIBLE){
            param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            param.width = LinearLayout.LayoutParams.MATCH_PARENT;
            itemView.setVisibility(View.VISIBLE);
        }
        //Completely gone
        else{
            itemView.setVisibility(View.GONE);
            param.height = 0;
            param.width = 0;
        }
        itemView.setLayoutParams(param);
    }
}
