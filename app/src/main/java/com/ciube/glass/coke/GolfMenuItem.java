package com.ciube.glass.coke;

public class GolfMenuItem {

    private final String name;
    private final int iconResId;
    private final GolfAction action;
    private final GolfIndicator.Provider indicator; // nullable

    public GolfMenuItem(String name, int iconResId, GolfAction action, GolfIndicator.Provider indicator) {
        this.name = name;
        this.iconResId = iconResId;
        this.action = action;
        this.indicator = indicator;
    }

    public GolfMenuItem(String name, GolfAction action, GolfIndicator.Provider indicator) {
        this(name, 0, action, indicator);
    }

    public GolfMenuItem(String name, GolfAction action) {
        this(name, 0, action, null);
    }

    public String getName()                  { return name; }
    public int getIconResId()                { return iconResId; }
    public boolean hasIcon()                 { return iconResId != 0; }
    public GolfAction getAction()            { return action; }
    public GolfIndicator.Provider getIndicator() { return indicator; }
    public boolean hasIndicator()            { return indicator != null; }
}