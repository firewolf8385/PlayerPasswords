package com.github.firewolf8385.playerpasswords.listeners;

import com.github.firewolf8385.playerpasswords.PlayerPasswords;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private final PlayerPasswords plugin;

    public PlayerQuitListener(PlayerPasswords plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        // Remove player from list of players.
        plugin.getPasswordPlayerManager().removePlayer(event.getPlayer());
    }

}
