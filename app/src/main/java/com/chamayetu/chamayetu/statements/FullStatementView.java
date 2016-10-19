package com.chamayetu.chamayetu.statements;

import com.chamayetu.chamayetu.adapters.FullStatementViewHolder;
import com.chamayetu.chamayetu.models.FullStatementModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.util.List;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.statements
 * Created by lusinabrian on 19/10/16.
 * Description: View of the Full statement view*/

interface FullStatementView {
    /**show progress to user when fetching items for recyclerview*/
    void showProgress();

    /**hide progress when loading is complete*/
    void hideProgress();

    /**set the items to the recyclerView*/
    void setAdapter(FirebaseRecyclerAdapter<FullStatementModel, FullStatementViewHolder> statmentRecyclerAdapter);

    void openItemActivity();

    /**display message to user if error occurs*/
    void showMessage(String message, int messageType);
}
