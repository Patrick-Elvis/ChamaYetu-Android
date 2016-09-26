package com.chamayetu.chamayetu.login;

import android.content.Context;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.login
 * Created by lusinabrian on 26/09/16.
 * Description: Presenter class for the LoginActivity
 */

public interface LoginPresenter {

    /**Display login progress to user in the context they are currently in
     * @param context context in which to dissplay the progress dialog*/
    void showPogressDialog(Context context);

    /**Dismiss the Progress Dialog depending on the sucess of the response, display message
     * @param context context to run
     * @param isSuccess if the process is not successful display {@code displayErrorMessage}
     *                 else display {@code displaySuccessMessage}**/
    void dismissPogressDialog(Context context, boolean isSuccess);


    /**
     * The Error message to display. This messge will be custom to the Login method used.
     * @param context Context in which to display the error message
     * @param ErrorMessage The error message to display to the uer*/
    void displayErrorMessage(Context context, String ErrorMessage);

    /**
     * The success message to display to user,
     * @param context The context to run
     * @param message The success message to display, which will be the user name**/
    void displaySuccessMessage(Context context, String message);

}
