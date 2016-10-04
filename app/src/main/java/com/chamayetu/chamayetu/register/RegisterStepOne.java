package com.chamayetu.chamayetu.register;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chamayetu.chamayetu.R;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.github.paolorotolo.appintro.ISlidePolicy;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.register
 * Created by lusinabrian on 03/10/16.
 * Description: Register the new user
 */

public class RegisterStepOne extends Fragment implements ISlidePolicy, ISlideBackgroundColorHolder{

    /*UI views*/
    @BindView(R.id.registerstep1_form_container) LinearLayout registerStep1Container;
    @BindView(R.id.signup_name_id) EditText signUpName;
    @BindView(R.id.signup_email_id) EditText signUpEmail;
    @BindView(R.id.signup_phoneNo_id) EditText signUpPhoneNo;
    @BindView(R.id.signup_password_id) EditText signUpPassword;
    @BindView(R.id.signup_retypepassword_id) EditText signUpRetypePassword;

    @BindView(R.id.signup_nametxtInput_id) TextInputLayout signUpNameTxtIn;
    @BindView(R.id.signup_emailtxtInput_id) TextInputLayout signUpEmailTxtInptLayout;
    @BindView(R.id.signup_phoneNoTxtInp_id) TextInputLayout signUpPhoneNoTxtIn;
    @BindView(R.id.signup_pass_txtInput_id) TextInputLayout signUpPassTxtInptLayout;
    @BindView(R.id.signup_retypepass_txtInput_id) TextInputLayout signUpRetypePasswordTxtIn;

    public RegisterStepOne(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.registerstep1_form, container, false);
        ButterKnife.bind(this,rootView);
        signUpEmail.addTextChangedListener(new MyTextWatcher(signUpEmail));
        signUpPassword.addTextChangedListener(new MyTextWatcher((signUpPassword)));

        return rootView;
    }

    /**Check if all the fields have passed to enable the user to proceed to the next slide.
     * If all pass, proceed to next slide, if one fails, display error to user and point to error
     * */
    @Override
    public boolean isPolicyRespected() {
        return false;
    }

    /**Notify the user of the unchecked requirements to proceed to next slide*/
    @Override
    public void onUserIllegallyRequestedNextPage() {
        Toast.makeText(getContext(), R.string.registerstep_slide_policy_error, Toast.LENGTH_SHORT).show();
    }

    /**overrides the color of the background color in the xml layout*/
    @Override
    public int getDefaultBackgroundColor() {
        return Color.parseColor("#FFDEAD");
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        if (registerStep1Container != null) {
            registerStep1Container.setBackgroundColor(backgroundColor);
        }
    }
}
