package com.darkmoose117.demos.customdrawing;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
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

    private static final float CIRCLE_INTERCEPT_THRESHOLD = 16f;

    private static float sLargeCircleRadius;
    private static float sSmallCircleRadius;
    private static float sInterceptRadius;

    private Paint mCirclePaint;
    private Paint mInterceptPaint;
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

        mInterceptPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInterceptPaint.setColor(getResources().getColor(R.color.green));
        mInterceptPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        sLargeCircleRadius = getResources().getDimension(R.dimen.large_circle_radius);
        sSmallCircleRadius = getResources().getDimension(R.dimen.small_circle_radius);
        sInterceptRadius = getResources().getDimension(R.dimen.intercept_radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int centerX = canvas.getWidth() / 2;
        final int centerY = canvas.getHeight() / 2;

        setRectForCircle(centerX, centerY, sLargeCircleRadius);
        canvas.drawArc(mRectF, 0, 360, false, mCirclePaint);

        if (mIsPointerDown) {
            setRectForCircle(mPointerX, mPointerY, sSmallCircleRadius);
            canvas.drawArc(mRectF, 0, 360, false, mCirclePaint);

            float[] intercepts = getCircleIntercepts(centerX, centerY, sLargeCircleRadius,
                    mPointerX, mPointerY, sSmallCircleRadius);

            if (intercepts.length >= 4) drawPoint(canvas, intercepts[2], intercepts[3]);
            if (intercepts.length >= 2) drawPoint(canvas, intercepts[0], intercepts[1]);
        }
    }

    private void setRectForCircle(float cX, float cY, float r) {
        mRectF.set(cX - r, cY - r, cX + r, cY + r);
    }

    private void drawPoint(Canvas canvas, float x, float y) {
        canvas.drawCircle(x, y, sInterceptRadius, mInterceptPaint);
    }

    /**
     *
     * @param centerX the X of the center of the large stationary circle
     * @param centerY the Y of the center of the large stationary circle
     * @param rC the radius of the large stationary circle
     * @param pointerX  the X of the circle centered at the user's pointer finger
     * @param pointerY  the Y of the circle centered at the user's pointer finger
     * @param rP the raduis of the circle centered at the user's pointer finger
     * @return the intercepts of the two circles. [x1, y1, x2, y2] if two intercepts exist, [x, y]
     *         if only one, and [] is none.
     */
    private static float[] getCircleIntercepts(float centerX, float centerY, float rC, float pointerX, float pointerY, float rP) {
        Double doubleHolder;
        // x and y are the center of the small circle with the center of the large adjusted to (0, 0)
        float x = pointerX - centerX;
        float y = pointerY - centerY;
        doubleHolder = new Double(Math.atan(x / y));
        float theta = doubleHolder.floatValue();

        // dT is the total distance between the two centers
        doubleHolder = Math.sqrt((double) (x * x + y * y));
        float dT = doubleHolder.floatValue();

        if (dT > rC + rP) {
            // the circles do on intercept
            return new float[0];
        } else if (Math.abs(dT - rC - rP) < CIRCLE_INTERCEPT_THRESHOLD) {
            // the circles only have one intercept
            int sign = y > 0 ? 1 : -1;

            float result[] = new float[2];
            doubleHolder = centerX + sign * rC * Math.sin(theta);
            result[0] = doubleHolder.floatValue();
            doubleHolder = centerY + sign * rC * Math.cos(theta);
            result[1] = doubleHolder.floatValue();
            return result;
        }

        // TODO add math for 2 intercepts
        return new float[0];
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