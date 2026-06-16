package com.ciube.glass.coke.lists;

import android.content.Context;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

public class GolfActions {

    private static final String TAG = "GolfActions";

    private static void runAsRoot(String... commands) {
        try {
            for (String cmd : commands) {
                Log.d(TAG, "Running: " + cmd);
                Process process = Runtime.getRuntime().exec(cmd);

                java.io.BufferedReader stdout = new java.io.BufferedReader(
                    new java.io.InputStreamReader(process.getInputStream()));
                String line;
                while ((line = stdout.readLine()) != null) {
                    Log.d(TAG, "stdout: " + line);
                }

                java.io.BufferedReader stderr = new java.io.BufferedReader(
                    new java.io.InputStreamReader(process.getErrorStream()));
                while ((line = stderr.readLine()) != null) {
                    Log.e(TAG, "stderr: " + line);
                }

                int exitCode = process.waitFor();
                Log.d(TAG, "Exit code: " + exitCode);
            }
        } catch (Exception e) {
            Log.e(TAG, "Command failed", e);
        }
    }

    // value is unused for toggles, required by GolfAction interface
    public static void toggleWifi(Context context, int value) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        boolean isOn = wifiManager.isWifiEnabled();
        wifiManager.setWifiEnabled(!isOn);
    }

    public static void setScreenTimeout(Context context, int seconds) {
        android.provider.Settings.System.putInt(
            context.getContentResolver(),
            android.provider.Settings.System.SCREEN_OFF_TIMEOUT,
            seconds * 1000
        );
    }

    public static void setScreenBrightness(Window window, Context context, int value) {
        int raw = Math.round(value * 2.55f);
        Settings.System.putInt(
            context.getContentResolver(),
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        );
        Settings.System.putInt(
            context.getContentResolver(),
            Settings.System.SCREEN_BRIGHTNESS,
            raw
        );
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = raw / 255.0f;
        window.setAttributes(lp);
    }
}