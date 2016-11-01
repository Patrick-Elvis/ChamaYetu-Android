package com.chamayetu.chamayetu.auth.loginchama;

import android.content.Context;

import com.chamayetu.chamayetu.adapters.LoginChamaViewHolder;
import com.chamayetu.chamayetu.models.LoginChamaModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.auth.loginchama
 * Created by lusinabrian on 29/10/16.
 * Description:Interactor interface for
 */

interface LoginChamaInteractor {

    interface OnFinishedListener {
        /**when finished loading set the items to recycler*/
        void onFinished(FirebaseRecyclerAdapter<String, LoginChamaViewHolder> loginChamaRecyAdapter);
    }

    /**
     * Finds the items in the DatabaseReference and loads them into the adapter in the current context
     * @param username the username of the current user
     * @param context context in which the items will be found
     * @param loginChamaRecyAdapter The adapter in which the data items will be loaded
     * @param mDatabaseReference The database reference where the data will be fetched
     * @param listener The interface which will load the data from the adapter into the recyclerView */
    void findItems(String username, Context context, FirebaseRecyclerAdapter<String, LoginChamaViewHolder> loginChamaRecyAdapter, DatabaseReference mDatabaseReference, OnFinishedListener listener);
}
