package org.firewolf8385.playerpasswords.commands;

import org.firewolf8385.playerpasswords.PlayerPasswords;
import org.firewolf8385.playerpasswords.managers.SettingsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.firewolf8385.playerpasswords.objects.PasswordPlayer;
import org.firewolf8385.playerpasswords.utils.ChatUtils;
import org.firewolf8385.playerpasswords.utils.EncryptionUtils;

public class Password implements CommandExecutor {
    private final PlayerPasswords plugin;
    private final SettingsManager settings = SettingsManager.getInstance();
    private final String gold = settings.getConfig().getString("color1");
    private final String yellow = settings.getConfig().getString("color2");
    private final String gray = settings.getConfig().getString("color3");

    public Password(PlayerPasswords plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Exit if not a player.
        if(!(sender instanceof Player)) {
            ChatUtils.chat(sender, settings.getConfig().getString("MustBePlayer"));
            return true;
        }

        Player p = (Player) sender;
        String uuid = p.getUniqueId().toString();
        PasswordPlayer pl = plugin.getPlayerManager().get(p);

        // Player cannot use command if they aren't logged in.
        if(!pl.isVerified()) {
            ChatUtils.chat(p, settings.getConfig().getString("MustBeLoggedIn"));
            return true;
        }

        // Shows default page if no arguments are given.
        if(args.length == 0) {
            ChatUtils.chat(p, gold + "&l]" + gray + "&m--------------------" + gold + "&lPasswords" + gray + "&m--------------------" + gold +"&l[");
            ChatUtils.chat(p, "  " + gray + "» " + gold + "/password enable " + gray + "- " + yellow + "Enables Your Password");
            ChatUtils.chat(p, "  " + gray + "» " + gold + "/password disable " + gray + "- " + yellow + "Disables Your Password");
            ChatUtils.chat(p, "  " + gray + "» " + gold + "/password set [password] " + gray + "- " + yellow + "Changes Your Password");
            ChatUtils.chat(p, gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l[");
            return true;
        }


        switch(args[0].toLowerCase()) {
            default:
                ChatUtils.chat(p, gold + "&l]" + gray + "&m--------------------" + gold + "&lPasswords" + gray + "&m--------------------" + gold +"&l[");
                ChatUtils.chat(p, "  " + gray + "» " + gold + "/password enable " + gray + "- " + yellow + "Enables Your Password");
                ChatUtils.chat(p, "  " + gray + "» " + gold + "/password disable " + gray + "- " + yellow + "Disables Your Password");
                ChatUtils.chat(p, "  " + gray + "» " + gold + "/password set [password] " + gray + "- " + yellow + "Changes Your Password");
                ChatUtils.chat(p, gold + "&l]" + gray +"&m---------------------------------------------------" + gold + "&l[");
                break;

            case "enable":
                if(!sender.hasPermission("playerpasswords.enable")) {
                    ChatUtils.chat(sender, settings.getConfig().getString("NoPermission"));
                    return true;
                }

                if(settings.getData().getString("passwords." + uuid + ".password").length() == 0) {
                    ChatUtils.chat(p, settings.getConfig().getString("MustSetPassword"));
                    return true;
                }

                pl.setEnabled(true);
                ChatUtils.chat(p, settings.getConfig().getString("PasswordEnabled"));
                break;

            case "disable":
                if(!sender.hasPermission("playerpasswords.disable")) {
                    ChatUtils.chat(p, settings.getConfig().getString("NoPermission"));
                    return true;
                }
                if(settings.getConfig().getBoolean("Optional")) {
                    pl.setEnabled(false);
                    ChatUtils.chat(p, settings.getConfig().getString("PasswordDisabled"));
                }
                else {
                    ChatUtils.chat(p, settings.getConfig().getString("OptionalPasswordDisabled"));
                }
                break;

            case "set":
                if(!sender.hasPermission("playerpasswords.set")) {
                    ChatUtils.chat(p, settings.getConfig().getString("NoPermission"));
                    return true;
                }
                if(args.length > 1) {
                    pl.setPassword(EncryptionUtils.getSha256(args[1]));
                    ChatUtils.chat(p, settings.getConfig().getString("PasswordSet").replace("%password%", args[1]));
                }
                else {
                    ChatUtils.chat(p, settings.getConfig().getString("PasswordSetUsage"));
                }
                break;
        }


        return true;
    }
}
