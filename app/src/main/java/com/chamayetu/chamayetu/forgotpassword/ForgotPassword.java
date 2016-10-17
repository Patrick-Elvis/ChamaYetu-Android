package com.chamayetu.chamayetu.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.forgotpassword
 * Created by lusinabrian on 17/10/16.
 * Description: Handles Forgot password screen
 */

public class ForgotPassword extends AppCompatActivity implements ForgotPasswordView,View.OnClickListener{

    @BindView(R.id.cancel_return) Button cancelBtn;
    @BindView(R.id.submit_password_reset) Button submitPasswordReset;
    @BindView(R.id.forgot_email) EditText forgotEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword_layout);
        ButterKnife.bind(this);

        cancelBtn.setOnClickListener(this);
        submitPasswordReset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel_return:
                //return to login screen
                startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
                break;

            case R.id.submit_password_reset:
                /*submit the user email*/
                String email = forgotEmail.getText().toString().trim();

                break;
        }
    }

    @Override
    public void setEmailError() {

    }

    @Override
    public void displayProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void displayToast(String message, int messageType) {

    }
}
