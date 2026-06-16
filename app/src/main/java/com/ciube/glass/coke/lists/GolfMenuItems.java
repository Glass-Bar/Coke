package com.ciube.glass.coke.lists;

import android.content.Context;
import java.util.Arrays;
import java.util.List;
import com.ciube.glass.coke.item.GolfAction;
import com.ciube.glass.coke.item.GolfMenuItem;
import android.view.Window;

public class GolfMenuItems {

public static List<GolfMenuItem> getItems(final Context context, final Window window) {
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
            ),

            new GolfMenuItem("Screen Brightness",
                new GolfAction() {
                    @Override
                    public void execute(int value) {
                        GolfActions.setScreenBrightness(window, context, value);                    }
                },
                GolfIndicators.screenBrightness(context),
                1, 100,
                new GolfMenuItem.InitialValueProvider() {
                    @Override
                    public int get() {
                        return GolfIndicators.getScreenBrightnessLevel(context);
                    }
                }
            )
        );
    }
}