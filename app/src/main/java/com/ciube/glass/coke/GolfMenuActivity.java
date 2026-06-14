package com.ciube.glass.coke;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import com.google.android.glass.widget.CardScrollView;

import android.content.Context;
import android.media.AudioManager;
import com.google.android.glass.media.Sounds;

import java.util.List;

/**
 * Immersion activity that shows a horizontally scrollable list of golf
 * utility cards. Tapping on a card dispatches the item's action.
 *
 * To add behaviour to an action, extend handleAction() below.
 */
public class GolfMenuActivity extends Activity {

    private static final String TAG = "GolfMenuActivity";
    private final android.os.Handler mHandler = new android.os.Handler();

    private CardScrollView mCardScrollView;
    private GolfCardScrollAdapter mAdapter;
    private GestureDetector mGestureDetector;

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

    // Route touch events through the GestureDetector
    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mGestureDetector != null && mGestureDetector.onMotionEvent(event);
    }

    // --- Gesture detector ---

    private GestureDetector buildGestureDetector() {
        return new GestureDetector(this)
            .setBaseListener(new GestureDetector.BaseListener() {
                @Override
                public boolean onGesture(Gesture gesture) {
                    if (gesture == Gesture.TAP) {
                        int position = mCardScrollView.getSelectedItemPosition();
                        if (position >= 0 && position < mAdapter.getCount()) {
                            GolfMenuItem item = (GolfMenuItem) mAdapter.getItem(position);
                            handleAction(item);
                            return true;
                        }
                    }
                    return false;
                }
            });
    }

    // --- Action dispatcher ---

    /**
     * Called when the user taps on a card.
     * Add a case here for each ACTION_* string defined in GolfMenuItems.
     */
    private void handleAction(GolfMenuItem item) {
        AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audio.playSoundEffect(Sounds.TAP);

        Log.d(TAG, "Executing: " + item.getName());
        final GolfAction action = item.getAction();
        new Thread(new Runnable() {
            @Override
            public void run() {
                action.execute();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.refreshIndicators();
                    }
                }, 1500); // wait 1.5s for the command to take effect
            }
        }).start();
    }
}
