package com.chamayetu.chamayetu.login;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcelable;
import android.view.View;

import com.chamayetu.chamayetu.R;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.SuperToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;
import com.google.firebase.auth.FirebaseAuth;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.login
 * Created by lusinabrian on 26/09/16.
 * Description: Default implementation of the LoginPresenter
 */

public class LoginPresenterImpl implements LoginPresenter {
    private SweetAlertDialog pDialog;
    private SuperToast superToast;
    public LoginPresenterImpl(){}

    @Override
    public void showProgressDialog(Context context) {
        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    public void dismissProgressDialog(Context context, boolean isSuccess) {
        if(pDialog.isShowing() && isSuccess){
            pDialog.setTitleText("Success!");
            pDialog.dismissWithAnimation();
        }else{
            pDialog = new SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE);
            pDialog.setTitleText("Authentication Failed. Please try again");
            pDialog.dismissWithAnimation();
        }
    }

    @Override
    public void displayErrorMessage(Context context, String ErrorMessage) {
        superToast = new SuperToast(context);
        superToast.setDuration(Style.DURATION_LONG);
        superToast.setText(ErrorMessage);
        superToast.show();
    }

    @Override
    public void displaySuccessMessage(Context context, String message) {
        superToast = new SuperToast(context);
        superToast.setDuration(Style.DURATION_LONG);
        superToast.setText(message);
        superToast.show();
    }

    @Override
    public void retry(Context context) {
        //LoginAuthHandler.handleFacebookLogin(token, FirebaseAuth.getInstance(),context);
    }

    @Override
    public void isNetworkBeforeLoginAttempt(Context context) {

    }

}
