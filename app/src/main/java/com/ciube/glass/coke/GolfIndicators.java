package com.ciube.glass.coke;

import android.content.Context;
import android.net.wifi.WifiManager;

public class GolfIndicators {

    public static GolfIndicator.Provider wifi(final Context context) {
        return new GolfIndicator.Provider() {
            @Override
            public String getText() {
                WifiManager wifiManager =
                    (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                return wifiManager.isWifiEnabled() ? "On" : "Off";
            }

            @Override
            public int getColor() {
                WifiManager wifiManager =
                    (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                return wifiManager.isWifiEnabled() ? GolfIndicator.GREEN : GolfIndicator.RED;
            }
        };
    }
}