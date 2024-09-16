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
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import com.github.firewolf8385.playerpasswords.player.PasswordPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class runs the /playerpasswords command, which is the main admin command for the plugin.
 * aliases:
 *   - /pp
 */
public class PlayerPasswordsCMD implements CommandExecutor, TabCompleter {
    private final PlayerPasswordsPlugin plugin;

    /**
     * To be able to access the configuration files, we need to pass an instance of the plugin to our listener.
     * @param plugin Instance of the plugin.
     */
    public PlayerPasswordsCMD(@NotNull final PlayerPasswordsPlugin plugin) {
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

        // Makes sure the player has permission to use the command.
        if(!sender.hasPermission("playerpasswords.admin")) {
            ChatUtils.chat(sender, plugin.getConfigManager().getMessage(ConfigMessage.MISC_NO_PERMISSION));
            return true;
        }

        // Shows default page if no arguments are given.
        if(args.length == 0) {
            args = new String[]{"help"};
        }

        // Process the sub commands.
        switch(args[0]) {
            default:
                ChatUtils.chat(sender, "");
                ChatUtils.chat(sender, "<center><color3><st>        </st> <color1><bold>PlayerPasswords <color3><st>        </st> ");
                ChatUtils.chat(sender, "");
                ChatUtils.chat(sender, "  <color3>» <color1>/pp info <color3>- <color2>Shows plugin info.");
                ChatUtils.chat(sender, "  <color3>» <color1>/pp reload <color3>- <color2>Reload the plugin's config file.");
                ChatUtils.chat(sender, "  <color3>» <color1>/pp verified <color3>- <color2>List all verified players.");
                ChatUtils.chat(sender, "");
                break;

            case "info":
                ChatUtils.chat(sender, "");
                ChatUtils.chat(sender, "<center><color3><st>        </st> <color1><bold>PlayerPasswords <color3><st>        </st> ");
                ChatUtils.chat(sender, "");
                ChatUtils.chat(sender, "  <color3>» <color1>Author <color3>- <color2>firewolf8385");
                ChatUtils.chat(sender, "  <color3>» <color1>Version <color3>- <color2>" + plugin.getDescription().getVersion());
                ChatUtils.chat(sender, "  <color3>» <color1>GitHub <color3>- <color2><click:open_url:'https://www.github.com/firewolf8385/PlayerPasswords'>github.com/firewolf8385/PlayerPasswords</click>");
                ChatUtils.chat(sender, "  <color3>» <color1>Modrinth <color3>- <color2><click:open_url:'https://www.modrinth.com/project/playerpasswords'>modrinth.com/project/playerpasswords</click>");
                ChatUtils.chat(sender, "");
                break;

            case "players":
                ChatUtils.chat(sender, "");
                ChatUtils.chat(sender, "<center><color3><st>        </st> <color1><bold>PlayerPasswords <color3><st>        </st> ");
                ChatUtils.chat(sender, "");

                final StringBuilder builder = new StringBuilder();

                for(final Player player : Bukkit.getOnlinePlayers()) {
                    final PasswordPlayer passwordPlayer = plugin.getPasswordPlayerManager().getPlayer(player);

                    if(player.hasPermission("playerpasswords.bypass")) {
                        builder.append("<aqua>");
                    }
                    else if(passwordPlayer.isVerified()) {
                        builder.append("<green>");
                    }
                    else {
                        builder.append("<red>");
                    }

                    builder.append(player.getName()).append("<color3>, ");
                }

                ChatUtils.chat(sender, builder.substring(0, builder.length() - 2));
                ChatUtils.chat(sender, "");
                ChatUtils.chat(sender, "<center><green>■ Verified <dark_gray>- <red>■ Not Verified <dark_gray>- <aqua>■ Exempt");
                ChatUtils.chat(sender, "");
                break;

            case "reload":
                plugin.getConfigManager().reloadConfig();
                plugin.getConfigManager().reloadData();
                plugin.getConfigManager().reloadMessages();
                ChatUtils.chat(sender, plugin.getConfigManager().getMessage(ConfigMessage.MISC_CONFIG_RELOADED));
                break;

            case "reset":
                // Makes sure the command is being used properly.
                if(args.length != 2) {
                    ChatUtils.chat(sender, plugin.getConfigManager().getMessage(ConfigMessage.MISC_PASSWORD_RESET_USAGE));
                    return true;
                }

                // Get the player to reset the password of.
                final OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

                // Makes sure the player exists.
                if(!plugin.getConfigManager().getData().isSet("passwords." + target.getUniqueId() + ".enabled")) {
                    ChatUtils.chat(sender, plugin.getConfigManager().getMessage(ConfigMessage.MISC_PLAYER_DOES_NOT_EXIST));
                    return true;
                }

                plugin.getConfigManager().getData().set("passwords." + target.getUniqueId() + ".enabled", false);
                plugin.getConfigManager().getData().set("passwords." + target.getUniqueId() + ".password", "");

                // Reload the data file after updating it.
                plugin.getConfigManager().saveData();
                plugin.getConfigManager().reloadData();

                ChatUtils.chat(sender, plugin.getConfigManager().getMessage(ConfigMessage.MISC_PASSWORD_RESET));

                break;
        }

        return true;
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
            return Arrays.asList("help", "info", "help", "reload", "reset", "players", "version");
        }

        // Otherwise, send an empty list.
        return Collections.emptyList();
    }
}
