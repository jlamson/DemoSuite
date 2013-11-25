package com.darkmoose117.demos.model;

import android.support.v4.app.Fragment;

import com.darkmoose117.demos.Constants;
import com.darkmoose117.demos.notifications.NotificationsDemoFragment;

/**
 * Created by Joshua Lamson on 11/25/13.
 */
public class DemoUtils implements Constants {

    public static Demo[] DEMOS = new Demo[DEMO_COUNT];
    static {
        DEMOS[NOTIFICATIONS_DEMO_ID] = new Demo(NOTIFICATIONS_DEMO_ID, NOTIFICATIONS_DEMO_TITLE);
    }

    public static Fragment getFragmentForId(int id) {
        switch (id) {
            case NOTIFICATIONS_DEMO_ID:
                return new NotificationsDemoFragment();
            default:
                throw new IllegalArgumentException(String.format("No Fragment for for id %d", id));
        }
    }

    public static String getTitleForId(int demoId) {
        return DEMOS[demoId].title;
    }
}
