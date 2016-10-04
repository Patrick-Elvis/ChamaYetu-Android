package com.chamayetu.chamayetu.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chamayetu.chamayetu.R;
import com.github.paolorotolo.appintro.ISlidePolicy;

import butterknife.ButterKnife;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.register
 * Created by lusinabrian on 04/10/16.
 * Description:
 */

public class RegisterStepThree extends Fragment implements ISlidePolicy{

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
    public boolean isPolicyRespected() {
        return false;
    }

    @Override
    public void onUserIllegallyRequestedNextPage() {

    }
}
