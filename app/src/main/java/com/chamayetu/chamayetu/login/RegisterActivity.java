package com.chamayetu.chamayetu.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.chamayetu.chamayetu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.login
 * Created by lusinabrian on 30/09/16.
 * Description: Register Activity class for a new user
 */
public class RegisterActivity extends AppCompatActivity{
    public static final String REGISTERACT_TAG = RegisterActivity.class.getSimpleName();

    /*UI views*/
    @BindView(R.id.signup_button_id) Button signUpButton;
    @BindView(R.id.signup_email_id) EditText signUpEmail;
    @BindView(R.id.signup_password_id) EditText signUpPassword;
    @BindView(R.id.signup_emailtxtInput_id) TextInputLayout signUpEmailTxtInptLayout;
    @BindView(R.id.signup_pass_txtInput_id) TextInputLayout signUpPassTxtInptLayout;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeractivity);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
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
}
