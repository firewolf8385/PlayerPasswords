package com.github.firewolf8385.playerpasswords.listeners;

import com.github.firewolf8385.playerpasswords.PlayerPasswords;
import com.github.firewolf8385.playerpasswords.settings.PluginMessage;
import com.github.firewolf8385.playerpasswords.settings.SettingsManager;
import com.github.firewolf8385.playerpasswords.player.PasswordPlayer;
import com.github.firewolf8385.playerpasswords.utils.ChatUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreProcessListener implements Listener {
    private final SettingsManager settings = SettingsManager.getInstance();
    private final PlayerPasswords plugin;

    public PlayerCommandPreProcessListener(PlayerPasswords plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        PasswordPlayer passwordPlayer = plugin.getPasswordPlayerManager().getPlayer(event.getPlayer());

        // Exit if player is verified.
        if(passwordPlayer.isVerified()) {
            return;
        }

        String[] args = event.getMessage().split(" ");

        // Exit if command is /login or /register
        if(args[0].equalsIgnoreCase("/login") || args[0].equalsIgnoreCase("/register")) {
            return;
        }

        // Exit if BlockCommands is disabled.
        if(!settings.getConfig().getBoolean("BlockCommands")) {
            return;
        }

        ChatUtils.chat(event.getPlayer(), PluginMessage.MUST_BE_LOGGED_IN.toString());
        event.setCancelled(true);
    }

}