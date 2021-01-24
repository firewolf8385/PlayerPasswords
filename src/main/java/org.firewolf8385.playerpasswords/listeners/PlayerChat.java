package org.firewolf8385.playerpasswords.listeners;

import org.firewolf8385.playerpasswords.PlayerPasswords;
import org.firewolf8385.playerpasswords.managers.SettingsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.firewolf8385.playerpasswords.objects.PasswordPlayer;

public class PlayerChat implements Listener {
    private final PlayerPasswords plugin;
    private final SettingsManager settings = SettingsManager.getInstance();

    public PlayerChat(PlayerPasswords plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        PasswordPlayer p = plugin.getPlayerManager().get(e.getPlayer());

        // Return if Verified
        if(p.isVerified()) {
            return;
        }

        // Exit if BlockChat is false.
        if(!settings.getConfig().getBoolean("BlockChat")) {
            return;
        }

        // Cancel Event.
        e.setCancelled(true);
    }
}
