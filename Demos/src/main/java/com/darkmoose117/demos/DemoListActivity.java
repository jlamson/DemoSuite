package com.darkmoose117.demos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;

import com.darkmoose117.demos.interfaces.OnDemoSelected;
import com.darkmoose117.demos.utils.DemoUtils;

/**
 * An activity representing a list of Demos. This activity has different presentations for handset
 * and tablet-size devices. On handsets, the activity presents a list of items, which when touched,
 * lead to a {@link DemoDetailActivity} representing item details. On tablets, the activity presents
 * the list of items and item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a {@link DemoListFragment} and
 * the item details (if present) is a fragment for the demo.
 * <p>
 * This activity also implements the required
 * {@link com.darkmoose117.demos.interfaces.OnDemoSelected} interface to listen for item
 * selections.
 */
public class DemoListActivity extends FragmentActivity implements OnDemoSelected, Constants {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // uses values-swdp6 refs.xml to map R.layout.activity_demo_list to activity_demo_twopane
        // when on a tablet.
        setContentView(R.layout.activity_demo_list);

        if (findViewById(R.id.demo_detail_container) != null) {
            // The detail container view will be present only in the when we've loaded
            // activity_demo_twopane
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((DemoListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.demo_list))
                    .setActivateOnItemClick(true);
        }

        // TODO If exposing deep links into your app, handle intents here.
    }

    @Override
    public void onDemoSelected(int id) {
        if (mTwoPane) {
            Fragment fragment = DemoUtils.getFragmentForId(id);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.demo_detail_container, fragment)
                    .commit();
        } else {
            Intent detailIntent = new Intent(this, DemoDetailActivity.class);
            detailIntent.putExtra(EXTRA_DEMO_ID, id);
            startActivity(detailIntent);
        }
    }
}
