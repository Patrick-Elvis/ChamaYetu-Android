package com.chamayetu.chamayetu.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.models.FullStatementModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.adapters
 * Created by lusinabrian on 19/10/16.
 * Description: Adapter handling data received from Statementnode
 */

public class FullStatementAdapter extends RecyclerView.Adapter<FullStatementAdapter.ViewHolder> {
    private Context mContext;
    private List<FullStatementModel> fullStatementModelList;
    private int itemLayout;

    // constructor
    public FullStatementAdapter(Context mContext, List<FullStatementModel> fullStatementModelList, int itemLayout){
        this.mContext = mContext;
        this.itemLayout = itemLayout;
        this.fullStatementModelList = fullStatementModelList;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FullStatementModel fullStatementModel = fullStatementModelList.get(position);
        holder.itemView.setTag(fullStatementModel);
        holder.bind(fullStatementModel);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView date, transactor, details,amount;
        public CircleImageView itemImg;

        public ViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.full_statement_item_date);
            transactor = (TextView) itemView.findViewById(R.id.full_statement_item_transactor);
            details = (TextView)itemView.findViewById(R.id.full_statement_item_details);
            amount = (TextView)itemView.findViewById(R.id.full_statement_item_amount);
            itemImg = (CircleImageView)itemView.findViewById(R.id.full_statement_item_img);
        }

        /*bind the views to the models*/
        public void bind(FullStatementModel fullStatementModel){
            date.setText(fullStatementModel.getDate());
            transactor.setText(fullStatementModel.getTransactor());
            details.setText(fullStatementModel.getDate());
            amount.setText(String.valueOf(fullStatementModel.getAmount()) + " Ksh" );
        }
    }

    public void add(FullStatementModel itemModel, int position){
        fullStatementModelList.add(position,itemModel);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return fullStatementModelList.size();
    }
}
