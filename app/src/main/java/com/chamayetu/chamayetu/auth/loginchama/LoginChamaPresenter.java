package com.chamayetu.chamayetu.auth.loginchama;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.auth.loginchama
 * Created by lusinabrian on 28/10/16.
 * Description:
 */

interface LoginChamaPresenter {

    /**when activity is resumed*/
    void onResume();

    /** performs actions on user click, passes the position to implementer*/
    void onChamaItemClicked(int position);

    void onDestroy();
}
