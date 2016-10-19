package com.chamayetu.chamayetu.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.models.FullStatementModel;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.adapters
 * Created by lusinabrian on 19/10/16.
 * Description: Adapter handling data received from Statementnode
 */

public class FullStatementViewHolder extends RecyclerView.ViewHolder{
    public TextView date, transactor, details,amount;
    public CircleImageView itemImg;

    public FullStatementViewHolder(View itemView) {
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

