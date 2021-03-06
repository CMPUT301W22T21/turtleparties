package com.example.turtlepartiesapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityEspressoTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Rule public GrantPermissionRule grantPermissionRule = GrantPermissionRule.grant("android.permission.ACCESS_FINE_LOCATION");

    @Test
    public void goesToLeaderBoardScreen(){
        onView(withId(R.id.openLeaderActivityButton)).check(matches(isDisplayed()));
        onView(withId(R.id.openLeaderActivityButton)).perform(click());
    }

    @Test
    public void goesToMap(){
        onView(withId(R.id.mapActivityButton)).check(matches(isDisplayed()));
        onView(withId(R.id.mapActivityButton)).perform(click());
    }

    @Test
    public void goesToScanQR(){
        onView(withId(R.id.scanQRActivityButton)).check(matches(isDisplayed()));
        onView(withId(R.id.scanQRActivityButton)).perform(click());
    }

    @Test
    public void goesToSearch(){
        onView(withId(R.id.searchPlayerActivityButton)).check(matches(isDisplayed()));
        onView(withId(R.id.searchPlayerActivityButton)).perform(click());
    }

    @Test
    public void goesToProfile(){
        onView(withId(R.id.profileActivityButton)).check(matches(isDisplayed()));
        onView(withId(R.id.profileActivityButton)).perform(click());
    }

    @Test
    public void fullUITest(){
        onView(withId(R.id.openLeaderActivityButton)).check(matches(isDisplayed()));
        onView(withId(R.id.openLeaderActivityButton)).perform(click());
        Espresso.pressBack();
        onView(withId(R.id.mapActivityButton)).check(matches(isDisplayed()));
        onView(withId(R.id.mapActivityButton)).perform(click());
        Espresso.pressBack();
        onView(withId(R.id.scanQRActivityButton)).check(matches(isDisplayed()));
        onView(withId(R.id.scanQRActivityButton)).perform(click());
        Espresso.pressBack();
        onView(withId(R.id.searchPlayerActivityButton)).check(matches(isDisplayed()));
        onView(withId(R.id.searchPlayerActivityButton)).perform(click());
        Espresso.pressBack();
        onView(withId(R.id.profileActivityButton)).check(matches(isDisplayed()));
        onView(withId(R.id.profileActivityButton)).perform(click());

    }
}
