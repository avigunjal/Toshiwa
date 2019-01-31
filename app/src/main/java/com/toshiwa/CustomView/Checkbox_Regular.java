package com.toshiwa.CustomView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class Checkbox_Regular extends android.support.v7.widget.AppCompatCheckBox {

    public Checkbox_Regular(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public Checkbox_Regular(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public Checkbox_Regular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = Typeface.createFromAsset(context.getAssets(),
                "roboto-slab.regular.ttf");
        setTypeface(customFont);
    }

}
