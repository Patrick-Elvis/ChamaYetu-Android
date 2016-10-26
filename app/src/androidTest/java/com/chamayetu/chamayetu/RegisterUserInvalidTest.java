package com.chamayetu.chamayetu;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.chamayetu.chamayetu.register.RegisterUserActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu
 * Created by lusinabrian on 27/10/16.
 * Description: Tests to check invalid user input on registering a new user
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegisterUserInvalidTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.chamayetu.chamayetu", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<RegisterUserActivity> mActivityRule = new ActivityTestRule(RegisterUserActivity.class);



}
