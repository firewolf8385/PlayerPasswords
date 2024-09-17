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

import com.github.firewolf8385.playerpasswords.player.PasswordPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * API Methods for the plugin.
 * Designed for external use.
 */
public class PlayerPasswords {
    private static PlayerPasswordsPlugin plugin;

    /**
     * Initializes the API.
     * @param pl Instance of the PlayerPasswords plugin.
     */
    protected static void initialize(@NotNull final PlayerPasswordsPlugin pl) {
        plugin = pl;
    }

    /**
     * Check if chat is configured to be blocked for unverified players.
     * @return True if blocked, false if not.
     */
    public static boolean isChatBlocked() {
        return plugin.getConfigManager().isChatBlocked();
    }

    /**
     * Check if commands are configued to be blocked for unverified players.
     * @return True if blocked, false if not.
     */
    public static boolean isCommandsBlocked() {
        return plugin.getConfigManager().isCommandsBlocked();
    }

    /**
     * Check if item interaction is configured to be blocked for unverified players.
     * @return True if blocked, false it not.
     */
    public static boolean isInteractBlocked() {
        return plugin.getConfigManager().isInteractBlocked();
    }

    /**
     * Check if item dropping is configured to be blocked for unverified players.
     * @return True if blocked, false if not.
     */
    public static boolean isItemDropBlocked() {
        return plugin.getConfigManager().isItemDropBlocked();
    }

    /**
     * Check if movement is configured to be blocked for unverified players.
     * @return True if blocked, false if not.
     */
    public static boolean isMovementBlocked() {
        return plugin.getConfigManager().isMovementBlocked();
    }

    /**
     * Check if a given player is logged in (verified).
     * @param player Player to check verification status of.
     * @return true if they are verified, false if they are not.
     */
    public static boolean isVerified(@NotNull final Player player) {
        final PasswordPlayer passwordPlayer = plugin.getPasswordPlayerManager().getPlayer(player);

        if(passwordPlayer == null) {
            return false;
        }

        return passwordPlayer.isVerified();
    }
}