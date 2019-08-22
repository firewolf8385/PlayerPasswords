package firewolf8385.playerpasswords.events;

import firewolf8385.playerpasswords.PlayerPasswords;
import firewolf8385.playerpasswords.SettingsManager;
import firewolf8385.playerpasswords.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;


public class PlayerCommandPreProcess implements Listener
{
    SettingsManager settings = SettingsManager.getInstance();

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e)
    {
        Player p = e.getPlayer();
        boolean required = settings.getConfig().getBoolean("Optional");
        boolean enabled = settings.getData().getBoolean("passwords." + p.getUniqueId().toString() + ".enabled");
        boolean verified = PlayerPasswords.verified.contains(p);

        String[] args = e.getMessage().split(" ");

        // Only runs if they need to
        if((enabled || required) && !verified)
        {
            if(!args[0].equalsIgnoreCase("/login") && !args[0].equalsIgnoreCase("/register"))
            {
                e.setCancelled(true);
            }
        }
    }

}
