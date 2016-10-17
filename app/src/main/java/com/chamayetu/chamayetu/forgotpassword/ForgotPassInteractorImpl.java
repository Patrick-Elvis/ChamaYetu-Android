package com.chamayetu.chamayetu.forgotpassword;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import com.google.firebase.auth.FirebaseAuth;
import com.sdsmdg.tastytoast.TastyToast;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.forgotpassword
 * Created by lusinabrian on 17/10/16.
 * Description: handles the implementation of the ForgotPasswordInteractor
 */
class ForgotPassInteractorImpl implements ForgotPassInteractor {
    private static final String TAG = ForgotPass.FORGOTPASS_TAG;

    @Override
    public void sendPasswordReset(Context context, String email, FirebaseAuth mAuth, onSubmitListener onSubmitListener) {
        boolean error = false;

        /*if the email is invalid, display an error to user*/
        if(!isValidEmail(email)){
            onSubmitListener.onEmailError();
            error = true;
        }

        /*else send password reset email*/
        if(!error){
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    /*proceed to next step*/
                    Log.d(TAG+"EmailReset:",String.valueOf(task.isSuccessful()));
                    onSubmitListener.onSuccess();
                }else{
                    /*display error to user*/
                    onSubmitListener.onTaskError("Error encountered, please try again.", TastyToast.ERROR);

                    /*log that sucker :D*/
                    Log.e(TAG+"EmailReset",task.getException().getMessage());
                }
            });
        }
    }

    /**validate user email
     * @return boolean
     **checks for a valid email address from a pattern*/
    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
