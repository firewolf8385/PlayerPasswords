package firewolf8385.playerpasswords.commands;

import firewolf8385.playerpasswords.PlayerPasswords;
import firewolf8385.playerpasswords.SettingsManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Register implements CommandExecutor
{
    SettingsManager settings = SettingsManager.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        Player p = (Player) sender;
        String uuid = p.getUniqueId().toString();
        boolean verified = PlayerPasswords.verified.contains(p);
        boolean enabled = settings.getData().getBoolean("passwords." + uuid + ".enabled");
        int minimum = settings.getConfig().getInt("MinimumPasswordLength");
        int maximum = settings.getConfig().getInt("MaximumPasswordLength");

        // If the player is already logged in, the command will end.
        if(verified)
        {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getConfig().getString("AlreadyLoggedIn")));
            return true;
        }

        // If the player already set their password, the command will end.
        if(enabled)
        {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getConfig().getString("AlreadyRegistered")));
            return true;
        }

        // If the player did not enter a password, the command will end.
        if(args.length == 0)
        {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getConfig().getString("RegisterUsage")));
            return true;
        }

        // Shows the player a message if their password does not fit the requirements.
        if(!(args[0].length() >= minimum && args[0].length() <= maximum))
        {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getConfig().getString("OutOfBounds")));
            return true;
        }


        settings.getData().set("passwords." + uuid + ".password", args[0]);

        p.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getConfig().getString("SetPasswordSuccessful")));
        if(!(PlayerPasswords.verified.contains(p)))
        {
            PlayerPasswords.verified.add(p);
        }

        if(!(settings.getData().getBoolean("passwords." + uuid + ".enabled")))
        {
            settings.getData().set("passwords." + uuid + ".enabled", true);
        }

        settings.saveData();
        settings.reloadData();

        return true;
    }
}
