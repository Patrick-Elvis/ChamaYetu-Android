package com.chamayetu.chamayetu.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.chamayetu.chamayetu.main.MainActivity;
import com.github.paolorotolo.appintro.AppIntro2;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.register
 * Created by lusinabrian on 04/10/16.
 * Description: Contains the Fragments for the registration of a new user and a new chama
 */

public class RegisterSlideContainer extends AppIntro2 {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Add the slides for the registration steps*/

        /*register the user*/
        addSlide(new RegisterStepOne());

        /*register the new chama, if not by invite*/
        addSlide(new RegisterStepTwo());

        /*slide to confirm the user registration*/
        addSlide(new RegisterStepThree());

        // Hide Skip/Done button.
        showSkipButton(false);
        setProgressButtonEnabled(true);

        /*Set the animation for the sliders*/
        setZoomAnimation();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    /*Load main activity when the user is done with the registration steps*/
    void loadMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
