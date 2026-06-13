package com.ciube.glass.coke;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Launched by the "ok glass, start a round of golf" voice trigger.
 * Its only job is to start the immersion activity and finish itself.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, GolfMenuActivity.class);
        startActivity(intent);
        finish(); // don't leave this stub in the back stack
    }
}
