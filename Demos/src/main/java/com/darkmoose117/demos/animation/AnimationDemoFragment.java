package com.darkmoose117.demos.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.darkmoose117.demos.R;

/**
 * Created by Joshua Lamson on 4/9/14.
 */
public class AnimationDemoFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = AnimationDemoFragment.class.getSimpleName();

    private static final int ENTER_ANIMATION_DURATION = 500;
    private static final int EXIT_ANIMATION_DURATION = 500;

    private static float sEnterY = -1f;
    private static float sExitY = -1f;
    private static TimeInterpolator sEnterInterpolator;
    private static TimeInterpolator sExitInterpolator;

    private ObjectAnimator mCircleAnimator;

    private boolean isReversing;
    private boolean isEnteringOrEntered;

    private FrameLayout mRootView;
    private TextView mDescriptionTextView;
    private ImageView mCircleView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = (FrameLayout) inflater.inflate(R.layout.animation_demo_fragment, container, false);
        mDescriptionTextView = (TextView) mRootView.findViewById(R.id.animation_description_text_view);
        mCircleView = (ImageView) mRootView.findViewById(R.id.animation_circle_view);

        if (sEnterInterpolator == null || sExitInterpolator == null) {
            sEnterInterpolator = new OvershootInterpolator();
            sExitInterpolator = new AccelerateInterpolator(1.5f);
        }

        mRootView.setOnClickListener(this);
        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    mRootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    mRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                sExitY = mRootView.getHeight();
                sEnterY = (sExitY - mCircleView.getHeight()) / 2f;

                mCircleAnimator = ObjectAnimator.ofFloat(mCircleView, View.TRANSLATION_Y, sEnterY);

                mCircleView.animate().translationY(mRootView.getHeight()).setDuration(0);
                isEnteringOrEntered = false;
                isReversing = false;
            }
        });
        return mRootView;
    }

    @Override
    public void onClick(View v) {
        if (mCircleAnimator.isRunning() && !isReversing) {
            mCircleAnimator.reverse();
            isReversing = true;
        } else {
            float destY = isEnteringOrEntered ? sExitY : sEnterY;
            TimeInterpolator interpolator = isEnteringOrEntered ?
                    sExitInterpolator : sEnterInterpolator;
            long duration = isEnteringOrEntered ?
                    EXIT_ANIMATION_DURATION : ENTER_ANIMATION_DURATION;

            mCircleAnimator.setInterpolator(interpolator);
            mCircleAnimator.setFloatValues(destY);
            mCircleAnimator.start();

            isReversing = false;
        }

        isEnteringOrEntered = !isEnteringOrEntered;
        int textResId = isEnteringOrEntered ? R.string.exit_circle_prompt : R.string.enter_circle_prompt;
        mDescriptionTextView.setText(textResId);
    }
}
