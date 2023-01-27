package com.github.firewolf8385.playerpasswords.player;

import com.github.firewolf8385.playerpasswords.settings.SettingsManager;
import org.bukkit.entity.Player;

public class PasswordPlayer {
    SettingsManager settings = SettingsManager.getInstance();

    private final boolean required;
    private boolean verified;
    private final Player player;
    private int loginAttempts = 0;

    /**
     * Create a PasswordPlayer
     * @param player Player object.
     */
    public PasswordPlayer(Player player) {
        this.player = player;

        boolean one = !settings.getConfig().getBoolean("Optional");
        boolean two = settings.getData().getBoolean("passwords." + player.getUniqueId() + ".enabled");
        boolean three = getPlayer().hasPermission("playerpasswords.required");

        required = one || two || three;

        verified = !required || getPlayer().hasPermission("playerpasswords.bypass");
    }

    /**
     * Adds a login attempt to the player.
     */
    public void addLoginAttempt() {
        loginAttempts++;
    }

    /**
     * Get the number of times the player has tried to log in.
     * @return Login attempts.
     */
    public int getLoginAttempts() {
        return loginAttempts;
    }

    /**
     * Get the player.
     * @return Player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get if a player has a password.
     * @return Whether the player has set a password.
     */
    public boolean hasPassword() {

        // If the player does not have any data, then they can't have a password.
        if(!settings.getData().isSet("passwords." + player.getUniqueId() + ".password")) {
            return false;
        }

        // If there is data, check if the data is empty.
        return !settings.getData().getString("passwords." + player.getUniqueId() + ".password").equals("");
    }

    /**
     * Get if the player requires a password.
     * @return Whether a password is required.
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Get if the player is verified.
     * @return Whether it is verified.
     */
    public boolean isVerified() {
        return verified;
    }

    /**
     * Set if the player is verified.
     * @param verified Whether the player is verified.
     */
    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}