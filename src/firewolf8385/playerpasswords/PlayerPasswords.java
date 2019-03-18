package firewolf8385.playerpasswords;

import firewolf8385.playerpasswords.commands.Login;
import firewolf8385.playerpasswords.commands.SetPassword;
import firewolf8385.playerpasswords.events.PlayerChat;
import firewolf8385.playerpasswords.events.PlayerJoin;
import firewolf8385.playerpasswords.events.PlayerMove;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class PlayerPasswords extends JavaPlugin
{
    SettingsManager settings = SettingsManager.getInstance();
    public static List<Player> verified = new ArrayList<>();

    /**
     * This runs necessary tasks when the plugin is enabled.
     */
    public void onEnable()
    {
         settings.setup(this);

         registerCommands();
         registerEvents();
    }

    /**
     * This registers the plugin's commands.
     */
    private void registerCommands()
    {
        getCommand("login").setExecutor(new Login());
        getCommand("setpassword").setExecutor(new SetPassword());
    }

    /**
     * This registers events the plugin uses.
     */
    private void registerEvents()
    {
        Bukkit.getPluginManager().registerEvents(new PlayerChat(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMove(), this);
    }
}