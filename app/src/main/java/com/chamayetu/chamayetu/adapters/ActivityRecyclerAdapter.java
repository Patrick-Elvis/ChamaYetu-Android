package com.chamayetu.chamayetu.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.adapters
 * Created by lusinabrian on 13/10/16.
 * Description: Adapter for the Activity of the Chama
 */

public class ActivityRecyclerAdapter extends RecyclerView.Adapter<> {
    private Context mContext;
    private List<ActorModel> actorModelList;
    public int itemLayout;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
