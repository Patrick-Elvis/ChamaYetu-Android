package com.chamayetu.chamayetu.auth.loginuser;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.login
 * Created by lusinabrian on 17/10/16.
 * Description: interface for the LoginActivity
 */

 interface LoginView {

    /**display an error if the email entered is invalid or empty*/
    void setEmailError();

    /**Set password error*/
    void setPasswordError();

    /**Display progress while the user email and password are being submitted*/
    void displayProgress();

    /**dismiss the progress dialog in case of any error encountered, or in case of success*/
    void hideProgress();

    /**navigates to Main Activity screen when successful only if the user has 1 chama,
     * if they have several chamas, proceed to chama login instead
     * @param toMain boolean value to determine whether to navigate to main activity or chama login
     * @param username the username to pass on to the next activity*/
    void navigateToMain(boolean toMain, String username);

    /**display toast on success or error
     * @param message The message to display
     * @param messageType the error type to display*/
    void displayToast(String message, int messageType);
}
