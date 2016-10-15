package com.chamayetu.chamayetu.register;

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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chamayetu.chamayetu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
public class RegisterActivity extends AppCompatActivity implements RegisterView, View.OnClickListener{
    public static final String REGISTERACT_TAG = RegisterActivity.class.getSimpleName();

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
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabaseRef;
    private RegisterPresenter registerPresenter;
    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeractivity);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                Log.d(REGISTERACT_TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                // User is signed out
                Log.d(REGISTERACT_TAG, "onAuthStateChanged:signed_out");
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }
        floatingActionButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);

        registerPresenter = new RegisterPresenterImpl(this, RegisterActivity.this ,mAuth, mDatabaseRef);

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
                RegisterActivity.super.onBackPressed();
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
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onDestroy() {
        registerPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        materialDialog = new MaterialDialog.Builder(this)
                .title(R.string.progress_dialog_title)
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
        requestFocus(signUpEmail);
    }

    @Override
    public void setPasswordError() {
        requestFocus(signUpPassword);
        requestFocus(retypePassword);
    }

    @Override
    public void displayToastError(String message, int messageType) {
        TastyToast.makeText(this, message, TastyToast.LENGTH_SHORT, messageType);
    }

    @Override
    public void setPhoneNoError(){
        requestFocus(signUpPhoneNo);
    }

    @Override
    public void setFullNameError(){
        requestFocus(signUpName);
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

                registerPresenter.validateCredentials(name, email, Long.parseLong(phoneNumber), password, retypePass);
                break;

            case R.id.fab:
                animateRevealClose();
                break;
        }

    }

    /**sets the focus to the edit text with the error message*/
    private void requestFocus(View view) {
        if(view.requestFocus()) {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
