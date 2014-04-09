package com.darkmoose117.demos.notifications;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.darkmoose117.demos.R;

/**
 * Created by Joshua Lamson on 4/9/14.
 */
public class NotificationAppWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int appWidgetCount = appWidgetIds.length;

        for (int i = 0; i < appWidgetCount; i++) {
            int appWidgetId = appWidgetIds[i];

            PendingIntent intent = SimpleNotificationIntentService.getSimpleNotificationPendingIntent(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.notifications_appwidget);
            views.setOnClickPendingIntent(R.id.widget_notification_button, intent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
