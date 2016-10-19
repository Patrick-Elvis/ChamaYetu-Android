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

class StatementPresenterImpl implements StatementPresenter, FindItemsInteractor.OnFinishedListener {

    private FullStatementView fullStatementView;
    private FindItemsInteractor findItemsInteractor;
    private DatabaseReference mDatabaseRef;
    private Context context;
    private FirebaseRecyclerAdapter<FullStatementModel, FullStatementViewHolder> statementFirebaseRecyclerAdapter;

    StatementPresenterImpl(FirebaseRecyclerAdapter<FullStatementModel, FullStatementViewHolder> statementFirebaseRecyclerAdapter, Context context, FullStatementView fullStatementView, FindItemsInteractor findItemsInteractor, DatabaseReference mDatabaseRef){
        this.mDatabaseRef = mDatabaseRef;
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
        findItemsInteractor.findItems(this);
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
    public void onFinished(FullStatementModel items) {
        if (fullStatementView != null) {
            fullStatementView.setItems(items);
            fullStatementView.hideProgress();
        }
    }
}
