package com.ikota.espressowrapper.example.ui.reactive_sample;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.ikota.espressowrapper.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ReactiveSampleActivityTest extends ActivityInstrumentationTestCase2<ReactiveSampleActivity> {

    private Intent mIntent;
    private Context mContext;

    public ReactiveSampleActivityTest() {
        super(ReactiveSampleActivity.class);
    }

    @Rule
    public ActivityTestRule<ReactiveSampleActivity> activityRule = new ActivityTestRule<>(
            ReactiveSampleActivity.class,
            true,     // initialTouchMode
            false);   // launchActivity. False so we can customize the intent per test method

    @Before
    public void setup() throws Exception {
        super.setUp();
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        mContext = instrumentation.getContext();
        mIntent = new Intent(mContext, ReactiveSampleActivity.class);
    }

    @Test
    public void checkViewExistence() {
        Activity activity = activityRule.launchActivity(mIntent);
        onView(withId(R.id.image)).check(matches(hasContentDescription()));
        onView(withId(R.id.title)).check(matches(withText(R.string.loading)));
        onView(withId(R.id.like_num)).check(matches(withText("53 like")));
        onView(withId(R.id.like_btn)).check(matches(withText("Like")));
        onView(withId(R.id.tag_send)).check(matches(withText("Create")));
        onView(withId(R.id.comment_num)).check(matches(withText("No Comments")));
        onView(withId(R.id.add_comment_btn)).check(matches(withText("Add Comment")));
        onView(withId(R.id.comment_send)).check(matches(withText("Create")));
        onView(withId(R.id.comment_parent)).check(matches(withId(R.id.comment_parent)));
    }
}
