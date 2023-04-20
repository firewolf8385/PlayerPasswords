package com.github.firewolf8385.playerpasswords.commands;

import com.github.firewolf8385.playerpasswords.PlayerPasswords;
import com.github.firewolf8385.playerpasswords.settings.PluginMessage;
import com.github.firewolf8385.playerpasswords.settings.SettingsManager;
import com.github.firewolf8385.playerpasswords.settings.ThemeColor;
import com.github.firewolf8385.playerpasswords.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.github.firewolf8385.playerpasswords.player.PasswordPlayer;
import com.github.firewolf8385.playerpasswords.utils.StringUtils;

public class PasswordCMD implements CommandExecutor {
    private final SettingsManager settings = SettingsManager.getInstance();
    private final PlayerPasswords plugin;

    public PasswordCMD(PlayerPasswords plugin) {
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

        // Player cannot use command if they aren't logged in.
        if(!passwordPlayer.isVerified()) {
            ChatUtils.chat(player, PluginMessage.MUST_BE_LOGGED_IN.toString());
            return true;
        }

        String gold = ThemeColor.GOLD.toString();
        String yellow = ThemeColor.YELLOW.toString();
        String gray = ThemeColor.GRAY.toString();

        // Shows default page if no arguments are given.
        if(args.length == 0) {
            ChatUtils.chat(player, gold + "&l]" + gray + "&m--------------------" + gold + "&lPasswords" + gray + "&m--------------------" + gold +"&l[");
            ChatUtils.chat(player, "  " + gray + "» " + gold + "/password enable " + gray + "- " + yellow + "Enables Your Password");
            ChatUtils.chat(player, "  " + gray + "» " + gold + "/password disable " + gray + "- " + yellow + "Disables Your Password");
            ChatUtils.chat(player, "  " + gray + "» " + gold + "/password set [password] " + gray + "- " + yellow + "Changes Your Password");
            ChatUtils.chat(player, gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l[");
            return true;
        }


        switch(args[0].toLowerCase()) {
            default:
                ChatUtils.chat(player, gold + "&l]" + gray + "&m--------------------" + gold + "&lPasswords" + gray + "&m--------------------" + gold +"&l[");
                ChatUtils.chat(player, "  " + gray + "» " + gold + "/password enable " + gray + "- " + yellow + "Enables Your Password");
                ChatUtils.chat(player, "  " + gray + "» " + gold + "/password disable " + gray + "- " + yellow + "Disables Your Password");
                ChatUtils.chat(player, "  " + gray + "» " + gold + "/password set [password] " + gray + "- " + yellow + "Changes Your Password");
                ChatUtils.chat(player, gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l[");
                break;

            case "enable":
                if(!sender.hasPermission("playerpasswords.enable")) {
                    ChatUtils.chat(sender, PluginMessage.NO_PERMISSION.toString());
                    return true;
                }

                settings.getData().set("passwords." + uuid + ".enabled", true);
                settings.saveData();
                settings.reloadData();
                ChatUtils.chat(player, PluginMessage.PASSWORD_ENABLED.toString());
                break;

            case "disable":
                if(!sender.hasPermission("playerpasswords.disable")) {
                    ChatUtils.chat(player, PluginMessage.NO_PERMISSION.toString());
                    return true;
                }
                if(settings.getConfig().getBoolean("Optional")) {
                    settings.getData().set("passwords." + uuid + ".enabled", false);
                    settings.saveData();
                    settings.reloadData();
                    ChatUtils.chat(player, PluginMessage.PASSWORD_DISABLED.toString());
                }
                else {
                    ChatUtils.chat(player, PluginMessage.OPTIONAL_PASSWORD_DISABLED.toString());
                }
                break;

            case "set":
                if(!sender.hasPermission("playerpasswords.set")) {
                    ChatUtils.chat(player, PluginMessage.NO_PERMISSION.toString());
                    return true;
                }

                if(args.length > 1) {
                    // Gets the minimum and maximum allowed password length.
                    int minimum = settings.getConfig().getInt("MinimumPasswordLength");
                    int maximum = settings.getConfig().getInt("MaximumPasswordLength");

                    // Shows the player a message if their password does not fit the requirements.
                    if(!(args[1].length() >= minimum && args[1].length() <= maximum)) {
                        ChatUtils.chat(player, PluginMessage.OUT_OF_BOUNDS.toString());
                        return true;
                    }

                    passwordPlayer.setPassword(args[1]);
                    if(settings.getConfig().getBoolean("RequireConfirmation")) {
                        ChatUtils.chat(player, PluginMessage.CONFIRM_PASSWORD.toString());
                    }
                    else {
                        settings.getData().set("passwords." + uuid + ".password", StringUtils.hash(args[1]));
                        settings.saveData();
                        settings.reloadData();
                        ChatUtils.chat(player, PluginMessage.PASSWORD_SET.toString().replace("%password%", args[1]));
                    }
                }
                else {
                    ChatUtils.chat(player, PluginMessage.PASSWORD_SET_USAGE.toString());
                }
                break;

            case "confirm":
                // If the cache is empty, then there is no password to confirm.
                if(passwordPlayer.getPassword().isEmpty()) {
                    ChatUtils.chat(player, PluginMessage.PASSWORD_NOT_SET.toString());
                    return true;
                }

                // If there is less than 2 arguments, the command was not run right.
                if(args.length > 1) {
                    // Check if the cache and confirmation are equal.
                    if(passwordPlayer.getPassword().equals(args[1])) {
                        // If so, saves the password.
                        settings.getData().set("passwords." + uuid + ".password", StringUtils.hash(args[1]));
                        settings.saveData();
                        settings.reloadData();
                        ChatUtils.chat(player, PluginMessage.PASSWORD_SET.toString().replace("%password%", args[1]));
                    }
                    else {
                        ChatUtils.chat(player, PluginMessage.PASSWORD_INCORRECT.toString());
                    }
                    return true;
                }
                else {
                    ChatUtils.chat(player, PluginMessage.PASSWORD_CONFIRM_USAGE.toString());
                }
                break;
        }

        return true;
    }
}
