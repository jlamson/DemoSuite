package com.darkmoose117.demos.customdrawing;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.darkmoose117.demos.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class CircleTouchFragment extends Fragment {


    public CircleTouchFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return new CircleTouchView(getActivity());
    }


}
