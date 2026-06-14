package com.ciube.glass.coke;

import android.graphics.Color;

public class GolfIndicator {

    public interface Provider {
        String getText();
        int getColor();
    }

    public static Provider fixed(final String text, final int color) {
        return new Provider() {
            @Override public String getText() { return text; }
            @Override public int getColor()   { return color; }
        };
    }

    // Official Glass color palette
    public static final int WHITE  = Color.parseColor("#ffffff");
    public static final int GRAY   = Color.parseColor("#808080");
    public static final int BLUE   = Color.parseColor("#34a7ff");
    public static final int RED    = Color.parseColor("#cc3333");
    public static final int GREEN  = Color.parseColor("#99cc33");
    public static final int YELLOW = Color.parseColor("#ddbb11");
}