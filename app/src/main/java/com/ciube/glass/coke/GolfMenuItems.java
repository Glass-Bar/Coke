package com.ciube.glass.coke;

import android.content.Context;
import java.util.Arrays;
import java.util.List;

public class GolfMenuItems {

    public static List<GolfMenuItem> getItems(final Context context) {
        return Arrays.asList(

            new GolfMenuItem("Toggle WiFi",
                new GolfAction() {
                    @Override
                    public void execute(int value) {
                        GolfActions.toggleWifi(context, value);
                    }
                },
                GolfIndicators.wifi(context)
            ),

            new GolfMenuItem("Screen Delay",
                new GolfAction() {
                    @Override
                    public void execute(int value) {
                        GolfActions.setScreenTimeout(context, value);
                    }
                },
                GolfIndicators.screenDelay(context),
                1, 60,
                new GolfMenuItem.InitialValueProvider() {
                    @Override
                    public int get() {
                        return GolfIndicators.getScreenTimeoutSeconds(context);
                    }
                }
            )
        );
    }
}