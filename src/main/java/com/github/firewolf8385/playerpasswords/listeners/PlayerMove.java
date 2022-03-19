package com.github.firewolf8385.playerpasswords.listeners;

import com.github.firewolf8385.playerpasswords.PlayerPasswords;
import com.github.firewolf8385.playerpasswords.SettingsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import com.github.firewolf8385.playerpasswords.player.PasswordPlayer;

public class PlayerMove implements Listener {
    SettingsManager settings = SettingsManager.getInstance();
    private final PlayerPasswords plugin;

    public PlayerMove(PlayerPasswords plugin) {
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
