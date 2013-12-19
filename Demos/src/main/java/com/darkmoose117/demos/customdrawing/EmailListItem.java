package com.darkmoose117.demos.customdrawing;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
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

    private TextPaint mTextPaint;
    private Paint mIconPaint;


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
        canvas.drawText(mEmail.subject, 2 * sPadding + sIconSize, mTextPaint.getTextSize() + sPadding, mTextPaint);
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

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(res.getColor(R.color.subject_text_color));
        mTextPaint.setTextSize(res.getDimensionPixelSize(R.dimen.subject_text_size));
    }
}
