package com.chamayetu.chamayetu.register;

import android.app.Activity;
import android.content.Context;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import com.chamayetu.chamayetu.models.ChamaPojo;
import com.chamayetu.chamayetu.models.UserPojo;
import com.chamayetu.chamayetu.utils.Contract;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.chamayetu.chamayetu.utils.Contract.SHAREPREF_PRIVATE_MODE;
import static com.chamayetu.chamayetu.utils.UtilityMethods.isValidEmail;
import static com.chamayetu.chamayetu.utils.UtilityMethods.validateRegisterPassword;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.registeruser
 * Created by lusinabrian on 15/10/16.
 * Interactor implementation returns the results or just returns the control to presenter implementation by calling listener methods
 */

public class RegisterInteractorImpl implements RegisterInteractor {
    private static final String TAG = RegisterUserActivity.REGISTERACT_TAG;

    @Override
    public void registerNewUser(Context context, String name, String email, String password, String retypePassword, long phoneNumber, FirebaseAuth mAuth, DatabaseReference mDatabaseReference, OnRegistrationFinishedListener listener) {
        boolean error = false;

        /*if email is not valid, display an error*/
        if(!isValidEmail(email)){
            listener.onEmailError();
            error = true;
        }
        /*if password is invalid*/
        if(!validateRegisterPassword(password, retypePassword)) {
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
                            writeNewUser(context, name, email, "chairperson", phoneNumber, mDatabaseReference,
                                    listener);
                            /*send email verification*/
                            try{
                                mAuth.addAuthStateListener(firebaseAuth -> firebaseAuth.getCurrentUser()
                                        .sendEmailVerification());
                            }catch (NullPointerException npe){
                                Log.e(TAG+"EMAILVerification:", npe.getMessage());
                            }
                        }
                    });
        }
    }

    @Override
    public void registerNewChama(Context context, String chamaName, String chamaMembers, String bankName, long accountNumber, FirebaseAuth mAuth, DatabaseReference mDatabaseReference, OnRegisterNewChamaFinishedListener listener) {

        boolean error = false;
        //check if the chama name is filled out
        if(TextUtils.isEmpty(chamaName)){
            listener.onChamaNameError();
            error = true;
        }
        //if the chama members is 0 or empty
        if(TextUtils.isEmpty(chamaMembers)){
            listener.onChamaMemberError();
            error = true;
        }

        //if the bankname is empty
        if(TextUtils.isEmpty(bankName)){
            listener.onChamaBankNameError();
            error = true;
        }

        if(TextUtils.isEmpty(String.valueOf(accountNumber))){
            listener.onChamaAccountError();
            error = true;
        }

        /*if there is no error, proceed to registering the new chama*/
        if(!error){
            Calendar c = Calendar.getInstance();

            SimpleDateFormat df = new SimpleDateFormat("MMMM-dd-yyyy", Locale.ENGLISH);
            String formattedDate = df.format(c.getTime());

            ChamaPojo newChama = new ChamaPojo(formattedDate,"","",Long.parseLong(chamaMembers),0,chamaName,"",bankName,accountNumber);

            RegisterNewUserChama registerNewUserChama = new RegisterNewUserChama(context, mAuth,mDatabaseReference,listener, newChama);

            registerNewUserChama.newChama(chamaName, newChama);
        }
    }

    /**Writes a new user to the Firebase Database at the User node*/
    private void writeNewUser(Context context, String name, String email,String role, long phoneNumber, DatabaseReference mDatabaseReference, OnRegistrationFinishedListener listener) {

        /*creating a new share preference file for the current user*/
        SharedPreferences userSharedPref = context.getSharedPreferences("CurrentUser",SHAREPREF_PRIVATE_MODE);
        SharedPreferences.Editor userPrefEditor = userSharedPref.edit();

        //todo: consider 3 name splits
        String firstName = name.split(" ")[0];
        String lastName = name.split(" ")[1];

        // obtain only the first part of the email address
        int index = email.indexOf('@');
        String userName = email.substring(0, index).toLowerCase();

        userPrefEditor.putString("CurrentUserName", userName);
        userPrefEditor.apply();

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
                    listener.onEmailError();
                    Log.d(TAG+"UserExists: ",String.valueOf(dataSnapshot.hasChild(userName)));
                }else{
                    //perform write operation, adding new user
                    Map<String, Object> userHash = new HashMap<>();
                    userHash.put(userName, newUser);
                    mDatabaseReference.child(Contract.USERS_NODE).updateChildren(userHash);
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

}
