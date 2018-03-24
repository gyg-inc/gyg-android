package com.capstonegyg.gyg.UI.ViewGyg;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.capstonegyg.gyg.R;

/**
 * Created by hp-pc on 3/24/2018.
 */

public class ViewGygViewHolder extends RecyclerView.ViewHolder {

    TextView gygName, gygPosterName;

    public ViewGygViewHolder(View itemView) {
        super(itemView);

        gygName = itemView.findViewById(R.id.gyg_name);
        gygPosterName = itemView.findViewById(R.id.gyg_poster_name);
    }

    public void setGygName(String gygName) {
        this.gygName.setText(gygName);
    }

    public void setGygPosterName(String gygPosterName) {
        this.gygName.setText(gygPosterName);
    }
}
