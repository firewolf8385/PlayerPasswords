package org.firewolf8385.playerpasswords.commands;

import org.firewolf8385.playerpasswords.PlayerPasswords;
import org.firewolf8385.playerpasswords.SettingsManager;
import org.firewolf8385.playerpasswords.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Login implements CommandExecutor
{
    SettingsManager settings = SettingsManager.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        Player p = (Player) sender;
        String uuid = p.getUniqueId().toString();

        // If The Command Is Run Without Args, Show Error Message
        if(args.length == 0)
        {
            Utils.chat(p, settings.getConfig().getString("LoginUsage"));
            return true;
        }

        // If the player is already logged in, they can't log in again.
        if(PlayerPasswords.verified.contains(p))
        {
            Utils.chat(p, settings.getConfig().getString("AlreadyLoggedIn"));
            return true;
        }

        if(Utils.hash(args[0]) == (settings.getData().getInt("passwords." + uuid + ".password")))
        {
            Utils.chat(p, settings.getConfig().getString("LogInSuccessful"));
            PlayerPasswords.verified.add(p);
        }
        else
        {
            if(settings.getConfig().getString("WrongPassword").toLowerCase().equals("tryagain"))
            {
                Utils.chat(p, settings.getConfig().getString("PasswordIncorrect"));
            }
            else
            {
                p.kickPlayer(ChatColor.translateAlternateColorCodes('&', settings.getConfig().getString("KickMessage")));
            }
        }
        return true;
    }
}