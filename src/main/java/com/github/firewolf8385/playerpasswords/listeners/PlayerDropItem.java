package com.github.firewolf8385.playerpasswords.listeners;

import com.github.firewolf8385.playerpasswords.SettingsManager;
import com.github.firewolf8385.playerpasswords.objects.PasswordPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItem implements Listener
{
    SettingsManager settings = SettingsManager.getInstance();

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e)
    {
        PasswordPlayer p = PasswordPlayer.getPlayers().get(e.getPlayer().getUniqueId());

        // Exit if player is verified.
        if(p.isVerified())
        {
            return;
        }

        // Exit if BlockItemDrop is disabled.
        if(!settings.getConfig().getBoolean("BlockItemDrop"))
        {
            return;
        }

        e.setCancelled(true);
    }

}
