package com.ciube.glass.coke;

public class GolfMenuItem {

    private final String name;
    private final int iconResId;
    private final GolfAction action;

    public GolfMenuItem(String name, int iconResId, GolfAction action) {
        this.name = name;
        this.iconResId = iconResId;
        this.action = action;
    }

    public GolfMenuItem(String name, GolfAction action) {
        this(name, 0, action);
    }

    public String getName()    { return name; }
    public int getIconResId()  { return iconResId; }
    public boolean hasIcon()   { return iconResId != 0; }
    public GolfAction getAction() { return action; }
}