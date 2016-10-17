package com.chamayetu.chamayetu.login;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.login
 * Created by lusinabrian on 26/09/16.
 * Description: Presenter class for the LoginActivity
 */

interface LoginPresenter {

    /**pass the email and password to the interactor for validation*/
    void validateUserCredentials(String email, String password);

    void onDestroy();
}
