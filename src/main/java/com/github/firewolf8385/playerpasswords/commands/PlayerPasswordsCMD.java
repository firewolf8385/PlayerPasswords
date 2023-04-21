package com.github.firewolf8385.playerpasswords.commands;

import com.github.firewolf8385.playerpasswords.PlayerPasswords;
import com.github.firewolf8385.playerpasswords.settings.PluginMessage;
import com.github.firewolf8385.playerpasswords.settings.SettingsManager;
import com.github.firewolf8385.playerpasswords.settings.ThemeColor;
import com.github.firewolf8385.playerpasswords.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.github.firewolf8385.playerpasswords.player.PasswordPlayer;
import org.jetbrains.annotations.NotNull;

/**
 * This class runs the /playerpasswords command, which is the main admin command for the plugin.
 * aliases:
 *   - /pp
 */
public class PlayerPasswordsCMD implements CommandExecutor {
    private final SettingsManager settings = SettingsManager.getInstance();
    private final PlayerPasswords plugin;

    /**
     * To be able to access the configuration files, we need to pass an instance of the plugin to our listener.
     * @param plugin Instance of the plugin.
     */
    public PlayerPasswordsCMD(PlayerPasswords plugin) {
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

        // Makes sure the player has permission to use the command.
        if(!sender.hasPermission("playerpasswords.admin")) {
            ChatUtils.chat(sender, PluginMessage.NO_PERMISSION.toString());
            return true;
        }

        String gold = ThemeColor.GOLD.toString();
        String yellow = ThemeColor.YELLOW.toString();
        String gray = ThemeColor.GRAY.toString();

        // Shows default page if no arguments are given.
        if(args.length == 0) {
            args = new String[]{"help"};
        }

        // Process the sub commands.
        switch(args[0]) {
            default:
                ChatUtils.chat(sender, gold + "<bold>]</bold>" + gray + "<strikethrough>-----------------</strikethrough>" + gold + "<bold>PlayerPasswords</bold>" + gray + "<strikethrough>-----------------</strikethrough>" + gold +"<bold>[</bold>");
                ChatUtils.chat(sender, "  " + gray + "» " + gold + "/pp info " + gray + "- " + yellow + "Shows plugin info.");
                ChatUtils.chat(sender, "  " + gray + "» " + gold + "/pp reload " + gray + "- " + yellow + "Reload the plugin's config file.");
                ChatUtils.chat(sender, "  " + gray + "» " + gold + "/pp verified " + gray + "- " + yellow + "List all verified players.");
                ChatUtils.chat(sender, gold + "<bold>]</bold>" + gray +"<strikethrough>---------------------------------------------------</strikethrough>" + gold + "<bold>[</bold>");
                break;

            case "info":
                ChatUtils.chat(sender, gold + "<bold>]</bold>" + gray + "<strikethrough>-----------------</strikethrough>" + gold + "&lPlayerPasswords" + gray + "<strikethrough>-----------------</strikethrough>" + gold +"<bold>[</bold>");
                ChatUtils.chat(sender, "  " + gray + "» " + gold + "Author " + gray + "- " + yellow + "firewolf8385");
                ChatUtils.chat(sender, "  " + gray + "» " + gold + "Version " + gray + "- " + yellow + plugin.getDescription().getVersion());
                ChatUtils.chat(sender, "  " + gray + "» " + gold + "GitHub " + gray + "- " + yellow + "https://www.github.com/firewolf8385/PlayerPasswords");
                ChatUtils.chat(sender, gold + "<bold>]</bold>" + gray +"<strikethrough>---------------------------------------------------</strikethrough>" + gold + "<bold>[</bold>");
                break;

            case "verified":
                ChatUtils.chat(sender, gold + "<bold>]</bold>" + gray + "<strikethrough>-----------------</strikethrough>" + gold + "&lPlayerPasswords" + gray + "<strikethrough>-----------------</strikethrough>" + gold +"<bold>[</bold>");
                for(Player player : Bukkit.getOnlinePlayers()) {
                    PasswordPlayer passwordPlayer = plugin.getPasswordPlayerManager().getPlayer(player);

                    if(passwordPlayer.isVerified()) {
                        ChatUtils.chat(sender, "  " + gray + "» " + yellow + player.getName());
                    }
                }
                ChatUtils.chat(sender, gold + "<bold>]</bold>" + gray +"<strikethrough>---------------------------------------------------</strikethrough>" + gold + "<bold>[</bold>");
                break;

            case "reload":
                settings.reloadConfig();
                settings.reloadData();
                ChatUtils.chat(sender, PluginMessage.CONFIG_RELOADED.toString());
                break;

            case "reset":
                // Makes sure the command is being used properly.
                if(args.length != 2) {
                    ChatUtils.chat(sender, PluginMessage.PASSWORD_RESET_USAGE.toString());
                    return true;
                }

                // Get the player to reset the password of.
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

                // Makes sure the player exists.
                if(!settings.getData().isSet("passwords." + target.getUniqueId() + ".enabled")) {
                    ChatUtils.chat(sender, PluginMessage.PLAYER_DOES_NOT_EXIST.toString());
                    return true;
                }

                settings.getData().set("passwords." + target.getUniqueId() + ".enabled", false);
                settings.getData().set("passwords." + target.getUniqueId() + ".password", "");

                // Reload the data file after updating it.
                settings.saveData();
                settings.reloadData();

                ChatUtils.chat(sender, PluginMessage.PASSWORD_RESET.toString());

                break;
        }

        return true;
    }
}
