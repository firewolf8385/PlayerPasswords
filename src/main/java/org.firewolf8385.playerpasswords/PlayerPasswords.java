package org.firewolf8385.playerpasswords;

import org.firewolf8385.playerpasswords.commands.Login;
import org.firewolf8385.playerpasswords.commands.PP;
import org.firewolf8385.playerpasswords.commands.Password;
import org.firewolf8385.playerpasswords.commands.Register;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.firewolf8385.playerpasswords.listeners.PlayerChat;
import org.firewolf8385.playerpasswords.listeners.PlayerCommandPreProcess;
import org.firewolf8385.playerpasswords.listeners.PlayerDropItem;
import org.firewolf8385.playerpasswords.listeners.PlayerInteract;
import org.firewolf8385.playerpasswords.listeners.PlayerJoin;
import org.firewolf8385.playerpasswords.listeners.PlayerMove;
import org.firewolf8385.playerpasswords.listeners.PlayerQuit;
import org.firewolf8385.playerpasswords.managers.PlayerManager;
import org.firewolf8385.playerpasswords.managers.SettingsManager;
import org.firewolf8385.playerpasswords.objects.PasswordPlayer;

public class PlayerPasswords extends JavaPlugin {
    private PlayerManager playerManager;
    private final SettingsManager settings = SettingsManager.getInstance();

    /**
     * This runs necessary tasks when the plugin is enabled.
     */
    public void onEnable() {
        // Enables bStats
        new MetricsLite(this, 5190);

        settings.setup(this);

        playerManager = new PlayerManager();

        registerCommands();
        registerEvents();

        // Adds all online players to the verified list.
        // This fixes issues with reloading.
        for(Player p : Bukkit.getOnlinePlayers()) {
            PasswordPlayer pl = playerManager.get(p);
            pl.setVerified(true);
        }
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
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
