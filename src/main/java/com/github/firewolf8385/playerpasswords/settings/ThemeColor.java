package com.github.firewolf8385.playerpasswords.settings;

/**
 * Represents the theme colors set in the config file.
 * If a value is missing, it uses the default instead.
 */
public enum ThemeColor {
    GOLD("color1", "<gold>"),
    YELLOW("color2", "<yellow>"),
    GRAY("color3", "<dark_gray>");

    private final String configName;
    private final String defaultValue;
    private final SettingsManager settings = SettingsManager.getInstance();

    /**
     * Creates the enum.
     * @param configName The name of the setting in the config.
     * @param defaultValue Default value of the setting.
     */
    ThemeColor(String configName, String defaultValue) {
        this.configName = configName;
        this.defaultValue = defaultValue;
    }

    /**
     * Converts the enum into it's expected String.
     * @return Formatted color string.
     */
    public String toString() {
        String configValue = settings.getConfig().getString(configName);

        if(configValue != null) {
            return configValue;
        }
        else {
            return defaultValue;
        }
    }
}