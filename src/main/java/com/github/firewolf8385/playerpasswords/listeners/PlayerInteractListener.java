package com.github.firewolf8385.playerpasswords.listeners;

import com.github.firewolf8385.playerpasswords.PlayerPasswords;
import com.github.firewolf8385.playerpasswords.settings.SettingsManager;
import com.github.firewolf8385.playerpasswords.player.PasswordPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {
    private final SettingsManager settings = SettingsManager.getInstance();
    private final PlayerPasswords plugin;

    public PlayerInteractListener(PlayerPasswords plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        PasswordPlayer passwordPlayer = plugin.getPasswordPlayerManager().getPlayer(event.getPlayer());

        // Exit if player is verified.
        if(passwordPlayer.isVerified()) {
            return;
        }

        // Exit if BlockInteract is disabled.
        if(!settings.getConfig().getBoolean("BlockInteract")) {
            return;
        }

        event.setCancelled(true);
    }
}
