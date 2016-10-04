package com.chamayetu.chamayetu.register;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.chamayetu.chamayetu.R;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.register
 * Created by lusinabrian on 04/10/16.
 * Description:Implements the TextWatcher interface dealing with TextInputLayout
 */

public class MyTextWatcher implements TextWatcher {
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
