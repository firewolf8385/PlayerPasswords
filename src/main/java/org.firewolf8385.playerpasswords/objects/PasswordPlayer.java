package org.firewolf8385.playerpasswords.objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.firewolf8385.playerpasswords.managers.SettingsManager;

import java.util.*;

public class PasswordPlayer {
    private final SettingsManager settings = SettingsManager.getInstance();

    private boolean required;
    private boolean verified;
    private UUID uuid;

    /**
     * Create a PasswordPlayer
     * @param uuid UUID of player.
     */
    public PasswordPlayer(UUID uuid) {
        this.uuid = uuid;

        boolean one = !settings.getConfig().getBoolean("Optional");
        boolean three = getPlayer().hasPermission("playerpasswords.required");

        required = one || isEnabled() || three;
        verified = !required || getPlayer().hasPermission("playerpasswords.bypass");
    }

    /**
     * Get the player's password.
     * @return Player's password.
     */
    public String getPassword() {
        return settings.getData().getString("passwords." + uuid + ".password");
    }

    /**
     * Get the player.
     * @return Player
     */
    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    /**
     * Get if the player has a password enabled.
     * @return Whether or not the player has a password enabled.
     */
    public boolean isEnabled() {
        return settings.getData().getBoolean("passwords." + uuid + ".enabled");
    }

    /**
     * Get if the player requires a password.
     * @return Whether or not a password is required.
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Get if the player is verified.
     * @return Whether or not it is verified.
     */
    public boolean isVerified() {
        return verified;
    }

    /**
     * Set if the player is verified.
     * @param verified Whether or not the player is verified.
     */
    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    /**
     * Change if the player's password is enabled.
     * @param enabled Whether or not the password is enabled.
     */
    public void setEnabled(boolean enabled) {
        settings.getData().set("passwords." + uuid + ".enabled", enabled);
        settings.saveData();
        settings.reloadData();
    }

    /**
     * Change a player's password.
     * @param password Password to set.
     */
    public void setPassword(String password) {
        settings.getData().set("passwords." + uuid + ".password", password);
        settings.saveData();
        settings.reloadData();
    }
}