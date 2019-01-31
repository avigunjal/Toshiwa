package com.toshiwa.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.toshiwa.toshiwa.R;

import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter {

    private int[] img;
    private LayoutInflater inflater;
    private Context context;


    public SliderAdapter(Context context, int[] images) {
        this.context = context;
        this.img=images;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.slider_layout, view, false);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.image);

       myImage.setImageResource(img[position]);

       view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}