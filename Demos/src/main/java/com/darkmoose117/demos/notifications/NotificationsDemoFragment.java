package com.darkmoose117.demos.notifications;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.darkmoose117.demos.Constants;
import com.darkmoose117.demos.R;

/**
 * Created by Joshua Lamson on 11/25/13.
 */
public class NotificationsDemoFragment extends Fragment implements Constants, View.OnClickListener {

    private static final int SIMPLE_NOTIFICATION_ID = 0;

    private NotificationManager mNotificationManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notifications_demo_fragment, container, false);

        Button simpleNotificationButton = (Button) view.findViewById(R.id.simple_notification_button);
        simpleNotificationButton.setOnClickListener(this);

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
            default:
                Toast.makeText(getActivity(), "not implemeneted yet", Toast.LENGTH_SHORT).show();
        }
    }

    private void showSimpleNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setSmallIcon(R.drawable.ic_stat_test)
                .setContentTitle("Sup yo")
                .setContentText("This is a simple notification");

        mNotificationManager.notify(SIMPLE_NOTIFICATION_ID, builder.build());
    }
}