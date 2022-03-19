package com.github.firewolf8385.playerpasswords;

import com.github.firewolf8385.playerpasswords.commands.Login;
import com.github.firewolf8385.playerpasswords.commands.PP;
import com.github.firewolf8385.playerpasswords.commands.Password;
import com.github.firewolf8385.playerpasswords.commands.Register;
import com.github.firewolf8385.playerpasswords.player.PasswordPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.github.firewolf8385.playerpasswords.listeners.PlayerChat;
import com.github.firewolf8385.playerpasswords.listeners.PlayerCommandPreProcess;
import com.github.firewolf8385.playerpasswords.listeners.PlayerDropItem;
import com.github.firewolf8385.playerpasswords.listeners.PlayerInteract;
import com.github.firewolf8385.playerpasswords.listeners.PlayerJoin;
import com.github.firewolf8385.playerpasswords.listeners.PlayerMove;
import com.github.firewolf8385.playerpasswords.listeners.PlayerQuit;

/***************************************************************************************
 *    Title: PlayerPasswords
 *    Author: firewolf8385
 *    Date: March 19th, 2022
 *    Code version: 1.4
 ***************************************************************************************/

public class PlayerPasswords extends JavaPlugin {
    private PasswordPlayerManager passwordPlayerManager;
    SettingsManager settings = SettingsManager.getInstance();

    /**
     * This runs necessary tasks when the plugin is enabled.
     */
    public void onEnable() {
        // Enables bStats
        Metrics metrics = new Metrics(this);

        settings.setup(this);

        passwordPlayerManager = new PasswordPlayerManager(this);

        registerCommands();
        registerEvents();

        // Checks for any new updates.
        UpdateChecker update = new UpdateChecker(this.getDescription().getVersion());

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
        getCommand("login").setExecutor(new Login(this));
        getCommand("register").setExecutor(new Register(this));
        getCommand("playerpasswords").setExecutor(new PP(this));
        getCommand("password").setExecutor(new Password(this));
    }

    /**
     * This registers events the plugin uses.
     */
    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PlayerChat(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMove(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreProcess(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDropItem(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(this), this);
    }
}
