package com.github.firewolf8385.playerpasswords.commands;

import com.github.firewolf8385.playerpasswords.PlayerPasswords;
import com.github.firewolf8385.playerpasswords.settings.PluginMessage;
import com.github.firewolf8385.playerpasswords.settings.SettingsManager;
import com.github.firewolf8385.playerpasswords.utils.chat.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.github.firewolf8385.playerpasswords.player.PasswordPlayer;
import com.github.firewolf8385.playerpasswords.utils.StringUtils;

public class LoginCMD implements CommandExecutor {
    private final SettingsManager settings = SettingsManager.getInstance();
    private final PlayerPasswords plugin;

    public LoginCMD(PlayerPasswords plugin) {
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

        // If the player is already logged in, they can't log in again.
        if(passwordPlayer.isVerified()) {
            ChatUtils.chat(player, PluginMessage.ALREADY_LOGGED_IN.toString());
            return true;
        }

        // Makes sure the player has a password set.
        if(!passwordPlayer.hasPassword()) {
            // If not, and they're required to, tell them how to register.
            if(passwordPlayer.isRequired()) {
                ChatUtils.chat(player, PluginMessage.REGISTER.toString());
                return true;
            }

            // Otherwise, tell them how to set a password.
            ChatUtils.chat(player, PluginMessage.PASSWORD_NOT_SET.toString());
        }

        // If The Command Is Run Without Args, Show Error Message
        if(args.length == 0) {
            ChatUtils.chat(player, PluginMessage.LOGIN_USAGE.toString());
            return true;
        }

        if(StringUtils.hash(args[0]) == (settings.getData().getInt("passwords." + uuid + ".password"))) {
            ChatUtils.chat(player, PluginMessage.LOGIN_SUCCESSFUL.toString());
            passwordPlayer.setVerified(true);
        }
        else {
            if(settings.getConfig().getString("WrongPassword").equalsIgnoreCase("tryagain")) {
                ChatUtils.chat(player, PluginMessage.PASSWORD_INCORRECT.toString());
            }
            else {
                player.kickPlayer(ChatColor.translateAlternateColorCodes('&', PluginMessage.KICK_MESSAGE.toString()));
            }
        }
        return true;
    }
}