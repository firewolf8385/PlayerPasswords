package org.firewolf8385.playerpasswords.listeners;

import org.firewolf8385.playerpasswords.PlayerPasswords;
import org.firewolf8385.playerpasswords.managers.SettingsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.firewolf8385.playerpasswords.objects.PasswordPlayer;

public class PlayerMove implements Listener {
    private final PlayerPasswords plugin;
    private final SettingsManager settings = SettingsManager.getInstance();

    public PlayerMove(PlayerPasswords plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(PlayerMoveEvent e) {
        PasswordPlayer p = plugin.getPlayerManager().get(e.getPlayer());

        // Exit if player is verified.
        if(p.isVerified()) {
            return;
        }

        // Exit if BlockMovement is disabled.
        if(!settings.getConfig().getBoolean("BlockMovement")) {
            return;
        }

        e.setCancelled(true);
    }
}
