package com.chamayetu.chamayetu.statements;

import android.content.Context;

import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.adapters.FullStatementViewHolder;
import com.chamayetu.chamayetu.models.FullStatementModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import static com.chamayetu.chamayetu.utils.Contract.FULL_STATEMENT_NODE;
import static com.chamayetu.chamayetu.utils.Contract.STATEMENT_NODE;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.statements
 * Created by lusinabrian on 19/10/16.
 * Description: Find items interactor which implements {@link FindItemsInteractor}
 */

class FindItemsInteractorImpl implements FindItemsInteractor {

    @Override
    public void findItems(String chamaName,Context context, FirebaseRecyclerAdapter<FullStatementModel, FullStatementViewHolder> statmentRecyclerAdapter, DatabaseReference mDatabaseReference, OnFinishedListener listener) {
        /*initialize the FirebaseRecyclerAdapter*/
        statmentRecyclerAdapter = new FirebaseRecyclerAdapter<FullStatementModel,
                FullStatementViewHolder>(
                FullStatementModel.class,
                R.layout.fullstatement_item_layout,
                FullStatementViewHolder.class,
                mDatabaseReference.child(STATEMENT_NODE).child(chamaName).child(FULL_STATEMENT_NODE)
        )
        {
            @Override
            protected void populateViewHolder(FullStatementViewHolder viewHolder,
                                              FullStatementModel model, int position) {
                viewHolder.bind(model);
            }
        };

        /*set the adapter when loading is finished*/
        listener.onFinished(statmentRecyclerAdapter);
    }
}
