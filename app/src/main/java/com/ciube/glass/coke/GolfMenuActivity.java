package com.ciube.glass.coke;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import com.google.android.glass.widget.CardScrollView;

import com.ciube.glass.coke.item.GolfAction;
import com.ciube.glass.coke.item.GolfMenuItem;
import com.ciube.glass.coke.item.GolfSliderView;
import com.ciube.glass.coke.item.ItemType;
import com.ciube.glass.coke.lists.GolfMenuItems;

import java.util.List;

public class GolfMenuActivity extends Activity {

    private static final String TAG = "GolfMenuActivity";
    private final android.os.Handler mHandler = new android.os.Handler();

    private FrameLayout mRootLayout;
    private CardScrollView mCardScrollView;
    private GolfCardScrollAdapter mAdapter;
    private GestureDetector mGestureDetector;

    private boolean mSliderMode = false;
    private GolfMenuItem mSliderItem;
    private GolfSliderView mSliderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<GolfMenuItem> items = GolfMenuItems.getItems(this);
        mAdapter = new GolfCardScrollAdapter(this, items);

        mCardScrollView = new CardScrollView(this);
        mCardScrollView.setAdapter(mAdapter);
        mCardScrollView.activate();

        mRootLayout = new FrameLayout(this);
        mRootLayout.addView(mCardScrollView);

        setContentView(mRootLayout);
        mGestureDetector = buildGestureDetector();
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mGestureDetector != null && mGestureDetector.onMotionEvent(event);
    }

    private GestureDetector buildGestureDetector() {
        return new GestureDetector(this)
            .setBaseListener(new GestureDetector.BaseListener() {
                @Override
                public boolean onGesture(Gesture gesture) {
                    if (mSliderMode) {
                        return handleSliderGesture(gesture);
                    } else {
                        return handleMenuGesture(gesture);
                    }
                }
            })
            .setScrollListener(new GestureDetector.ScrollListener() {
                @Override
                public boolean onScroll(float displacement, float delta, float velocity) {
                    if (mSliderMode) {
                        int steps = Math.round(delta / 10f);
                        if (steps > 0) {
                            for (int i = 0; i < steps; i++) mSliderView.decrement();
                        } else {
                            for (int i = 0; i < -steps; i++) mSliderView.increment();
                        }
                        return true;
                    }
                    return false;
                }
            });
    }

    private boolean handleMenuGesture(Gesture gesture) {
        if (gesture == Gesture.TAP) {
            int position = mCardScrollView.getSelectedItemPosition();
            if (position >= 0 && position < mAdapter.getCount()) {
                GolfMenuItem item = (GolfMenuItem) mAdapter.getItem(position);
                if (item.getType() == ItemType.SLIDER) {
                    openSlider(item);
                } else {
                    playSoundTap();
                    executeAction(item, 0);
                }
                return true;
            }
        }
        return false;
    }

    private boolean handleSliderGesture(Gesture gesture) {
        switch (gesture) {
            case TAP:
                final int value = mSliderView.getValue();
                final GolfMenuItem sliderItem = mSliderItem;
                playSoundTap();
                closeSlider();
                executeAction(sliderItem, value);
                return true;
            case SWIPE_DOWN:
                playSoundTap();
                closeSlider();
                return true;
            default:
                return false;
        }
    }

    private void openSlider(GolfMenuItem item) {
        playSoundTap();
        mSliderMode = true;
        mSliderItem = item;
        mSliderView = new GolfSliderView(this, item.getName(),
            item.getMinValue(), item.getMaxValue(), item.getInitialValue());

        mRootLayout.addView(mSliderView);
        mCardScrollView.setVisibility(android.view.View.GONE);
    }

    private void closeSlider() {
        mSliderMode = false;
        mRootLayout.removeView(mSliderView);
        mSliderItem = null;
        mSliderView = null;
        mCardScrollView.setVisibility(android.view.View.VISIBLE);
    }

    private void executeAction(final GolfMenuItem item, final int value) {
        final GolfAction action = item.getAction();
        new Thread(new Runnable() {
            @Override
            public void run() {
                action.execute(value);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.refreshIndicators();
                    }
                }, item.getRefreshDelay());
            }
        }).start();
    }

    private void playSoundTap() {
        AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audio.playSoundEffect(Sounds.TAP);
    }
}