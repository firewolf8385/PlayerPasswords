/*
 * This file is part of PlayerPasswords, licensed under the MIT License.
 *
 *  Copyright (c) JadedMC
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package com.github.firewolf8385.playerpasswords.settings;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a configurable message in messages.yml.
 * Used to easily access configured messages and acts as a failsafe if one of them is missing.
 */
public enum ConfigMessage {
    LOGIN_LOGIN_TO_CONTINUE("Messages.Login.LOGIN_TO_CONTINUE", "<color1><bold>Login</bold> <color3>» <gray>You must log in to continue! <color2>/login [password]<gray>."),
    LOGIN_LOGIN_USAGE("Messages.Login.LOGIN_USAGE", "<red><bold>Usage</bold> <dark_gray>» <red>/login [password]"),
    LOGIN_LOGGED_IN_SUCCESSFULLY("Messages.Login.LOGGED_IN_SUCCESSFULLY", "<color1><bold>Login</bold> <color3>» <gray>You have logged in successfully."),
    LOGIN_ALREADY_LOGGED_IN("Messages.Login.ALREADY_LOGGED_IN", "<red><bold>Error</bold> <dark_gray>» <red>You are already logged in!"),
    REGISTER_REGISTER_TO_CONTINUE("Messages.Register.REGISTER_TO_CONTINUE", "<color1><bold>Register</bold> <color3>» <gray>You must register to continue! <color2>/register [password]<gray>."),
    REGISTER_REGISTER_USAGE("Messages.Register.REGISTER_USAGE", "<red><bold>Usage</bold> <color3>» <red>/register [password]."),
    REGISTER_CONFIRM_REGISTER("Messages.Register.CONFIRM_REGISTER", "<color1><bold>Register</bold> <color3>» <gray>Confirm your password with <color2>/register confirm [password]<gray>."),
    REGISTER_REGISTER_CONFIRM_USAGE("Messages.Register.REGISTER_CONFIRM_USAGE", "<red><bold>Usage</bold> <color3>» <red>/register confirm [password]."),
    REGISTER_ALREADY_REGISTERED("Messages.Register.ALREADY_REGISTERED", "<red><bold>Error</bold> <color3>» <red>You are already registered!"),
    PASSWORD_PASSWORD_ENABLED("Messages.Password.PASSWORD_ENABLED", "<color1><bold>Password</bold> <color3>» <gray>You have <green>enabled <gray>password protection."),
    PASSWORD_PASSWORD_DISABLED("Messages.Password.PASSWORD_DISABLED", "<color1><bold>Password</bold> <color3>» <gray>You have <red>disabled <gray>password protection."),
    PASSWORD_PASSWORD_NOT_ENABLED("Messages.Password.PASSWORD_NOT_ENABLED", "<color1><bold>Password</bold> <color3>» <gray>Enable your password with <color2>/password enable<gray>."),
    PASSWORD_PASSWORD_NOT_SET("Messages.Password.PASSWORD_NOT_SET", "<red><bold>Error</bold> <color3>» <red>You do not have a password set. Set one with /password set [password]."),
    PASSWORD_PASSWORD_SET_USAGE("Messages.Password.PASSWORD_SET_USAGE", "<red><bold>Usage</bold> <color3>» <red>/password set [password]"),
    PASSWORD_PASSWORD_SET_SUCCESSFULLY("Messages.Password.PASSWORD_SET_SUCCESSFULLY", "<color1><bold>Password</bold> <color3>» <gray>You have successfully set your password."),
    PASSWORD_CONFIRM_PASSWORD("Messages.Password.CONFIRM_PASSWORD", "<color1><bold>Password</bold> <color3>» <gray>Confirm your password with <color2>/password confirm [password]<gray>."),
    PASSWORD_PASSWORD_CONFIRM_USAGE("Messages.Password.PASSWORD_CONFIRM_USAGE", "<red><bold>Usage</bold> <color3>» <gray>/password confirm [password]"),
    PASSWORD_OPTIONAL_PASSWORD_DISABLED("Messages.Password.OPTIONAL_PASSWORD_DISABLED", "<red><bold>Error</bold> <color3>» <red>Optional Passwords are disabled."),
    PASSWORD_PASSWORD_OUT_OF_BOUNDS("Messages.Password.PASSWORD_OUT_OF_BOUNDS", "<red><bold>Error</bold> <color3>» <red>Password must be between <white>%minimum% <red>and <white>%maximum% <red>characters long."),
    PASSWORD_PASSWORD_INCORRECT("Messages.Password.PASSWORD_INCORRECT", "<red><bold>Error</bold> <color3>» <red>Password Is Incorrect. Try again."),
    PASSWORD_HELP_PAGE("Messages.password.HELP_PAGE", "<reset><newline><center><color3><st>        </st> <color1><bold>Passwords <color3><st>        </st><newline><reset><newline><color3>  » <color1>/password enable <color3>- <color2>Enables Your Password<newline><color3>  » <color1>/password disable <color3>- <color2>Disables Your Password<newline><color3>  » <color1>/password set [password] <color3>- <color2>Changes Your Password<newline><reset>"),
    MISC_NO_PERMISSION("Messages.Misc.NO_PERMISSION", "<red><bold>Error</bold> <color3>» <red>You do not have permission to do that."),
    MISC_NOT_A_PLAYER("Messages.Misc.NOT_A_PLAYER", "<red><bold>Error</bold> <color3>» <red>Only players can use that command."),
    MISC_PLAYER_DOES_NOT_EXIST("Messages.Misc.PLAYER_DOES_NOT_EXIST", "<red><bold>Error</bold> <color3>» <red>That player does not exist!"),
    MISC_MUST_BE_LOGGED_IN("Messages.Misc.MUST_BE_LOGGED_IN", "<red><bold>Error</bold> <color3>» <red>You must be logged in to do that."),
    MISC_CONFIG_RELOADED("Messages.Misc.CONFIG_RELOADED", "<color1><bold>PlayerPasswords</bold> <color3>» <green>Configuration has been reloaded!"),
    MISC_PASSWORD_RESET("Messages.Misc.PASSWORD_RESET", "<color1><bold>PlayerPasswords</bold> <color3>» <green>Password has been reset successfully!"),
    MISC_PASSWORD_RESET_USAGE("Messages.Misc.PASSWORD_RESET_USAGE", "<red><bold>Usage</bold> <color3>» <red>/password reset [player]");

    private final String key;
    private final String defaultMessage;

    /**
     * Creates the config message.
     * @param key Key value for messages.yml.
     * @param defaultMessage Default message, used when nothing is found in messages.yml.
     */
    ConfigMessage(@NotNull final String key, @NotNull final String defaultMessage) {
        this.key = key;
        this.defaultMessage = defaultMessage;
    }

    /**
     * Gets the default message.
     * @return Default message string.
     */
    @NotNull
    public String getDefaultMessage() {
        return defaultMessage;
    }

    /**
     * Get the key of the message in messages.yml.
     * @return Message key.
     */
    @NotNull
    public String getKey() {
        return key;
    }
}
