package com.chamayetu.chamayetu.forgotpassword;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.forgotpassword
 * Created by lusinabrian on 17/10/16.
 * Description:
 */
class ForgotPresenterImpl implements ForgotPresenter, ForgotPassInteractor.onSubmitListener{
    private Context context;
    private ForgotPassView forgotPassView;
    private ForgotPassInteractor forgotPassInteractor;
    private FirebaseAuth mAuth;

    ForgotPresenterImpl(Context context, ForgotPassView forgotPassView, FirebaseAuth mAuth){
        this.context = context;
        this.mAuth = mAuth;
        this.forgotPassView = forgotPassView;
        this.forgotPassInteractor = new ForgotPassInteractorImpl();
    }

    @Override
    public void validateUserCredentials(String email) {
        /*if the view is not null, display the progress*/
        if(forgotPassView != null){
            forgotPassView.displayProgress();
        }
        /*interactor will send the forgot password link to the email provided*/
        forgotPassInteractor.sendPasswordReset(context, email, this);
    }

    @Override
    public void onDestroy() {
        forgotPassView = null;
    }

    @Override
    public void onEmailError() {
        if(forgotPassView != null){
            forgotPassView.hideProgress();
            forgotPassView.setEmailError();
        }
    }

    @Override
    public void onSuccess() {
        if(forgotPassView != null){
            forgotPassView.navigateToLogin();
        }
    }

    @Override
    public void onTaskError(String message, int messageType) {
        if(forgotPassView != null){
            forgotPassView.hideProgress();
            forgotPassView.displayToast(message, messageType);
        }
    }
}
