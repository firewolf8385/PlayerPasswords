package com.github.firewolf8385.playerpasswords.commands;

import com.github.firewolf8385.playerpasswords.PlayerPasswords;
import com.github.firewolf8385.playerpasswords.settings.ThemeColor;
import com.github.firewolf8385.playerpasswords.utils.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.github.firewolf8385.playerpasswords.player.PasswordPlayer;

public class PlayerPasswordsCMD implements CommandExecutor {
    private final PlayerPasswords plugin;

    public PlayerPasswordsCMD(PlayerPasswords plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String gold = ThemeColor.GOLD.toString();
        String yellow = ThemeColor.YELLOW.toString();
        String gray = ThemeColor.GRAY.toString();

        // If no arguments, show plugin help.
        if(args.length == 0) {
            ChatUtils.chat(sender, gold + "&l]" + gray + "&m-----------------" + gold + "&lPlayerPasswords" + gray + "&m-----------------" + gold +"&l[");
            ChatUtils.chat(sender, "  " + gray + "» " + gold + "/pp info " + gray + "- " + yellow + "Shows plugin info.");
            ChatUtils.chat(sender, "  " + gray + "» " + gold + "/pp support " + gray + "- " + yellow + "Display's the support discord link.");
            ChatUtils.chat(sender, "  " + gray + "» " + gold + "/pp verified " + gray + "- " + yellow + "List all verified players.");
            ChatUtils.chat(sender, gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l[");
            return true;
        }

        switch(args[0]) {
            default:
                ChatUtils.chat(sender, gold + "&l]" + gray + "&m-----------------" + gold + "&lPlayerPasswords" + gray + "&m-----------------" + gold +"&l[");
                ChatUtils.chat(sender, "  " + gray + "» " + gold + "Author " + gray + "- " + yellow + "firewolf8385");
                ChatUtils.chat(sender, "  " + gray + "» " + gold + "Version " + gray + "- " + yellow + plugin.getDescription().getVersion());
                ChatUtils.chat(sender, "  " + gray + "» " + gold + "Spigot " + gray + "- " + yellow + "https://www.spigotmc.org/resources/70520/");
                ChatUtils.chat(sender, gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l[");
                break;

            case "version":
                ChatUtils.chat(sender, gold + "&l]" + gray + "&m-----------------" + gold + "&lPlayerPasswords" + gray + "&m-----------------" + gold +"&l[");
                ChatUtils.chat(sender, "  " + gray + "» " + gold + "Version " + gray + "- " + yellow + "1.4");
                ChatUtils.chat(sender, gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l[");
                break;

            case "support":
                ChatUtils.chat(sender, gold + "&l]" + gray + "&m-----------------" + gold + "&lPlayerPasswords" + gray + "&m-----------------" + gold +"&l[");
                ChatUtils.chat(sender, "  " + gray + "» " + gold + "Discord " + gray + "- " + yellow + "https://discord.gg/FtBteC8");
                ChatUtils.chat(sender, gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l[");
                break;

            case "disable":
                break;

            case "help":
                ChatUtils.chat(sender, gold + "&l]" + gray + "&m-----------------" + gold + "&lPlayerPasswords" + gray + "&m-----------------" + gold +"&l[");
                ChatUtils.chat(sender, "  " + gray + "» " + gold + "/pp info " + gray + "- " + yellow + "Shows plugin info.");
                ChatUtils.chat(sender, "  " + gray + "» " + gold + "&6/pp support " + gray + "- " + yellow + "Display's the support discord link.");
                ChatUtils.chat(sender, "  " + gray + "» " + gold + "&6/pp verified " + gray + "- " + yellow + "List all verified players.");
                ChatUtils.chat(sender, gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l[");
                break;

            case "verified":
                ChatUtils.chat(sender, gold + "&l]" + gray + "&m-----------------" + gold + "&lPlayerPasswords" + gray + "&m-----------------" + gold +"&l[");
                for(Player player : Bukkit.getOnlinePlayers()) {
                    PasswordPlayer passwordPlayer = plugin.getPasswordPlayerManager().getPlayer(player);

                    if(passwordPlayer.isVerified()) {
                        ChatUtils.chat(sender, "  " + gray + "» " + yellow + player.getName());
                    }
                }
                ChatUtils.chat(sender, gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l[");
                break;

        }

        return true;
    }
}
