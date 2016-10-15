package com.chamayetu.chamayetu.registeruser;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import com.chamayetu.chamayetu.models.UserPojo;
import com.chamayetu.chamayetu.utils.Contract;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.registeruser
 * Created by lusinabrian on 15/10/16.
 * Interactor implementation returns the results or just returns the control to presenter implementation by calling listener methods
 */

public class RegisterInteractorImpl implements RegisterInteractor {
    private static final String TAG = RegisterActivity.REGISTERACT_TAG;

    @Override
    public void registerNewUser(Context context, String name, String email, String password, String retypePassword, long phoneNumber, FirebaseAuth mAuth, DatabaseReference mDatabaseReference, OnRegistrationFinishedListener listener) {
        /**Submit registration details*/
        boolean error = false;

        /*if email is not valid, display an error*/
        if(!isValidEmail(email)){
            listener.onEmailError();
            error = true;
        }
        /*if password is invalid*/
        if(!validatePassword(password, retypePassword)) {
            listener.onPasswordError();
            error = true;
        }

        /*if there is no error, begin registration*/
        if(!error) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) context, task -> {
                        Log.d(TAG+"register",email + password);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.d(TAG, "Create User with Email: "+ task.isSuccessful());
                            listener.onTaskError("Authentication failed", TastyToast.ERROR);
                            Log.e(TAG, task.getException().getMessage());
                        } else {
                            //write new user to the node users in FirebaseDatabase
                            writeNewUser(name, email, "chairperson", phoneNumber, mDatabaseReference, listener);
                            /*send email verification*/
                            mAuth.addAuthStateListener(firebaseAuth -> firebaseAuth.getCurrentUser()
                                    .sendEmailVerification());
                            //listener.onSuccess();
                        }
                    });
        }
    }

    /**Writes a new user to the Firebase Database at the User node*/
    private void writeNewUser(String name, String email,String role, long phoneNumber, DatabaseReference mDatabaseReference, OnRegistrationFinishedListener listener) {
        String firstName = name.split(" ")[0];
        String lastName = name.split(" ")[1];

        // obtain only the first part of the email address
        int index = email.indexOf('@');
        String userName = email.substring(0, index);

        // new instance of the new user
        UserPojo newUser = new UserPojo(firstName, lastName, email, role, phoneNumber,0,0);
        Log.d(TAG, newUser.toString());

        //check if the user already exists in the database at the User's node
        mDatabaseReference.child(Contract.USERS_NODE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //if the user name already exists
                if(dataSnapshot.hasChild(userName)){
                    //alert the user about the conflict
                    listener.onTaskError("User already exists",TastyToast.ERROR);
                    listener.onEmailError();
                    Log.d(TAG+"UserExists: ",String.valueOf(dataSnapshot.hasChild(userName)));
                }else{
                    //perform write operation, adding new user
                    mDatabaseReference.setValue(newUser);
                    listener.onSuccess();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onTaskError("Error encountered, please retry", TastyToast.ERROR);
                Log.d(TAG+"DBError", databaseError.getMessage());
            }
        });
    }

    @Override
    public void registerNewChama(String chamaName, String chamaMembers, String chairPerson, OnRegisterNewChamaFinishedListener listener) {

    }


    /**VALIDATE user password
     * Check if user password is valid, if the user password is empty, display an error
     * If the user retype password and passwords do not match, display an error to user
     * else, if all checks out, then return true
     * @return boolean*/
    private boolean validatePassword(String password, String retypePassword) {
        if(TextUtils.isEmpty(password)|| TextUtils.isEmpty(retypePassword)){
            return false;
        }
        /*if the passwords do not match*/
        else if(!password.equals(retypePassword)){
            return false;
        }
        return true;
    }

    /**validate user email
     * @return boolean
    **checks for a valid email address from a pattern*/
    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
