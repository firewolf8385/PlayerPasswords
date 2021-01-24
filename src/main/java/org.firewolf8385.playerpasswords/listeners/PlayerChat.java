package org.firewolf8385.playerpasswords.listeners;

import org.firewolf8385.playerpasswords.managers.SettingsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.firewolf8385.playerpasswords.objects.PasswordPlayer;

public class PlayerChat implements Listener {
    private final SettingsManager settings = SettingsManager.getInstance();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        PasswordPlayer p = PasswordPlayer.getPlayers().get(e.getPlayer().getUniqueId());

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
