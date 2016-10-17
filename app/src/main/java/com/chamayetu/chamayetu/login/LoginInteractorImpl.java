package com.chamayetu.chamayetu.login;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.sdsmdg.tastytoast.TastyToast;
import static com.chamayetu.chamayetu.utils.Contract.LOGINACT_TAG;
import static com.chamayetu.chamayetu.utils.UtilityMethods.isValidEmail;
import static com.chamayetu.chamayetu.utils.UtilityMethods.validateLoginPassword;

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

        /*if password is blank, display an error to user*/
        if(validateLoginPassword(password)){
           listener.onPasswordError();
            error = true;
        }

        /*if there is no error login in the user with their email and password*/
        if(!error){
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                Log.d(LOGINACT_TAG, "signInWithEmail:onComplete "+ task.isSuccessful());
                if(!task.isSuccessful()){
                    Log.d(LOGINACT_TAG, "SignInWithEmail: ", task.getException());
                    listener.onTaskError("Email or password is invalid", TastyToast.ERROR);
                } else {
                    listener.onSuccess();
                }
            });
        }
    }

    @Override
    public void loginUserWithGoogle(Context context, GoogleSignInAccount googleSignInAccount, FirebaseAuth mAuth, OnLoginFinishedListener listener) {
        Log.d(LOGINACT_TAG, "FirebaseWithGoogleLogin: " + googleSignInAccount.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

        mAuth.signInWithCredential(credential).addOnCompleteListener((Activity)context, task -> {
            Log.d(LOGINACT_TAG, "SignInWithCredentialComplete: "+ task.isSuccessful());
            if(!task.isSuccessful()){
                Log.d(LOGINACT_TAG, "GoogleSignInFail: ", task.getException());
                listener.onTaskError("Failed to sign in, please try again", TastyToast.ERROR);
            }else{
                listener.onSuccess();
            }
        });
    }
}
