package com.chamayetu.chamayetu.forgotpassword;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.forgotpassword
 * Created by lusinabrian on 17/10/16.
 * Description: View interface to handle interaction with user
 */

public interface ForgotPasswordView {

    /**display an error if the email entered is invalid or empty*/
    void setEmailError();

    /**Display progress while the user email is being submitted*/
    void displayProgress();

    /**dismiss the progress dialog in case of any error encountered, or in case of success*/
    void hideProgress();

    /**display toast on success or error
     * @param message The message to display
     * @param messageType the error type to display*/
    void displayToast(String message, int messageType);
}
