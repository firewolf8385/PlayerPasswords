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
import com.github.firewolf8385.playerpasswords.settings.PluginMessage;
import com.github.firewolf8385.playerpasswords.settings.SettingsManager;
import com.github.firewolf8385.playerpasswords.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.github.firewolf8385.playerpasswords.player.PasswordPlayer;
import com.github.firewolf8385.playerpasswords.utils.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * This class runs the /register command, which allows the player to register a password when optional is set to false.
 */
public class RegisterCMD implements CommandExecutor {
    private final SettingsManager settings = SettingsManager.getInstance();
    private final PlayerPasswordsPlugin plugin;

    /**
     * To be able to access the configuration files, we need to pass an instance of the plugin to our listener.
     * @param plugin Instance of the plugin.
     */
    public RegisterCMD(PlayerPasswordsPlugin plugin) {
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

        // Check if the player is trying to confirm their registration.
        if(args[0].equalsIgnoreCase("confirm")) {
            // If the cache is empty, then there is no password to confirm.
            if(passwordPlayer.getPassword().isEmpty()) {
                ChatUtils.chat(player, PluginMessage.REGISTER_USAGE.toString());
                return true;
            }

            // If there is less than 2 arguments, the command was not run right.
            if(args.length > 1) {
                // Check if the cache and confirmation are equal.
                if(passwordPlayer.getPassword().equals(args[1])) {
                    // If so, saves the password.
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
                }
                else {
                    ChatUtils.chat(player, PluginMessage.PASSWORD_INCORRECT.toString());
                }
                return true;
            }
            else {
                ChatUtils.chat(player, PluginMessage.REGISTER_CONFIRM_USAGE.toString());
            }

            return true;
        }

        // Shows the player a message if their password does not fit the requirements.
        if(!(args[0].length() >= minimum && args[0].length() <= maximum)) {
            ChatUtils.chat(player, PluginMessage.OUT_OF_BOUNDS.toString());
            return true;
        }

        // Updates the player's password.
        passwordPlayer.setPassword(args[0]);

        // Makes sure the player does not need to confirm the password.
        if(settings.getConfig().getBoolean("RequireConfirmation")) {
            ChatUtils.chat(player, PluginMessage.CONFIRM_REGISTER.toString());
            return true;
        }

        // Otherwise, processes the registration.
        settings.getData().set("passwords." + uuid + ".password", StringUtils.hash(args[0]));
        ChatUtils.chat(player, PluginMessage.SET_PASSWORD_SUCCESSFUL.toString());

        // Sets the player to be verified.
        if(!passwordPlayer.isVerified()) {
            passwordPlayer.setVerified(true);
        }

        // Enables the player's password.
        if(!(settings.getData().getBoolean("passwords." + uuid + ".enabled"))) {
            settings.getData().set("passwords." + uuid + ".enabled", true);
        }

        // Updates the data file.
        settings.saveData();
        settings.reloadData();

        return true;
    }
}