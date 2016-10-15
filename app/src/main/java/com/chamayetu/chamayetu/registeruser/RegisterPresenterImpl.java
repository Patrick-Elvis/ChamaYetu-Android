package com.chamayetu.chamayetu.registeruser;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.registeruser
 * Created by lusinabrian on 15/10/16.
 * Description:Presenter implementation calls view methods to update the UI by calling view interface.
 */

public class RegisterPresenterImpl implements RegisterPresenter {
    private RegisterView registerView;
    private RegisterInteractor registerInteractor;

    public RegisterPresenterImpl(RegisterView registerView) {
        this.registerView = registerView;
        this.registerInteractor = new RLoginInteractorImpl();
    }


    @Override
    public void validateCredentials(String name, String email, long phoneNumber, String password, String retypePassword) {

    }

    @Override
    public void onDestroy() {

    }
}
