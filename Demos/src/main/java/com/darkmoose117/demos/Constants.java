package com.darkmoose117.demos;

/**
 * Created by Joshua Lamson on 11/25/13.
 */
public interface Constants {

    public static final String EXTRA_DEMO_ID = "DEMO_ID";

    // indexes and count for demos.
    public static final int NOTIFICATIONS_DEMO_ID       = 0;
    public static final int CUSTOM_DRAWING_DEMO_ID      = 1;
    public static final int ANIMATION_DEMO_ID           = 2;
    public static final int CIRCLE_TOUCH_DEMO_ID        = 3;
    public static final int DEMO_COUNT                  = 4;

    // titles for demos
    public static final String NOTIFICATIONS_DEMO_TITLE     = "Notifications";
    public static final String CUSTOM_DRAWING_DEMO_TITLE    = "Custom Drawing";
    public static final String ANIMATION_DEMO_TITLE         = "4.0+ Animation";
    public static final String CIRCLE_TOUCH_DEMO_TITLE      = "Circle Touch";

    // Notification keys
    public static final int SIMPLE_NOTIFICATION_ID              = 1;
    public static final int REPLY_NOTIFICATION_ID               = 2;
    public static final int MULTI_PAGE_NOTIFICATION_ID          = 3;
    public static final int PROGRESS_NOTIFICATION_ID            = 4;
    public static final int PROGRESS_COMPLETE_NOTIFICATION_ID   = 5;
    // We add numbers to this value, so ensure it's always larger than the others
    public static final int STACKED_NOTIFICATION_ID             = 6;

    public static final String STACKED_NOTIFICATION_GROUP_KEY = "stacked_group_key";

}
