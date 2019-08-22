package firewolf8385.playerpasswords.events;

import firewolf8385.playerpasswords.PlayerPasswords;
import firewolf8385.playerpasswords.SettingsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener
{
    SettingsManager settings = SettingsManager.getInstance();

    @EventHandler
    public void onInteract(PlayerInteractEvent e)
    {
        Player p = e.getPlayer();

        if(!PlayerPasswords.verified.contains(p))
        {
            if(settings.getConfig().getBoolean("BlockInteract"))
            {
                e.setCancelled(true);
            }
        }
    }
}
