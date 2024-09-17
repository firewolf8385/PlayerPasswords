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
import com.github.firewolf8385.playerpasswords.listeners.*;
import com.github.firewolf8385.playerpasswords.player.PasswordPlayerManager;
import com.github.firewolf8385.playerpasswords.settings.ConfigManager;
import com.github.firewolf8385.playerpasswords.settings.HookManager;
import com.github.firewolf8385.playerpasswords.utils.ChatUtils;
import org.bstats.bukkit.Metrics;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerPasswordsPlugin extends JavaPlugin {
    private ConfigManager configManager;
    private PasswordPlayerManager passwordPlayerManager;
    private HookManager hookManager;

    /**
     * This runs necessary tasks when the plugin is enabled.
     */
    public void onEnable() {
        // Enables bStats
        new Metrics(this, 5190);

        configManager = new ConfigManager(this);

        passwordPlayerManager = new PasswordPlayerManager(this);

        registerCommands();
        registerEvents();

        // Adds all online players to the verified list.
        // This fixes issues with reloading.
        for(final Player player : getServer().getOnlinePlayers()) {
            passwordPlayerManager.addPlayer(player);
            passwordPlayerManager.getPlayer(player).setVerified(true);
        }

        hookManager = new HookManager();
        ChatUtils.initialize(this);

        // Initialize PlayerPasswords API.
        PlayerPasswords.initialize(this);
    }

    @Override
    public void onDisable() {
        ChatUtils.disable();
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public HookManager getHookManager() {
        return this.hookManager;
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
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerCommandPreProcessListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDropItemListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
    }
}
