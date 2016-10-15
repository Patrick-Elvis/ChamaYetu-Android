package com.chamayetu.chamayetu.registeruser;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.registeruser
 * Created by lusinabrian on 15/10/16.
 * Description:
 * Interactor implementation returns the results or just returns the control to presenter implementation by calling listener methods
 */

public interface RegisterInteractor {

    interface OnRegistrationFinishedListener {
        void onNameError();

        void onEmailError();

        void onPhoneError();

        void onPasswordError();

        void onSuccess();

    }

    void login(String name, String password, OnRegistrationFinishedListener listener);

}
