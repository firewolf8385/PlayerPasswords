package com.github.firewolf8385.playerpasswords.listeners;

import com.github.firewolf8385.playerpasswords.SettingsManager;
import com.github.firewolf8385.playerpasswords.UpdateChecker;
import com.github.firewolf8385.playerpasswords.utils.chat.ChatUtils;
import com.github.firewolf8385.playerpasswords.objects.PasswordPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener
{
    SettingsManager settings = SettingsManager.getInstance();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        String uuid = p.getUniqueId().toString();

        PasswordPlayer pl = new PasswordPlayer(p.getUniqueId());

        boolean enabled = settings.getData().getBoolean("passwords." + uuid + ".enabled");

        // Creates a new section if the player has not joined before.
        if(!settings.getData().contains("passwords." + uuid))
        {
            settings.getData().set("passwords." + uuid + ".password", "");
            settings.getData().set("passwords." + uuid + ".enabled", false);
            settings.saveData();
            settings.reloadData();
        }


        if(pl.isRequired())
        {
            if(settings.getData().getString("passwords." + uuid + ".password").equals(""))
            {
                ChatUtils.chat(p, settings.getConfig().getString("Register"));
            }
            else
            {
                ChatUtils.chat(p, settings.getConfig().getString("Login"));
            }
        }


        if(p.hasPermission("playerpasswords.admin"))
        {
            if(UpdateChecker.update)
            {
                ChatUtils.chat(p, settings.getConfig().getString("UpdateAvailable").replace("%version%", UpdateChecker.latestVersion));
            }

            if(settings.getConfig().getInt("ConfigVersion") != 2)
            {
                ChatUtils.chat(p, settings.getConfig().getString("OutdatedConfig"));
            }
        }

    }
}
