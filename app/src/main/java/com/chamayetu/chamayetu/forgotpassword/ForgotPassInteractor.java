package com.chamayetu.chamayetu.forgotpassword;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.forgotpassword
 * Created by lusinabrian on 17/10/16.
 * Description:
 */

interface ForgotPassInteractor {

    interface onSubmitListener{
        void onEmailError();

        void onSuccess();

        void onTaskError(String message, int messageType);
    }

    /**
     * Sends the email reset in the provided context
     * @param context the context in which this email will be reset
     * @param email the email to send the email reset*/
    void sendPasswordReset(Context context, String email, FirebaseAuth mAuth, onSubmitListener onSubmitListener);

}
