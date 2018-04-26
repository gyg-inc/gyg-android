package com.capstonegyg.gyg.UI.NotificationsScreen;


import android.app.Notification;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.braintreepayments.api.dropin.DropInRequest;
import com.capstonegyg.gyg.logic.payment;

import com.bumptech.glide.Glide;
import com.capstonegyg.gyg.R;
import com.capstonegyg.gyg.UI.ViewGygScreen.ViewGyg.ViewGygViewHolder;

import java.util.Timer;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by Shawn on 4/24/18.
 */

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    private LinearLayout parentView;
    private TextView hitUserName, gygName;
    private Button startJob;
    int flag = 0;
    Timer timer = new Timer();
    //---------------------------------Click Listeners----------------------------------//

    private NotificationViewHolder.ClickListener mClickListener;

    //Interface to send callbacks...
    public interface ClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnClickListener(NotificationViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

    public void sendPayment(View view){
        //onBraintreeSubmit(view);
    }


    //------------------------------End Click Listeners--------------------------------//

    public NotificationViewHolder(final View itemView) {
        super(itemView);

        parentView = itemView.findViewById(R.id.notifications_recycler);
        gygName = itemView.findViewById(R.id.gyg_name);
        hitUserName = itemView.findViewById(R.id.gyg_worker_name);
        startJob = itemView.findViewById(R.id.start_job);

        startJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 0){
                    startJob.setText("End Job");
                    flag = 1;
                }
                else{
                    //startJob.setText("Start Job");
                    //flag = 0;
                    sendPayment(itemView);
                }
            }
        });

        //Add a click listener to Android
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });

    }

//    public void onClick(View v) {
//
//        if(flag == 0){
//            //startJob.setText("End Job");
//            flag = 1;
//        }
//        else{
//            //startJob.setText("Start Job");
//            flag = 0;
//        }
//    }


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
