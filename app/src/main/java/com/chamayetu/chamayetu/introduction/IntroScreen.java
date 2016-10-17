package com.chamayetu.chamayetu.introduction;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.chamayetu.chamayetu.R;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntro2Fragment;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.introduction
 * Created by lusinabrian on 17/10/16.
 * Description: Brief introduction screen for the App. only displayed once for a first time user
 */

public class IntroScreen extends AppIntro2 {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        /*adding the slides for the app, 3 slides*/

        /*brief welcome screen*/
        addSlide(AppIntro2Fragment.newInstance(
                getString(R.string.appintro),
                getString(R.string.appdesc),
                R.drawable.ic_action_user_groups_filled_50,
                ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark)));

        /*what the app is about, money management*/
        addSlide(AppIntro2Fragment.newInstance(
                getString(R.string.appintro_2),
                getString(R.string.appdesc_2),
                R.drawable.ic_action_money_100,
                ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary))
        );

        /*bonus of what the app offers,*/
        addSlide(AppIntro2Fragment.newInstance(
                getString(R.string.appintro_3),
                getString(R.string.appdesc_3),
                R.drawable.ic_action_downloading_updates_96,
                ContextCompat.getColor(getApplicationContext(),R.color.foreground1))
        );

        /*final screen*/
        addSlide(AppIntro2Fragment.newInstance());

        /*set the depth animation*/
        setDepthAnimation();

        /*display progress to user*/
        setProgressButtonEnabled(true);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        /*proceed to login screen when user skips intros*/
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        /*moves to login screen when the user is done with the app intro*/
    }


    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        /*do nothing when the slide is changed*/
    }


}
