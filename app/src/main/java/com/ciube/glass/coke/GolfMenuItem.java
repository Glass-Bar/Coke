package com.ciube.glass.coke;

public class GolfMenuItem {

    private final String name;
    private final int iconResId;
    private final GolfAction action;
    private final GolfIndicator.Provider indicator;
    private final ItemType type;
    private final int minValue;
    private final int maxValue;
    private final int refreshDelay;
    private final int initialValue;

    // Full constructor
    public GolfMenuItem(String name, int iconResId, GolfAction action,
                        GolfIndicator.Provider indicator, ItemType type,
                        int minValue, int maxValue, int refreshDelay, int initialValue) {
        this.name = name;
        this.iconResId = iconResId;
        this.action = action;
        this.indicator = indicator;
        this.type = type;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.refreshDelay = refreshDelay;
        this.initialValue = initialValue;
    }

    // TOGGLE with indicator
    public GolfMenuItem(String name, GolfAction action, GolfIndicator.Provider indicator) {
        this(name, 0, action, indicator, ItemType.TOGGLE, 0, 0, 1200, 0);
    }

    // TOGGLE without indicator
    public GolfMenuItem(String name, GolfAction action) {
        this(name, 0, action, null, ItemType.TOGGLE, 0, 0, 0, 0);
    }

    // SLIDER with indicator
    public GolfMenuItem(String name, GolfAction action,
                        GolfIndicator.Provider indicator, int minValue, int maxValue) {
        this(name, 0, action, indicator, ItemType.SLIDER, minValue, maxValue, 1200, minValue);
    }

    // SLIDER with indicator and custom initial value
    public GolfMenuItem(String name, GolfAction action,
                        GolfIndicator.Provider indicator, int minValue, int maxValue, int initialValue) {
        this(name, 0, action, indicator, ItemType.SLIDER, minValue, maxValue, 1200, initialValue);
    }

    // SLIDER without indicator
    public GolfMenuItem(String name, GolfAction action, int minValue, int maxValue) {
        this(name, 0, action, null, ItemType.SLIDER, minValue, maxValue, 0, minValue);
    }

    public String getName()                      { return name; }
    public int getIconResId()                    { return iconResId; }
    public boolean hasIcon()                     { return iconResId != 0; }
    public GolfAction getAction()                { return action; }
    public GolfIndicator.Provider getIndicator() { return indicator; }
    public boolean hasIndicator()                { return indicator != null; }
    public ItemType getType()                    { return type; }
    public int getMinValue()                     { return minValue; }
    public int getMaxValue()                     { return maxValue; }
    public int getRefreshDelay()                 { return refreshDelay; }
    public int getInitialValue()                 { return initialValue; }
}