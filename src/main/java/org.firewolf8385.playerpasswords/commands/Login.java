package org.firewolf8385.playerpasswords.commands;

import org.firewolf8385.playerpasswords.PlayerPasswords;
import org.firewolf8385.playerpasswords.managers.SettingsManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.firewolf8385.playerpasswords.objects.PasswordPlayer;
import org.firewolf8385.playerpasswords.utils.ChatUtils;
import org.firewolf8385.playerpasswords.utils.StringUtils;

public class Login implements CommandExecutor {
    private final PlayerPasswords plugin;
    private final SettingsManager settings = SettingsManager.getInstance();

    public Login(PlayerPasswords plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Exit if not a player.
        if(!(sender instanceof Player)) {
            ChatUtils.chat(sender, settings.getConfig().getString("MustBePlayer"));
            return true;
        }

        Player p = (Player) sender;
        String uuid = p.getUniqueId().toString();
        PasswordPlayer pl = plugin.getPlayerManager().get(p.getUniqueId());

        // If the player is already logged in, they can't log in again.
        if(pl.isVerified()) {
            ChatUtils.chat(p, settings.getConfig().getString("AlreadyLoggedIn"));
            return true;
        }

        // If The Command Is Run Without Args, Show Error Message
        if(args.length == 0) {
            ChatUtils.chat(p, settings.getConfig().getString("LoginUsage"));
            return true;
        }

        if(StringUtils.hash(args[0]) == (settings.getData().getInt("passwords." + uuid + ".password"))) {
            ChatUtils.chat(p, settings.getConfig().getString("LogInSuccessful"));
            pl.setVerified(true);
        }
        else {
            if(settings.getConfig().getString("WrongPassword").toLowerCase().equals("tryagain")) {
                ChatUtils.chat(p, settings.getConfig().getString("PasswordIncorrect"));
            }
            else {
                p.kickPlayer(ChatColor.translateAlternateColorCodes('&', settings.getConfig().getString("KickMessage")));
            }
        }
        return true;
    }
}