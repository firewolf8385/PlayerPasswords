package com.github.firewolf8385.playerpasswords.commands;

import com.github.firewolf8385.playerpasswords.PlayerPasswords;
import com.github.firewolf8385.playerpasswords.settings.PluginMessage;
import com.github.firewolf8385.playerpasswords.settings.SettingsManager;
import com.github.firewolf8385.playerpasswords.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.github.firewolf8385.playerpasswords.player.PasswordPlayer;
import com.github.firewolf8385.playerpasswords.utils.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * This class runs the /login command, which runs login related functions.
 */
public class LoginCMD implements CommandExecutor {
    private final SettingsManager settings = SettingsManager.getInstance();
    private final PlayerPasswords plugin;

    /**
     * To be able to access the configuration files, we need to pass an instance of the plugin to our listener.
     * @param plugin Instance of the plugin.
     */
    public LoginCMD(PlayerPasswords plugin) {
        this.plugin = plugin;
    }

    /**
     * Runs when the command is executed.
     * @param sender Source of the command
     * @param cmd Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return If the command was successful.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        // Exit if not a player.
        if(!(sender instanceof Player)) {
            ChatUtils.chat(sender, PluginMessage.NOT_A_PLAYER.toString());
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

        // If the password is correct, mark the player as verified.
        if(StringUtils.hash(args[0]) == (settings.getData().getInt("passwords." + uuid + ".password"))) {
            ChatUtils.chat(player, PluginMessage.LOGIN_SUCCESSFUL.toString());
            passwordPlayer.setVerified(true);
            return true;
        }

        // Otherwise, process the login attempt.
        passwordPlayer.addLoginAttempt();
        int maxAttempts = settings.getConfig().getInt("MaxAttempts");

        // If there have been too many login attempts, process the fail commands.
        if(maxAttempts != -1 && passwordPlayer.getLoginAttempts() >= maxAttempts) {
            for(String command : settings.getConfig().getStringList("FailCommands")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
            }

            return true;
        }

        // If they still have attempts remaining, send them the incorrect password message.
        ChatUtils.chat(player, PluginMessage.PASSWORD_INCORRECT.toString());
        return true;
    }
}