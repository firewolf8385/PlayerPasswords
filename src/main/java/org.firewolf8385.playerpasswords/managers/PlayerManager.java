package org.firewolf8385.playerpasswords.managers;

import org.bukkit.entity.Player;
import org.firewolf8385.playerpasswords.objects.PasswordPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Manages PasswordPlayer objects.
 */
public class PlayerManager {
    private Map<UUID, PasswordPlayer> players = new HashMap<>();

    /**
     * Creates a new instance of the manager.
     */
    public PlayerManager() {}

    /**
     * Adds a player to the manager.
     * @param uuid UUID of player to be added.
     */
    public void add(UUID uuid) {
        players.put(uuid, new PasswordPlayer(uuid));
    }

    /**
     * Adds a player to the manager.
     * @param p Player to be added.
     */
    public void add(Player p) {
        add(p.getUniqueId());
    }

    /**
     * Checks if the manager contains a player.
     * @param uuid UUID of player to check.
     * @return Whether or not the manager contains the player.
     */
    public boolean contains(UUID uuid) {
        return players.containsKey(uuid);
    }

    /**
     * Checks if the manager contains a player.
     * @param p Player to check.
     * @return Whether or not the manager contains the player.
     */
    public boolean contains(Player p) {
        return  contains(p.getUniqueId());
    }

    /**
     * Get a player from the manager.
     * @param uuid UUID of the player to get.
     * @return The PasswordPlayer object,
     */
    public PasswordPlayer get(UUID uuid) {
        return  players.get(uuid);
    }

    /**
     * Get a player from the manager.
     * @param p Player to get.
     * @return The PasswordPlayer object,
     */
    public PasswordPlayer get(Player p) {
        return get(p.getUniqueId());
    }

    /**
     * Remove a player from the manager.
     * @param uuid UUID of player to remove.
     */
    public void remove(UUID uuid) {
        players.remove(uuid);
    }

    /**
     * Remove a player from the manager.
     * @param p Player to remove.
     */
    public void remove(Player p) {
        remove(p.getUniqueId());
    }
}