package com.ciube.glass.coke.item;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GolfSliderView extends LinearLayout {

    private final TextView mValueView;
    private final ProgressBar mProgressBar;
    private final int mMin;
    private final int mMax;
    private int mValue;

    public GolfSliderView(Context ctx, String name, int min, int max, int initialValue) {
        super(ctx);
        mMin = min;
        mMax = max;
        mValue = Math.max(min, Math.min(max, initialValue));

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        setBackgroundColor(Color.TRANSPARENT);
        setPadding(dp(ctx, 40), dp(ctx, 40), dp(ctx, 40), dp(ctx, 40));

        // Large value — 64sp Roboto Thin per Glass style guide
        mValueView = new TextView(ctx);
        mValueView.setText(String.valueOf(mValue));
        mValueView.setTextColor(Color.WHITE);
        mValueView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 64);
        mValueView.setTypeface(Typeface.create("sans-serif-thin", Typeface.NORMAL));
        mValueView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams valueParams = new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
        );
        valueParams.bottomMargin = dp(ctx, 24);
        mValueView.setLayoutParams(valueParams);
        addView(mValueView);

        // White progress bar
        mProgressBar = new ProgressBar(ctx, null, android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setMax(mMax - mMin);
        mProgressBar.setProgress(mValue - mMin);

        // Make the bar white
        ColorDrawable background = new ColorDrawable(Color.parseColor("#808080"));
        ColorDrawable fill = new ColorDrawable(Color.WHITE);
        ClipDrawable clip = new ClipDrawable(fill, Gravity.LEFT, ClipDrawable.HORIZONTAL);
        LayerDrawable layer = new LayerDrawable(new android.graphics.drawable.Drawable[]{background, clip});
        layer.setId(0, android.R.id.background);
        layer.setId(1, android.R.id.progress);
        mProgressBar.setProgressDrawable(layer);

        LinearLayout.LayoutParams pbParams = new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, dp(ctx, 4)
        );
        mProgressBar.setLayoutParams(pbParams);
        addView(mProgressBar);
    }

    public void increment() { setValue(mValue + 1); }
    public void decrement() { setValue(mValue - 1); }
    public int getValue()   { return mValue; }

    private void setValue(int value) {
        mValue = Math.max(mMin, Math.min(mMax, value));
        mValueView.setText(String.valueOf(mValue));
        mProgressBar.setProgress(mValue - mMin);
    }

    private static int dp(Context ctx, int dp) {
        return Math.round(TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp,
            ctx.getResources().getDisplayMetrics()
        ));
    }
}