package com.github.firewolf8385.playerpasswords.listeners;

import com.github.firewolf8385.playerpasswords.PlayerPasswords;
import com.github.firewolf8385.playerpasswords.SettingsManager;
import com.github.firewolf8385.playerpasswords.UpdateChecker;
import com.github.firewolf8385.playerpasswords.utils.chat.ChatUtils;
import com.github.firewolf8385.playerpasswords.player.PasswordPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    SettingsManager settings = SettingsManager.getInstance();
    private final PlayerPasswords plugin;

    public PlayerJoin(PlayerPasswords plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();

        plugin.getPasswordPlayerManager().addPlayer(player);
        PasswordPlayer passwordPlayer = plugin.getPasswordPlayerManager().getPlayer(player);

        boolean enabled = settings.getData().getBoolean("passwords." + uuid + ".enabled");

        // Creates a new section if the player has not joined before.
        if(!settings.getData().contains("passwords." + uuid)) {
            settings.getData().set("passwords." + uuid + ".password", "");
            settings.getData().set("passwords." + uuid + ".enabled", false);
            settings.saveData();
            settings.reloadData();
        }


        if(passwordPlayer.isRequired()) {
            if(settings.getData().getString("passwords." + uuid + ".password").equals("")) {
                ChatUtils.chat(player, settings.getConfig().getString("Register"));
            }
            else {
                ChatUtils.chat(player, settings.getConfig().getString("Login"));
            }
        }


        if(player.hasPermission("playerpasswords.admin")) {
            if(UpdateChecker.update) {
                ChatUtils.chat(player, settings.getConfig().getString("UpdateAvailable").replace("%version%", UpdateChecker.latestVersion));
            }

            if(settings.getConfig().getInt("ConfigVersion") != 2) {
                ChatUtils.chat(player, settings.getConfig().getString("OutdatedConfig"));
            }
        }

    }
}
