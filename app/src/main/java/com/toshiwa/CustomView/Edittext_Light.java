package com.toshiwa.CustomView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by leometric-2 on 17/3/17.
 */


public class Edittext_Light extends EditText {

    public Edittext_Light(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public Edittext_Light(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public Edittext_Light(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = Typeface.createFromAsset(context.getAssets(),
                "roboto-slab.light.ttf");
        setTypeface(customFont);
    }

}
