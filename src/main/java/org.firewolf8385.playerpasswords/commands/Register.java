package org.firewolf8385.playerpasswords.commands;

import org.firewolf8385.playerpasswords.PlayerPasswords;
import org.firewolf8385.playerpasswords.managers.SettingsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.firewolf8385.playerpasswords.objects.PasswordPlayer;
import org.firewolf8385.playerpasswords.utils.ChatUtils;
import org.firewolf8385.playerpasswords.utils.StringUtils;

public class Register implements CommandExecutor {
    private final PlayerPasswords plugin;
    private final SettingsManager settings = SettingsManager.getInstance();

    public Register(PlayerPasswords plugin) {
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
        PasswordPlayer pl = plugin.getPlayerManager().get(p);
        boolean enabled = settings.getData().getBoolean("passwords." + uuid + ".enabled");
        int minimum = settings.getConfig().getInt("MinimumPasswordLength");
        int maximum = settings.getConfig().getInt("MaximumPasswordLength");

        // If the player is already logged in, the command will end.
        if(pl.isVerified()) {
            ChatUtils.chat(p, settings.getConfig().getString("AlreadyLoggedIn"));
            return true;
        }

        // If the player already set their password, the command will end.
        if(enabled) {
            ChatUtils.chat(p, settings.getConfig().getString("AlreadyRegistered"));
            return true;
        }

        // If the player did not enter a password, the command will end.
        if(args.length == 0) {
            ChatUtils.chat(p, settings.getConfig().getString("RegisterUsage"));
            return true;
        }

        // Shows the player a message if their password does not fit the requirements.
        if(!(args[0].length() >= minimum && args[0].length() <= maximum)) {
            ChatUtils.chat(p, settings.getConfig().getString("OutOfBounds"));
            return true;
        }

        settings.getData().set("passwords." + uuid + ".password", StringUtils.hash(args[0]));
        ChatUtils.chat(p, settings.getConfig().getString("SetPasswordSuccessful"));

        if(!pl.isVerified()) {
            pl.setVerified(true);
        }

        if(!(settings.getData().getBoolean("passwords." + uuid + ".enabled"))) {
            settings.getData().set("passwords." + uuid + ".enabled", true);
        }

        settings.saveData();
        settings.reloadData();

        return true;
    }
}