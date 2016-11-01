package com.chamayetu.chamayetu.auth.loginchama;

import android.content.Context;
import android.util.Log;

import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.adapters.LoginChamaViewHolder;
import com.chamayetu.chamayetu.models.LoginChamaModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import static com.chamayetu.chamayetu.utils.Contract.CHAMA_GROUPS;
import static com.chamayetu.chamayetu.utils.Contract.LOGINCHAMA_TAG;
import static com.chamayetu.chamayetu.utils.Contract.USERS_NODE;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.auth.loginchama
 * Created by lusinabrian on 29/10/16.
 * Description: Implementation of {@link LoginChamaInteractor}
 */

class LoginChamaInteractorImpl implements LoginChamaInteractor {
    
    @Override
    public void findItems(String username, Context context, FirebaseRecyclerAdapter<LoginChamaModel, LoginChamaViewHolder> loginChamaRecyAdapter, DatabaseReference mDatabaseReference, OnFinishedListener listener) {

        loginChamaRecyAdapter = new FirebaseRecyclerAdapter<LoginChamaModel, LoginChamaViewHolder>(
                LoginChamaModel.class,
                R.layout.chama_login_item_layout,
                LoginChamaViewHolder.class,
                mDatabaseReference.child(USERS_NODE).child(username).child(CHAMA_GROUPS)
        ) {
            @Override
            protected void populateViewHolder(LoginChamaViewHolder viewHolder,
                                              LoginChamaModel model, int position) {
                viewHolder.bind(model);
            }
        };
        //pass on the adapter to the interactor
        listener.onFinished(loginChamaRecyAdapter);
    }
}
