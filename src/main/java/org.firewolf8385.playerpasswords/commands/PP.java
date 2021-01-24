package org.firewolf8385.playerpasswords.commands;

import org.bukkit.Bukkit;
import org.firewolf8385.playerpasswords.PlayerPasswords;
import org.firewolf8385.playerpasswords.managers.SettingsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.firewolf8385.playerpasswords.objects.PasswordPlayer;
import org.firewolf8385.playerpasswords.utils.ChatUtils;

public class PP implements CommandExecutor {
    private PlayerPasswords plugin;
    private final SettingsManager settings = SettingsManager.getInstance();
    String gold = settings.getConfig().getString("color1");
    String yellow = settings.getConfig().getString("color2");
    String gray = settings.getConfig().getString("color3");

    public PP(PlayerPasswords plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
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
                ChatUtils.chat(sender, "  " + gray + "» " + gold + "Version " + gray + "- " + yellow + "2.0");
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
                for(Player p : Bukkit.getOnlinePlayers()) {
                    PasswordPlayer pl = plugin.getPlayerManager().get(p.getUniqueId());

                    if(pl.isVerified()) {
                        ChatUtils.chat(sender, "  " + gray + "» " + yellow + p.getName());
                    }
                }
                ChatUtils.chat(sender, gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l[");
                break;

        }

        return true;
    }
}
