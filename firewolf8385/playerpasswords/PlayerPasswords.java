package firewolf8385.playerpasswords;

import firewolf8385.playerpasswords.commands.Login;
import firewolf8385.playerpasswords.commands.PP;
import firewolf8385.playerpasswords.commands.Password;
import firewolf8385.playerpasswords.commands.Register;
import firewolf8385.playerpasswords.events.PlayerChat;
import firewolf8385.playerpasswords.events.PlayerJoin;
import firewolf8385.playerpasswords.events.PlayerMove;
import firewolf8385.playerpasswords.events.PlayerQuit;
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
        // Enables bStats
        Metrics metrics = new Metrics(this);

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
        getCommand("register").setExecutor(new Register());
        getCommand("playerpasswords").setExecutor(new PP());
        getCommand("password").setExecutor(new Password());
    }

    /**
     * This registers events the plugin uses.
     */
    private void registerEvents()
    {
        Bukkit.getPluginManager().registerEvents(new PlayerChat(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMove(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
    }
}
