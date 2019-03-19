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

        if(PlayerPasswords.verified.contains(p) || !(settings.getData().getBoolean("passwords." + p.getName() + ".enabled")))
        {
            if(args.length != 0)
            {
                int minimum = settings.getConfig().getInt("MinimumPasswordLength");
                int maximum = settings.getConfig().getInt("MaximumPasswordLength");

                if(args[0].length() >= minimum && args[0].length() <= maximum)
                {
                    settings.getData().set("passwords." + p.getName() + ".password", args[0]);

                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getConfig().getString("SetPasswordSuccessful")));
                    if(!(PlayerPasswords.verified.contains(p)))
                    {
                        PlayerPasswords.verified.add(p);
                    }

                    if(!(settings.getData().getBoolean("passwords." + p.getName() + ".enabled")))
                    {
                        settings.getData().set("passwords." + p.getName() + ".enabled", true);
                    }

                    settings.saveData();
                    settings.reloadData();
                }
                else
                {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getConfig().getString("OutOfBounds")));
                }
            }
            else
            {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getConfig().getString("SetPasswordUsage")));
            }
        }

        return true;
    }
}
