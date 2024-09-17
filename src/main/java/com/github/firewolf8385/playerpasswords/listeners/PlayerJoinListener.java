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
import com.github.firewolf8385.playerpasswords.settings.ConfigMessage;
import com.github.firewolf8385.playerpasswords.utils.ChatUtils;
import com.github.firewolf8385.playerpasswords.player.PasswordPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerJoinListener implements Listener {
    private final PlayerPasswordsPlugin plugin;

    public PlayerJoinListener(@NotNull final PlayerPasswordsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(@NotNull final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final String uuid = player.getUniqueId().toString();

        plugin.getPasswordPlayerManager().addPlayer(player);
        final PasswordPlayer passwordPlayer = plugin.getPasswordPlayerManager().getPlayer(player);


        // Creates a new section if the player has not joined before.
        if(!plugin.getConfigManager().getData().contains("passwords." + uuid)) {
            plugin.getConfigManager().getData().set("passwords." + uuid + ".password", "");
            plugin.getConfigManager().getData().set("passwords." + uuid + ".enabled", false);
            plugin.getConfigManager().saveData();
            plugin.getConfigManager().reloadData();
        }


        // Run message tasks later to make plugin messages the most recent.
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if(passwordPlayer.isRequired() && !passwordPlayer.isVerified()) {
                if(plugin.getConfigManager().getData().getString("passwords." + uuid + ".password").isEmpty()) {
                    ChatUtils.chat(player, plugin.getConfigManager().getMessage(player, ConfigMessage.REGISTER_REGISTER_TO_CONTINUE));
                }
                else {
                    ChatUtils.chat(player, plugin.getConfigManager().getMessage(player, ConfigMessage.LOGIN_LOGIN_TO_CONTINUE));
                }
            }
        }, 5);
    }
}
