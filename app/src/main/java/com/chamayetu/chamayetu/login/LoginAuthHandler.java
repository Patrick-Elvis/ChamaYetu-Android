package com.chamayetu.chamayetu.login;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

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
        loginPresenter.showProgressDialog(context);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(
                (Activity) context, task -> {
                    Log.d(TAG, "FacebookSignInCredential:"+task.isSuccessful());
                    if(!task.isSuccessful()){
                        Log.w(TAG, "FacebookSignInFail:",task.getException());
                        success = false;
                        loginPresenter.dismissProgressDialog(context, success);
                        loginPresenter.displayErrorMessage(context, "Failed to login to Facebook");
                    }else{
                        success = task.isSuccessful();
                    }
                });
        return success;
    }

    public static boolean handleGoogleLogin(GoogleSignInAccount account, FirebaseAuth auth, Context context){
        Log.d(TAG, "FirebaseWithGoogleLogin: " + account.getId());
        loginPresenter.showProgressDialog(context);
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        auth.signInWithCredential(credential).addOnCompleteListener((Activity)context, task -> {
            Log.d(TAG, "SignInWithCredentialComplete"+ task.isSuccessful());
           if(!task.isSuccessful()){
               Log.d(TAG, "GoogleSignInFail:", task.getException());
               success = !task.isSuccessful();
               loginPresenter.dismissProgressDialog(context, success);
               loginPresenter.displayErrorMessage(context, "Failed to login with Google");
           }else{
               success = task.isSuccessful();
           }
        });
        return success;
    }
}
