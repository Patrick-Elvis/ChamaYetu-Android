package com.chamayetu.chamayetu.auth.loginchama;

import android.content.Context;
import com.chamayetu.chamayetu.adapters.FullStatementViewHolder;
import com.chamayetu.chamayetu.models.LoginChamaModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.auth.loginchama
 * Created by lusinabrian on 28/10/16.
 * Description: Implementation of {@link LoginChamaPresenter}
 */

class LoginChamaPresenterImpl implements LoginChamaPresenter {

    private LoginChamaView loginChamaView;
    private DatabaseReference mDatabaseRef;
    private Context context;
    private String username;
    private FirebaseRecyclerAdapter<LoginChamaModel, FullStatementViewHolder> loginChamaFirebaseRecyclerAdapter;

    LoginChamaPresenterImpl(Context context, String username,DatabaseReference mDatabaseRef, FirebaseRecyclerAdapter<LoginChamaModel, FullStatementViewHolder> loginChamaFirebaseRecyclerAdapter){
        this.context = context;
        this.mDatabaseRef = mDatabaseRef;
        this.username = username;
        this.loginChamaFirebaseRecyclerAdapter = loginChamaFirebaseRecyclerAdapter;
    }

    @Override
    public void onResume() {


    }

    @Override
    public void onChamaItemClicked(int position) {

    }

    @Override
    public void onDestroy() {

    }
}
