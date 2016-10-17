package com.chamayetu.chamayetu.login;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

import static com.chamayetu.chamayetu.utils.UtilityMethods.isValidEmail;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.login
 * Created by lusinabrian on 17/10/16.
 * Description: Interactor implementation of {@link LoginInteractor}
 */

class LoginInteractorImpl implements LoginInteractor{

    @Override
    public void loginUser(Context context, String email, String password, FirebaseAuth mAuth, OnLoginFinishedListener listener) {
        boolean error = false;

        /*if email is not valid, display an error*/
        if(!isValidEmail(email)){
            listener.onEmailError();
            error = true;
        }


    }
}
