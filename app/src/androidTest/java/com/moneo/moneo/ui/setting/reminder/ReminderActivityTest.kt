package com.moneo.moneo.ui.setting.reminder

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Before
import org.junit.Test
import com.moneo.moneo.R
import org.hamcrest.Matchers.allOf

class ReminderActivityTest{

    @Before
    fun setup(){
        ActivityScenario.launch(ReminderActivity::class.java)
    }

    @Test
    fun NotificationTest(){
        Intents.init()
        // Tunggu hingga aktivitas muncul
        Espresso.onView(withId(R.id.switch_reminder)).check(matches(isDisplayed()))

        // Aktifkan notifikasi
        Espresso.onView(withId(R.id.switch_reminder)).perform(click())

        // Tunggu hingga notifikasi muncul
        Thread.sleep(5000)

        // Pastikan notifikasi muncul
        val notificationMatcher = allOf(
            isDisplayed(),
            withText("Daily Prediction"),
            withContentDescription("Total Income:")
        )

        Espresso.onView(notificationMatcher).check(matches(isDisplayed()))

        // Nonaktifkan notifikasi
        Espresso.onView(withId(R.id.switch_reminder)).perform(click())
    }
}