package com.jimliuxyz.tsnote.activity.main;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.jimliuxyz.tsnote.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private final static String TITLE1 = "TEST TITLE";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private void waitms(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void delBookByItemIdx(int itemIdx) {
        onView(
                allOf(withId(R.id.recyclerView))
        ).perform(actionOnItemAtPosition(itemIdx, longClick()));

        onView(
                allOf(withId(android.R.id.button1))
        ).perform(click());
    }

    private void delBookByTitle(String bookTitle) {
        onView(
                allOf(withId(R.id.recyclerView))
        ).perform(actionOnItem(hasDescendant(withText(bookTitle)), longClick()));
        //.perform(scrollTo(hasDescendant(withText(bookTitle))));
        waitms(1000);

        onView(
                allOf(withId(android.R.id.button1))
        ).perform(click());
        waitms(1000);
    }

    private void addBook(String bookTitle) {
        onView(
                allOf(withId(R.id.fab),
                        isDisplayed())
        ).perform(click());

        onView(
                allOf(withId(R.id.tvBookTitle),
                        isDisplayed())
        ).perform(
                clearText(),
                replaceText(""),
                typeText(bookTitle),
                closeSoftKeyboard()
        );
        waitms(1000);

        pressBack();
        waitms(1000);

        pressBack();
    }

    @Test
    public void testAddDel() {
        addBook(TITLE1);

        delBookByTitle(TITLE1);

        onView(
                allOf(
                        isDescendantOfA(withId(R.id.recyclerView)),
                        withText(TITLE1)
                )
        ).check(doesNotExist());
    }

}
