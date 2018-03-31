package com.capstonegyg.gyg.UI.Profile;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.capstonegyg.gyg.R;

/**
 * Created by Shawn on 3/31/18.
 */

public class ProfileViewHolder extends RecyclerView.ViewHolder{
    TextView userName, qrCode;

    public ProfileViewHolder(View itemView) {
        super(itemView);
        userName = itemView.findViewById(R.id.gyg_name);
        qrCode = itemView.findViewById(R.id.gyg_poster_name);
    }

    public void setUserName(String userName) {
        this.userName.setText(userName);
    }

    public void setQRCode(int qrCode) {
        this.qrCode.setText(qrCode);
    }
}
