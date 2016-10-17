package com.chamayetu.chamayetu.login;

import android.content.Context;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.login
 * Created by lusinabrian on 26/09/16.
 * Description: Default implementation of the LoginPresenter
 */

class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnLoginFinishedListener {
    private Context context;
    private LoginView loginView;
    private FirebaseAuth mAuth;
    private LoginInteractor loginInteractor;

    LoginPresenterImpl(Context context, LoginView loginView, FirebaseAuth mAuth){
        this.context = context;
        this.loginView = loginView;
        this.mAuth = mAuth;
        this.loginInteractor = new LoginInteractorImpl();
    }

    @Override
    public void validateUserCredentials(String email, String password) {
        if(loginView != null){
            loginView.displayProgress();
        }
        loginInteractor.loginUser(context, email,password,mAuth,this);
    }

    @Override
    public void validateGoogleLogin(GoogleSignInAccount googleSignInAccount, FirebaseAuth mAuth, Context context) {
        if(loginView != null){
            loginView.displayProgress();
        }

        /**login the user with Google*/
        loginInteractor.loginUserWithGoogle(context,googleSignInAccount,mAuth,this);
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }


    @Override
    public void onEmailError() {
        if(loginView != null){
            loginView.hideProgress();
            loginView.setEmailError();
        }
    }

    @Override
    public void onPasswordError() {
        if(loginView != null){
            loginView.hideProgress();
            loginView.setPasswordError();
        }
    }

    @Override
    public void onSuccess() {
        if(loginView != null){
            loginView.hideProgress();
            loginView.navigateToMain();
        }
    }

    @Override
    public void onTaskError(String message, int messageType) {
        if(loginView != null){
            loginView.hideProgress();
            loginView.displayToast(message, messageType);
        }
    }
}
