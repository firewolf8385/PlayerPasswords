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
package com.github.firewolf8385.playerpasswords.player;

import com.github.firewolf8385.playerpasswords.PlayerPasswordsPlugin;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PasswordPlayer {
    private final PlayerPasswordsPlugin plugin;
    private final boolean required;
    private boolean verified;
    private final Player player;
    private int loginAttempts = 0;
    private String password = "";

    /**
     * Create a PasswordPlayer
     * @param player Player object.
     */
    public PasswordPlayer(@NotNull final PlayerPasswordsPlugin plugin, @NotNull final Player player) {
        this.plugin = plugin;
        this.player = player;

        boolean optional = plugin.getConfigManager().getConfig().getBoolean("Optional");
        boolean enabled = plugin.getConfigManager().getData().getBoolean("passwords." + player.getUniqueId() + ".enabled");
        boolean requiredPerm = player.hasPermission("playerpasswords.required");
        boolean bypass = player.hasPermission("playerpasswords.bypass");

        if(enabled) {
            verified = false;
            required = true;
            return;
        }

        if((requiredPerm || !optional) && !bypass) {
            verified = false;
            required = true;
            return;
        }

        verified = true;
        required = false;
    }

    /**
     * Adds a login attempt to the player.
     */
    public void addLoginAttempt() {
        loginAttempts++;
    }

    /**
     * Get the number of times the player has tried to log in.
     * @return Login attempts.
     */
    public int getLoginAttempts() {
        return loginAttempts;
    }

    /**
     * Get the currently cached password.
     * Used for confirming passwords before setting them.
     * @return Cached password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get the player.
     * @return Player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get if a player has a password.
     * @return Whether the player has set a password.
     */
    public boolean hasPassword() {

        // If the player does not have any data, then they can't have a password.
        if(!plugin.getConfigManager().getData().isSet("passwords." + player.getUniqueId() + ".password")) {
            return false;
        }

        // If there is data, check if the data is empty.
        return !plugin.getConfigManager().getData().getString("passwords." + player.getUniqueId() + ".password").isEmpty();
    }

    /**
     * Get if the player has their password enabled.
     * @return Whether the password is enabled.
     */
    public boolean hasPasswordEnabled() {
        return plugin.getConfigManager().getData().getBoolean("passwords." + player.getUniqueId() + ".enabled");
    }

    /**
     * Get if the player requires a password.
     * @return Whether a password is required.
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Get if the player is verified.
     * @return Whether it is verified.
     */
    public boolean isVerified() {
        return verified;
    }

    /**
     * Change the stored password cache.
     * @param password New password to save.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set if the player is verified.
     * @param verified Whether the player is verified.
     */
    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}