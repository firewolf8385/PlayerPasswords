package com.github.firewolf8385.playerpasswords.listeners;

import com.github.firewolf8385.playerpasswords.SettingsManager;
import com.github.firewolf8385.playerpasswords.objects.PasswordPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener
{
    SettingsManager settings = SettingsManager.getInstance();

    @EventHandler
    public void onInteract(PlayerInteractEvent e)
    {
        PasswordPlayer p = PasswordPlayer.getPlayers().get(e.getPlayer().getUniqueId());

        // Exit if player is verified.
        if(p.isVerified())
        {
            return;
        }

        // Exit if BlockInteract is disabled.
        if(!settings.getConfig().getBoolean("BlockInteract"))
        {
            return;
        }

        e.setCancelled(true);
    }
}
