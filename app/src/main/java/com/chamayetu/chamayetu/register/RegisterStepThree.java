package com.chamayetu.chamayetu.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.login.LoginActivity;
import com.chamayetu.chamayetu.main.MainActivity;
import com.chamayetu.chamayetu.utils.Contract;
import com.chamayetu.chamayetu.utils.models.ChamaPojo;
import com.chamayetu.chamayetu.utils.models.UserPojo;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.github.paolorotolo.appintro.ISlidePolicy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.register
 * Created by lusinabrian on 04/10/16.
 * Description:
 */

public class RegisterStepThree extends Fragment implements ISlidePolicy, ISlideBackgroundColorHolder{
    public static final String REGISTERSTEP_3 = RegisterStepThree.class.getSimpleName();
    @BindView(R.id.registerstep3_form_container) LinearLayout registerStep3;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference mDatabaseRef;

    public RegisterStepThree(){}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.registerstep3_confirm, container, false);
        ButterKnife.bind(this, rootView);
        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        mAuthStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                Log.d(REGISTERSTEP_3, "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                // User is signed out
                Log.d(REGISTERSTEP_3, "onAuthStateChanged:signed_out");
            }
        };

        writeNewUserWithFirebase();
        return rootView;
    }

    /**Creates a new user with Email and password to Firebase*/
    private void writeNewUserWithFirebase() {
        //retrieve the shared preferences that stores the user credentials
        SharedPreferences mUserCredentials = getActivity().getSharedPreferences("UserCredentials",0);

        //retrieve the shared prefs file that has the chama details
        SharedPreferences mUserChamaCredentials = getActivity().getSharedPreferences("UserChama",0);

        //retrieve the data and send it to firebase to create a new user
        String email = mUserCredentials.getString("EMAIL","missing email");
        String password = mUserCredentials.getString("PASSWORD","missing password");
        String fullName = mUserCredentials.getString("FULLNAME","missing full name");
        String phoneNo = mUserCredentials.getString("PHONENO", "missing phone");

        String chamaNodeName = mUserChamaCredentials.getString("CHAMA_NODE_NAME", "missingChama");
        String chamaName = mUserChamaCredentials.getString("CHAMA_NAME", "missingValue");
        long amountExpected = mUserChamaCredentials.getLong("AMOUNT_EXPECTED",0);
        long totalAmt = mUserChamaCredentials.getLong("TOTAL_AMOUNT", 0);
        String dateCreated = mUserChamaCredentials.getString("DATE_CREATED", "missingValue");
        long members = mUserChamaCredentials.getLong("MEMBERS", 1);
        String milestone = mUserChamaCredentials.getString("MILESTONE", "missingValue");
        String milestoneDate = mUserChamaCredentials.getString("MILESTONE_DATE", "missingValue");
        String nxtMeetingTime = mUserChamaCredentials.getString("NXTMEETING_TIME", "missingValue");
        String nxtMeetingVenue = mUserChamaCredentials.getString("NXTMEETING_VENUE", "missingValue");
        String chamaRole = mUserChamaCredentials.getString("CHAMAROLE", "missingValue");
        String firstName = fullName.split(" ")[0];
        String lastName = fullName.split(" ")[1];
        Map<String, Object> chamaGroups = new HashMap<>();

        // obtain only the first part of the email address
        int index = email.indexOf('@');
        String userName = email.substring(0, index).toLowerCase();

        chamaGroups.put(chamaName, true);

        // new instance of the new user
        UserPojo newUser = new UserPojo(firstName, lastName, email, chamaRole, Long.parseLong(phoneNo),0,0,chamaGroups);

        /*create a new chama pojo*/
        ChamaPojo chamaPojo = new ChamaPojo(dateCreated,nxtMeetingTime,milestoneDate,members,totalAmt,amountExpected,chamaName,nxtMeetingVenue,milestone);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(
                getActivity(), task -> {
                    Log.d(REGISTERSTEP_3, "Create User with Email: "+ task.isSuccessful());
                    TastyToast.makeText(getActivity(),"Success!", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    /*if unsuccessful*/
                    if(!task.isSuccessful()){
                        TastyToast.makeText(getActivity(),"Authentication failed.", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                        Log.d(REGISTERSTEP_3, task.getException().toString());
                    }
                }
        );

        //check if the user already exists in the database at the User's node
        mDatabaseRef.child(Contract.USERS_NODE).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //if the user name already exists
                        if(dataSnapshot.hasChild(userName)){
                            //alert the user about the conflict
                            TastyToast.makeText(getActivity(),"username exists",TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                        }else{
                            //perform write operation, adding new user, start next activity
                            mDatabaseRef.child(userName).setValue(newUser);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        TastyToast.makeText(getActivity(), "Error encountered",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                        Log.e(REGISTERSTEP_3, databaseError.getMessage());
                    }
                });

        //check if the user already exists in the database at the User's node
        mDatabaseRef.child(Contract.CHAMA_NODE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDatabaseRef.child(chamaNodeName).setValue(chamaPojo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                TastyToast.makeText(getActivity(), "Error encountered",TastyToast.LENGTH_SHORT,TastyToast.ERROR);

                Log.e(REGISTERSTEP_3, databaseError.getMessage());
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public boolean isPolicyRespected() {
        return false;
    }

    @Override
    public void onUserIllegallyRequestedNextPage() {

    }

    @Override
    public int getDefaultBackgroundColor() {
        return 0;
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {

    }
}
