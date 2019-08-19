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

        boolean passwordRequired = settings.getConfig().getBoolean("Optional");
        boolean passwordEnabled = settings.getData().getBoolean("passwords." + pName + ".enabled");
        boolean playerRequired = e.getPlayer().hasPermission("playerpasswords.required");
        boolean playerBypass = e.getPlayer().hasPermission("playerpasswords.bypass");

        if(!(settings.getData().contains("passwords." + pName)))
        {
            settings.getData().set("passwords." + pName + ".password", "");
            settings.getData().set("passwords." + pName + ".enabled", false);
            settings.saveData();
            settings.reloadData();
        }

        if((!passwordRequired && !passwordEnabled && !playerBypass) || playerRequired)
        {
            if(settings.getData().getString("passwords." + pName + ".password").equals(""))
            {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getConfig().getString("Register")));
            }
            else
            {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getConfig().getString("Login")));
            }
        }
        else
        {
            PlayerPasswords.verified.add(p);
        }

    }
}
