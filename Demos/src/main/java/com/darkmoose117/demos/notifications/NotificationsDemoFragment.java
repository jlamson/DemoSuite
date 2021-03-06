package com.darkmoose117.demos.notifications;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preview.support.v4.app.NotificationManagerCompat;
import android.preview.support.wearable.notifications.RemoteInput;
import android.preview.support.wearable.notifications.WearableNotifications;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.darkmoose117.demos.Constants;
import com.darkmoose117.demos.R;

import java.util.ArrayList;

/**
 * Created by Joshua Lamson on 11/25/13.
 */
public class NotificationsDemoFragment extends Fragment implements Constants, View.OnClickListener {
    private static final String TAG = NotificationsDemoFragment.class.getSimpleName();

    /*
     *  TODO do a big style notification
     *      http://developer.android.com/guide/topics/ui/notifiers/notifications.html#CreateNotification
     */

    private NotificationManagerCompat mNotificationManager;
    private CheckBox mProgressCheckbox;

    private int mNotificationCount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notifications_demo_fragment, container, false);

        view.findViewById(R.id.simple_notification_button).setOnClickListener(this);
        view.findViewById(R.id.progress_notification_button).setOnClickListener(this);
        
        view.findViewById(R.id.reply_notification_button).setOnClickListener(this);
        view.findViewById(R.id.multi_page_notification_button).setOnClickListener(this);
        view.findViewById(R.id.stacked_notification_button).setOnClickListener(this);

        mProgressCheckbox = (CheckBox) view.findViewById(R.id.notification_ongoing_progress_cb);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mNotificationManager = NotificationManagerCompat.from(activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.simple_notification_button:
                showSimpleNotification();
                break;
            case R.id.progress_notification_button:
                showProgressNotification();
                break;
            case R.id.reply_notification_button:
                showReplyNotification();
                break;
            case R.id.multi_page_notification_button:
                showMultiPageNotification();
                break;
            case R.id.stacked_notification_button:
                showStackedNotification();
                break;
            default:
                Toast.makeText(getActivity(), "not implemented yet", Toast.LENGTH_SHORT).show();
        }
    }

    private void showSimpleNotification() {
        PendingIntent pendingIntent = NotificationResultActivity.getNotificationIntent(
                getActivity(),
                "You got here from the simple notification",
                SIMPLE_NOTIFICATION_ID);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setSmallIcon(R.drawable.ic_stat_test)
                .setContentTitle("Test!")
                .setContentText("This is a simple notification")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.wear_background));

        mNotificationManager.notify(SIMPLE_NOTIFICATION_ID, builder.build());
    }

    private void showReplyNotification() {
        final String replyLabel = getResources().getString(R.string.default_notification_reply_label);
        final String[] replyChoices = getResources().getStringArray(R.array.default_notification_replies);

        PendingIntent pendingIntent = NotificationResultActivity.getNotificationIntent(
                getActivity(),
                "You got here from a reply notification",
                REPLY_NOTIFICATION_ID);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setSmallIcon(R.drawable.ic_stat_test)
                .setContentTitle("Test!")
                .setContentText("You can reply to this!")
                .setContentIntent(pendingIntent)
                .setNumber(++mNotificationCount)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.wear_background));

        RemoteInput remoteInput = new RemoteInput.Builder(NotificationResultActivity.EXTRA_REPLY_TEXT)
                .setLabel(replyLabel)
                .setChoices(replyChoices)
                .build();

        Notification notification = new WearableNotifications.Builder(builder)
                .addRemoteInputForContentIntent(remoteInput)
                .build();

        mNotificationManager.notify(REPLY_NOTIFICATION_ID, notification);
    }

    private void showMultiPageNotification() {
        final Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.jt);
        ArrayList<Notification> notificationPages = new ArrayList<Notification>();
        String[] pageDescriptions = getResources().getStringArray(R.array.multi_page_notification_text);
        final int pageCount = pageDescriptions.length;

        for (int i = 0; i < pageCount; ++i) {
            NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
            style.bigText(pageDescriptions[i]);
            if (i == pageCount - 1) {
                style.setBigContentTitle("In Summary...");
            } else {
                style.setBigContentTitle(String.format("Step %d of %d", i + 1, pageCount));
            }
            style.setSummaryText("");
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
            builder.setStyle(style);
            notificationPages.add(builder.build());
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
            .setLargeIcon(background)
            .setContentTitle("Multi-Pager")
            .setContentText("It's my D#@% in a box!")
            .setSmallIcon(R.drawable.ic_stat_test);

        Notification notification = new WearableNotifications.Builder(builder)
                .addPages(notificationPages)
                .build();

        mNotificationManager.notify(MULTI_PAGE_NOTIFICATION_ID, notification);
    }

    private void showStackedNotification() {
        final Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.jt);
        final ArrayList<Notification> notifications = new ArrayList<Notification>();
        final String[] messages = getResources().getStringArray(R.array.stacked_notification_text);
        final int COUNT = messages.length;

        for (String message : messages) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                    .setLargeIcon(background)
                    .setContentTitle("Message: J.T")
                    .setContentText(message)
                    .setSmallIcon(R.drawable.ic_stat_test);

            notifications.add(new WearableNotifications.Builder(builder)
                    .setGroup(STACKED_NOTIFICATION_GROUP_KEY)
                    .build());
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setLargeIcon(background)
                .setContentTitle("J.T")
                .setContentText(COUNT + " new messages from: J.T")
                .setSmallIcon(R.drawable.ic_stat_test);

        notifications.add(new WearableNotifications.Builder(builder)
                .setGroup(STACKED_NOTIFICATION_GROUP_KEY, WearableNotifications.GROUP_ORDER_SUMMARY)
                .build());

        for (int i = 0; i < notifications.size(); i++) {
            mNotificationManager.notify(STACKED_NOTIFICATION_ID + i, notifications.get(i));
        }
    }

    private void showProgressNotification() {

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setContentTitle("Picture Download")
                .setContentText("Download in progress")
                .setSmallIcon(R.drawable.ic_stat_test)
                .setOngoing(true);

        // Start a lengthy operation in a background thread
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int incr;
                        for (incr = 0; incr <= 100; incr += 5) {
                            builder.setProgress(100, incr, false);
                            mNotificationManager.notify(PROGRESS_NOTIFICATION_ID, builder.build());

                            try {
                                // Do generic work
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                Log.d(TAG, "sleep failure");
                            }
                        }

                        // When the loop is finished, cancel the ongoing notification and add a removable one
                        mNotificationManager.cancel(PROGRESS_NOTIFICATION_ID);
                        builder.setContentText("Download complete")
                                // Removes the progress bar
                                .setProgress(0, 0, false)
                                .setOngoing(false);
                        mNotificationManager.notify(PROGRESS_COMPLETE_NOTIFICATION_ID, builder.build());
                    }
                }
        ).start();
    }
}