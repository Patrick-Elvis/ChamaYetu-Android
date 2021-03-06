package com.chamayetu.chamayetu.auth.register;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.auth.loginuser.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.tastytoast.TastyToast;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.login
 * Created by lusinabrian on 30/09/16.
 * Description: Register Activity class for a new user
 */
public class RegisterUserActivity extends AppCompatActivity implements RegisterView.RegisterUser, View.OnClickListener{

    public static final String REGISTERACT_TAG = RegisterUserActivity.class.getSimpleName();

    /*UI views*/
    @BindView(R.id.signup_name_id) EditText signUpName;
    @BindView(R.id.signup_email_id) EditText signUpEmail;
    @BindView(R.id.signup_phoneNo_id) EditText signUpPhoneNo;
    @BindView(R.id.signup_password_id) EditText signUpPassword;
    @BindView(R.id.signup_retypepassword_id) EditText retypePassword;
    @BindView(R.id.signup_button_id) Button signUpButton;
    @BindView(R.id.cv_add) CardView cardViewAdd;
    @BindView(R.id.fab) FloatingActionButton floatingActionButton;

    private FirebaseAuth mAuth;
    private RegisterPresenter registerPresenter;
    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user_activity);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }
        floatingActionButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);

        registerPresenter = new RegisterPresenterImpl(this, RegisterUserActivity.this, mAuth, mDatabaseRef);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cardViewAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }

        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cardViewAdd, cardViewAdd.getWidth()/2,0, floatingActionButton.getWidth() / 2, cardViewAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cardViewAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cardViewAdd,cardViewAdd.getWidth()/2,0, cardViewAdd.getHeight(), floatingActionButton.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cardViewAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                floatingActionButton.setImageResource(R.drawable.plus);
                RegisterUserActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    public void onBackPressed() {
        animateRevealClose();
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        registerPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        materialDialog = new MaterialDialog.Builder(RegisterUserActivity.this)
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
            finish();
        }
    }

    @Override
    public void setEmailError() {
        signUpEmail.setError(getString(R.string.err_msg_email));
    }

    @Override
    public void setPasswordError() {
        signUpPassword.setError(getString(R.string.err_msg_password_match));
        retypePassword.setError(getString(R.string.err_msg_password_match));
    }

    @Override
    public void displayToastError(String message, int messageType) {
        TastyToast.makeText(this, message, TastyToast.LENGTH_SHORT, messageType);
    }

    @Override
    public void setPhoneNoError(){
        signUpPhoneNo.setError(getString(R.string.err_msg_phone));
    }

    @Override
    public void setFullNameError(){
        signUpName.setError(getString(R.string.err_msg_name));
    }

    @Override
    public void navigateToChamaReg() {
        startActivity(new Intent(this, RegisterChamaActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup_button_id:
                String password = signUpPassword.getText().toString().trim();
                String email = signUpEmail.getText().toString().trim();
                String name = signUpName.getText().toString();
                String retypePass = retypePassword.getText().toString();
                String phoneNumber = signUpPhoneNo.getText().toString();
                long phone = 0;

                //handle empty phone input
                if(phoneNumber.isEmpty()){
                    try{
                        phone = Long.parseLong(phoneNumber);
                    }catch (NumberFormatException nfe){
                        Log.d(REGISTERACT_TAG, nfe.getMessage());
                        phone = 0;
                    }
                }
                registerPresenter.validateCredentials(name, email, phone, password, retypePass);
                break;

            case R.id.fab:
                /*check for versions*/
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    animateRevealClose();
                }else{
                    startActivity(new Intent(RegisterUserActivity.this, LoginActivity.class));
                }
                break;
        }
    }
}
