package com.github.firewolf8385.playerpasswords.listeners;

import com.github.firewolf8385.playerpasswords.SettingsManager;
import com.github.firewolf8385.playerpasswords.objects.PasswordPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener
{
    SettingsManager settings = SettingsManager.getInstance();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e)
    {
        PasswordPlayer p = PasswordPlayer.getPlayers().get(e.getPlayer().getUniqueId());

        // Return if Verified
        if(p.isVerified())
        {
            return;
        }

        // Exit if BlockChat is false.
        if(!settings.getConfig().getBoolean("BlockChat"))
        {
            return;
        }

        // Cancel Event.
        e.setCancelled(true);
    }
}
