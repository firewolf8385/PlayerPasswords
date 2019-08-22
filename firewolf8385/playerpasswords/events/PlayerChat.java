package firewolf8385.playerpasswords.events;

import firewolf8385.playerpasswords.PlayerPasswords;
import firewolf8385.playerpasswords.SettingsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener
{
    SettingsManager settings = SettingsManager.getInstance();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e)
    {
        if(!(PlayerPasswords.verified.contains(e.getPlayer())))
        {
            if(settings.getConfig().getBoolean("BlockChat"))
            {
                e.setCancelled(true);
            }
        }
    }
}
