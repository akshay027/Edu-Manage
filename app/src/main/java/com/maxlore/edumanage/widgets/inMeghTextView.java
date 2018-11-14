package com.maxlore.edumanage.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Akshay on 02-May-16.
 */
public class inMeghTextView extends TextView {

    public inMeghTextView(Context context) {
        super(context);
        setFont();
    }

    public inMeghTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public inMeghTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    private void setFont() {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/text_open_sans_font.ttf");
        setTypeface(typeface);
    }
}
