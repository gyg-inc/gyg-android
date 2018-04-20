package com.capstonegyg.gyg.UI.MyGygs;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.capstonegyg.gyg.R;

public class MyGygsViewHolder extends RecyclerView.ViewHolder {
    TextView gygName, gygPosterName, gygFee, gygLocation;
    String gygDescription, gygTime, gygCategory;

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

    public MyGygsViewHolder(View itemView) {
        super(itemView);

        gygName = itemView.findViewById(R.id.gyg_name);
        gygPosterName = itemView.findViewById(R.id.gyg_poster_name);
        gygFee = itemView.findViewById(R.id.gyg_fee);
        gygLocation = itemView.findViewById(R.id.gyg_location);

        //Add a click listener to Android
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle dataBundle = new Bundle();
                dataBundle.putString("GYG_NAME", gygName.getText().toString());
                dataBundle.putString("GYG_POSTER_NAME", gygPosterName.getText().toString());
                dataBundle.putString("GYG_FEE", gygFee.getText().toString());
                dataBundle.putString("GYG_LOCATION", gygLocation.getText().toString());
                dataBundle.putString("GYG_DESCRIPTION", gygPosterName.getText().toString());
                dataBundle.putString("GYG_TIME", gygPosterName.getText().toString());
                dataBundle.putString("GYG_CATEGORYN", gygPosterName.getText().toString());
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
