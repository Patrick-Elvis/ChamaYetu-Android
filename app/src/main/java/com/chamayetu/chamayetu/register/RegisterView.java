package com.chamayetu.chamayetu.register;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.register
 * Created by lusinabrian on 15/10/16.
 * Description: Calls methods whenever there is user interaction in the RegisterActivity
 * */

public interface RegisterView {

    /**display progress to user*/
    void showProgress();

    /**hide progress when the registration is complete*/
    void hideProgress();

    /**set email error if the email is invalid*/
    void setEmailError();

    /**set password error if the password is less than 6 characters or does not match the retype password*/
    void setPasswordError();

    /**navigate to main activity if all tests pass*/
    void navigateToMain();
}
