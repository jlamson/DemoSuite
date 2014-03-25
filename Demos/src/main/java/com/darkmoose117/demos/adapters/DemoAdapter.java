package com.darkmoose117.demos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.darkmoose117.demos.R;
import com.darkmoose117.demos.utils.DemoUtils;

/**
 * Created by Joshua Lamson on 11/25/13.
 */
public class DemoAdapter extends BaseAdapter {

    private final Context mContext;

    public DemoAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return DemoUtils.DEMOS.length;
    }

    @Override
    public Object getItem(int position) {
        return DemoUtils.DEMOS[position];
    }

    @Override
    public long getItemId(int position) {
        return DemoUtils.DEMOS[position].id;
    }

    private String getItemText(int position) {
        return DemoUtils.DEMOS[position].title;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) LayoutInflater.from(mContext).inflate(
                R.layout.demo_list_item, parent, false);
        view.setText(getItemText(position));
        return view;
    }
}
