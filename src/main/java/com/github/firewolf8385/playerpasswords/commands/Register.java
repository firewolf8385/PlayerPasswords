package com.github.firewolf8385.playerpasswords.commands;

import com.github.firewolf8385.playerpasswords.SettingsManager;
import com.github.firewolf8385.playerpasswords.utils.chat.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.github.firewolf8385.playerpasswords.objects.PasswordPlayer;
import com.github.firewolf8385.playerpasswords.utils.StringUtils;

public class Register implements CommandExecutor
{
    SettingsManager settings = SettingsManager.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        Player p = (Player) sender;
        String uuid = p.getUniqueId().toString();
        PasswordPlayer pl = PasswordPlayer.getPlayers().get(p.getUniqueId());
        boolean enabled = settings.getData().getBoolean("passwords." + uuid + ".enabled");
        int minimum = settings.getConfig().getInt("MinimumPasswordLength");
        int maximum = settings.getConfig().getInt("MaximumPasswordLength");

        // If the player is already logged in, the command will end.
        if(pl.isVerified())
        {
            ChatUtils.chat(p, settings.getConfig().getString("AlreadyLoggedIn"));
            return true;
        }

        // If the player already set their password, the command will end.
        if(enabled)
        {
            ChatUtils.chat(p, settings.getConfig().getString("AlreadyRegistered"));
            return true;
        }

        // If the player did not enter a password, the command will end.
        if(args.length == 0)
        {
            ChatUtils.chat(p, settings.getConfig().getString("RegisterUsage"));
            return true;
        }

        // Shows the player a message if their password does not fit the requirements.
        if(!(args[0].length() >= minimum && args[0].length() <= maximum))
        {
            ChatUtils.chat(p, settings.getConfig().getString("OutOfBounds"));
            return true;
        }


        settings.getData().set("passwords." + uuid + ".password", StringUtils.hash(args[0]));
        ChatUtils.chat(p, settings.getConfig().getString("SetPasswordSuccessful"));

        if(!pl.isVerified())
        {
            pl.setVerified(true);
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