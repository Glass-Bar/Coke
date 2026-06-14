package com.ciube.glass.coke;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;
import com.ciube.glass.coke.item.GolfIndicator;
import com.ciube.glass.coke.item.GolfMenuItem;

import com.google.android.glass.widget.CardScrollAdapter;

import java.util.List;

/**
 * Adapter that builds one card per GolfMenuItem.
 *
 * Views are built once and cached — no allocations during scrolling.
 * Each card shows:
 *   - An optional icon (only if GolfMenuItem.hasIcon() is true)
 *   - The item name centred on the card
 */
public class GolfCardScrollAdapter extends CardScrollAdapter {

    private final List<GolfMenuItem> mItems;
    private final View[] mViews;
    private final TextView[] mIndicatorViews; // ← store refs to indicator TextViews

    public GolfCardScrollAdapter(Context context, List<GolfMenuItem> items) {
        mItems = items;
        mViews = new View[items.size()];
        mIndicatorViews = new TextView[items.size()];

        for (int i = 0; i < items.size(); i++) {
            mViews[i] = buildCardView(context, items.get(i), i);
        }
    }

    @Override
    public int getCount() { return mItems.size(); }

    @Override
    public Object getItem(int position) { return mItems.get(position); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return mViews[position];
    }

    @Override
    public int getPosition(Object item) { return mItems.indexOf(item); }

    public void refreshIndicators() {
        for (int i = 0; i < mItems.size(); i++) {
            GolfMenuItem item = mItems.get(i);
            if (item.hasIndicator() && mIndicatorViews[i] != null) {
                GolfIndicator.Provider indicator = item.getIndicator();
                String text = indicator.getText();
                int color = indicator.getColor();
                Log.d("RefreshIndicators", "item=" + item.getName() + " text=" + text);
                mIndicatorViews[i].setText(text);
                mIndicatorViews[i].setTextColor(color);
                mIndicatorViews[i].invalidate();
            }
        }
    }

    private View buildCardView(Context ctx, GolfMenuItem item, int index) {
        LinearLayout root = new LinearLayout(ctx);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.TRANSPARENT);
        root.setGravity(Gravity.CENTER);
        root.setPadding(dp(ctx, 40), dp(ctx, 20), dp(ctx, 40), dp(ctx, 20));

        // Optional icon — works for both TOGGLE and SLIDER items
        if (item.hasIcon()) {
            ImageView icon = new ImageView(ctx);
            icon.setImageResource(item.getIconResId());
            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(
                dp(ctx, 48), dp(ctx, 48)
            );
            iconParams.bottomMargin = dp(ctx, 12);
            iconParams.gravity = Gravity.CENTER_HORIZONTAL;
            icon.setLayoutParams(iconParams);
            root.addView(icon);
        }

        // Item name
        TextView nameView = new TextView(ctx);
        nameView.setText(item.getName());
        nameView.setTextColor(Color.WHITE);
        nameView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
        nameView.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        nameView.setGravity(Gravity.CENTER);
        root.addView(nameView);

        // Optional indicator — works for both TOGGLE and SLIDER items
        if (item.hasIndicator()) {
            GolfIndicator.Provider indicator = item.getIndicator();
            TextView indicatorView = new TextView(ctx);
            indicatorView.setText(indicator.getText());
            indicatorView.setTextColor(indicator.getColor());
            indicatorView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            indicatorView.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
            indicatorView.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams indicatorParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
            indicatorParams.topMargin = dp(ctx, 4);
            indicatorView.setLayoutParams(indicatorParams);
            root.addView(indicatorView);
            mIndicatorViews[index] = indicatorView;
        }

        return root;
    }

    private static int dp(Context ctx, int dp) {
        return Math.round(TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp,
            ctx.getResources().getDisplayMetrics()
        ));
    }
}