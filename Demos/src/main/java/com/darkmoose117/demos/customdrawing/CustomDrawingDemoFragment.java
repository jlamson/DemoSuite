package com.darkmoose117.demos.customdrawing;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.darkmoose117.demos.R;
import com.darkmoose117.demos.utils.EmailUtils;

/**
 * Created by Joshua Lamson on 12/3/13.
 */
public class CustomDrawingDemoFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.email_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EmailAdapter adapter = new EmailAdapter();
        adapter.setEmails(EmailUtils.getDemoEmailList());

        setListAdapter(adapter);
    }
}
