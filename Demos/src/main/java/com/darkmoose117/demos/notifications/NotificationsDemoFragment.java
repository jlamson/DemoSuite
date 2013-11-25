package com.darkmoose117.demos.notifications;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.darkmoose117.demos.R;
import com.darkmoose117.demos.Constants;

/**
 * Created by Joshua Lamson on 11/25/13.
 */
public class NotificationsDemoFragment extends Fragment implements Constants {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notifications_demo_fragment, container, false);
        ((TextView) view).setText(NOTIFICATIONS_DEMO_TITLE);
        return view;
    }
}