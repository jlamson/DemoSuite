package com.darkmoose117.demos.notifications;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.widget.TextView;

import com.darkmoose117.demos.R;

public class NotificationResultActivity extends Activity {

    private static final String EXTRA_TEXT_TO_DISPLAY = "EXTRA_TEXT_TO_DISPLAY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_result);

        Bundle extras = getIntent().getExtras();
        if (extras == null || !extras.containsKey(EXTRA_TEXT_TO_DISPLAY)) {
            throw new IllegalArgumentException("Missing extra for text to show");
        }

        TextView textView = (TextView) findViewById(R.id.notification_result_text);
        textView.setText(extras.getString(EXTRA_TEXT_TO_DISPLAY));

    }

    public static PendingIntent getNotificationIntent(Context context, String textToDisplay) {
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, NotificationResultActivity.class);
        resultIntent.putExtra(EXTRA_TEXT_TO_DISPLAY, textToDisplay);

        // The stack builder object will contain an artificial back stack for the started Activity.
        // This ensures that navigating backward from the Activity leads out of your application to
        // the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(NotificationResultActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);

        return stackBuilder.getPendingIntent(
                0, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
