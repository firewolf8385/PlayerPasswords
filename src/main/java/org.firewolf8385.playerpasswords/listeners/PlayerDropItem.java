package org.firewolf8385.playerpasswords.listeners;

import org.firewolf8385.playerpasswords.SettingsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.firewolf8385.playerpasswords.objects.PasswordPlayer;

public class PlayerDropItem implements Listener {
    private SettingsManager settings = SettingsManager.getInstance();

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        PasswordPlayer p = PasswordPlayer.getPlayers().get(e.getPlayer().getUniqueId());

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
