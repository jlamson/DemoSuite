package com.darkmoose117.demos.customdrawing;

import android.content.Context;
import android.graphics.*;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.darkmoose117.demos.R;

/**
 * TODO: document your custom view class.
 */
public class CircleTouchView extends View {

    private static final float CIRCLE_INTERCEPT_THRESHOLD = 4f;

    private static float sLargeCircleRadius;
    private static float sSmallCircleRadius;
    private static float sInterceptRadius;

    private static Paint sCirclePaint;
    private static Paint sInterceptPaint;
    private static TextPaint sDebugTextPaint;
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

        if (sCirclePaint == null) {
            sCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            sCirclePaint.setColor(getResources().getColor(R.color.purple));
            sCirclePaint.setStrokeWidth(getResources().getDimension(R.dimen.circle_stoke_width));
            sCirclePaint.setStyle(Paint.Style.STROKE);
        }

        if (sInterceptPaint == null) {
            sInterceptPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            sInterceptPaint.setColor(getResources().getColor(R.color.green));
            sInterceptPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        }

        if (sDebugTextPaint == null) {
            sDebugTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            sDebugTextPaint.setTextSize(getResources().getDimension(R.dimen.debug_text_size));
        }

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
        canvas.drawArc(mRectF, 0, 360, false, sCirclePaint);

        if (mIsPointerDown) {
            setRectForCircle(mPointerX, mPointerY, sSmallCircleRadius);
            canvas.drawArc(mRectF, 0, 360, false, sCirclePaint);

            float[] intercepts = getCircleIntercepts(centerX, centerY, sLargeCircleRadius,
                    mPointerX, mPointerY, sSmallCircleRadius, canvas);

            if (intercepts.length >= 4) drawPoint(canvas, intercepts[2], intercepts[3]);
            if (intercepts.length >= 2) drawPoint(canvas, intercepts[0], intercepts[1]);
        }
    }

    private void setRectForCircle(float cX, float cY, float r) {
        mRectF.set(cX - r, cY - r, cX + r, cY + r);
    }

    private void drawPoint(Canvas canvas, float x, float y) {
        canvas.drawCircle(x, y, sInterceptRadius, sInterceptPaint);
    }

    private static Double sDoubleHolder = new Double(0.0);
    /**
     *
     * @param centerX the X of the center of the large stationary circle
     * @param centerY the Y of the center of the large stationary circle
     * @param rC the radius of the large stationary circle
     * @param pointerX  the X of the circle centered at the user's pointer finger
     * @param pointerY  the Y of the circle centered at the user's pointer finger
     * @param rP the radius of the circle centered at the user's pointer finger
     * @param canvas used for printing debug information.
     * @return the intercepts of the two circles. [x1, y1, x2, y2] if two intercepts exist, [x, y]
     *         if only one, and [] is none.
     */
    private static float[] getCircleIntercepts(float centerX, float centerY, float rC, float pointerX, float pointerY, float rP, Canvas canvas) {
        // x and y are the center of the small circle with the center of the large adjusted to
        // (0, 0).
        float x = pointerX - centerX;
        float y = pointerY - centerY;
        float theta = getTheta(x, y);

        // dT is the total distance between the two centers
        float dT = toFloat(Math.sqrt((double) (x * x + y * y)));

        if (dT > rC + rP + CIRCLE_INTERCEPT_THRESHOLD || rC > dT + rP) {
            // the circles do on intercept
            return new float[0];
        } else if (Math.abs(dT - rC - rP) < CIRCLE_INTERCEPT_THRESHOLD) {
            // the circles only have one intercept

            float[] result = new float[2];
            result[0] = toFloat(centerX - rC * Math.sin(theta));
            result[1] = toFloat(centerY - rC * Math.cos(theta));
            return result;
        }

        float[] result = new float[4];
        float a = getLengthOfRadical(dT, rC, rP);
        float dR = getDistanceToRadical(dT, rC, rP);
        float xR = toFloat(centerX + dR * Math.sin(theta));
        float yR = toFloat(centerY + dR * Math.cos(theta));

        float halfA = a / 2f;
        float xA = toFloat(halfA * Math.cos(theta));
        float yA = toFloat(halfA * Math.sin(theta));

        result[0] = xR + xA;
        result[1] = yR - yA;
        result[2] = xR - xA;
        result[3] = yR + yA;
        return result;
    }

    private static float getTheta(float x, float y) {
        return toFloat(Math.atan2((double) -y, (double) x) - Math.PI / 2f);
    }

    /**
     * Math from http://mathworld.wolfram.com/Circle-CircleIntersection.html equation (9). This
     * method returns the length of the radical line created by the intercepts of two circles (must
     * have two intercepts) given the following:
     * @param dT the distance between the two centers
     * @param rC the radius of the large stationary circle
     * @param rP the radius of the circle centered at the user's pointer finger
     * @return the length of the radical line created by the two intercepts of the circles
     */
    private static float getLengthOfRadical(float dT, float rC, float rP) {
        return toFloat(Math.sqrt(
                (-dT + rP - rC) * (-dT - rP + rC) * (-dT + rP + rC) * (dT + rP + rC)) / dT);
    }

    /**
     * Math from http://mathworld.wolfram.com/RadicalLine.html equation (3). This method returns the
     * length along the line created between the two centers from rC to the radical line gicen the
     * following:
     * @param dT the distance between the two centers
     * @param rC the radius of the large stationary circle
     * @param rP the radius of the circle centered at the user's pointer finger
     * @return the distane from the center of the screen to the radical line.
     */
    private static float getDistanceToRadical(float dT, float rC, float rP) {
        return -((dT * dT) + (rC * rC) - (rP * rP)) / (2 * dT);
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

    private static float toFloat(Double lDouble) {
        return lDouble.floatValue();
    }
}