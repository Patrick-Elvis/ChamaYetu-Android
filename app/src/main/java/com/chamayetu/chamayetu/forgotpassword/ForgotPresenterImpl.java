package com.chamayetu.chamayetu.forgotpassword;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.forgotpassword
 * Created by lusinabrian on 17/10/16.
 * Description:
 */

public class ForgotPresenterImpl implements ForgotPresenter, ForgotPassInteractor.onSubmitListener{
    private Context context;
    private ForgotPasswordView forgotPasswordView;
    private ForgotPassInteractor forgotPassInteractor;
    private FirebaseAuth mAuth;

    ForgotPresenterImpl(Context context, ForgotPasswordView forgotPasswordView, FirebaseAuth mAuth){
        this.context = context;
        this.mAuth = mAuth;
        this.forgotPasswordView = forgotPasswordView;
        this.forgotPassInteractor = new ForgotPassInteractorImpl();
    }

    @Override
    public void validateUserCredentials(String email) {
        /*if the view is not null, display the progress*/
        if(forgotPasswordView != null){
            forgotPasswordView.displayProgress();
        }
        /*interactor will send the forgot password link to the email provided*/
        forgotPassInteractor.sendPasswordReset(context, email, this);
    }

    @Override
    public void onDestroy() {
        forgotPasswordView = null;
    }

    @Override
    public void onEmailError() {
        if(forgotPasswordView != null){
            forgotPasswordView.hideProgress();
            forgotPasswordView.setEmailError();
        }
    }
}
