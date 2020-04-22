package org.firewolf8385.playerpasswords.objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.firewolf8385.playerpasswords.SettingsManager;

import java.util.*;

public class PasswordPlayer
{
    public static HashMap<UUID, PasswordPlayer> players = new HashMap<>();
    SettingsManager settings = SettingsManager.getInstance();

    private boolean required;
    private boolean verified;
    private UUID uuid;

    /**
     * Create a PasswordPlayer
     * @param uuid UUID of player.
     */
    public PasswordPlayer(UUID uuid)
    {
        this.uuid = uuid;

        boolean one = !settings.getConfig().getBoolean("Optional");
        boolean two = settings.getData().getBoolean("passwords." + uuid + ".enabled");
        boolean three = getPlayer().hasPermission("playerpasswords.required");

        required = one || two || three;

        verified = !required;
        //verified = !one && ((!two && !three) || getPlayer().hasPermission("playerpasswords.bypass"));

        players.put(uuid, this);
    }

    /**
     * Get the player.
     * @return Player
     */
    public Player getPlayer()
    {
        return Bukkit.getPlayer(uuid);
    }

    /**
     * Get a list of all players.
     * @return Players
     */
    public static Map<UUID, PasswordPlayer> getPlayers()
    {
        return players;
    }

    /**
     * Get if the player requires a password.
     * @return Whether or not a password is required.
     */
    public boolean isRequired()
    {
        return required;
    }

    /**
     * Get if the player is verified.
     * @return Whether or not it is verified.
     */
    public boolean isVerified()
    {
        return verified;
    }

    /**
     * Set if the player is verified.
     * @param verified Whether or not the player is verified.
     */
    public void setVerified(boolean verified)
    {
        this.verified = verified;
    }
}