package com.ciube.glass.coke;

public class GolfMenuItem {

    public interface InitialValueProvider {
        int get();
    }

    private final String name;
    private final int iconResId;
    private final GolfAction action;
    private final GolfIndicator.Provider indicator;
    private final ItemType type;
    private final int minValue;
    private final int maxValue;
    private final int refreshDelay;
    private final InitialValueProvider initialValueProvider;

    // Full constructor
    public GolfMenuItem(String name, int iconResId, GolfAction action,
                        GolfIndicator.Provider indicator, ItemType type,
                        int minValue, int maxValue, int refreshDelay,
                        InitialValueProvider initialValueProvider) {
        this.name = name;
        this.iconResId = iconResId;
        this.action = action;
        this.indicator = indicator;
        this.type = type;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.refreshDelay = refreshDelay;
        this.initialValueProvider = initialValueProvider;
    }

    // TOGGLE with indicator
    public GolfMenuItem(String name, GolfAction action, GolfIndicator.Provider indicator) {
        this(name, 0, action, indicator, ItemType.TOGGLE, 0, 0, 1500,
            new InitialValueProvider() {
                @Override public int get() { return 0; }
            });
    }

    // TOGGLE without indicator
    public GolfMenuItem(String name, GolfAction action) {
        this(name, 0, action, null, ItemType.TOGGLE, 0, 0, 0,
            new InitialValueProvider() {
                @Override public int get() { return 0; }
            });
    }

    // SLIDER with indicator, defaults to minValue
    public GolfMenuItem(String name, GolfAction action,
                        GolfIndicator.Provider indicator, final int minValue, int maxValue) {
        this(name, 0, action, indicator, ItemType.SLIDER, minValue, maxValue, 1500,
            new InitialValueProvider() {
                @Override public int get() { return minValue; }
            });
    }

    // SLIDER with indicator and static initial value
    public GolfMenuItem(String name, GolfAction action,
                        GolfIndicator.Provider indicator, final int minValue, int maxValue,
                        final int initialValue) {
        this(name, 0, action, indicator, ItemType.SLIDER, minValue, maxValue, 1500,
            new InitialValueProvider() {
                @Override public int get() { return initialValue; }
            });
    }

    // SLIDER with indicator and dynamic initial value
    public GolfMenuItem(String name, GolfAction action,
                        GolfIndicator.Provider indicator, int minValue, int maxValue,
                        InitialValueProvider initialValueProvider) {
        this(name, 0, action, indicator, ItemType.SLIDER, minValue, maxValue, 1500,
            initialValueProvider);
    }

    // SLIDER without indicator
    public GolfMenuItem(String name, GolfAction action, final int minValue, int maxValue) {
        this(name, 0, action, null, ItemType.SLIDER, minValue, maxValue, 0,
            new InitialValueProvider() {
                @Override public int get() { return minValue; }
            });
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
    public int getInitialValue()                 { return initialValueProvider.get(); }
}