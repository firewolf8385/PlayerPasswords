package com.github.firewolf8385.playerpasswords.commands;

import com.github.firewolf8385.playerpasswords.PlayerPasswords;
import com.github.firewolf8385.playerpasswords.settings.PluginMessage;
import com.github.firewolf8385.playerpasswords.settings.SettingsManager;
import com.github.firewolf8385.playerpasswords.utils.chat.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.github.firewolf8385.playerpasswords.player.PasswordPlayer;
import com.github.firewolf8385.playerpasswords.utils.StringUtils;

public class RegisterCMD implements CommandExecutor {
    private final SettingsManager settings = SettingsManager.getInstance();
    private final PlayerPasswords plugin;

    public RegisterCMD(PlayerPasswords plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Exit if not a player.
        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        String uuid = player.getUniqueId().toString();
        PasswordPlayer passwordPlayer = plugin.getPasswordPlayerManager().getPlayer(player);
        boolean enabled = settings.getData().getBoolean("passwords." + uuid + ".enabled");
        int minimum = settings.getConfig().getInt("MinimumPasswordLength");
        int maximum = settings.getConfig().getInt("MaximumPasswordLength");

        // If the player is already logged in, the command will end.
        if(passwordPlayer.isVerified()) {
            ChatUtils.chat(player, PluginMessage.ALREADY_LOGGED_IN.toString());
            return true;
        }

        // If the player already set their password, the command will end.
        if(enabled) {
            ChatUtils.chat(player, PluginMessage.ALREADY_REGISTERED.toString());
            return true;
        }

        // If the player did not enter a password, the command will end.
        if(args.length == 0) {
            ChatUtils.chat(player, PluginMessage.REGISTER_USAGE.toString());
            return true;
        }

        // Shows the player a message if their password does not fit the requirements.
        if(!(args[0].length() >= minimum && args[0].length() <= maximum)) {
            ChatUtils.chat(player, PluginMessage.OUT_OF_BOUNDS.toString());
            return true;
        }


        settings.getData().set("passwords." + uuid + ".password", StringUtils.hash(args[0]));
        ChatUtils.chat(player, PluginMessage.SET_PASSWORD_SUCCESSFUL.toString());

        if(!passwordPlayer.isVerified()) {
            passwordPlayer.setVerified(true);
        }

        if(!(settings.getData().getBoolean("passwords." + uuid + ".enabled"))) {
            settings.getData().set("passwords." + uuid + ".enabled", true);
        }

        settings.saveData();
        settings.reloadData();

        return true;
    }
}