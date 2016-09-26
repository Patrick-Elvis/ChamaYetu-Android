package com.chamayetu.chamayetu.login;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.facebook.AccessToken;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.login
 * Created by lusinabrian on 26/09/16.
 * Description: Handles Login for {@link LoginActivity}
 */

public class LoginAuthHandler{
    private static boolean success = false;
    private static String TAG = LoginActivity.LOGINACT_TAG;
    private static LoginPresenterImpl loginPresenter = new LoginPresenterImpl();

    public LoginAuthHandler(LoginPresenterImpl loginPresenter){
        LoginAuthHandler.loginPresenter = loginPresenter;
    }

    public static boolean handleFacebookLogin(AccessToken token, FirebaseAuth firebaseAuth, Context context){
        Log.d(TAG, "FirebaseWithFacebookLogin:"+token);
        loginPresenter.showPogressDialog(context);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(
                (Activity) context, task -> {
                    Log.d(TAG, "FacebookSignInCredential:"+task.isSuccessful());
                    if(!task.isSuccessful()){
                        Log.w(TAG, "FacebookSignInFail:",task.getException());
                        success = false;
                    }else{
                        success = task.isSuccessful();
                    }
                });
        return success;
    }
}
