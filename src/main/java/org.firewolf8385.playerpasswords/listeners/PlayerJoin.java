package org.firewolf8385.playerpasswords.listeners;

import org.bukkit.Bukkit;
import org.firewolf8385.playerpasswords.PlayerPasswords;
import org.firewolf8385.playerpasswords.managers.SettingsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.firewolf8385.playerpasswords.objects.PasswordPlayer;
import org.firewolf8385.playerpasswords.utils.ChatUtils;

public class PlayerJoin implements Listener {
    private final PlayerPasswords plugin;
    private final SettingsManager settings = SettingsManager.getInstance();

    public PlayerJoin(PlayerPasswords plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String uuid = p.getUniqueId().toString();

        plugin.getPlayerManager().add(p);
        PasswordPlayer pl = plugin.getPlayerManager().get(p);

        // Creates a new section if the player has not joined before.
        if(plugin.getPlayerManager().exists(p)) {
            settings.getData().set("passwords." + uuid + ".password", "");
            settings.getData().set("passwords." + uuid + ".enabled", false);
            settings.saveData();
            settings.reloadData();
        }


        // Run message tasks later to make plugin messages the most recent.
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if(pl.isRequired()) {
                if(settings.getData().getString("passwords." + uuid + ".password").equals("")) {
                    ChatUtils.chat(p, settings.getConfig().getString("Register"));
                }
                else {
                    ChatUtils.chat(p, settings.getConfig().getString("Login"));
                }
            }


            if(p.hasPermission("playerpasswords.admin")) {
                if(settings.getConfig().getInt("ConfigVersion") != 4) {
                    ChatUtils.chat(p, settings.getConfig().getString("OutdatedConfig"));
                }
            }
        }, 5);

    }
}
