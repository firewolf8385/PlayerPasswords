package org.firewolf8385.playerpasswords.commands;

import org.firewolf8385.playerpasswords.PlayerPasswords;
import org.firewolf8385.playerpasswords.SettingsManager;
import org.firewolf8385.playerpasswords.Utils;
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
        String uuid = p.getUniqueId().toString();

        // Player cannot use command if they aren't logged in.
        if(!PlayerPasswords.verified.contains(p))
        {
            Utils.chat(p, settings.getConfig().getString("MustBeLoggedIn"));
            return true;
        }

        // Shows default page if no arguments are given.
        if(args.length == 0)
        {
            Utils.chat(p, gold + "&l]" + gray + "&m--------------------" + gold + "&lPasswords" + gray + "&m--------------------" + gold +"&l[");
            Utils.chat(p, "  " + gray + "» " + gold + "/password enable " + gray + "- " + yellow + "Enables Your Password");
            Utils.chat(p, "  " + gray + "» " + gold + "/password disable " + gray + "- " + yellow + "Disables Your Password");
            Utils.chat(p, "  " + gray + "» " + gold + "/password set [password] " + gray + "- " + yellow + "Changes Your Password");
            Utils.chat(p, gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l[");
            return true;
        }


        switch(args[0].toLowerCase())
        {
            default:
                Utils.chat(p, gold + "&l]" + gray + "&m--------------------" + gold + "&lPasswords" + gray + "&m--------------------" + gold +"&l[");
                Utils.chat(p, "  " + gray + "» " + gold + "/password enable " + gray + "- " + yellow + "Enables Your Password");
                Utils.chat(p, "  " + gray + "» " + gold + "/password disable " + gray + "- " + yellow + "Disables Your Password");
                Utils.chat(p, "  " + gray + "» " + gold + "/password set [password] " + gray + "- " + yellow + "Changes Your Password");
                Utils.chat(p, gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l[");
                break;

            case "enable":
                if(!sender.hasPermission("playerpasswords.enable"))
                {
                    Utils.chat(sender, settings.getConfig().getString("NoPermission"));
                    return true;
                }

                settings.getData().set("passwords." + uuid + ".enabled", true);
                settings.saveData();
                settings.reloadData();
                Utils.chat(p, settings.getConfig().getString("PasswordEnabled"));
                break;

            case "disable":
                if(!sender.hasPermission("playerpasswords.disable"))
                {
                    Utils.chat(p, settings.getConfig().getString("NoPermission"));
                    return true;
                }
                if(settings.getConfig().getBoolean("Optional"))
                {
                    settings.getData().set("passwords." + uuid + ".enabled", false);
                    settings.saveData();
                    settings.reloadData();
                    Utils.chat(p, settings.getConfig().getString("PasswordDisabled"));
                }
                else
                {
                    Utils.chat(p, settings.getConfig().getString("OptionalPasswordDisabled"));
                }
                break;

            case "set":
                if(!sender.hasPermission("playerpasswords.set"))
                {
                    Utils.chat(p, settings.getConfig().getString("NoPermission"));
                    return true;
                }
                if(args.length > 1)
                {
                    settings.getData().set("passwords." + uuid + ".password", Utils.hash(args[1]));
                    settings.saveData();
                    settings.reloadData();
                    Utils.chat(p, settings.getConfig().getString("PasswordSet").replace("%password%", args[1]));
                }
                else
                {
                    Utils.chat(p, settings.getConfig().getString("PasswordSetUsage"));
                }
                break;
        }


        return true;
    }
}
