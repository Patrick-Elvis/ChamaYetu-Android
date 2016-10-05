package com.chamayetu.chamayetu.register;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.chamayetu.chamayetu.R;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.github.paolorotolo.appintro.ISlidePolicy;
import com.sdsmdg.tastytoast.TastyToast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.register
 * Created by lusinabrian on 03/10/16.
 * Description: Register the new user
 * The {@link ISlidePolicy} validates the registration form fields checking whether all are field to proceed to next slide.
 * First obtain all the UI references, then add textChangeListeners to the EditTexts
 * Validate email and password fields to check whether they are valid
 * Validate phone number and check whether the name field is empty
 * if all fields pass, allow user to proceed to next screen for registration
 */

public class RegisterStepOne extends Fragment implements ISlidePolicy, ISlideBackgroundColorHolder{
    public static final String REGISTERSTEP1_TAG = RegisterStepOne.class.getSimpleName();

    /*UI views*/
    @BindView(R.id.registerstep1_form_container) LinearLayout registerStep1Container;
    @BindView(R.id.signup_name_id) EditText signUpName;
    @BindView(R.id.signup_email_id) EditText signUpEmail;
    @BindView(R.id.signup_phoneNo_id) EditText signUpPhoneNo;
    @BindView(R.id.signup_password_id) EditText signUpPassword;

    @BindView(R.id.signup_nametxtInput_id) TextInputLayout signUpNameTxtIn;
    @BindView(R.id.signup_emailtxtInput_id) TextInputLayout signUpEmailTxtInptLayout;
    @BindView(R.id.signup_phoneNoTxtInp_id) TextInputLayout signUpPhoneNoTxtIn;
    @BindView(R.id.signup_pass_txtInput_id) TextInputLayout signUpPassTxtInptLayout;

    public RegisterStepOne(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.registerstep1_form, container, false);
        ButterKnife.bind(this,rootView);
        signUpEmail.addTextChangedListener(new MyTextWatcher(signUpEmail));
        signUpPassword.addTextChangedListener(new MyTextWatcher((signUpPassword)));
        signUpPhoneNo.addTextChangedListener(new MyTextWatcher(signUpPhoneNo));
        signUpPassword.addTextChangedListener(new MyTextWatcher(signUpPassword));
        return rootView;
    }

    /**Check if all the fields have passed to enable the user to proceed to the next slide.
     * If all pass, proceed to next slide, if one fails, display error to user and point to error
     * */
    @Override
    public boolean isPolicyRespected() {
        return validateAllFields();
    }

    /**Performs a check on all the fields, confirming whether all the fields have been met*/
    private boolean validateAllFields() {
        boolean success = false;
        SharedPreferences userCredentials = getActivity().getSharedPreferences("UserCredentials",0);
        SharedPreferences.Editor editor= userCredentials.edit();

        //check if password and email have been validated
        if((validateEmail() && validatePassword()) && (validatePhoneAndName())){
            success = true;
            Log.d(REGISTERSTEP1_TAG,"Registration Status: " + success);
            //store the user credentials in a shared preference
            String fullName = signUpName.getText().toString().trim();
            String email = signUpEmail.getText().toString().trim();
            String phoneNo = signUpPhoneNo.getText().toString().trim();
            String password = signUpPassword.getText().toString();

            editor.putString("FULLNAME",fullName);
            editor.putString("EMAIL",email);
            editor.putString("PHONENO", phoneNo);
            editor.putString("PASSWORD", password);
            Log.d(REGISTERSTEP1_TAG+"Editor", editor.toString());
            //apply the edits
            editor.apply();
        }
        return success;
    }


    /**Notify the user of the unchecked requirements to proceed to next slide*/
    @Override
    public void onUserIllegallyRequestedNextPage() {
        TastyToast.makeText(getContext(), getResources().getString(R.string.registerstep_slide_policy_error),TastyToast.LENGTH_SHORT,TastyToast.ERROR);
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

    private class MyTextWatcher implements TextWatcher {
        private View view;

        public MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()){
                case R.id.signup_email_id:
                    validateEmail();
                    break;

                case R.id.signup_password_id:
                    validatePassword();
                    break;
            }
        }
    }

    /**VALIDATE user password
     * Check if user password is valid, if the user password is empty, display an error
     * If the user retype password and passwords do not match, display an error to user
     * else, if all checks out, then return true
     * @return boolean*/
    private boolean validatePassword() {
        String password = signUpPassword.getText().toString();
        if(password.isEmpty()){
            signUpPassTxtInptLayout.setError(getString(R.string.err_msg_password));
            requestFocus(signUpPassword);
            return false;
        }
        //if password is less than 6 characters, display error message
        if(password.length() < 6) {
            signUpPassTxtInptLayout.setError(getString(R.string.err_msg_password_short));
            requestFocus(signUpPassword);
        }
        else{
            signUpPassTxtInptLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * validate user email
     * Obtain the user email from the email convert to string and trim it to remove whitespace
     * if the string is empty or is a not a valid email address display an error to the user
     * else disable errors and return true
     * @return boolean*/
    private boolean validateEmail() {
        String email = signUpEmail.getText().toString().trim();
        // if empty or is valid display an error
        if(email.isEmpty() || !isValidEmail(email)){
            signUpEmailTxtInptLayout.setError(getString(R.string.err_msg_email));
            requestFocus(signUpEmail);
            return false;
        }else{
            signUpPassTxtInptLayout.setErrorEnabled(false);
            return true;
        }
    }

    /**Validates whether the phone number and the user name have been entered(not empty)*/
    private boolean validatePhoneAndName() {
        String fullName = signUpName.getText().toString().trim();
        String phoneNumber = signUpPhoneNo.getText().toString().trim();

        //check if fields have been filled out
        if(fullName.isEmpty()){
            signUpNameTxtIn.setError(getString(R.string.err_msg_name));
            requestFocus(signUpName);
            return false;
        }else if(phoneNumber.isEmpty()){
            signUpPhoneNo.setError(getString(R.string.err_msg_phone));
            requestFocus(signUpPhoneNo);
            return false;
        }else{
            signUpNameTxtIn.setErrorEnabled(false);
            signUpPhoneNoTxtIn.setErrorEnabled(false);
        }
        return true;
    }

    /**checks for a valid email address from a pattern
     * If the email field is not empty and the email matches an email pattern then return true
     * @return boolean*/
    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**sets the focus to the edit text with the error message*/
    private void requestFocus(View view) {
        if(view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
