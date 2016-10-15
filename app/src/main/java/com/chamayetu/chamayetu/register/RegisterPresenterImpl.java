package com.chamayetu.chamayetu.register;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.registeruser
 * Created by lusinabrian on 15/10/16.
 * Description:Presenter implementation calls view methods to update the UI by calling view interface.
 */

public class RegisterPresenterImpl implements RegisterPresenter, RegisterInteractor.OnRegistrationFinishedListener, RegisterInteractor.OnRegisterNewChamaFinishedListener {

    private RegisterView.RegisterChama registerChamaView;
    private RegisterView.RegisterUser registerUserView;
    private RegisterInteractor registerInteractor;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    private Context context;

    public RegisterPresenterImpl(RegisterView.RegisterUser registerUserView, Context context, FirebaseAuth mAuth, DatabaseReference mDatabaseRef) {
        this.registerUserView = registerUserView;
        this.registerInteractor = new RegisterInteractorImpl();
        this.mAuth = mAuth;
        this.mDatabaseReference = mDatabaseRef;
        this.context = context;
    }

    public RegisterPresenterImpl(RegisterView.RegisterChama registerChamaView, Context context, FirebaseAuth mAuth, DatabaseReference mDatabaseRef) {
        this.registerChamaView = registerChamaView;
        this.registerInteractor = new RegisterInteractorImpl();
        this.mAuth = mAuth;
        this.mDatabaseReference = mDatabaseRef;
        this.context = context;
    }

    
    @Override
    public void validateCredentials(String name, String email, long phoneNumber, String password, String retypePassword) {
        if(registerUserView !=null){
            registerUserView.showProgress();
        }

        /*register the new user*/
        registerInteractor.registerNewUser(context,name,email,password,retypePassword,phoneNumber, mAuth, mDatabaseReference, this);
    }

    @Override
    public void validateChamaCredentials(String chamaName, int memberNo, String bankName, int accountNo) {
        if(registerChamaView !=null){
            registerChamaView.showProgress();
        }

        /*register the new Chama*/
        registerInteractor.registerNewChama(context,chamaName,String.valueOf(memberNo),bankName,accountNo,mAuth, mDatabaseReference, this);
    }

    @Override
    public void onDestroy() {
        registerUserView = null;
    }

    @Override
    public void onNameError() {
        if (registerUserView != null) {
            registerUserView.setFullNameError();
            registerUserView.hideProgress();
        }
    }

    @Override
    public void onEmailError() {
        if (registerUserView != null) {
            registerUserView.setEmailError();
            registerUserView.hideProgress();
        }
    }

    @Override
    public void onPhoneError() {
        if (registerUserView != null) {
            registerUserView.setPhoneNoError();
            registerUserView.hideProgress();
        }
    }

    @Override
    public void onPasswordError() {
        if (registerUserView != null) {
            registerUserView.setPasswordError();
            registerUserView.hideProgress();
        }
    }


    @Override
    public void onTaskError(String message, int messageType) {
        if(registerUserView != null){
            registerUserView.displayToastError(message, messageType);
            registerUserView.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        if (registerUserView != null) {
            registerUserView.navigateToChamaReg();
        }
    }


    @Override
    public void onChamaNameError() {
        if (registerChamaView != null) {
            registerChamaView.setChamaNameError();
            registerChamaView.hideProgress();
        }
    }

    @Override
    public void onChamaMemberError() {
        if (registerChamaView != null) {
            registerChamaView.setChamaMemberNumbers();
            registerChamaView.hideProgress();
        }
    }

    @Override
    public void onChamaBankNameError() {
        if (registerChamaView != null) {
            registerChamaView.setBankNameError();
            registerChamaView.hideProgress();
        }
    }

    @Override
    public void onChamaAccountError() {
        if (registerChamaView != null) {
            registerChamaView.setBankAccountError();
            registerChamaView.hideProgress();
        }
    }

    @Override
    public void onChamaSuccess() {
        if (registerChamaView != null) {
            registerChamaView.navigateToMainActivity();
        }
    }

    @Override
    public void chamaNameExistsError(String message, int messageType) {
        if (registerChamaView != null) {
            registerChamaView.displayToastError(message, messageType);
            registerChamaView.hideProgress();
        }
    }
}
