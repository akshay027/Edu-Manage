package com.maxlore.edumanage.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Nikhil on 15-05-2016.
 */
public class inMeghTextViewTitle extends TextView {

    public inMeghTextViewTitle(Context context) {
        super(context);
        setFont();
    }

    public inMeghTextViewTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public inMeghTextViewTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    private void setFont() {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/heading_open_sans_semibold_font.ttf");
        setTypeface(typeface);
    }
}
