package com.example.turtlepartiesapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LeaderboardActivitiyEspressoTest {
    @Rule
    public ActivityScenarioRule<LeaderboardActivity> activityRule =
            new ActivityScenarioRule<>(LeaderboardActivity.class);



    @Test
    public void checksAllLeaderboards(){
        onView(withId(R.id.highestScoreButton)).check(matches(isDisplayed()));
        onView(withId(R.id.highestScoreButton)).perform(click());
        onView(withId(R.id.mostScansButton)).check(matches(isDisplayed()));
        onView(withId(R.id.mostScansButton)).perform(click());
        onView(withId(R.id.greatestSumButton)).check(matches(isDisplayed()));
        onView(withId(R.id.greatestSumButton)).perform(click());
    }

}
