package com.toshiwa.CustomView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by leometric-2 on 17/3/17.
 */


public class TextView_Regular extends TextView {

    public TextView_Regular(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public TextView_Regular(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public TextView_Regular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = Typeface.createFromAsset(context.getAssets(),
                "roboto-slab.regular.ttf");
        setTypeface(customFont);
    }

}
