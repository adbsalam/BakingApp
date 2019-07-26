package com.salam.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class espressotest {
    @Rule
    public final ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testSample(){
        if (getRVcount() > 0){
            SystemClock.sleep(2000);
            //test if on click item works and launches new intent
            onView(withId(R.id.recipe_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        }
    }
    private int getRVcount(){
        SystemClock.sleep(2000);
        //check if recycler gets items
        RecyclerView recyclerView = (RecyclerView) mActivityRule.getActivity().findViewById(R.id.recipe_recycler);
        return recyclerView.getAdapter().getItemCount();
    }




}
