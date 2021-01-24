package org.firewolf8385.playerpasswords;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Utils {
    /**
     * A quicker method to send colored chat messages.
     * @param p Player to send message to.
     * @param s Message to send.
     */
    public static void chat(Player p, String s) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
    }

    /**
     * A quicker method to send colored chat messages.
     * @param sender CommandSender to sender message to.
     * @param s Message to send.
     */
    public static void chat(CommandSender sender, String s) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
    }

}
