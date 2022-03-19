package com.github.firewolf8385.playerpasswords;

import com.github.firewolf8385.playerpasswords.commands.Login;
import com.github.firewolf8385.playerpasswords.commands.PP;
import com.github.firewolf8385.playerpasswords.commands.Password;
import com.github.firewolf8385.playerpasswords.commands.Register;
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
import com.github.firewolf8385.playerpasswords.objects.PasswordPlayer;


public class PlayerPasswords extends JavaPlugin
{
    /***************************************************************************************
     *    Title: PlayerPasswords
     *    Author: firewolf8385
     *    Date: April 48th, 2020
     *    Code version: 1.4
     ***************************************************************************************/
    SettingsManager settings = SettingsManager.getInstance();

    /**
     * This runs necessary tasks when the plugin is enabled.
     */
    public void onEnable()
    {
        // Enables bStats
        Metrics metrics = new Metrics(this);

        settings.setup(this);

        registerCommands();
        registerEvents();

        // Checks for any new updates.
        UpdateChecker update = new UpdateChecker(this.getDescription().getVersion());

        // Adds all online players to the verified list.
        // This fixes issues with reloading.
        for(Player p : Bukkit.getOnlinePlayers())
        {
            PasswordPlayer pl = new PasswordPlayer(p.getUniqueId());
            pl.setVerified(true);
        }
    }

    /**
     * This registers the plugin's commands.
     */
    private void registerCommands()
    {
        getCommand("login").setExecutor(new Login());
        getCommand("register").setExecutor(new Register());
        getCommand("playerpasswords").setExecutor(new PP());
        getCommand("password").setExecutor(new Password());
    }

    /**
     * This registers events the plugin uses.
     */
    private void registerEvents()
    {
        Bukkit.getPluginManager().registerEvents(new PlayerChat(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMove(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreProcess(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDropItem(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(), this);
    }
}
