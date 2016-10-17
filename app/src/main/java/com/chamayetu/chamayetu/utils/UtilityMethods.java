package com.chamayetu.chamayetu.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import com.sdsmdg.tastytoast.TastyToast;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.utils
 * Created by lusinabrian on 14/10/16.
 * Description: Contains methods that are used all over the application
 * Simply override the methods
 */

public class UtilityMethods {

    public static void showToast(Context context, String message, int messageType) {
        TastyToast.makeText(context, message, TastyToast.LENGTH_SHORT, messageType);
    }

    /**validate user email
     * @return boolean
     **checks for a valid email address from a pattern*/
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**VALIDATE user password
     * Check if user password is valid, if the user password is empty, display an error
     * If the user retype password and passwords do not match, display an error to user
     * else, if all checks out, then return true
     * @return boolean*/
    public static boolean validateRegisterPassword(String password, String retypePassword) {
        if(TextUtils.isEmpty(password)|| TextUtils.isEmpty(retypePassword)){
            return false;
        }
        /*if the passwords do not match*/
        else if(!password.equals(retypePassword)){
            return false;
        }
        return true;
    }

    /**
     * Validates the login password checking if the password is blank
     * @param password takes in the password of the user as a parameter*/
    public static boolean validateLoginPassword(String password){
        return TextUtils.isEmpty(password);
    }


}
