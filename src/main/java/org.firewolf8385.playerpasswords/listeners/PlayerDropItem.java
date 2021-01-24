package org.firewolf8385.playerpasswords.listeners;

import org.firewolf8385.playerpasswords.PlayerPasswords;
import org.firewolf8385.playerpasswords.managers.SettingsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.firewolf8385.playerpasswords.objects.PasswordPlayer;

public class PlayerDropItem implements Listener {
    private final PlayerPasswords plugin;
    private final SettingsManager settings = SettingsManager.getInstance();

    public PlayerDropItem(PlayerPasswords plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        PasswordPlayer p = plugin.getPlayerManager().get(e.getPlayer());

        // Exit if player is verified.
        if(p.isVerified()) {
            return;
        }

        // Exit if BlockItemDrop is disabled.
        if(!settings.getConfig().getBoolean("BlockItemDrop")) {
            return;
        }

        e.setCancelled(true);
    }

}
