package com.darkmoose117.demos.notifications;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.preview.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.darkmoose117.demos.R;

/**
 * Created by Joshua Lamson on 4/9/14.
 */
public class SimpleNotificationIntentService extends IntentService {

    private static final String TAG = SimpleNotificationIntentService.class.getSimpleName();

    public SimpleNotificationIntentService(String name) {
        super(name);
    }

    public static PendingIntent getSimpleNotificationPendingIntent(Context context) {
        Log.d(TAG, "getSimpleNotificationPendingIntent");
        Intent intent = new Intent(context, SimpleNotificationIntentService.class);
        return PendingIntent.getService(context, 0, intent, 0);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent");
        PendingIntent pendingIntent = NotificationResultActivity.getNotificationIntent(
                this,
                "You got here from the simple notification launched from the home screen widget",
                NotificationsDemoFragment.SIMPLE_NOTIFICATION_ID);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_test)
                .setContentTitle("Test!")
                .setContentText("This is a simple notification")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.wear_background));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NotificationsDemoFragment.SIMPLE_NOTIFICATION_ID, builder.build());
    }

}
