package org.firewolf8385.playerpasswords.listeners;

import org.firewolf8385.playerpasswords.PlayerPasswords;
import org.firewolf8385.playerpasswords.SettingsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItem implements Listener
{
    SettingsManager settings = SettingsManager.getInstance();

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e)
    {
        Player p = e.getPlayer();

        if(!PlayerPasswords.verified.contains(p))
        {
            if(settings.getConfig().getBoolean("BlockItemDrop"))
            {
                e.setCancelled(true);
            }
        }
    }

}
