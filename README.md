# Coke

A Glass utility Glassware

- AI slopped
- tested on XE 24

---

### Avaibles functions
| Name | Description |
|---|---|
| Wifi Toggle | Enable / Disable Wifi - persists after shutdown |
| Screen Off Timeout | Set how long the display remains illuminated during periods of inactivity |
| Screen Brightness | Set the display brightness level |

---

### Add a new Item

### Step 1 — Choose the item type
* **Toggle** — a single tap executes the action immediately.
* **Slider** — a tap opens a scrollable value picker; a second tap confirms and executes the action with the chosen value.

### Step 2 — Define the action in `GolfActions.java`
Add a public static method. For toggles the value parameter is ignored; for sliders it carries the number the user selected. 

### Step 3 — Define the indicator in `GolfIndicators.java` (optional)
Create a provider class implementing `GolfIndicator.Provider`. Implement `getText()` to return the live display string and `getColor()` to pass a constant from the Glass color palette.

### Step 4 — Add the item to `GolfMenuItems.java`
Instantiate your item inside the `getItems()` list. Select the specific `GolfMenuItem` constructor overload that matches your chosen combination of actions, sliders, and static or dynamic initial value providers.

### Structure
| Property | Type | Required | Description |
| :--- | :--- | :---: | :--- |
| **name** | String | ✅ | Label displayed on the card |
| **action** | GolfAction | ✅ | Code executed when the item is tapped (toggle) or confirmed (slider) |
| **indicator** | GolfIndicator.Provider | ❌ | Live text shown below the name (e.g. "On", "15s") |
| **iconResId** | int (drawable) | ❌ | Icon shown above the name, only available via full constructor |
| **type** | ItemType | auto | `TOGGLE` or `SLIDER`, inferred from which constructor you use |
| **minValue** | int | slider only | Minimum value of the slider |
| **maxValue** | int | slider only | Maximum value of the slider |
| **initialValue** | Provider/int | ❌ | Dynamic starting position of the slider, defaults to `minValue` |
| **refreshDelay** | int (ms) | auto | How long to wait before refreshing the indicator after action, defaults to 1500ms |s

