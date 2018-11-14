package com.maxlore.edumanage.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Akshay on 02-May-16.
 */
public class inMeghEditText extends EditText {

    public inMeghEditText(Context context) {
        super(context);
        setFont();
    }

    public inMeghEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public inMeghEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    private void setFont() {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/text_open_sans_font.ttf");
        setTypeface(typeface);
    }
}
