package com.chamayetu.chamayetu.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.chamayetu.chamayetu.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.register
 * Created by lusinabrian on 03/10/16.
 * Description: Register the new user
 */

public class RegisterStepOne extends Fragment {

    /*UI views*/
    @BindView(R.id.signup_email_id) EditText signUpEmail;
    @BindView(R.id.signup_password_id) EditText signUpPassword;
    @BindView(R.id.signup_emailtxtInput_id) TextInputLayout signUpEmailTxtInptLayout;
    @BindView(R.id.signup_pass_txtInput_id) TextInputLayout signUpPassTxtInptLayout;
    @BindView(R.id.signup_phoneNo_id) EditText signUpPhoneNo;
    @BindView(R.id.signup_phoneNoTxtInp_id) TextInputLayout signUpPhoneNoTxtIn;
    @BindView(R.id.signup_name_id) EditText signUpName;
    @BindView(R.id.signup_nametxtInput_id) TextInputLayout signUpNameTxtIn;
    @BindView(R.id.signup_retypepassword_id) EditText retypePassword;
    @BindView(R.id.signup_retypepass_txtInput_id) TextInputLayout retypePasswordTxtIn;

    public RegisterStepOne(){}

    public static Fragment newInstance(){
        RegisterStepOne registerStepOne = new RegisterStepOne();
        registerStepOne.setRetainInstance(true);
        return registerStepOne;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.registerstep1_form, container, false);
        ButterKnife.bind(this,rootView);

        return rootView;
    }
}
