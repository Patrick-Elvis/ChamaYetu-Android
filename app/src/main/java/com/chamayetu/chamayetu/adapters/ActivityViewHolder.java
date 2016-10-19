package com.chamayetu.chamayetu.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.models.ActivityModel;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.adapters
 * Created by lusinabrian on 13/10/16.
 * Description: Adapter for the Activity of the Chama
 */

public class ActivityViewHolder extends RecyclerView.ViewHolder{

    public TextView personName, activityName, activityDate,amount;

    public ActivityViewHolder(View itemView) {
        super(itemView);
        personName = (TextView) itemView.findViewById(R.id.chamaactivityperson_name);
        activityName = (TextView) itemView.findViewById(R.id.chamaactivity_type);
        activityDate = (TextView)itemView.findViewById(R.id.chamaactivity_date);
        amount = (TextView)itemView.findViewById(R.id.chamaactivity_amount);
    }
    public void bind(ActivityModel activityModel){
            personName.setText(activityModel.getPerson());
            activityName.setText(activityModel.getActivityType());
            activityDate.setText(activityModel.getDate());
            amount.setText("Ksh. " + String.valueOf(activityModel.getAmount()));
        }
}

