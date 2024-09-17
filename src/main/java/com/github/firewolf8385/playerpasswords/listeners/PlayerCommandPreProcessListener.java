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
package com.github.firewolf8385.playerpasswords.listeners;

import com.github.firewolf8385.playerpasswords.PlayerPasswordsPlugin;
import com.github.firewolf8385.playerpasswords.player.PasswordPlayer;
import com.github.firewolf8385.playerpasswords.settings.ConfigMessage;
import com.github.firewolf8385.playerpasswords.utils.ChatUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerCommandPreProcessListener implements Listener {
    private final PlayerPasswordsPlugin plugin;

    public PlayerCommandPreProcessListener(@NotNull final PlayerPasswordsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommand(@NotNull final PlayerCommandPreprocessEvent event) {
        final PasswordPlayer passwordPlayer = plugin.getPasswordPlayerManager().getPlayer(event.getPlayer());

        // Exit if player is verified.
        if(passwordPlayer.isVerified()) {
            return;
        }

        final String[] args = event.getMessage().split(" ");

        // Exit if command is /login or /register
        if(args[0].equalsIgnoreCase("/login") || args[0].equalsIgnoreCase("/register")) {
            return;
        }

        // Exit if BlockCommands is disabled.
        if(!plugin.getConfigManager().isCommandsBlocked()) {
            return;
        }

        ChatUtils.chat(event.getPlayer(), plugin.getConfigManager().getMessage(event.getPlayer(), ConfigMessage.MISC_MUST_BE_LOGGED_IN));
        event.setCancelled(true);
    }
}