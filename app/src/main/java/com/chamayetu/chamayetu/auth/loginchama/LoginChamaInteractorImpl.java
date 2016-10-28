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
 * Description:
 */

public class LoginChamaInteractorImpl implements LoginChamaInteractor {
    @Override
    public void findItems(String username, Context context, FirebaseRecyclerAdapter<LoginChamaModel, LoginChamaViewHolder> loginChamaRecyAdapter, DatabaseReference mDatabaseReference, OnFinishedListener listener) {

    }
}
