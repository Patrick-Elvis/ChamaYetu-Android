package com.chamayetu.chamayetu.register;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.chamayetu.chamayetu.R;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.github.paolorotolo.appintro.ISlidePolicy;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.register
 * Created by lusinabrian on 04/10/16.
 * Description: Step to register a new chama
 * Obtain fields from the user*/

public class RegisterStepTwo extends Fragment implements ISlidePolicy, ISlideBackgroundColorHolder{
    @BindView(R.id.registerstep2_form_container) LinearLayout registerStep2Layout;
    @BindView(R.id.signUp_chamaroles_spinner) Spinner chamaRolesSpinner;

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
        //create an array adapter to populate the spinner with user choices
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.chama_roles_array,android.R.layout.simple_spinner_item);

        //specify the layout to use when the list of choices appear
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //apply the adapter to the spinner
        chamaRolesSpinner.setAdapter(arrayAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.registerstep2_form, container, false);
        ButterKnife.bind(this, rootView);
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

}
