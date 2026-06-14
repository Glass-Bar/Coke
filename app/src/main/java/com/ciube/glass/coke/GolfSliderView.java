package com.ciube.glass.coke;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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
    mValue = Math.max(min, Math.min(max, initialValue)); // clamp to range
    
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        setBackgroundColor(Color.TRANSPARENT);
        setPadding(dp(ctx, 40), dp(ctx, 20), dp(ctx, 40), dp(ctx, 20));

        // Item name
        TextView nameView = new TextView(ctx);
        nameView.setText(name);
        nameView.setTextColor(Color.WHITE);
        nameView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
        nameView.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        nameView.setGravity(Gravity.CENTER);
        addView(nameView);

        // Progress bar
        mProgressBar = new ProgressBar(ctx, null, android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setMax(mMax - mMin);
        mProgressBar.setProgress(mValue - mMin);
        LinearLayout.LayoutParams pbParams = new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, dp(ctx, 8)
        );
        pbParams.topMargin = dp(ctx, 16);
        pbParams.bottomMargin = dp(ctx, 8);
        mProgressBar.setLayoutParams(pbParams);
        addView(mProgressBar);

        // Value label
        mValueView = new TextView(ctx);
        mValueView.setText(String.valueOf(mValue));
        mValueView.setTextColor(Color.parseColor("#34a7ff")); // Glass blue
        mValueView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
        mValueView.setTypeface(Typeface.create("sans-serif-thin", Typeface.NORMAL));
        mValueView.setGravity(Gravity.CENTER);
        addView(mValueView);

        // Hint
        TextView hint = new TextView(ctx);
        hint.setText("swipe to adjust, tap to confirm");
        hint.setTextColor(Color.parseColor("#808080")); // Glass gray
        hint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        hint.setGravity(Gravity.CENTER);
        addView(hint);
    }

    public void increment() {
        setValue(mValue + 1);
    }

    public void decrement() {
        setValue(mValue - 1);
    }

    public int getValue() {
        return mValue;
    }

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