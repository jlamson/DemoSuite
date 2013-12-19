package com.darkmoose117.demos.customdrawing;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.darkmoose117.demos.model.Email;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Joshua Lamson on 12/19/13.
 */
public class EmailAdapter extends BaseAdapter {

    private List<Email> mEmails;

    public EmailAdapter() {
        mEmails = new ArrayList<Email>();
    }

    public void setEmails(Collection<? extends Email> emails) {
        mEmails.clear();
        mEmails.addAll(emails);
    }

    @Override
    public int getCount() {
        return mEmails.size();
    }

    @Override
    public Email getItem(int position) {
        return position < getCount() ? mEmails.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = getView(convertView, parent.getContext());
        initView(textView, getItem(position));

        return textView;
    }

    private void initView(TextView textView, Email email) {
        textView.setText(email.subject);
    }

    private TextView getView(View convertView, Context context) {
        if (convertView == null) return new TextView(context);
        else return (TextView) convertView;
    }
}
