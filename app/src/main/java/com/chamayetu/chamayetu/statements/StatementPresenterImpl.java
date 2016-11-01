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
 * Description: IMplementation of {@link StatementPresenter}
 */

class StatementPresenterImpl implements StatementPresenter, FindItemsInteractor.OnFinishedListener {

    private FullStatementView fullStatementView;
    private FindItemsInteractor findItemsInteractor;
    private DatabaseReference mDatabaseRef;
    private Context context;
    private String chamaName;
    private FirebaseRecyclerAdapter<FullStatementModel, FullStatementViewHolder> statementFirebaseRecyclerAdapter;

    StatementPresenterImpl(String chamaName, FirebaseRecyclerAdapter<FullStatementModel, FullStatementViewHolder> statementFirebaseRecyclerAdapter, Context context, FullStatementView fullStatementView, FindItemsInteractor findItemsInteractor, DatabaseReference mDatabaseRef){
        this.mDatabaseRef = mDatabaseRef;
        this.chamaName = chamaName;
        this.fullStatementView = fullStatementView;
        this.findItemsInteractor = findItemsInteractor;
        this.context = context;
        this.statementFirebaseRecyclerAdapter = statementFirebaseRecyclerAdapter;
    }

    @Override
    public void onResume() {
        if (fullStatementView != null) {
            fullStatementView.showProgress();
        }

        /*find the items for the recycler view*/
        findItemsInteractor.findItems(
                chamaName,
                context,
                statementFirebaseRecyclerAdapter,
                mDatabaseRef,
                this
        );
    }

    @Override
    public void onItemClicked(int position) {
        if (fullStatementView != null) {
            /*open more details of the item*/
            fullStatementView.openItemActivity();
        }
    }

    @Override
    public void onDestroy() {
        fullStatementView = null;
    }

    public FullStatementView getFullStatementView(){
        return fullStatementView;
    }

    @Override
    public void onFinished(FirebaseRecyclerAdapter<FullStatementModel, FullStatementViewHolder> statmentRecyclerAdapter) {
        if (fullStatementView != null) {
            fullStatementView.setAdapter(statmentRecyclerAdapter);
            fullStatementView.hideProgress();
        }
    }
}
