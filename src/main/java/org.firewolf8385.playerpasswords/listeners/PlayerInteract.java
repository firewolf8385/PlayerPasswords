package org.firewolf8385.playerpasswords.listeners;

import org.firewolf8385.playerpasswords.PlayerPasswords;
import org.firewolf8385.playerpasswords.managers.SettingsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.firewolf8385.playerpasswords.objects.PasswordPlayer;

public class PlayerInteract implements Listener {
    private final PlayerPasswords plugin;
    private final SettingsManager settings = SettingsManager.getInstance();

    public PlayerInteract(PlayerPasswords plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        PasswordPlayer p = plugin.getPlayerManager().get(e.getPlayer().getUniqueId());

        // Exit if player is verified.
        if(p.isVerified()) {
            return;
        }

        // Exit if BlockInteract is disabled.
        if(!settings.getConfig().getBoolean("BlockInteract")) {
            return;
        }

        e.setCancelled(true);
    }
}