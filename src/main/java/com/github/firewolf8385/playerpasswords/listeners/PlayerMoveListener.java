package com.github.firewolf8385.playerpasswords.listeners;

import com.github.firewolf8385.playerpasswords.PlayerPasswords;
import com.github.firewolf8385.playerpasswords.settings.SettingsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import com.github.firewolf8385.playerpasswords.player.PasswordPlayer;

public class PlayerMoveListener implements Listener {
    private final SettingsManager settings = SettingsManager.getInstance();
    private final PlayerPasswords plugin;

    public PlayerMoveListener(PlayerPasswords plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(PlayerMoveEvent event) {
        PasswordPlayer passwordPlayer = plugin.getPasswordPlayerManager().getPlayer(event.getPlayer());

        // Exit if player is verified.
        if(passwordPlayer.isVerified()) {
            return;
        }

        // Exit if BlockMovement is disabled.
        if(!settings.getConfig().getBoolean("BlockMovement")) {
            return;
        }

        event.setCancelled(true);
    }
}