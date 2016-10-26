package com.chamayetu.chamayetu;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.chamayetu.chamayetu.login.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.chamayetu.chamayetu", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule(LoginActivity.class);

    @Test
    public void LoginLabelDisplayed() {
        onView(withText("LOGIN")).check(matches(isDisplayed()));
    }

    @Test
    public void InvalidLoginInput(){
        onView(withId(R.id.email))
                .perform(typeText("ryanlucas7"))
                .check(matches(isDisplayed()));

        /*type in wrong password*/
        onView(withId(R.id.password))
                .perform(typeText("password123"))
                .check(matches(isDisplayed()));

        /*attempt login*/
        onView(withId(R.id.email_sign_in_button))
                .perform(click());

        /*check for email error*/
        onView(withId(R.id.email))
                .check(matches(hasErrorText("Enter valid email address")));

        /*check for invalid password error*/
        onView(withId(R.id.password))
                .check(matches(hasErrorText("Invalid Password")));
    }

    @Test
    public void ForgotPasswordClickable(){
        onView(withId(R.id.forgot_password_link))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()));
    }

}
