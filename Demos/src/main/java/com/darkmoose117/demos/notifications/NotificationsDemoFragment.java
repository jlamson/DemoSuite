package com.darkmoose117.demos.notifications;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.preview.support.v4.app.NotificationManagerCompat;
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

/**
 * Created by Joshua Lamson on 11/25/13.
 */
public class NotificationsDemoFragment extends Fragment implements Constants, View.OnClickListener {
    private static final String TAG = NotificationsDemoFragment.class.getSimpleName();

    /*
     *  TODO do a progress notification
     *      http://developer.android.com/guide/topics/ui/notifiers/notifications.html#Progress
     *  TODO do a big style notification
     *      http://developer.android.com/guide/topics/ui/notifiers/notifications.html#CreateNotification
     */

    private NotificationManager mNotificationManager;
    private CheckBox mOngoingCheckbox;
    private CheckBox mProgressCheckbox;

    private int mNotificationCount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notifications_demo_fragment, container, false);

        view.findViewById(R.id.simple_notification_button).setOnClickListener(this);
        mOngoingCheckbox = (CheckBox) view.findViewById(R.id.notification_ongoing_simple_cb);

        view.findViewById(R.id.progress_notification_button).setOnClickListener(this);
        mProgressCheckbox = (CheckBox) view.findViewById(R.id.notification_ongoing_progress_cb);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mNotificationManager = (NotificationManager) activity.getSystemService(
                Context.NOTIFICATION_SERVICE);
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
            default:
                Toast.makeText(getActivity(), "not implemented yet", Toast.LENGTH_SHORT).show();
        }
    }

    private void showSimpleNotification() {
        final boolean ongoing = mOngoingCheckbox.isChecked();
        PendingIntent pendingIntent = NotificationResultActivity.getNotificationIntent(
                getActivity(),
                "You got here from the simple notification",
                ongoing);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setSmallIcon(R.drawable.ic_stat_test)
                .setContentTitle("Test!")
                .setContentText("This is a simple notification")
                .setNumber(++mNotificationCount)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setAutoCancel(!ongoing)
                .setOngoing(ongoing)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.wear_accept, "Accept", pendingIntent)
                .addAction(R.drawable.wear_cancel, "Cancel", pendingIntent);

        mNotificationManager.notify(SIMPLE_NOTIFICATION_ID, builder.build());
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