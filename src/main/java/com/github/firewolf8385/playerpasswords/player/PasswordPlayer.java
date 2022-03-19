package com.github.firewolf8385.playerpasswords.player;

import com.github.firewolf8385.playerpasswords.SettingsManager;
import org.bukkit.entity.Player;

public class PasswordPlayer {
    SettingsManager settings = SettingsManager.getInstance();

    private final boolean required;
    private boolean verified;
    private final Player player;

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
     * Get the player.
     * @return Player
     */
    public Player getPlayer() {
        return player;
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
}