package org.firewolf8385.playerpasswords.commands;

import org.firewolf8385.playerpasswords.SettingsManager;
import org.firewolf8385.playerpasswords.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.firewolf8385.playerpasswords.objects.PasswordPlayer;
import org.firewolf8385.playerpasswords.utils.StringUtils;

public class Login implements CommandExecutor
{
    SettingsManager settings = SettingsManager.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        // Exit if not a player.
        if(!(sender instanceof Player))
        {
            return true;
        }

        Player p = (Player) sender;
        String uuid = p.getUniqueId().toString();
        PasswordPlayer pl = PasswordPlayer.getPlayers().get(p.getUniqueId());

        // If The Command Is Run Without Args, Show Error Message
        if(args.length == 0)
        {
            Utils.chat(p, settings.getConfig().getString("LoginUsage"));
            return true;
        }

        // If the player is already logged in, they can't log in again.
        if(pl.isVerified())
        {
            Utils.chat(p, settings.getConfig().getString("AlreadyLoggedIn"));
            return true;
        }

        if(StringUtils.hash(args[0]) == (settings.getData().getInt("passwords." + uuid + ".password")))
        {
            Utils.chat(p, settings.getConfig().getString("LogInSuccessful"));
            pl.setVerified(true);
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