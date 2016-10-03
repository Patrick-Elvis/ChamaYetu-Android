package com.chamayetu.chamayetu.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.main.MainActivity;
import com.chamayetu.chamayetu.utils.Contract;
import com.chamayetu.chamayetu.utils.models.UserPojo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

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
    @BindView(R.id.signup_chamanameTxtInp_id) TextInputLayout signUpChamaTxtInView;
    @BindView(R.id.signup_chamaname_id) EditText signUpChamaNameView;
    @BindView(R.id.signup_phoneNo_id) EditText signUpPhoneNo;
    @BindView(R.id.signup_phoneNoTxtInp_id) TextInputLayout signUpPhoneNoTxtIn;
    @BindView(R.id.signup_name_id) EditText signUpName;
    @BindView(R.id.signup_nametxtInput_id) TextInputLayout signUpNameTxtIn;
    @BindView(R.id.signup_retypepassword_id) EditText retypePassword;
    @BindView(R.id.signup_retypepass_txtInput_id) TextInputLayout retypePasswordTxtIn;
    @BindView(R.id.signup_role_id) EditText roleView;
    @BindView(R.id.signup_roletxtInput_id) TextInputLayout roleViewTxtIn;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabaseRef;


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
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        signUpEmail.addTextChangedListener(new MyTextWatcher(signUpEmail));
        signUpPassword.addTextChangedListener(new MyTextWatcher((signUpPassword)));
        signUpButton.setOnClickListener(view -> submitFormDetails());
    }

    /**Submit registration details*/
    private void submitFormDetails() {
        String password = signUpPassword.getText().toString().trim();
        String email = signUpEmail.getText().toString().trim();
        String name = signUpName.getText().toString();
        String chamaName = signUpChamaNameView.getText().toString();
        String role = roleView.getText().toString();
        String phoneNumber = signUpPhoneNo.getText().toString();

        if(!validateEmail() && validatePassword()){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        /*TODO: display a better message to user on sign up*/
                        Log.d(REGISTERACT_TAG, "Create User with Email: "+ task.isSuccessful());
                        Toast.makeText(RegisterActivity.this,"Success! :)", Toast.LENGTH_SHORT).show();
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this,"Authentication failed.", Toast.LENGTH_SHORT).show();
                            Log.d(REGISTERACT_TAG, task.getException().toString());
                        } else {
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        }
                    });
        }
        //write new user to the node users in FirebaseDatabase
        writeNewUser(name, email, chamaName, role, Integer.parseInt(phoneNumber));
    }

    /**Writes a new user to the Firebase Database at the User node*/
    private void writeNewUser(String name, String email, String chamaName,String role, long phoneNumber) {
        String firstName = name.split(" ")[0];
        String lastName = name.split(" ")[1];
        Map<String, Object> chamaGroups = new HashMap<>();

        // obtain only the first part of the email address
        int index = email.indexOf('@');
        String userName = email.substring(0, index);

        chamaGroups.put(chamaName, true);

        // new instance of the new user
        UserPojo newUser = new UserPojo(firstName, lastName, email, role, phoneNumber,0,0,chamaGroups);
        Log.d(REGISTERACT_TAG, "FN " + firstName +
                "LN:" + lastName +
                "EM: "+ email +
                "RL: " + role +
                "PH:" + phoneNumber +
                "TOTl " + 0 + " AVG " + 0 +
                "CHAMAGRPS: "+chamaGroups);
        mDatabaseRef.child(Contract.USERS_NODE).child(userName.toLowerCase()).setValue(newUser);
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

    /**Todo: Make TextWatcher an interface*/
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
        String password2 = retypePassword.getText().toString();
        if(password.isEmpty()){
            signUpPassTxtInptLayout.setError(getString(R.string.err_msg_password));
            requestFocus(signUpPassword);
            return false;
        }else if(password.length() < 6) {
            signUpPassTxtInptLayout.setError(getString(R.string.err_msg_password_short));
            requestFocus(signUpPassword);
        }else if(password.equals(password2)){
            signUpPassTxtInptLayout.setError(getString(R.string.err_msg_password_match));
            retypePasswordTxtIn.setError(getString(R.string.err_msg_password_match));
            requestFocus(retypePassword);
        }else{
            signUpPassTxtInptLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**validate user email
     * @return boolean*/
    private boolean validateEmail() {
        String email = signUpEmail.getText().toString().trim();

        // if empty or is not valid display an error
        if(email.isEmpty() || !isValidEmail(email)){
            signUpEmailTxtInptLayout.setError(getString(R.string.err_msg_email));
            requestFocus(signUpEmail);
            return false;
        }else{
            signUpPassTxtInptLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**checks for a valid email address from a pattern*/
    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**sets the focus to the edit text with the error message*/
    private void requestFocus(View view) {
        if(view.requestFocus()) {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}