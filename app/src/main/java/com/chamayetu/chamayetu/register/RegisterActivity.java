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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.main.MainActivity;
import com.chamayetu.chamayetu.utils.Contract;
import com.chamayetu.chamayetu.utils.models.UserPojo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }
        floatingActionButton.setOnClickListener(v -> animateRevealClose());

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        signUpEmail.addTextChangedListener(new MyTextWatcher(signUpEmail));
        signUpPassword.addTextChangedListener(new MyTextWatcher((signUpPassword)));
        signUpButton.setOnClickListener(view -> submitFormDetails());
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

    /**Submit registration details*/
    private void submitFormDetails() {
        String password = signUpPassword.getText().toString().trim();
        String email = signUpEmail.getText().toString().trim();
        String name = signUpName.getText().toString();
        String phoneNumber = signUpPhoneNo.getText().toString();

        if(!validateEmail() && validatePassword()){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        Log.d(REGISTERACT_TAG, "Create User with Email: "+ task.isSuccessful());
                        TastyToast.makeText(RegisterActivity.this,"Success!", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this,"Authentication failed.", Toast.LENGTH_SHORT).show();
                            Log.d(REGISTERACT_TAG, task.getException().toString());
                        } else {
                            //write new user to the node users in FirebaseDatabase
                            writeNewUser(name, email, "", "", Long.parseLong(phoneNumber));
                        }
                    });
        }
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
                "LN: " + lastName +
                "UName: " + userName+
                "EM: "+ email +
                "RL: " + role +
                "PH: " + String.valueOf(phoneNumber) +
                "CHAMAGRPS: "+chamaGroups);

        //check if the user already exists in the database at the User's node
        mDatabaseRef.child(Contract.USERS_NODE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //if the user name already exists
                if(dataSnapshot.hasChild(userName)){
                    //alert the user about the conflict
                }else{
                    //perform write operation, adding new user, start next activity
                    mDatabaseRef.child(userName).setValue(newUser);
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    /**Gets the role of the new user and registers their role*/
    public void onRadioButtonClicked(View view){

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
            requestFocus(signUpPassword);
            return false;
        }else if(password.length() < 6) {
            requestFocus(signUpPassword);
        }else if(!password.equals(password2)){
            requestFocus(signUpPassword);
            requestFocus(retypePassword);
        }

        return true;
    }

    /**validate user email
     * @return boolean*/
    private boolean validateEmail() {
        String email = signUpEmail.getText().toString().trim();

        // if empty or is not valid display an error
        if(email.isEmpty() || !isValidEmail(email)){
            requestFocus(signUpEmail);
            return false;
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
