package firewolf8385.playerpasswords.commands;

import firewolf8385.playerpasswords.PlayerPasswords;
import firewolf8385.playerpasswords.SettingsManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Password implements CommandExecutor
{
    SettingsManager settings = SettingsManager.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        Player p = (Player) sender;
        if(PlayerPasswords.verified.contains(p))
        {
            if(args.length != 0)
            {
                switch(args[0].toLowerCase())
                {
                    default:
                        break;

                    case "enable":
                        settings.getData().set("passwords." + p.getName() + ".enabled", true);
                        settings.saveData();
                        settings.reloadData();
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getConfig().getString("PasswordEnabled")));
                        break;

                    case "disable":
                        if(settings.getConfig().getBoolean("Optional"))
                        {
                            settings.getData().set("passwords." + p.getName() + ".enabled", false);
                            settings.saveData();
                            settings.reloadData();
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getConfig().getString("PasswordDisabled")));
                        }
                        else
                        {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getConfig().getString("OptionalPasswordDisabled")));
                        }
                        break;

                    case "set":
                        if(args.length > 1)
                        {
                            settings.getData().set("passwords." + p.getName() + ".password", args[1]);
                            settings.saveData();
                            settings.reloadData();
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getConfig().getString("PasswordSet")));
                        }
                        else
                        {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getConfig().getString("PasswordSetUsage")));
                        }
                        break;
                }
            }
            else
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l]&8&m-------------&6&lPasswords&8&m--------------&6&l["));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &6/password enable &8- &eEnables Your Password"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &6/password disable &8- &eDisables Your Password"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &6/password set [password] &8- &eChanges Your Password"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l]&8&m--------------------------------------&6&l["));
            }
        }
        else
        {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getConfig().getString("MustBeLoggedIn")));
        }


        return true;
    }
}
