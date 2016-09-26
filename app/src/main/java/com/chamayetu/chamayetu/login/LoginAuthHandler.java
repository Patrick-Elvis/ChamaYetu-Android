package com.chamayetu.chamayetu.login;

import android.content.Context;
import android.util.Log;

import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseAuth;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.login
 * Created by lusinabrian on 26/09/16.
 * Description: Handles Login for {@link LoginActivity}
 */

public class LoginAuthHandler extends LoginPresenterImpl {
    boolean success = false;
    public static String TAG = LoginActivity.LOGINACT_TAG;

    public LoginAuthHandler(){}

    public static boolean handleFacebookLogin(AccessToken token, FirebaseAuth firebaseAuth, Context context){
        Log.d(TAG, "FirebaseWithFacebookLogin:"+token);

    }
}
