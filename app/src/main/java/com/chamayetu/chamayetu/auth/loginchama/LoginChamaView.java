package com.chamayetu.chamayetu.auth.loginchama;

import com.chamayetu.chamayetu.adapters.LoginChamaViewHolder;
import com.chamayetu.chamayetu.models.LoginChamaModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.auth.loginchama
 * Created by lusinabrian on 28/10/16.
 * Description:View interface for {@link LoginChamaView}
 */

interface LoginChamaView {
    /**show progress to user when fetching items for recyclerview*/
    void showProgress();

    /**hide progress when loading is complete*/
    void hideProgress();

    /**set the items to the recyclerView*/
    void setAdapter(FirebaseRecyclerAdapter<LoginChamaModel, LoginChamaViewHolder> loginChamaRecyclerAdapter);

    void openMainActWithChama(int position);

    /**display message to user if error occurs*/
    void showMessage(String message, int messageType);
}
