package org.firewolf8385.playerpasswords.listeners;

import org.firewolf8385.playerpasswords.PlayerPasswords;
import org.firewolf8385.playerpasswords.SettingsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener
{
    SettingsManager settings = SettingsManager.getInstance();

    @EventHandler
    public void onChat(PlayerMoveEvent e)
    {
        if(!(PlayerPasswords.verified.contains(e.getPlayer())))
        {
            if(settings.getConfig().getBoolean("BlockMovement"))
            {
                e.setCancelled(true);
            }
        }
    }
}
