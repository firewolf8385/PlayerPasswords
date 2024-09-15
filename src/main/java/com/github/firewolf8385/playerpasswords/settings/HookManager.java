package com.github.firewolf8385.playerpasswords.settings;

import org.bukkit.Bukkit;

/**
 * Keeps track of other plugins installed on the server that we may want to interface with.
 */
public class HookManager {

    /**
     * Get if the plugin should use PlaceholderAPI.
     * @return Whether the plugin should interface with PlaceholderAPI.
     */
    public boolean usePlaceholderAPI() {
        return Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");
    }
}