package org.firewolf8385.playerpasswords.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.firewolf8385.playerpasswords.objects.PasswordPlayer;

public class PlayerQuit implements Listener
{

    @EventHandler
    public void onLeave(PlayerQuitEvent e)
    {
        // Remove player from list of players.
        PasswordPlayer.getPlayers().remove(e.getPlayer().getUniqueId());
    }

}
