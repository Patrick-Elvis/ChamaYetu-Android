package com.chamayetu.chamayetu.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.models.ChamaGroupsModel;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.useraccount
 * Created by lusinabrian on 30/09/16.
 * Description: Recycler Adapter for the User's chama*/

public class UserChamaViewHolder extends RecyclerView.ViewHolder{
    public TextView chamaName;

    public UserChamaViewHolder(View itemView) {
        super(itemView);
        chamaName = (TextView) itemView.findViewById(R.id.userchama_group_name);
    }

    /*bind the views to the models*/
    public void bind(ChamaGroupsModel chamaGroupsModel){
        chamaGroupsModel.getChamaGroups();
    }
}
