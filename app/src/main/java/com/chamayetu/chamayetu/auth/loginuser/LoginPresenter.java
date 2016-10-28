package com.chamayetu.chamayetu.auth.loginuser;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.login
 * Created by lusinabrian on 26/09/16.
 * Description: Presenter class for the LoginActivity
 */

interface LoginPresenter {

    /**pass the email and password to the interactor for validation*/
    void validateUserCredentials(String email, String password);

    /**validates Google Login with the user*/
    void validateGoogleLogin(GoogleSignInAccount googleSignInAccount, FirebaseAuth mAuth, Context context);

    void onDestroy();
}
