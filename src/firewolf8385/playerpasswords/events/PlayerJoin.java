package firewolf8385.playerpasswords.events;

import firewolf8385.playerpasswords.PlayerPasswords;
import firewolf8385.playerpasswords.SettingsManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener
{
    SettingsManager settings = SettingsManager.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        String pName = p.getName();

        if(!(settings.getConfig().contains("passwords." + pName)))
        {
            settings.getConfig().set("passwords." + pName + ".password", "");
            settings.getConfig().set("passwords." + pName + ".enabled", false);
            settings.saveConfig();
            settings.reloadConfig();
        }

        if(!(settings.getConfig().getBoolean("Optional")) && !(settings.getConfig().getBoolean("passwords." + pName + "enabled")))
        {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getConfig().getString("Register")));
        }
        else if(settings.getConfig().getBoolean("passwords." + pName + "enabled"))
        {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getConfig().getString("Login")));
        }
        else
        {
            PlayerPasswords.verified.add(p);
        }

    }
}
