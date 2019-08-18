package firewolf8385.playerpasswords.commands;

import firewolf8385.playerpasswords.SettingsManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PP implements CommandExecutor
{
    SettingsManager settings = SettingsManager.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(args.length != 0)
        {
            switch(args[0])
            {
                default:
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l]&8&m----------&6&lPlayer Passwords&8&m----------&6&l["));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &6Author &8- &eFireWolf"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &6Version &8- &e1.0"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &6Spigot &8- &eComing Soon"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l]&8&m--------------------------------------&6&l["));
                    break;

                case "version":
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l]&8&m----------&6&lPlayer Passwords&8&m----------&6&l["));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &6Version &8- &e1.0"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l]&8&m--------------------------------------&6&l["));
                    break;

                case "support":
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l]&8&m----------&6&lPlayer Passwords&8&m----------&6&l["));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &6Discord &8- &ehttps://discord.gg/FtBteC8"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l]&8&m--------------------------------------&6&l["));
                    break;

                case "disable":
                    break;

                case "help":
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l]&8&m----------&6&lPlayer Passwords&8&m----------&6&l["));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &6/pp info &8- &eShoes plugin info."));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &6/pp support &8- &eDisplay's the support discord link."));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &6/pp reload &8- &eReloads the config.yml."));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l]&8&m--------------------------------------&6&l["));
                    break;

                case "reload":
                    if(sender.hasPermission("playerpasswords.admin"))
                    {
                        settings.reloadConfig();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getConfig().getString("ConfigReloaded")));
                    }
                    else
                    {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getConfig().getString("NoPermission")));
                    }
                    break;
            }
        }
        else
        {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l]&8&m----------&6&lPlayer Passwords&8&m----------&6&l["));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &6/pp info &8- &eShoes plugin info."));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &6/pp support &8- &eDisplay's the support discord link."));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &6/pp reload &8- &eReloads the config.yml."));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l]&8&m--------------------------------------&6&l["));
        }

        return true;
    }
}
