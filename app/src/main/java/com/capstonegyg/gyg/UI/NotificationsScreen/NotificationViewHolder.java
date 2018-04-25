package com.capstonegyg.gyg.UI.NotificationsScreen;

import android.app.Notification;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.capstonegyg.gyg.R;
import com.capstonegyg.gyg.UI.ViewGygScreen.ViewGyg.ViewGygViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Shawn on 4/24/18.
 */

public class NotificationViewHolder extends RecyclerView.ViewHolder{
    private LinearLayout parentView;
    private TextView hitUserName, gygName;
    //---------------------------------Click Listeners----------------------------------//

    private NotificationViewHolder.ClickListener mClickListener;

    //Interface to send callbacks...
    public interface ClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnClickListener(NotificationViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

    //------------------------------End Click Listeners--------------------------------//

    public NotificationViewHolder(View itemView) {
        super(itemView);

        //should be notification_list_layout
        parentView = itemView.findViewById(R.id.gyg_list_layout_container);
        gygName = itemView.findViewById(R.id.gyg_name);
        hitUserName = itemView.findViewById(R.id.gyg_worker_name);

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
    public void setHitUserName(String hitUserName){this.hitUserName.setText(hitUserName);}


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
