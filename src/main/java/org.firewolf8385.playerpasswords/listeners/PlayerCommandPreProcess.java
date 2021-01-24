package org.firewolf8385.playerpasswords.listeners;

import org.firewolf8385.playerpasswords.PlayerPasswords;
import org.firewolf8385.playerpasswords.managers.SettingsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.firewolf8385.playerpasswords.objects.PasswordPlayer;


public class PlayerCommandPreProcess implements Listener {
    private final PlayerPasswords plugin;
    private final SettingsManager settings = SettingsManager.getInstance();

    public PlayerCommandPreProcess(PlayerPasswords plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        PasswordPlayer p = plugin.getPlayerManager().get(e.getPlayer().getUniqueId());

        // Exit if player is verified.
        if(p.isVerified()) {
            return;
        }

        String[] args = e.getMessage().split(" ");

        // Exit if command is /login or /register
        if(args[0].equalsIgnoreCase("/login") || args[0].equalsIgnoreCase("/register")) {
            return;
        }

        // Exit if BlockCommands is disabled.
        if(!settings.getConfig().getBoolean("BlockCommands")) {
            return;
        }

        e.setCancelled(true);
    }

}