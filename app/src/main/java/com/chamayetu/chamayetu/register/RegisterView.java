package com.chamayetu.chamayetu.register;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.register
 * Created by lusinabrian on 15/10/16.
 * Description: Calls methods whenever there is user interaction in the RegisterUserActivity
 * */

public interface RegisterView {

    interface RegisterUser{
        /**display progress to user*/
        void showProgress();

        /**hide progress when the registration is complete*/
        void hideProgress();

        /**set email error if the email is invalid*/
        void setEmailError();

        void setFullNameError();

        /**set password error if the password is less than 6 characters or does not match the retype password*/
        void setPasswordError();

        void displayToastError(String message, int messageType);

        /**navigate to chama registration if all tests pass*/
        void navigateToChamaReg();

        /**display an error when the phone number is blank or is not a valid phone number*/
        void setPhoneNoError();
    }

    interface RegisterChama{

        /**set chama name error if the chama name already exists*/
        void setChamaNameError();

        /**if the member number is less than or equal to 0*/
        void setChamaMemberNumbers();

        /**set the error if the bank name is blank*/
        void setBankNameError();

        /**set an error if the bank account entry is blank*/
        void setBankAccountError();

        /**display progress to user*/
        void showProgress();

        /**hide progress when the registration is complete*/
        void hideProgress();

        void displayToastError(String message, int messageType);

        /**navigate to main activity once all the registration is done*/
        void navigateToMainActivity();
    }

}
