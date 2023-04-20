package com.github.firewolf8385.playerpasswords.settings;

/**
 * Represents the plugin messages set in the config file.
 * If a value is missing, it uses the default instead.
 */
public enum PluginMessage {
    // Login Messages
    LOGIN("Login", "&6&lLogin &8» &7You must log in to continue! &e/login [password]"),
    REGISTER("Register", "&6&lRegister &8» &7You must register to continue! &e/register [password]"),
    LOGIN_SUCCESSFUL("LogInSuccessful", "&6&lLogin &8» &7You have logged in successfully."),
    LOGIN_USAGE("LoginUsage", "&6&lLogin &8» &c/login [password]"),
    CONFIRM_REGISTER("ConfirmRegister", "&6&lRegister &8» &7Confirm your password with &e/register confirm [password]"),
    REGISTER_CONFIRM_USAGE("RegisterConfirmUsage", "&6&lUsage &8» &7/register confirm [password]"),

    // Password Messages
    PASSWORD_ENABLED("PasswordEnabled", "&6&lPassword &8» &7You have &aenabled &7password protection."),
    PASSWORD_DISABLED("PasswordDisabled", "&6&lPassword &8» &7You have &cdisabled &7password protection."),
    PASSWORD_SET("PasswordSet", "&6&lPassword &8» &7Your password has been set to &e%password%&7."),
    SET_PASSWORD_SUCCESSFUL("SetPasswordSuccessful", "&6&lPassword &8» &7You have successfully set your password."),
    REGISTER_USAGE("RegisterUsage", "&6&lUsage &8» &7/register [password]"),
    PASSWORD_INCORRECT("PasswordIncorrect", "&6&lError &8» &cPassword Is Incorrect. Try again."),
    PASSWORD_SET_USAGE("PasswordSetUsage", "&6&lUsage &8» &7/password set [password]"),
    PASSWORD_NOT_SET("PasswordNotSet", "&6&lError &8» &cYou do not have a password set. Set one with /password set [password]."),
    CONFIRM_PASSWORD("ConfirmPassword", "&6&lPassword &8» &7Confirm your password with &e/password confirm [password]"),
    PASSWORD_CONFIRM_USAGE("PasswordConfirmUsage", "&6&lUsage &8» &7/password confirm [password]"),

    // Error Messages
    ALREADY_LOGGED_IN("AlreadyLoggedIn", "&6&lError &8» &cYou are already logged in!"),
    ALREADY_REGISTERED("AlreadyRegistered", "&6&lError &8» &cYou are already registered!"),
    NO_PERMISSION("NoPermission", "&6&lError &8» &cYou do not have permission to do that."),
    OUT_OF_BOUNDS("OutOfBounds", "&6&lError &8» &cPassword must be between 1 and 256 characters long."),
    MUST_BE_LOGGED_IN("MustBeLoggedIn", "&6&lError &8» &cYou must be logged in to do that."),
    OPTIONAL_PASSWORD_DISABLED("OptionalPasswordsDisabled", "&6&lError &8» &cOptional Passwords are disabled."),

    // Other Messages
    CONFIG_RELOADED("ConfigReloaded", "&6&lPlayerPasswords &8» &aConfiguration has been reloaded!");

    private final String configName;
    private final String defaultValue;
    private final SettingsManager settings = SettingsManager.getInstance();

    /**
     * Creates the enum.
     * @param configName The name of the setting in the config.
     * @param defaultValue Default value of the setting.
     */
    PluginMessage(String configName, String defaultValue) {
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