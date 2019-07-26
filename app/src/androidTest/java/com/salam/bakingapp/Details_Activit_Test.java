package com.salam.bakingapp;


import android.content.Intent;
import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class Details_Activit_Test  {
    @Rule
    public final ActivityTestRule<RecipeDetails> mActivityRule2 = new ActivityTestRule<RecipeDetails>(RecipeDetails.class);

    @Test
    public void ActivityLoadingwithFragment() {
        Intent intent = new Intent();
        intent.putExtra("id", "1");
        intent.putExtra("name", "cupcake");
        mActivityRule2.launchActivity(intent);

        SystemClock.sleep(3000);

    }




}
