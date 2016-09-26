package com.chamayetu.chamayetu.login;

import android.content.Context;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.login
 * Created by lusinabrian on 26/09/16.
 * Description: Presenter class for the LoginActivity
 */

public interface LoginPresenter {

    /**Display login progress to user in the context they are currently in*/
    void showPogressDialog(Context context);
    void dismissPogressDialog(Context context);
    void displayErrorMessage(Context context, String ErrorMessage);
}
