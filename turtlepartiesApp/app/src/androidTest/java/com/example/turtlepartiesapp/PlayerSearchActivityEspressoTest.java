package com.example.turtlepartiesapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.app.Activity;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
/**
 * Test class for PlayerSearchActivity. Robotium test framework is
 * used
 */
public class PlayerSearchActivityEspressoTest {

    @Rule
    public ActivityScenarioRule<PlayerSearchActivity> rule =
            new ActivityScenarioRule<>(PlayerSearchActivity.class);

//    @Before
//    public void setup(){
//        Intent intent = new Intent();
//        intent.putExtra("EXTRA", "Test");
//        rule.getScenario();
//        onView(withId(R.id.display)).check(matches(withText("Test")));
//    }


    @Test
    public void start(){
        ActivityScenario scenario = rule.getScenario();
        onView(withId(R.id.mapActivityButton)).check(matches(isDisplayed()));
        onView(withId(R.id.mapActivityButton)).perform(click());
    }


}
