package firewolf8385.playerpasswords.commands;

import firewolf8385.playerpasswords.PlayerPasswords;
import firewolf8385.playerpasswords.SettingsManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PP implements CommandExecutor
{
    SettingsManager settings = SettingsManager.getInstance();
    String gold = settings.getConfig().getString("color1");
    String yellow = settings.getConfig().getString("color2");
    String gray = settings.getConfig().getString("color3");

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(args.length != 0)
        {
            switch(args[0])
            {
                default:
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', gold + "&l]" + gray + "&m-----------------" + gold + "&lPlayerPasswords" + gray + "&m-----------------" + gold +"&l["));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  " + gray + "» " + gold + "Author " + gray + "- " + yellow + "FireWolf"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  " + gray + "» " + gold + "Version " + gray + "- " + yellow + "1.0"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  " + gray + "» " + gold + "Spigot " + gray + "- " + yellow + "Coming Soon"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l["));
                    break;

                case "version":
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', gold + "&l]" + gray + "&m-----------------" + gold + "&lPlayerPasswords" + gray + "&m-----------------" + gold +"&l["));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  " + gray + "» " + gold + "Version " + gray + "- " + yellow + "1.0"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l["));
                    break;

                case "support":
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', gold + "&l]" + gray + "&m-----------------" + gold + "&lPlayerPasswords" + gray + "&m-----------------" + gold +"&l["));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  " + gray + "» " + gold + "Discord " + gray + "- " + yellow + "https://discord.gg/FtBteC8"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l["));
                    break;

                case "disable":
                    break;

                case "help":
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', gold + "&l]" + gray + "&m-----------------" + gold + "&lPlayerPasswords" + gray + "&m-----------------" + gold +"&l["));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  " + gray + "» " + gold + "/pp info " + gray + "- " + yellow + "Shows plugin info."));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  " + gray + "» " + gold + "&6/pp support " + gray + "- " + yellow + "Display's the support discord link."));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l["));
                    break;

                case "verified":
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', gold + "&l]" + gray + "&m-----------------" + gold + "&lPlayerPasswords" + gray + "&m-----------------" + gold +"&l["));
                    for(Player p : PlayerPasswords.verified)
                    {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  " + gray + "» " + yellow + p.getName()));
                    }
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l["));
                    break;

            }
        }
        else
        {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', gold + "&l]" + gray + "&m-----------------" + gold + "&lPlayerPasswords" + gray + "&m-----------------" + gold +"&l["));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  " + gray + "» " + gold + "/pp info " + gray + "- " + yellow + "Shows plugin info."));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  " + gray + "» " + gold + "&6/pp support " + gray + "- " + yellow + "Display's the support discord link."));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  " + gray + "» " + gold + "&6/pp verified " + gray + "- " + yellow + "List all verified players."));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l["));
        }

        return true;
    }
}
