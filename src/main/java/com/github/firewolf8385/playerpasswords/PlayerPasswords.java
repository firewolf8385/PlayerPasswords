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
package com.github.firewolf8385.playerpasswords;

import com.github.firewolf8385.playerpasswords.settings.SettingsManager;
import org.jetbrains.annotations.NotNull;

/**
 * API Methods for the plugin.
 * Designed for external use.
 */
public class PlayerPasswords {
    private static PlayerPasswordsPlugin plugin;
    private static final SettingsManager settings = SettingsManager.getInstance();

    /**
     * Initializes the API.
     * @param pl Instance of the PlayerPasswords plugin.
     */
    protected static void initialize(@NotNull final PlayerPasswordsPlugin pl) {
        plugin = pl;
    }

    /**
     * Check if chat is configured to be blocked for unverified players.
     * @return
     */
    public static boolean isChatBlocked() {
        // Disable the block if it's not set in config.yml.
        if(!settings.getConfig().isSet("BlockChat")) {
            return false;
        }

        // Otherwise check config.yml.
        return settings.getConfig().getBoolean("BlockChat");
    }

    /**
     * Check if commands are configued to be blocked for unverified players.
     * @return True if blocked, false if not.
     */
    public static boolean isCommandsBlocked() {
        // Disable the block if it's not set in config.yml.
        if(!settings.getConfig().isSet("BlockCommands")) {
            return false;
        }

        // Otherwise check config.yml.
        return settings.getConfig().getBoolean("BlockCommands");
    }

    /**
     * Check if item interaction is configured to be blocked for unverified players.
     * @return True if blocked, false it not.
     */
    public static boolean isInteractBlocked() {
        // Disable the block if it's not set in config.yml.
        if(!settings.getConfig().isSet("BlockInteract")) {
            return false;
        }

        // Otherwise check config.yml.
        return settings.getConfig().getBoolean("BlockInteract");
    }

    /**
     * Check if item dropping is configured to be blocked for unverified players.
     * @return True if blocked, false if not.
     */
    public static boolean isItemDropBlocked() {
        // Disable the block if it's not set in config.yml.
        if(!settings.getConfig().isSet("BlockItemDrop")) {
            return false;
        }

        // Otherwise check config.yml.
        return settings.getConfig().getBoolean("BlockItemDrop");
    }

    /**
     * Check if movement is configured to be blocked for unverified players.
     * @return True if blocked, false if not.
     */
    public static boolean isMovementBlocked() {
        // Disable the block if it's not set in config.yml.
        if(!settings.getConfig().isSet("BlockMovement")) {
            return false;
        }

        // Otherwise check config.yml.
        return settings.getConfig().getBoolean("BlockMovement");
    }
}