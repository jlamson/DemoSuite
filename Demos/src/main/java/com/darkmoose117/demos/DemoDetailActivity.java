package com.darkmoose117.demos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.darkmoose117.demos.utils.DemoUtils;

public class DemoDetailActivity extends FragmentActivity implements Constants {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_detail);

        Bundle extras = getIntent().getExtras();
        int demoId;
        if (extras == null || !extras.containsKey(EXTRA_DEMO_ID)) {
            throw new IllegalStateException(String.format(
                    "%s must be given the EXTRA_DEMO_ID extra",
                    DemoDetailActivity.class.getSimpleName()));
        }

        demoId = extras.getInt(EXTRA_DEMO_ID);

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(DemoUtils.getTitleForId(demoId));

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            Fragment fragment = DemoUtils.getFragmentForId(demoId);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.demo_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, DemoListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
