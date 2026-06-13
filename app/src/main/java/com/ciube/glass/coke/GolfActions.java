package com.ciube.glass.coke;

import android.util.Log;

import java.io.DataOutputStream;

public class GolfActions {

    private static final String TAG = "GolfActions";

    // --- Root helper ---

    private static void runAsRoot(String... commands) {
        try {
            for (String cmd : commands) {
                Log.d(TAG, "Running: " + cmd);
                Process process = Runtime.getRuntime().exec(cmd);

                // Drain stdout
                java.io.BufferedReader stdout = new java.io.BufferedReader(
                    new java.io.InputStreamReader(process.getInputStream()));
                String line;
                while ((line = stdout.readLine()) != null) {
                    Log.d(TAG, "stdout: " + line);
                }

                // Drain stderr
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

    // --- Actions ---

    public static void toggleWifi(android.content.Context context) {
        android.net.wifi.WifiManager wifiManager =
            (android.net.wifi.WifiManager) context.getSystemService(android.content.Context.WIFI_SERVICE);
        boolean isOn = wifiManager.isWifiEnabled();
        Log.d(TAG, "WiFi currently: " + (isOn ? "ON" : "OFF") + ", toggling...");
        wifiManager.setWifiEnabled(!isOn);
    }
}