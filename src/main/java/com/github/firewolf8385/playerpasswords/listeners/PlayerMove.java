package com.github.firewolf8385.playerpasswords.listeners;

import com.github.firewolf8385.playerpasswords.SettingsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import com.github.firewolf8385.playerpasswords.objects.PasswordPlayer;

public class PlayerMove implements Listener
{
    SettingsManager settings = SettingsManager.getInstance();

    @EventHandler
    public void onChat(PlayerMoveEvent e)
    {
        PasswordPlayer p = PasswordPlayer.getPlayers().get(e.getPlayer().getUniqueId());

        // Exit if player is verified.
        if(p.isVerified())
        {
            return;
        }

        // Exit if BlockMovement is disabled.
        if(!settings.getConfig().getBoolean("BlockMovement"))
        {
            return;
        }

        e.setCancelled(true);
    }
}
