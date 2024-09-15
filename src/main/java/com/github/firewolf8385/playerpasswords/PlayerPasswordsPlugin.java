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

import com.github.firewolf8385.playerpasswords.commands.LoginCMD;
import com.github.firewolf8385.playerpasswords.commands.PlayerPasswordsCMD;
import com.github.firewolf8385.playerpasswords.commands.PasswordCMD;
import com.github.firewolf8385.playerpasswords.commands.RegisterCMD;
import com.github.firewolf8385.playerpasswords.player.PasswordPlayerManager;
import com.github.firewolf8385.playerpasswords.settings.SettingsManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.github.firewolf8385.playerpasswords.listeners.PlayerChatListener;
import com.github.firewolf8385.playerpasswords.listeners.PlayerCommandPreProcessListener;
import com.github.firewolf8385.playerpasswords.listeners.PlayerDropItemListener;
import com.github.firewolf8385.playerpasswords.listeners.PlayerInteractListener;
import com.github.firewolf8385.playerpasswords.listeners.PlayerJoinListener;
import com.github.firewolf8385.playerpasswords.listeners.PlayerMoveListener;
import com.github.firewolf8385.playerpasswords.listeners.PlayerQuitListener;

public class PlayerPasswordsPlugin extends JavaPlugin {
    private PasswordPlayerManager passwordPlayerManager;
    private final SettingsManager settings = SettingsManager.getInstance();

    /**
     * This runs necessary tasks when the plugin is enabled.
     */
    public void onEnable() {
        // Enables bStats
        new Metrics(this, 5190);

        settings.setup(this);

        passwordPlayerManager = new PasswordPlayerManager(this);

        registerCommands();
        registerEvents();

        // Adds all online players to the verified list.
        // This fixes issues with reloading.
        for(Player player : Bukkit.getOnlinePlayers()) {
            passwordPlayerManager.addPlayer(player);
            passwordPlayerManager.getPlayer(player).setVerified(true);
        }
    }

    public PasswordPlayerManager getPasswordPlayerManager() {
        return passwordPlayerManager;
    }

    /**
     * This registers the plugin's commands.
     */
    private void registerCommands() {
        getCommand("login").setExecutor(new LoginCMD(this));
        getCommand("register").setExecutor(new RegisterCMD(this));
        getCommand("playerpasswords").setExecutor(new PlayerPasswordsCMD(this));
        getCommand("password").setExecutor(new PasswordCMD(this));
    }

    /**
     * This registers events the plugin uses.
     */
    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreProcessListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDropItemListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(this), this);
    }
}
