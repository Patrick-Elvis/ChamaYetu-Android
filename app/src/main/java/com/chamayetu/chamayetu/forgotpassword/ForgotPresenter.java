package com.chamayetu.chamayetu.forgotpassword;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.forgotpassword
 * Created by lusinabrian on 17/10/16.
 * Description: Presenter to submit the email data to the models
 */

public interface ForgotPresenter {

    /**pass the email to the interactor*/
    void validateUserCredentials(String email);

    void onDestroy();
}
