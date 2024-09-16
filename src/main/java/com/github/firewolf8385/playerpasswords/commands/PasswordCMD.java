/*
 * This file is part of PlayerPasswords, licensed under the MIT License.
 *
 *  Copyright (c) JadedMC
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package com.github.firewolf8385.playerpasswords.commands;

import com.github.firewolf8385.playerpasswords.PlayerPasswordsPlugin;
import com.github.firewolf8385.playerpasswords.settings.ConfigMessage;
import com.github.firewolf8385.playerpasswords.utils.ChatUtils;
import com.github.firewolf8385.playerpasswords.utils.Tuple;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import com.github.firewolf8385.playerpasswords.player.PasswordPlayer;
import com.github.firewolf8385.playerpasswords.utils.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class runs the /password command, which runs password related functions.
 */
public class PasswordCMD implements CommandExecutor, TabCompleter {
    private final PlayerPasswordsPlugin plugin;

    /**
     * To be able to access the configuration files, we need to pass an instance of the plugin to our listener.
     * @param plugin Instance of the plugin.
     */
    public PasswordCMD(@NotNull final PlayerPasswordsPlugin plugin) {
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
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull String[] args) {

        // Exit if not a player.
        if(!(sender instanceof Player)) {
            ChatUtils.chat(sender, plugin.getConfigManager().getMessage(ConfigMessage.MISC_NOT_A_PLAYER));
            return true;
        }

        final Player player = (Player) sender;
        final String uuid = player.getUniqueId().toString();
        final PasswordPlayer passwordPlayer = plugin.getPasswordPlayerManager().getPlayer(player);

        // Player cannot use command if they aren't logged in.
        if(!passwordPlayer.isVerified()) {
            ChatUtils.chat(player, plugin.getConfigManager().getMessage(player, ConfigMessage.MISC_MUST_BE_LOGGED_IN));
            return true;
        }

        // Shows default page if no arguments are given.
        if(args.length == 0) {
            args = new String[]{"help"};
        }

        // Processes the sub command.
        switch(args[0].toLowerCase()) {
            default:
                ChatUtils.chat(sender, plugin.getConfigManager().getMessage(ConfigMessage.PASSWORD_HELP_PAGE));
                /*
                ChatUtils.chat(sender, "");
                ChatUtils.chat(sender, "<center><color3><st>        </st> <color1><bold>Passwords <color3><st>        </st> ");
                ChatUtils.chat(sender, "");
                ChatUtils.chat(player, "  <color3>» <color1>/password enable <color3>- <color2>Enables Your Password");
                ChatUtils.chat(player, "  <color3>» <color1>/password disable <color3>- <color2>Disables Your Password");
                ChatUtils.chat(player, "  <color3>» <color1>/password set [password] <color3>- <color2>Changes Your Password");
                ChatUtils.chat(sender, "");

                 */
                return true;

            case "enable":
                if(!sender.hasPermission("playerpasswords.enable")) {
                    ChatUtils.chat(sender, plugin.getConfigManager().getMessage(ConfigMessage.MISC_NO_PERMISSION));
                    return true;
                }

                if(!passwordPlayer.hasPassword()) {
                    ChatUtils.chat(player, plugin.getConfigManager().getMessage(player, ConfigMessage.PASSWORD_PASSWORD_NOT_SET));
                    return true;
                }

                plugin.getConfigManager().getData().set("passwords." + uuid + ".enabled", true);
                plugin.getConfigManager().saveData();
                plugin.getConfigManager().reloadData();
                ChatUtils.chat(player, plugin.getConfigManager().getMessage(player, ConfigMessage.PASSWORD_PASSWORD_ENABLED));
                return true;

            case "disable":
                if(!sender.hasPermission("playerpasswords.disable")) {
                    ChatUtils.chat(player, plugin.getConfigManager().getMessage(player, ConfigMessage.MISC_NO_PERMISSION));
                    return true;
                }
                if(plugin.getConfigManager().getConfig().getBoolean("Optional")) {
                    plugin.getConfigManager().getData().set("passwords." + uuid + ".enabled", false);
                    plugin.getConfigManager().saveData();
                    plugin.getConfigManager().reloadData();
                    ChatUtils.chat(player, plugin.getConfigManager().getMessage(player, ConfigMessage.PASSWORD_PASSWORD_DISABLED));
                }
                else {
                    ChatUtils.chat(player, plugin.getConfigManager().getMessage(player, ConfigMessage.PASSWORD_OPTIONAL_PASSWORD_DISABLED));
                }
                return true;

            case "set":
                if(!sender.hasPermission("playerpasswords.set")) {
                    ChatUtils.chat(player, plugin.getConfigManager().getMessage(player, ConfigMessage.MISC_NO_PERMISSION));
                    return true;
                }

                if(args.length > 1) {
                    // Gets the minimum and maximum allowed password length.
                    int minimum = plugin.getConfigManager().getConfig().getInt("MinimumPasswordLength");
                    int maximum = plugin.getConfigManager().getConfig().getInt("MaximumPasswordLength");

                    // Shows the player a message if their password does not fit the requirements.
                    if(!(args[1].length() >= minimum && args[1].length() <= maximum)) {
                        final Tuple<String, String> minimumPlaceholder = new Tuple<>("%minimum%", minimum + "");
                        final Tuple<String, String> maximumPlaceholder = new Tuple<>("%maximum%", maximum + "");
                        ChatUtils.chat(player, plugin.getConfigManager().getMessage(player, ConfigMessage.PASSWORD_PASSWORD_OUT_OF_BOUNDS, minimumPlaceholder, maximumPlaceholder));
                        return true;
                    }

                    passwordPlayer.setPassword(args[1]);
                    if(plugin.getConfigManager().getConfig().getBoolean("RequireConfirmation")) {
                        ChatUtils.chat(player, plugin.getConfigManager().getMessage(player, ConfigMessage.PASSWORD_CONFIRM_PASSWORD));
                    }
                    else {
                        plugin.getConfigManager().getData().set("passwords." + uuid + ".password", StringUtils.hash(args[1]));
                        plugin.getConfigManager().saveData();
                        plugin.getConfigManager().reloadData();
                        ChatUtils.chat(player, plugin.getConfigManager().getMessage(player, ConfigMessage.PASSWORD_PASSWORD_SET_SUCCESSFULLY));
                    }
                }
                else {
                    ChatUtils.chat(player, plugin.getConfigManager().getMessage(player, ConfigMessage.PASSWORD_PASSWORD_SET_USAGE));
                }
                return true;

            case "confirm":
                // If the cache is empty, then there is no password to confirm.
                if(passwordPlayer.getPassword().isEmpty()) {
                    ChatUtils.chat(player, plugin.getConfigManager().getMessage(player, ConfigMessage.PASSWORD_PASSWORD_NOT_SET));
                    return true;
                }

                // If there is less than 2 arguments, the command was not run right.
                if(args.length > 1) {
                    // Check if the cache and confirmation are equal.
                    if(passwordPlayer.getPassword().equals(args[1])) {
                        // If so, saves the password.
                        plugin.getConfigManager().getData().set("passwords." + uuid + ".password", StringUtils.hash(args[1]));
                        plugin.getConfigManager().saveData();
                        plugin.getConfigManager().reloadData();
                        ChatUtils.chat(player, plugin.getConfigManager().getMessage(player, ConfigMessage.PASSWORD_PASSWORD_SET_SUCCESSFULLY));

                        // Remind the player to enable their password.
                        if(plugin.getConfigManager().getConfig().getBoolean("Optional") && !passwordPlayer.hasPasswordEnabled()) {
                            ChatUtils.chat(player, plugin.getConfigManager().getMessage(player, ConfigMessage.PASSWORD_PASSWORD_NOT_ENABLED));
                        }
                    }
                    else {
                        ChatUtils.chat(player, plugin.getConfigManager().getMessage(player, ConfigMessage.PASSWORD_PASSWORD_INCORRECT));
                    }
                    return true;
                }
                else {
                    ChatUtils.chat(player, plugin.getConfigManager().getMessage(player, ConfigMessage.PASSWORD_PASSWORD_CONFIRM_USAGE));
                }
                return true;
        }
    }

    /**
     * Processes command tab completion.
     * @param sender Command sender.
     * @param cmd Command.
     * @param label Command label.
     * @param args Arguments of the command.
     * @return Tab completion.
     */
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        // Lists all subcommands if the player hasn't picked one yet.
        if(args.length < 2) {
            return Arrays.asList("confirm", "disable", "enable", "help", "set");
        }

        // Otherwise, send an empty list.
        return Collections.emptyList();
    }
}