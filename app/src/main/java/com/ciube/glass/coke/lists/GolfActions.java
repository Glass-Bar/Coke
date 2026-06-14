package com.ciube.glass.coke.lists;

import android.content.Context;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.util.Log;

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
}