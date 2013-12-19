package com.darkmoose117.demos.customdrawing;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
        EmailListItem emailView = getView(convertView, parent.getContext());
        emailView.setEmail(getItem(position));

        return emailView;
    }

    private EmailListItem getView(View convertView, Context context) {
        if (convertView == null) return new EmailListItem(context);
        else return (EmailListItem) convertView;
    }
}
