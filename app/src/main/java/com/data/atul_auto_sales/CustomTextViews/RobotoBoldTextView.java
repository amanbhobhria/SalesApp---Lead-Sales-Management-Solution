package com.data.atul_auto_sales.CustomTextViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class RobotoBoldTextView extends AppCompatTextView {

    public RobotoBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RobotoBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RobotoBoldTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/roboto_bold.ttf");
            setTypeface(tf);
        }
    }

}
