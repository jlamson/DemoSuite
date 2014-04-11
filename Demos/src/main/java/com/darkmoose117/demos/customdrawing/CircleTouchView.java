package com.darkmoose117.demos.customdrawing;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.darkmoose117.demos.R;

/**
 * TODO: document your custom view class.
 */
public class CircleTouchView extends View {

    private static float sLargeCircleRadius;
    private static float sSmallCircleRadius;

    private Paint mCirclePaint;
    private RectF mRectF = new RectF();

    private float mPointerX;
    private float mPointerY;
    private boolean mIsPointerDown;

    public CircleTouchView(Context context) {
        this(context, null, 0);
    }

    public CircleTouchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleTouchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(getResources().getColor(R.color.purple));
        mCirclePaint.setStrokeWidth(getResources().getDimension(R.dimen.large_circle_stoke_width));
        mCirclePaint.setStyle(Paint.Style.STROKE);


        sLargeCircleRadius = getResources().getDimension(R.dimen.large_circle_radius);
        sSmallCircleRadius = getResources().getDimension(R.dimen.small_circle_radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int centerHeight = canvas.getHeight() / 2;
        final int centerWidth = canvas.getWidth() / 2;

        mRectF.set(centerWidth - sLargeCircleRadius, centerHeight - sLargeCircleRadius,
                centerWidth + sLargeCircleRadius, centerHeight + sLargeCircleRadius);
        canvas.drawArc(mRectF, 0, 360, false, mCirclePaint);

        if (mIsPointerDown) {
            mRectF.set(mPointerX - sSmallCircleRadius, mPointerY - sSmallCircleRadius,
                    mPointerX + sSmallCircleRadius, mPointerY + sSmallCircleRadius);
            canvas.drawArc(mRectF, 0, 360, false, mCirclePaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() > 0 && event.getAction() != MotionEvent.ACTION_CANCEL && event.getAction() != MotionEvent.ACTION_UP) {
            mIsPointerDown = true;
            mPointerX = event.getX(0);
            mPointerY = event.getY(0);
            invalidate();
            return true;
        }

        mIsPointerDown = false;
        return super.onTouchEvent(event);
    }
}