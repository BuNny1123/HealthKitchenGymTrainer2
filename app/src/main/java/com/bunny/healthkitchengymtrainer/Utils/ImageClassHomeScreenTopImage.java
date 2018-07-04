package com.bunny.healthkitchengymtrainer.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class ImageClassHomeScreenTopImage extends android.support.v7.widget.AppCompatImageView {

    public ImageClassHomeScreenTopImage(Context context) {
        super(context);
    }

    public ImageClassHomeScreenTopImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageClassHomeScreenTopImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d = getDrawable();
        if (d != null) {
            int w = View.MeasureSpec.getSize(widthMeasureSpec);
            int h = w * d.getIntrinsicHeight() / d.getIntrinsicWidth();
            setMeasuredDimension(w, h);
        }
        else super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}