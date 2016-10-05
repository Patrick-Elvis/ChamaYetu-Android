package com.chamayetu.chamayetu.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.main.MainActivity;
import com.chamayetu.chamayetu.utils.Contract;
import com.chamayetu.chamayetu.utils.models.ChamaPojo;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.github.paolorotolo.appintro.ISlidePolicy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.register
 * Created by lusinabrian on 04/10/16.
 * Description: Step to register a new chama
 * Obtain fields from the user
 * */

public class RegisterStepTwo extends Fragment implements ISlidePolicy, ISlideBackgroundColorHolder, AdapterView.OnItemSelectedListener{
    public static final String REGISTERSTEP_2 = RegisterStepTwo.class.getSimpleName();

    @BindView(R.id.registerstep2_form_container) LinearLayout registerStep2Layout;
    @BindView(R.id.signup_chamaname_id) EditText chamaName;
    @BindView(R.id.signup_chamanameTxtInp_id) TextInputLayout chamaTextInput;
    @BindView(R.id.signUp_chamaroles_spinner) Spinner chamaRolesSpinner;
    private DatabaseReference databaseReference;

    private String userChamaRoleChoice;

    public RegisterStepTwo(){}

    @Override
    public boolean isPolicyRespected() {
        return false;
    }

    @Override
    public void onUserIllegallyRequestedNextPage() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.registerstep2_form, container, false);
        ButterKnife.bind(this, rootView);
        //create an array adapter to populate the spinner with user choices
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.chama_roles_array,android.R.layout.simple_spinner_item);

        //specify the layout to use when the list of choices appear
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //apply the adapter to the spinner
        chamaRolesSpinner.setAdapter(arrayAdapter);
        chamaRolesSpinner.setOnItemSelectedListener(this);
        chamaName.addTextChangedListener(new ChamaTextWatcher(chamaName));

        // initialize the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        return rootView;
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
    public void onStop() {
        super.onStop();
    }

    /**overrides the color of the background color in the xml layout*/
    @Override
    public int getDefaultBackgroundColor() {
        return Color.parseColor("#FFDEAD");
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        if (registerStep2Layout != null) {
            registerStep2Layout.setBackgroundColor(backgroundColor);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //get the selected item from the dropdown list and initialize the String choice
        userChamaRoleChoice = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //
    }

    private class ChamaTextWatcher implements TextWatcher {
        private View view;
        public ChamaTextWatcher(View view) {
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
            switch(view.getId()){
                case R.id.signup_chamaname_id:
                    validateChamaNameField();
                    break;
            }
        }
    }

    /**Validates the chama name field, checking if the name already exists in the database.
     * If the name already exists, display an error,
     * Check if the field is empty, if so, display an error
     * */
    private boolean validateChamaNameField() {
        final boolean[] isValid = {false};
        SharedPreferences userChama = getActivity().getSharedPreferences("UserChama",0);
        SharedPreferences.Editor editor = userChama.edit();

        String chamaInputName = chamaName.getText().toString().trim();

        // if field is empty
        if(chamaInputName.isEmpty()){
            chamaTextInput.setError(getString(R.string.err_msg_chamaname));
            requestFocus(chamaName);
            return false;
        }

        //if field is not empty, but the name already exists in the database
        if(!chamaInputName.isEmpty()){
            String userIn = chamaInputName.toLowerCase();
            //check if the user already exists in the database at the User's node
            databaseReference.child(Contract.CHAMA_NODE).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //if the chama name already exists
                    if(dataSnapshot.hasChild(userIn)){
                        //alert the user about the conflict
                        TastyToast.makeText(getActivity(), chamaInputName + " already exists", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    }else{
                        //perform write operation, adding new chama with default values, set isValid to true
                        //add default values for the new chama
                        long amountExpected = 0;
                        long totalAmount = 0;
                        //set the members to 1, as the creator is also a member
                        long members = 1;
                        String milestone = "";
                        String milestoneDate = "";
                        String nextMeetingTime = "";
                        String nextMeetingVenue = "";

                        //get current date of creation in format Month Date Year
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
                        String formattedDate = df.format(c.getTime());
                        Log.d(REGISTERSTEP_2+ "date: ",  formattedDate);

                        //store the data in sharedpreference file for access in last screen
                        editor.putString("CHAMA_NODE_NAME",userIn);
                        editor.putString("CHAMA_NAME", chamaInputName);
                        editor.putLong("AMOUNT_EXPECTED",amountExpected);
                        editor.putLong("TOTAL_AMOUNT", totalAmount);
                        editor.putString("DATE_CREATED", formattedDate);
                        editor.putLong("MEMBERS", members);
                        editor.putString("MILESTONE", milestone);
                        editor.putString("MILESTONE_DATE", milestoneDate);
                        editor.putString("NXTMEETING_TIME", nextMeetingTime);
                        editor.putString("NXTMEETING_VENUE", nextMeetingVenue);
                        editor.putString("CHAMAROLE", userChamaRoleChoice);

                        Log.d(REGISTERSTEP_2+"Editor2", editor.toString());
                        //apply the edits
                        editor.apply();

                        isValid[0] = true;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //display an error in event of one
                    TastyToast.makeText(getActivity(), "Error encountered", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    Log.e(REGISTERSTEP_2+"DB Error", databaseError.getMessage());
                }

            });

        }
        return isValid[0];
    }
    /**sets the focus to the edit text with the error message*/
    private void requestFocus(View view) {
        if(view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
