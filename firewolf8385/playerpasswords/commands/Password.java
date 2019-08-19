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
    String gold = settings.getConfig().getString("color1");
    String yellow = settings.getConfig().getString("color2");
    String gray = settings.getConfig().getString("color3");

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
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', gold + "&l]" + gray + "&m--------------------" + gold + "&lPasswords" + gray + "&m--------------------" + gold +"&l["));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  " + gray + "» " + gold + "/password enable " + gray + "- " + yellow + "Enables Your Password"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  " + gray + "» " + gold + "/password disable " + gray + "- " + yellow + "Disables Your Password"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  " + gray + "» " + gold + "/password set [password] " + gray + "- " + yellow + "Changes Your Password"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l["));
            }
        }
        else
        {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getConfig().getString("MustBeLoggedIn")));
        }


        return true;
    }
}
