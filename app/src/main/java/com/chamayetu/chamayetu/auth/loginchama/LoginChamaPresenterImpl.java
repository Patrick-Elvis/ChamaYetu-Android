package com.chamayetu.chamayetu.auth.loginchama;

import android.content.Context;
import com.chamayetu.chamayetu.adapters.FullStatementViewHolder;
import com.chamayetu.chamayetu.adapters.LoginChamaViewHolder;
import com.chamayetu.chamayetu.models.LoginChamaModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.auth.loginchama
 * Created by lusinabrian on 28/10/16.
 * Description: Implementation of {@link LoginChamaPresenter}
 */

class LoginChamaPresenterImpl implements LoginChamaPresenter, LoginChamaInteractor.OnFinishedListener{

    private LoginChamaView loginChamaView;
    private LoginChamaInteractor loginChamaInteractor;
    private DatabaseReference mDatabaseRef;
    private Context context;
    private String username;
    private FirebaseRecyclerAdapter<LoginChamaModel, LoginChamaViewHolder> loginChamaFirebaseRecyclerAdapter;

    LoginChamaPresenterImpl(Context context, String username, DatabaseReference mDatabaseRef, FirebaseRecyclerAdapter<LoginChamaModel, LoginChamaViewHolder> loginChamaFirebaseRecyclerAdapter,LoginChamaInteractor loginChamaInteractor, LoginChamaView loginChamaView){
        this.context = context;
        this.mDatabaseRef = mDatabaseRef;
        this.username = username;
        this.loginChamaView = loginChamaView;
        this.loginChamaFirebaseRecyclerAdapter = loginChamaFirebaseRecyclerAdapter;
        this.loginChamaInteractor = loginChamaInteractor;
    }

    @Override
    public void onResume() {
        if(loginChamaView != null){
            loginChamaView.showProgress();
        }
        //find items for the recyclerView
        loginChamaInteractor.findItems(
                username,
                context,
                loginChamaFirebaseRecyclerAdapter,
                mDatabaseRef,
                this);
    }

    @Override
    public void onChamaItemClicked(int position) {
        if(loginChamaView != null){
            loginChamaView.openMainActWithChama(position);
        }
    }

    @Override
    public void onDestroy() {
        loginChamaView = null;
    }

    @Override
    public void onFinished(FirebaseRecyclerAdapter<LoginChamaModel, LoginChamaViewHolder> loginChamaRecyAdapter) {
        if(loginChamaView != null){
            loginChamaView.setAdapter(loginChamaRecyAdapter);
            loginChamaView.hideProgress();
        }

    }
}
