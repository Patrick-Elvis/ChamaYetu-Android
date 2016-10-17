package com.chamayetu.chamayetu.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.sdsmdg.tastytoast.TastyToast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.forgotpassword
 * Created by lusinabrian on 17/10/16.
 * Description: Handles Forgot password screen
 */

public class ForgotPass extends AppCompatActivity implements ForgotPassView,View.OnClickListener{
    public static final String FORGOTPASS_TAG = ForgotPass.class.getSimpleName();

    @BindView(R.id.cancel_return) Button cancelBtn;
    @BindView(R.id.submit_password_reset) Button submitPasswordReset;
    @BindView(R.id.forgot_email) EditText forgotEmail;
    private ForgotPresenter forgotPresenter;
    private MaterialDialog materialDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword_layout);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

        /*create an object of the PresenterImpl and pass it to the presenter*/
        forgotPresenter = new ForgotPresenterImpl(ForgotPass.this, this, mAuth);

        cancelBtn.setOnClickListener(this);
        submitPasswordReset.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        forgotPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel_return:
                //return to login screen
                startActivity(new Intent(ForgotPass.this, LoginActivity.class));
                break;

            case R.id.submit_password_reset:
                /*submit the user email*/
                String email = forgotEmail.getText().toString().trim();
                forgotPresenter.validateUserCredentials(email);
                break;
        }
    }

    @Override
    public void setEmailError() {
        forgotEmail.setError(getString(R.string.error_invalid_email));
    }

    @Override
    public void displayProgress() {
        materialDialog = new MaterialDialog.Builder(ForgotPass.this)
                .title(R.string.progress_dialog_title)
                .theme(Theme.DARK)
                .content(R.string.please_wait)
                .progress(true, 0)
                .show();
    }

    @Override
    public void hideProgress() {
        if(materialDialog.isShowing()){
            materialDialog.dismiss();
        }
    }

    @Override
    public void navigateToLogin() {
        /*start the next activity, the login screen*/
        startActivity(new Intent(ForgotPass.this, LoginActivity.class));
    }

    @Override
    public void displayToast(String message, int messageType) {
        TastyToast.makeText(ForgotPass.this,message, TastyToast.LENGTH_SHORT, messageType);
    }
}
