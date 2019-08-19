package firewolf8385.playerpasswords.events;

import firewolf8385.playerpasswords.PlayerPasswords;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener
{

    @EventHandler
    public void onLeave(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();

        if(PlayerPasswords.verified.contains(p))
        {
            PlayerPasswords.verified.remove(p);
        }
    }

}
