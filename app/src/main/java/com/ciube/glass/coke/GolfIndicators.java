package com.ciube.glass.coke;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.util.Log;

public class GolfIndicators {

    private static final String TAG = "GolfIndicators";

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

    public static int getScreenTimeoutSeconds(Context context) {
        try {
            int ms = Settings.System.getInt(
                context.getContentResolver(),
                Settings.System.SCREEN_OFF_TIMEOUT
            );
            return Math.max(1, Math.min(60, ms / 1000));
        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, "Could not read screen timeout", e);
            return 30;
        }
    }

    public static GolfIndicator.Provider screenDelay(final Context context) {
        return new GolfIndicator.Provider() {
            @Override
            public String getText() {
                return getScreenTimeoutSeconds(context) + "s";
            }

            @Override
            public int getColor() {
                return GolfIndicator.WHITE;
            }
        };
    }
}