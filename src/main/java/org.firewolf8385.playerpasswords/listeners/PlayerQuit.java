package org.firewolf8385.playerpasswords.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.firewolf8385.playerpasswords.PlayerPasswords;
import org.firewolf8385.playerpasswords.objects.PasswordPlayer;

public class PlayerQuit implements Listener {
    private final PlayerPasswords plugin;

    public PlayerQuit(PlayerPasswords plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        plugin.getPlayerManager().remove(e.getPlayer());
    }
}