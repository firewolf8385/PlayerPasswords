package org.firewolf8385.playerpasswords.commands;

import org.bukkit.Bukkit;
import org.firewolf8385.playerpasswords.PlayerPasswords;
import org.firewolf8385.playerpasswords.managers.SettingsManager;
import org.firewolf8385.playerpasswords.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.firewolf8385.playerpasswords.objects.PasswordPlayer;

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
            Utils.chat(sender, gold + "&l]" + gray + "&m-----------------" + gold + "&lPlayerPasswords" + gray + "&m-----------------" + gold +"&l[");
            Utils.chat(sender, "  " + gray + "» " + gold + "/pp info " + gray + "- " + yellow + "Shows plugin info.");
            Utils.chat(sender, "  " + gray + "» " + gold + "/pp support " + gray + "- " + yellow + "Display's the support discord link.");
            Utils.chat(sender, "  " + gray + "» " + gold + "/pp verified " + gray + "- " + yellow + "List all verified players.");
            Utils.chat(sender, gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l[");
            return true;
        }

        switch(args[0]) {
            default:
                Utils.chat(sender, gold + "&l]" + gray + "&m-----------------" + gold + "&lPlayerPasswords" + gray + "&m-----------------" + gold +"&l[");
                Utils.chat(sender, "  " + gray + "» " + gold + "Author " + gray + "- " + yellow + "firewolf8385");
                Utils.chat(sender, "  " + gray + "» " + gold + "Version " + gray + "- " + yellow + "2.0");
                Utils.chat(sender, "  " + gray + "» " + gold + "Spigot " + gray + "- " + yellow + "https://www.spigotmc.org/resources/70520/");
                Utils.chat(sender, gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l[");
                break;

            case "version":
                Utils.chat(sender, gold + "&l]" + gray + "&m-----------------" + gold + "&lPlayerPasswords" + gray + "&m-----------------" + gold +"&l[");
                Utils.chat(sender, "  " + gray + "» " + gold + "Version " + gray + "- " + yellow + "1.4");
                Utils.chat(sender, gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l[");
                break;

            case "support":
                Utils.chat(sender, gold + "&l]" + gray + "&m-----------------" + gold + "&lPlayerPasswords" + gray + "&m-----------------" + gold +"&l[");
                Utils.chat(sender, "  " + gray + "» " + gold + "Discord " + gray + "- " + yellow + "https://discord.gg/FtBteC8");
                Utils.chat(sender, gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l[");
                break;

            case "disable":
                break;

            case "help":
                Utils.chat(sender, gold + "&l]" + gray + "&m-----------------" + gold + "&lPlayerPasswords" + gray + "&m-----------------" + gold +"&l[");
                Utils.chat(sender, "  " + gray + "» " + gold + "/pp info " + gray + "- " + yellow + "Shows plugin info.");
                Utils.chat(sender, "  " + gray + "» " + gold + "&6/pp support " + gray + "- " + yellow + "Display's the support discord link.");
                Utils.chat(sender, "  " + gray + "» " + gold + "&6/pp verified " + gray + "- " + yellow + "List all verified players.");
                Utils.chat(sender, gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l[");
                break;

            case "verified":
                Utils.chat(sender, gold + "&l]" + gray + "&m-----------------" + gold + "&lPlayerPasswords" + gray + "&m-----------------" + gold +"&l[");
                for(Player p : Bukkit.getOnlinePlayers()) {
                    PasswordPlayer pl = plugin.getPlayerManager().get(p.getUniqueId());

                    if(pl.isVerified()) {
                        Utils.chat(sender, "  " + gray + "» " + yellow + p.getName());
                    }
                }
                Utils.chat(sender, gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l[");
                break;

        }

        return true;
    }
}
