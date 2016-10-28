package com.chamayetu.chamayetu.auth.loginuser;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import static com.chamayetu.chamayetu.utils.Contract.CHAMA_GROUPS;
import static com.chamayetu.chamayetu.utils.Contract.LOGINACT_TAG;
import static com.chamayetu.chamayetu.utils.Contract.USERS_NODE;
import static com.chamayetu.chamayetu.utils.UtilityMethods.isValidEmail;
import static com.chamayetu.chamayetu.utils.UtilityMethods.validateLoginPassword;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.login
 * Created by lusinabrian on 17/10/16.
 * Description: Interactor implementation of {@link LoginInteractor}
 */

class LoginInteractorImpl implements LoginInteractor{

    @Override
    public void loginUser(Context context, String email, String password, FirebaseAuth mAuth, OnLoginFinishedListener listener) {
        boolean error = false;

        /*if email is not valid, display an error*/
        if(!isValidEmail(email)){
            listener.onEmailError();
            error = true;
        }

        /*if password is blank, display an error to user*/
        if(validateLoginPassword(password)){
           listener.onPasswordError();
            error = true;
        }

        /*if there is no error login in the user with their email and password*/
        if(!error){
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                Log.d(LOGINACT_TAG, "signInWithEmail:onComplete "+ task.isSuccessful());
                if(!task.isSuccessful()){
                    Log.d(LOGINACT_TAG, "SignInWithEmail: ", task.getException());
                    listener.onTaskError("Email or password is invalid", TastyToast.ERROR);
                } else {
                    //On successful login, check the user's chamas to determine how many chams they are in.
                    //if user has 1 chama, proceed to MainActivity, if they have several,
                    // proceed to chama login
                    int indx = email.indexOf("@");
                    String username = email.substring(0, indx).toLowerCase();
                    //if the user has only one chama, navigate to MainActivity
                    listener.onSuccess(userChamas(username), username);
                }
            });
        }
    }

    @Override
    public void loginUserWithGoogle(Context context, GoogleSignInAccount googleSignInAccount, FirebaseAuth mAuth, OnLoginFinishedListener listener) {
        final boolean[] toMain = {true};
        Log.d(LOGINACT_TAG, "FirebaseWithGoogleLogin: " + googleSignInAccount.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

        mAuth.signInWithCredential(credential).addOnCompleteListener((Activity)context, task -> {
            Log.d(LOGINACT_TAG, "SignInWithCredentialComplete: "+ task.isSuccessful());
            if(!task.isSuccessful()){
                Log.d(LOGINACT_TAG, "GoogleSignInFail: ", task.getException());
                listener.onTaskError("Failed to sign in, please try again", TastyToast.ERROR);
            }else{
                int indx = mAuth.getCurrentUser().getEmail().indexOf("@");
                String username = mAuth.getCurrentUser().getEmail().substring(0,indx).toLowerCase();
                //if the user has more than one chama, navigate to chama login
                //if the user has only one chama, navigate to MainActivity
                listener.onSuccess(userChamas(username), username);
            }
        });
    }

    /**Verifies the number of chamas the user is in
     * @param username Takes the username and fetches user credentials for the user chamas*/
    private boolean userChamas(String username){
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        final boolean[] isCountOne = {true};

        //navigate down the node to the user's chama groups node and retrieve the user's chama count
        mDatabaseReference.child(USERS_NODE).child(username).child(CHAMA_GROUPS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildrenCount() > 1) {
                        Log.d(LOGINACT_TAG+"ChamaCount", String.valueOf(dataSnapshot.getChildrenCount()));
                            isCountOne[0] = false;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //log database errors
                        //todo: notify user
                        Log.e(LOGINACT_TAG, databaseError.getMessage());
                    }
                });
        Log.d(LOGINACT_TAG+"ChamaCountBool", String.valueOf(isCountOne[0]));
        return isCountOne[0];
    }
}
