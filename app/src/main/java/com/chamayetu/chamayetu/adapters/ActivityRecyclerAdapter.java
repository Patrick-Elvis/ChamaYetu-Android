package com.chamayetu.chamayetu.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.models.ActivityModel;

import java.util.List;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.adapters
 * Created by lusinabrian on 13/10/16.
 * Description: Adapter for the Activity of the Chama
 */

public class ActivityRecyclerAdapter extends RecyclerView.Adapter<ActivityRecyclerAdapter.ViewHolder> {
    private Context mContext;
    private List<ActivityModel> actitivyModelList;
    private int itemLayout;

    // constructor
    public ActivityRecyclerAdapter(Context mContext, List<ActivityModel> actitivyModelList, int itemLayout){
        this.mContext = mContext;
        this.itemLayout = itemLayout;
        this.actitivyModelList = actitivyModelList;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ActivityModel activityModel = actitivyModelList.get(position);
        holder.itemView.setTag(activityModel);
        holder.bind(activityModel);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView personName, activityName, activityDate,amount;

        public ViewHolder(View itemView) {
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

    public void add(ActivityModel itemModel, int position){
        actitivyModelList.add(position,itemModel);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return actitivyModelList.size();
    }
}
