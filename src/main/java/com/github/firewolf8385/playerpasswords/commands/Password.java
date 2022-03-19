package com.github.firewolf8385.playerpasswords.commands;

import com.github.firewolf8385.playerpasswords.PlayerPasswords;
import com.github.firewolf8385.playerpasswords.SettingsManager;
import com.github.firewolf8385.playerpasswords.utils.chat.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.github.firewolf8385.playerpasswords.player.PasswordPlayer;
import com.github.firewolf8385.playerpasswords.utils.StringUtils;

public class Password implements CommandExecutor {
    SettingsManager settings = SettingsManager.getInstance();
    String gold = settings.getConfig().getString("color1");
    String yellow = settings.getConfig().getString("color2");
    String gray = settings.getConfig().getString("color3");
    private final PlayerPasswords plugin;

    public Password(PlayerPasswords plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        String uuid = player.getUniqueId().toString();
        PasswordPlayer passwordPlayer = plugin.getPasswordPlayerManager().getPlayer(player);

        // Player cannot use command if they aren't logged in.
        if(!passwordPlayer.isVerified()) {
            ChatUtils.chat(player, settings.getConfig().getString("MustBeLoggedIn"));
            return true;
        }

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
                    ChatUtils.chat(sender, settings.getConfig().getString("NoPermission"));
                    return true;
                }

                settings.getData().set("passwords." + uuid + ".enabled", true);
                settings.saveData();
                settings.reloadData();
                ChatUtils.chat(player, settings.getConfig().getString("PasswordEnabled"));
                break;

            case "disable":
                if(!sender.hasPermission("playerpasswords.disable")) {
                    ChatUtils.chat(player, settings.getConfig().getString("NoPermission"));
                    return true;
                }
                if(settings.getConfig().getBoolean("Optional")) {
                    settings.getData().set("passwords." + uuid + ".enabled", false);
                    settings.saveData();
                    settings.reloadData();
                    ChatUtils.chat(player, settings.getConfig().getString("PasswordDisabled"));
                }
                else {
                    ChatUtils.chat(player, settings.getConfig().getString("OptionalPasswordDisabled"));
                }
                break;

            case "set":
                if(!sender.hasPermission("playerpasswords.set")) {
                    ChatUtils.chat(player, settings.getConfig().getString("NoPermission"));
                    return true;
                }
                if(args.length > 1) {
                    settings.getData().set("passwords." + uuid + ".password", StringUtils.hash(args[1]));
                    settings.saveData();
                    settings.reloadData();
                    ChatUtils.chat(player, settings.getConfig().getString("PasswordSet").replace("%password%", args[1]));
                }
                else {
                    ChatUtils.chat(player, settings.getConfig().getString("PasswordSetUsage"));
                }
                break;
        }

        return true;
    }
}
