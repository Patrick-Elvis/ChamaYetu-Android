package com.chamayetu.chamayetu.registeruser;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.registeruser
 * Created by lusinabrian on 15/10/16.
 * Description:Presenter implementation calls view methods to update the UI by calling view interface.
 */

public class RegisterPresenterImpl implements RegisterPresenter,RegisterInteractor.OnRegistrationFinishedListener {

    private RegisterView registerView;
    private RegisterInteractor registerInteractor;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;

    public RegisterPresenterImpl(RegisterView registerView, FirebaseAuth mAuth, DatabaseReference mDatabaseRef) {
        this.registerView = registerView;
        this.registerInteractor = new RegisterInteractorImpl();
        this.mAuth = mAuth;
        this.mDatabaseReference = mDatabaseRef;
    }


    @Override
    public void validateCredentials(String name, String email, long phoneNumber, String password, String retypePassword) {
        if(registerView !=null){
            registerView.showProgress();
        }

        /*register the new user*/
        registerInteractor.registerNewUser(name,email,password,retypePassword,phoneNumber, mAuth, mDatabaseReference, this);
    }

    @Override
    public void onDestroy() {
        registerView = null;
    }

    @Override
    public void onNameError() {
        if (registerView != null) {
            registerView.setFullNameError();
            registerView.hideProgress();
        }
    }

    @Override
    public void onEmailError() {
        if (registerView != null) {
            registerView.setEmailError();
            registerView.hideProgress();
        }
    }

    @Override
    public void onPhoneError() {
        if (registerView != null) {
            registerView.setPhoneNoError();
            registerView.hideProgress();
        }
    }

    @Override
    public void onPasswordError() {
        if (registerView != null) {
            registerView.setPasswordError();
            registerView.hideProgress();
        }
    }


    @Override
    public void onTaskError(String message, int messageType) {
        if(registerView != null){
            registerView.displayToastError(message, messageType);
            registerView.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        if (registerView != null) {
            registerView.navigateToChamaReg();
        }
    }

}
