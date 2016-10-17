package com.chamayetu.chamayetu.login;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.chamayetu.chamayetu.R;
import com.google.firebase.auth.FirebaseAuth;

import cn.pedant.SweetAlert.SweetAlertDialog;

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

    LoginPresenterImpl(Context context, LoginView loginView, FirebaseAuth mAuth){
        this.context = context;
        this.loginView = loginView;
        this.mAuth = mAuth;
    }

    @Override
    public void validateUserCredentials(String email, String password) {
        if(loginView != null){
            loginView.displayProgress();
        }
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
