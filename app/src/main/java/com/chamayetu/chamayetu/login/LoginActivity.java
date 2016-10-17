package com.chamayetu.chamayetu.login;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.introduction.IntroScreen;
import com.chamayetu.chamayetu.main.MainActivity;
import com.chamayetu.chamayetu.register.RegisterUserActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.chamayetu.chamayetu.utils.Contract.RC_SIGN_IN;

import com.google.android.gms.auth.api.Auth;
import com.sdsmdg.tastytoast.TastyToast;

/**
 * A login screen that offers login via email/password.
 * or social media logins.*/
public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    public static final String LOGINACT_TAG = LoginActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;

    // UI references.
    @BindView(R.id.cv_container) CardView cardViewContainer;

    @BindView(R.id.email) EditText mEmailView;
    @BindView(R.id.password) EditText mPasswordView;
    @BindView(R.id.email_sign_in_button)Button mEmailSignInButton;
    @BindView(R.id.forgot_password_link) TextView forgotPassword;
    @BindView(R.id.fab) FloatingActionButton floatingActionButton;

    @BindView(R.id.google_signin_button) SignInButton googleSignInButton;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.*/
    private UserLoginTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);

        mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.email_sign_in_button || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if(user !=null){
                // user is signed in, therefore redirect to MainActivity
                Log.d(LOGINACT_TAG, "onAuthStateChanged:signedIn: " + user.getUid());
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }else{
                Log.d(LOGINACT_TAG, "onAuthStateChanged:signedout");
            }
        };

        googleSignInButton.setOnClickListener(this);
        mEmailSignInButton.setOnClickListener(this);

        initSocialLogins();

        checkFirstStart();
    }

    /**method to check if this is the first start of the application for the user
     * THis is in order to show the brief app intros*/
    private void checkFirstStart() {
        Thread thread = new Thread(() -> {
            /*initialize the shared preferences*/
            SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

            /*create a new boolean and set it to true*/
            boolean isFirstStart = getPrefs.getBoolean("firstStart", true);
            /*if the activity has never started before, begin the app intros*/
            if(isFirstStart){

                /*start the intro screen*/
                startActivity(new Intent(LoginActivity.this, IntroScreen.class));
                SharedPreferences.Editor editor = getPrefs.edit();

                //edit preferences to make it false to not start a second time
                editor.putBoolean("firstStart", false);

                //apply these changes
                editor.apply();
            }
        });
        
        /*start our thread*/
        thread.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.email_sign_in_button, R.id.fab, R.id.google_signin_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                    getWindow().setExitTransition(null);
                    getWindow().setEnterTransition(null);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions options =
                                ActivityOptions.makeSceneTransitionAnimation(this, floatingActionButton, floatingActionButton.getTransitionName());
                        startActivity(new Intent(this, RegisterUserActivity.class), options.toBundle());
                    } else {
                        startActivity(new Intent(this, RegisterUserActivity.class));
                    }
                break;

            case R.id.email_sign_in_button:
                    Explode explode = new Explode();
                    explode.setDuration(500);
                    getWindow().setExitTransition(explode);
                    getWindow().setEnterTransition(explode);
                    //verify credentials
                    attemptLogin();
                break;

            /*Google login*/
            case R.id.google_signin_button:
                /*handle google login*/
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // result returned from launching the intent
        if(requestCode == RC_SIGN_IN) {
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(googleSignInResult.isSuccess()){
                //Google sign in was successful, authenticate with Firebase
                GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();
                if(LoginAuthHandler.handleGoogleLogin(googleSignInAccount,mAuth,LoginActivity.this)){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        }

    }

    /**Initialize social logins: Google, Twitter, Facebook, Github...*/
    private void initSocialLogins() {
        /*Google Sign in logic*/
        /*request permissions ffrom Google*/
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.isEmpty();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(LOGINACT_TAG, "onConnectionFailed: GoogleAPI" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            mAuth.signInWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(
                    LoginActivity.this,
                    task -> {
                        Log.d(LOGINACT_TAG, "signInWithEmail:onComplete "+ task.isSuccessful());
                        if(!task.isSuccessful()){
                            Log.w(LOGINACT_TAG, "SignInWithEmail: ", task.getException());
                            TastyToast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT, TastyToast.ERROR);
                        }
                });
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            // if successful, start main activity
            if (success) {
                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);
                Intent i2 = new Intent(LoginActivity.this, LoginSuccess.class);
                startActivity(i2, oc2.toBundle());
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
