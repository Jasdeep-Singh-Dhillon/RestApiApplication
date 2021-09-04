package com.jasdeep.restapiapplication;

import android.content.Context;
import android.content.Intent;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class IntentFactoryTest {
    @Test
    public void getIntentTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.jasdeep.restapiapplication", appContext.getPackageName());

        Intent intent = MainActivity.getIntent(appContext.getApplicationContext(),1, "Jasdeep");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        appContext.startActivity(intent);
        assertEquals(1, intent.getIntExtra(Util.USER_KEY, -1));
        assertEquals("Jasdeep", intent.getStringExtra(Util.USER_NAME));
    }

}