package com.chamayetu.chamayetu.login;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
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


    /**Handles Google Login, takes in GoogleSignInAccount from onActivityResult, gets token
     * checks if the task is complete and handles the process as required
     * @param context The context in which this method will run
     * @param account GoogleSignInAccount that will be retrieved from calling context
     * @param auth Firebase Auth that will be used to sign in this particular user to Firebase*/
    public static boolean handleGoogleLogin(GoogleSignInAccount account, FirebaseAuth auth, Context context){
        Log.d(TAG, "FirebaseWithGoogleLogin: " + account.getId());
        loginPresenter.showProgressDialog(context);
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        auth.signInWithCredential(credential).addOnCompleteListener((Activity)context, task -> {
            Log.d(TAG, "SignInWithCredentialComplete: "+ task.isSuccessful());
           if(!task.isSuccessful()){
               Log.d(TAG, "GoogleSignInFail: ", task.getException());
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
