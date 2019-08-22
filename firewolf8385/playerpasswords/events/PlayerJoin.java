package firewolf8385.playerpasswords.events;

import firewolf8385.playerpasswords.PlayerPasswords;
import firewolf8385.playerpasswords.SettingsManager;
import firewolf8385.playerpasswords.UpdateChecker;
import firewolf8385.playerpasswords.Utils;
import org.bukkit.ChatColor;
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
        boolean option = !settings.getConfig().getBoolean("Optional");
        boolean enabled = settings.getData().getBoolean("passwords." + uuid + ".enabled");
        boolean required = e.getPlayer().hasPermission("playerpasswords.required");
        boolean bypass = e.getPlayer().hasPermission("playerpasswords.bypass") && !enabled;

        // Creates a new section if the player has not joined before.
        if(!settings.getData().contains("passwords." + uuid))
        {
            settings.getData().set("passwords." + uuid + ".password", "");
            settings.getData().set("passwords." + uuid + ".enabled", false);
            settings.saveData();
            settings.reloadData();
        }


        if((!option || required || enabled) && !bypass)
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
        else
        {
            PlayerPasswords.verified.add(p);
        }


        if(p.hasPermission("playerpasswords.admin"))
        {
            if(UpdateChecker.update)
            {
                Utils.chat(p, settings.getConfig().getString("UpdateAvailable").replace("%version%", UpdateChecker.latestVersion));
            }

            if(settings.getConfig().getInt("configVersion") != 2)
            {
                Utils.chat(p, settings.getConfig().getString("OutdatedConfig"));
            }
        }

    }
}
