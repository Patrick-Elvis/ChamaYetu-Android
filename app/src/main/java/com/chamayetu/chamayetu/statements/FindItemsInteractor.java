package com.chamayetu.chamayetu.statements;

import android.content.Context;

import com.chamayetu.chamayetu.adapters.FullStatementViewHolder;
import com.chamayetu.chamayetu.models.FullStatementModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.statements
 * Created by lusinabrian on 19/10/16.
 * Description:
 */

interface FindItemsInteractor {

    interface OnFinishedListener {
        /**when finished loading set the items to recycler*/
        void onFinished(Context context, FirebaseRecyclerAdapter<FullStatementModel, FullStatementViewHolder> statmentRecyclerAdapter, DatabaseReference mDatabaseReference);
    }

    void findItems(OnFinishedListener listener);
}
