package com.ciube.glass.coke;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.MotionEvent;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import com.google.android.glass.widget.CardScrollView;

import java.util.List;

public class GolfMenuActivity extends Activity {

    private static final String TAG = "GolfMenuActivity";
    private final android.os.Handler mHandler = new android.os.Handler();

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

        setContentView(mCardScrollView);
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
                    executeAction(item, 0);
                }
                return true;
            }
        }
        return false;
    }

    private boolean handleSliderGesture(Gesture gesture) {
        switch (gesture) {
            case SWIPE_RIGHT:
                mSliderView.increment();
                playSoundTap();
                return true;
            case SWIPE_LEFT:
                mSliderView.decrement();
                playSoundTap();
                return true;
            case TAP:
                final int value = mSliderView.getValue();
                final GolfMenuItem sliderItem = mSliderItem;
                playSoundTap();
                closeSlider();
                executeAction(sliderItem, value);
                return true;
            case SWIPE_DOWN:
                closeSlider();
                playSoundTap();
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
        setContentView(mSliderView);
    }

    private void closeSlider() {
        mSliderMode = false;
        mSliderItem = null;
        mSliderView = null;
        setContentView(mCardScrollView);
    }

    private void executeAction(final GolfMenuItem item, final int value) {
        playSoundTap();
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