package com.chamayetu.chamayetu.auth.loginchama;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.adapters.LoginChamaViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import static com.chamayetu.chamayetu.utils.Contract.ANONYMOUS;
import static com.chamayetu.chamayetu.utils.Contract.CHAMA_GROUPS;
import static com.chamayetu.chamayetu.utils.Contract.LOGINCHAMA_TAG;
import static com.chamayetu.chamayetu.utils.Contract.USERS_NODE;
import static com.chamayetu.chamayetu.utils.Contract.USER_NAME_SP_KEY_PREF;
import static com.chamayetu.chamayetu.utils.Contract.USER_NAME_SP_PREF;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.auth.loginchama
 * Created by lusinabrian on 29/10/16.
 * Description: Implementation of {@link LoginChamaInteractor}
 */

class LoginChamaInteractorImpl implements LoginChamaInteractor {

    @Override
    public void findItems(String username, Context context, FirebaseRecyclerAdapter<String, LoginChamaViewHolder> loginChamaRecyAdapter, DatabaseReference mDatabaseReference, OnFinishedListener listener) {

        loginChamaRecyAdapter = new FirebaseRecyclerAdapter<String, LoginChamaViewHolder>(
                String.class,
                R.layout.chama_login_item_layout,
                LoginChamaViewHolder.class,
                mDatabaseReference.child(USERS_NODE).child(username).child(CHAMA_GROUPS)
        ) {
            @Override
            protected void populateViewHolder(LoginChamaViewHolder viewHolder,
                                              String model, int position) {
                viewHolder.bind(model);

                viewHolder.mView.setOnClickListener(v -> {
                    Log.d(LOGINCHAMA_TAG+"Interactor", "Clicked on: " + String.valueOf(position));
                    Log.d(LOGINCHAMA_TAG+"Interactor", "Clicked on: " + model);
                    SharedPreferences mUsername = context.getSharedPreferences(USER_NAME_SP_PREF, Context.MODE_PRIVATE);
                    //Retrieve the username from the SharedPreference file
                    String username = mUsername.getString(USER_NAME_SP_KEY_PREF,ANONYMOUS);
                    Log.d(LOGINCHAMA_TAG+"InteractorUser", "Username" + username);

                });
            }
        };
        //pass on the adapter to the interactor
        listener.onFinished(loginChamaRecyAdapter);
    }
}
