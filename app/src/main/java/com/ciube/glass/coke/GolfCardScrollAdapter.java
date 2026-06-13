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

    public GolfCardScrollAdapter(Context context, List<GolfMenuItem> items) {
        mItems = items;
        mViews = new View[items.size()];

        // Build all views once up front
        for (int i = 0; i < items.size(); i++) {
            mViews[i] = buildCardView(context, items.get(i));
        }
    }

    // --- CardScrollAdapter contract ---

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return mViews[position]; // return cached view — zero allocation
    }

    @Override
    public int getPosition(Object item) {
        return mItems.indexOf(item);
    }

    // --- Card view builder ---

    private View buildCardView(Context ctx, GolfMenuItem item) {
        LinearLayout root = new LinearLayout(ctx);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.TRANSPARENT);
        root.setGravity(Gravity.CENTER);
        root.setPadding(dp(ctx, 40), dp(ctx, 20), dp(ctx, 40), dp(ctx, 20));

        // Optional icon
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
        LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        nameView.setLayoutParams(nameParams);
        root.addView(nameView);

        return root;
    }

    private static int dp(Context ctx, int dp) {
        return Math.round(TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp,
            ctx.getResources().getDisplayMetrics()
        ));
    }
}
