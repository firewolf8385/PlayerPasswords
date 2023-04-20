package com.github.firewolf8385.playerpasswords.listeners;

import com.github.firewolf8385.playerpasswords.PlayerPasswords;
import com.github.firewolf8385.playerpasswords.settings.PluginMessage;
import com.github.firewolf8385.playerpasswords.settings.SettingsManager;
import com.github.firewolf8385.playerpasswords.utils.ChatUtils;
import com.github.firewolf8385.playerpasswords.player.PasswordPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final SettingsManager settings = SettingsManager.getInstance();
    private final PlayerPasswords plugin;

    public PlayerJoinListener(PlayerPasswords plugin) {
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


        // Run message tasks later to make plugin messages the most recent.
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if(passwordPlayer.isRequired() && !passwordPlayer.isVerified()) {
                if(settings.getData().getString("passwords." + uuid + ".password").equals("")) {
                    ChatUtils.chat(player, PluginMessage.REGISTER.toString());
                }
                else {
                    ChatUtils.chat(player, PluginMessage.LOGIN.toString());
                }
            }


            if(player.hasPermission("playerpasswords.admin")) {
                if(settings.getConfig().getInt("ConfigVersion") != 2) {
                    ChatUtils.chat(player, PluginMessage.OUTDATED_CONFIG.toString());
                }
            }
        }, 5);
    }
}
