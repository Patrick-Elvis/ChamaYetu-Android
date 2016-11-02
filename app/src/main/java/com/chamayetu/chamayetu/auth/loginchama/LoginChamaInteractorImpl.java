package com.chamayetu.chamayetu.auth.loginchama;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.adapters.LoginChamaViewHolder;
import com.chamayetu.chamayetu.main.MainActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import static com.chamayetu.chamayetu.utils.Contract.ANONYMOUS;
import static com.chamayetu.chamayetu.utils.Contract.CHAMA_GROUPS;
import static com.chamayetu.chamayetu.utils.Contract.LOGINCHAMA_TAG;
import static com.chamayetu.chamayetu.utils.Contract.USERS_NODE;
import static com.chamayetu.chamayetu.utils.Contract.USER_LOGGED_IN_CHAMA_AC;
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
        SharedPreferences mUsername = context.getSharedPreferences(USER_NAME_SP_PREF, Context.MODE_PRIVATE);
        String usernameFromPref = mUsername.getString(USER_NAME_SP_KEY_PREF,ANONYMOUS);

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
                    //store the chama account in a String variable
                    String chamaAccount = viewHolder.chamaName.getText().toString();
                    chamaAccount = chamaAccount.substring(0, chamaAccount.length()-8);

                    Log.d(LOGINCHAMA_TAG+"Interactor", chamaAccount);
                    // start a new intent and log in the user to the selected chama account
                    /*Intent loginToMain = new Intent(context, MainActivity.class);
                    loginToMain.putExtra(USER_LOGGED_IN_CHAMA_AC, chamaAccount);
                    context.startActivity(loginToMain);*/
                });
            }
        };
        //pass on the adapter to the interactor
        listener.onFinished(loginChamaRecyAdapter);
    }
}
