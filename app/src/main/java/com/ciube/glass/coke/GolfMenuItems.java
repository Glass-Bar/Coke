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
                    public void execute() {
                        GolfActions.toggleWifi(context);
                    }
                },
                GolfIndicators.wifi(context)
            )

        );
    }
}