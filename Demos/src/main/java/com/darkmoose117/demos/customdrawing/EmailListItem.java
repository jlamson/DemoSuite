package com.darkmoose117.demos.customdrawing;

import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.*;
import android.text.Layout.*;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.darkmoose117.demos.model.Email;
import com.darkmoose117.demos.R;

/**
 * Created by Joshua Lamson on 12/19/13.
 */
public class EmailListItem extends View {

    private static int sPadding = -1;
    private static int sIconSize = -1;

    private Email mEmail;

    private Paint mIconPaint;
    private TextPaint mFromPaint;
    private TextPaint mPreviewPaint;
    private StaticLayout mFromLayout;
    private StaticLayout mPreviewLayout;

    private boolean mHasNewData = false;

    public EmailListItem(Context context) {
        this(context, null);
    }

    public EmailListItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmailListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initDimens();
        initPaints();
    }

    public void setEmail(Email email) {
        mEmail = email;
        mHasNewData = true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (mHasNewData || w != oldw || h != oldh) {
            final int textWidth = w
                    - 2 * sPadding - sIconSize // content and padding to left
                    - sPadding; // content and padding on right

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < mEmail.people.size(); i++) {
                builder.append(mEmail.people.get(i));
                if (i >= 4) break; // don't want the list to get too long
                if (i < mEmail.people.size() - 1) builder.append(", ");
            }

            mFromLayout = new StaticLayout(builder.toString(), mFromPaint, textWidth,
                    Alignment.ALIGN_NORMAL, 1f, 1f, false);

            builder = new StringBuilder();
            builder.append(mEmail.subject);
            builder.append(" \u2014 ");
            builder.append(mEmail.body);

            SpannableString preview = new SpannableString(builder.toString());
            preview.setSpan(new StyleSpan(Typeface.BOLD), 0, mEmail.subject.length(), 0);

            mPreviewLayout = new StaticLayout(preview, 0, preview.length(), mPreviewPaint,
                    textWidth, Alignment.ALIGN_NORMAL, 1f, 1f, false,
                    TextUtils.TruncateAt.END, textWidth);

            mHasNewData = false;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Ignore given specs. Will always be the same size
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                getResources().getDimensionPixelSize(R.dimen.list_item_height),
                MeasureSpec.EXACTLY);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                ViewGroup.LayoutParams.MATCH_PARENT,
                MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(sPadding, sPadding, sPadding + sIconSize, sPadding + sIconSize, mIconPaint);

        canvas.save();
        canvas.translate(2 * sPadding + sIconSize, sPadding);
        mFromLayout.draw(canvas);
        canvas.translate(0, mFromPaint.getTextSize() + 2);
        mPreviewLayout.draw(canvas);
        canvas.restore();
    }

    private void initDimens() {
        Resources res = getResources();

        if (sPadding < 0)   sPadding    = res.getDimensionPixelSize(R.dimen.list_item_padding);
        if (sIconSize < 0)  sIconSize   = res.getDimensionPixelSize(R.dimen.list_item_icon_size);
    }

    private void initPaints() {
        Resources res = getResources();


        mIconPaint = new TextPaint();
        mIconPaint.setColor(res.getColor(R.color.purple));

        mFromPaint = new TextPaint();
        mFromPaint.setAntiAlias(true);
        mFromPaint.setColor(res.getColor(R.color.subject_text_color));
        mFromPaint.setTextSize(res.getDimensionPixelSize(R.dimen.from_text_size));

        mPreviewPaint = new TextPaint();
        mPreviewPaint.setAntiAlias(true);
        mPreviewPaint.setColor(res.getColor(R.color.subject_text_color));
        mPreviewPaint.setTextSize(res.getDimensionPixelSize(R.dimen.subject_text_size));
    }
}
