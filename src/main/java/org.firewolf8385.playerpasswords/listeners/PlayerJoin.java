package org.firewolf8385.playerpasswords.listeners;

import org.firewolf8385.playerpasswords.PlayerPasswords;
import org.firewolf8385.playerpasswords.SettingsManager;
import org.firewolf8385.playerpasswords.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.firewolf8385.playerpasswords.objects.PasswordPlayer;

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
                Utils.chat(p, settings.getConfig().getString("Register"));
            }
            else
            {
                Utils.chat(p, settings.getConfig().getString("Login"));
            }
        }


        if(p.hasPermission("playerpasswords.admin"))
        {
            if(settings.getConfig().getInt("ConfigVersion") != 2)
            {
                Utils.chat(p, settings.getConfig().getString("OutdatedConfig"));
            }
        }

    }
}
