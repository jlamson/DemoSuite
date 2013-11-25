package com.darkmoose117.demos.notifications;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notifications_demo_fragment, container, false);

        Button simpleNotificationButton = (Button) view.findViewById(R.id.simple_notification_button);
        simpleNotificationButton.setOnClickListener(this);

        return view;
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
        Toast.makeText(getActivity(), "show simple notification", Toast.LENGTH_SHORT).show();
    }
}