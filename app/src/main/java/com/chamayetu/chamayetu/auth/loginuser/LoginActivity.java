package com.chamayetu.chamayetu.auth.loginuser;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.auth.loginchama.LoginChamaActivity;
import com.chamayetu.chamayetu.forgotpassword.ForgotPass;
import com.chamayetu.chamayetu.introduction.IntroScreen;
import com.chamayetu.chamayetu.main.MainActivity;
import com.chamayetu.chamayetu.auth.register.RegisterUserActivity;
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

import static com.chamayetu.chamayetu.utils.Contract.LOGINACT_TAG;
import static com.chamayetu.chamayetu.utils.Contract.RC_SIGN_IN;
import static com.chamayetu.chamayetu.utils.Contract.USERNAME_BUNDLE_KEY;

import com.google.android.gms.auth.api.Auth;
import com.sdsmdg.tastytoast.TastyToast;

/**
 * A login screen that offers login via email/password.
 * or social media logins.*/
public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, LoginView{
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    private LoginPresenter loginPresenter;
    private MaterialDialog materialDialog;

    // UI references.
    @BindView(R.id.cv_container) CardView cardViewContainer;
    @BindView(R.id.email) EditText mEmailView;
    @BindView(R.id.password) EditText mPasswordView;
    @BindView(R.id.email_sign_in_button) Button mEmailSignInButton;
    @BindView(R.id.forgot_password_link) Button forgotPassword;
    @BindView(R.id.fab) FloatingActionButton floatingActionButton;
    @BindView(R.id.google_signin_button) SignInButton googleSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if(user !=null){
                // user is signed in, therefore redirect to LoginChamaActivity
                Log.d(LOGINACT_TAG, "onAuthStateChanged:signedIn: " + user.getUid());
                Intent loginChama = new Intent(LoginActivity.this, LoginChamaActivity.class);
                String email = user.getEmail();
                int idx =  email.indexOf("@");
                String username = email.substring(0, idx).toLowerCase();
                loginChama.putExtra(USERNAME_BUNDLE_KEY, username);
                startActivity(loginChama);
            }else{
                Log.d(LOGINACT_TAG, "onAuthStateChanged:signedout");
            }
        };

        /*instantiate the LoginPresenterImpl*/
        loginPresenter = new LoginPresenterImpl(LoginActivity.this,this, mAuth);

        googleSignInButton.setOnClickListener(this);
        mEmailSignInButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);

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


    @OnClick({R.id.email_sign_in_button, R.id.fab, R.id.google_signin_button, R.id.forgot_password_link})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setExitTransition(null);
                    getWindow().setEnterTransition(null);
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(this, floatingActionButton, floatingActionButton.getTransitionName());
                    startActivity(new Intent(this, RegisterUserActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(this, RegisterUserActivity.class));
                }
                break;

            case R.id.email_sign_in_button:
                // Store values at the time of the login attempt.
                String email = mEmailView.getText().toString();
                String password = mPasswordView.getText().toString();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Explode explode = new Explode();
                    explode.setDuration(500);
                    getWindow().setExitTransition(explode);
                    getWindow().setEnterTransition(explode);

                    loginPresenter.validateUserCredentials(email, password);
                }else{
                    loginPresenter.validateUserCredentials(email, password);
                }
                break;

            /*Google login*/
            case R.id.google_signin_button:
                /*handle google login*/
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
                break;

            /*forgot password*/
            case R.id.forgot_password_link:
                /*handle forgot password*/
                startActivity(new Intent(LoginActivity.this, ForgotPass.class));
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
                loginPresenter.validateGoogleLogin(googleSignInAccount,mAuth,LoginActivity.this);
            }
        }
    }

    @Override
    protected void onDestroy() {
        loginPresenter.onDestroy();
        super.onDestroy();
    }

    /**Initialize social logins: Google, Twitter, Facebook, Github...*/
    private void initSocialLogins() {
        /*Google Sign in logic*/
        /*request permissions from Google*/
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(LOGINACT_TAG, "onConnectionFailed: GoogleAPI" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setEmailError() {
        mEmailView.setError(getString(R.string.err_msg_email));
    }

    @Override
    public void setPasswordError() { mPasswordView.setError(getString(R.string.err_msg_password_login));}

    @Override
    public void displayProgress() {
        materialDialog = new MaterialDialog.Builder(LoginActivity.this)
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
    public void navigateToMain(boolean toMain, String username) {
        //Intents to start the next activities based on user chamas
        // if the user has 1 chama, start main activity
        // if they have more than one, start LoginChama activity
        Intent toMainAct = new Intent(LoginActivity.this, MainActivity.class)
                .putExtra(USERNAME_BUNDLE_KEY,username);
        Intent toChamaLogin = new Intent(LoginActivity.this, LoginChamaActivity.class)
                .putExtra(USERNAME_BUNDLE_KEY,username);
        ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);

        Log.d(LOGINACT_TAG+"Username",username);

        if(toMain){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                startActivity(toMainAct, oc2.toBundle());
            }else{
                startActivity(toMainAct);
            }
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                startActivity(toChamaLogin, oc2.toBundle());
            }else{
                startActivity(toChamaLogin);
            }
        }
        finish();
    }

    @Override
    public void displayToast(String message, int messageType) {
        TastyToast.makeText(LoginActivity.this, message, TastyToast.LENGTH_SHORT, messageType);
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
