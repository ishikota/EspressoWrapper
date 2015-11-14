package com.ikota.espressowrapper;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


public class WrappedEspressoTest extends EspressoWrapper {

    private Intent mIntent;

    @Override
    void beforeLaunchActivity(MainActivity activity, Application app) {
        // do something with activity ( ex. inject mock API module )
        mIntent = new Intent(app, MainActivity.class);
    }

    @Override
    public void describe(String message) {
        super.describe(message);
        Activity activity = activityRule.launchActivity(mIntent);
        onView(withId(R.id.text)).check(matches(withText(R.string.hello_world)));
    }



}
