package com.darkmoose117.demos.customdrawing;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.*;
import android.text.Layout.*;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.darkmoose117.demos.model.Email;
import com.darkmoose117.demos.R;

/**
 * Created by Joshua Lamson on 12/19/13.
 */
public class EmailListItem extends View {

    private static final String TAG = EmailListItem.class.getSimpleName();
    private static int sPadding = -1;
    private static int sIconSize = -1;

    private Email mEmail;

    private static Paint mIconPaint;
    private static TextPaint mFromPaint;
    private static TextPaint mPreviewPaint;
    private static TextPaint mDatePaint;
    private StaticLayout mFromLayout;
    private StaticLayout mPreviewLayout;

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
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (mEmail != null && (w != oldw || h != oldh)) {
            // Obtain width that Preview and From text can obtain
            final int textWidth = getWidth()
                    - 2 * sPadding - sIconSize // content and padding to left
                    - sPadding; // content and padding on right

            // Create From String
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < mEmail.people.size(); i++) {
                builder.append(mEmail.people.get(i));
                if (i >= 4) break; // don't want the list to get too long
                if (i < mEmail.people.size() - 1) builder.append(", ");
            }

            // Create From Layout
            mFromLayout = new StaticLayout(builder.toString(), mFromPaint, textWidth,
                    Alignment.ALIGN_NORMAL, 1f, 1f, false);

            // Create Preview String
            builder = new StringBuilder();
            builder.append(mEmail.subject);
            builder.append(" \u2014 ");
            builder.append(mEmail.body);

            // setup mPreviewLayout with full preview texts (any number of lines)
            mPreviewLayout = getPreviewLayout(textWidth, builder.toString());

            // find the index of the first letter of the third line
            int twoLineIndex = mPreviewLayout.getLineStart(Math.min(mPreviewLayout.getLineCount(), 2));

            // if the index is less than the entire string, chop of the end and add '...'
            if (twoLineIndex < builder.length()) {
                mPreviewLayout = getPreviewLayout(textWidth, builder.subSequence(0, twoLineIndex - 3) + "\u2026");
                Log.d(TAG, String.format("width: %d, textWidth: %d", w, textWidth));
            }


        }
    }

    private StaticLayout getPreviewLayout(int textWidth, String text) {
        // make subject bold
        SpannableString preview = new SpannableString(text);
        preview.setSpan(new StyleSpan(Typeface.BOLD),
                0, Math.min(text.length(), mEmail.subject.length()),
                0);

        // create layout with full text
        return new StaticLayout(preview, 0, preview.length(), mPreviewPaint,
                textWidth, Alignment.ALIGN_NORMAL, 1f, 1f, true,
                TextUtils.TruncateAt.END, textWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Ignore given specs. Will always be the same size
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                getResources().getDimensionPixelSize(R.dimen.list_item_height),
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

        if (mIconPaint == null) {
            mIconPaint = new TextPaint();
            mIconPaint.setColor(res.getColor(R.color.purple));
        }

        if (mFromPaint == null) {
            mFromPaint = new TextPaint();
            mFromPaint.setAntiAlias(true);
            mFromPaint.setColor(res.getColor(R.color.subject_text_color));
            mFromPaint.setTextSize(res.getDimensionPixelSize(R.dimen.from_text_size));
        }

        if (mPreviewPaint == null) {
            mPreviewPaint = new TextPaint();
            mPreviewPaint.setAntiAlias(true);
            mPreviewPaint.setColor(res.getColor(R.color.subject_text_color));
            mPreviewPaint.setTextSize(res.getDimensionPixelSize(R.dimen.preview_text_size));
        }

        if (mDatePaint == null) {
            mDatePaint = new TextPaint();
            mDatePaint.setAntiAlias(true);
            mDatePaint.setColor(res.getColor(R.color.date_text_color));
            mDatePaint.setTextSize(res.getDimensionPixelSize(R.dimen.date_text_size));
        }
    }
}
