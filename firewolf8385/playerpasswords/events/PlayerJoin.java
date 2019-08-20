package firewolf8385.playerpasswords.events;

import firewolf8385.playerpasswords.PlayerPasswords;
import firewolf8385.playerpasswords.SettingsManager;
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
        boolean passwordRequired = settings.getConfig().getBoolean("Optional");
        boolean passwordEnabled = settings.getData().getBoolean("passwords." + uuid + ".enabled");
        boolean playerRequired = e.getPlayer().hasPermission("playerpasswords.required");
        boolean playerBypass = e.getPlayer().hasPermission("playerpasswords.bypass");

        // Creates a new section if the player has not joined before.
        if(!settings.getData().contains("passwords." + uuid))
        {
            settings.getData().set("passwords." + uuid + ".password", "");
            settings.getData().set("passwords." + uuid + ".enabled", false);
            settings.saveData();
            settings.reloadData();
        }


        if((!passwordRequired && !passwordEnabled && !playerBypass) || playerRequired)
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

    }
}
