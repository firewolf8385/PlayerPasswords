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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerInteractListener implements Listener {
    private final PlayerPasswordsPlugin plugin;

    public PlayerInteractListener(@NotNull final PlayerPasswordsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(@NotNull final PlayerInteractEvent event) {
        final PasswordPlayer passwordPlayer = plugin.getPasswordPlayerManager().getPlayer(event.getPlayer());

        // Exit if player is verified.
        if(passwordPlayer.isVerified()) {
            return;
        }

        // Exit if BlockInteract is disabled.
        if(!plugin.getConfigManager().getConfig().getBoolean("BlockInteract")) {
            return;
        }

        event.setCancelled(true);
    }
}
