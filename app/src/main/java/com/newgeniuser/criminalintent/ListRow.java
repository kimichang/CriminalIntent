package com.newgeniuser.criminalintent;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by lenovo on 2016/6/27.
 */
public class ListRow extends RecyclerView.ViewHolder{
    public ImageView mThumbnail;
    public ListRow(View view){
        super(view);
        mThumbnail=(ImageView)view.findViewById(R.id.thumbnail);
    }
}
