package com.chamayetu.chamayetu.auth.loginuser;

import android.content.Context;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;


/**
 * ChamaYetu
 * com.chamayetu.chamayetu.login
 * Created by lusinabrian on 17/10/16.
 * Description:
 */

interface LoginInteractor {

    /**Interface to handle Login with email and password*/
    interface OnLoginFinishedListener{
        /**what should happen whtn the email is invalid*/
        void onEmailError();

        /**What should happen when the password is invalid**/
        void onPasswordError();

        /**on successfull operation navigate to next activity*/
        void onSuccess();

        /**display toast error when user encounters error when executing FirebaseAuth*/
        void onTaskError(String message, int messageType);
    }


    /**registers a new chama to the database adding the name and the members
     * @param context context in which this function will run
     * @param email user email
     * @param password password of the user
     * @param mAuth FirebaseAuth to assist with user login
     * @param listener The listenr which will handle when the user finished the login session*/
    void loginUser(Context context, String email, String password, FirebaseAuth mAuth, OnLoginFinishedListener listener);

    /**Login the user with their Google account*/
    void loginUserWithGoogle(Context context, GoogleSignInAccount googleSignInAccount,FirebaseAuth mAuth, OnLoginFinishedListener listener);

}
