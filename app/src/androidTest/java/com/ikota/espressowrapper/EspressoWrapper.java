package com.ikota.espressowrapper;

import android.app.Application;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.example.TargetActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by kota on 2015/11/14.
 */
@RunWith(AndroidJUnit4.class)
public abstract class EspressoWrapper extends ActivityInstrumentationTestCase2<MainActivity> {

    abstract void beforeLaunchActivity(MainActivity activity, Application app);

    @TargetActivity(300)
    Object object;

    public EspressoWrapper() {
        super(MainActivity.class);
    }

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(
            MainActivity.class,
            true,     // initialTouchMode
            false);   // launchActivity. False so we can customize the intent per test method

    @Test
    public void combinedTest() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        Application app = (Application)instrumentation.getTargetContext().getApplicationContext();

        beforeLaunchActivity(activityRule.getActivity(), app);
        describe("");
        //describe で書かれた内容を全てここで実行
    }

    // child class should override this method
    // this method is called as test method
    public void describe(String message) {

    }


}
