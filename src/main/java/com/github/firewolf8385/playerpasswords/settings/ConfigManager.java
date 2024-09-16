/*
 * This file is part of PlayerPasswords, licensed under the MIT License.
 *
 *  Copyright (c) JadedMC
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package com.github.firewolf8385.playerpasswords.settings;

import com.github.firewolf8385.playerpasswords.PlayerPasswordsPlugin;
import com.github.firewolf8385.playerpasswords.utils.Tuple;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

/**
 * Manages plugin configuration and data.
 */
public class ConfigManager {
    private final PlayerPasswordsPlugin plugin;
    private FileConfiguration config;
    private final File configFile;
    private FileConfiguration data;
    private final File dataFile;
    private FileConfiguration messages;
    private final File messagesFile;

    /**
     * Sets up and loads the plugin configuration.
     * @param plugin Instance of the plugin.
     */
    public ConfigManager(@NotNull final PlayerPasswordsPlugin plugin) {
        this.plugin = plugin;

        // Loads config.yml
        this.config = plugin.getConfig();
        this.config.options().copyDefaults(true);
        this.configFile = new File(plugin.getDataFolder(), "config.yml");
        plugin.saveConfig();

        // Loads messages.yml
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        if(!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        messages = YamlConfiguration.loadConfiguration(messagesFile);

        // Loads data.yml.
        dataFile = new File(plugin.getDataFolder(), "data.yml");
        data = YamlConfiguration.loadConfiguration(dataFile);

        // If data.yml doesn't exist, creates it.
        if(!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            }
            catch(IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Get the main configuration file.
     * @return Main configuration file.
     */
    public FileConfiguration getConfig() {
        return config;
    }

    /**
     * Allows us to access the data file.
     * @return data file
     */
    public FileConfiguration getData() {
        return data;
    }

    /**
     * Gets a configurable message from the config.
     * @param configMessage Targeted Configurable Message.
     * @return Configured String of the message.
     */
    public String getMessage(final ConfigMessage configMessage) {
        // Loads the default config message.
        String message = configMessage.getDefaultMessage();

        // If the message is configured, use that one instead.
        if(messages.isSet(configMessage.getKey())) {
            message = messages.getString(configMessage.getKey());
        }

        // Replace newline characters from YAML with MiniMessage newline.
        message = message.replace("\\n", "<newline>");

        return message;
    }

    /**
     * Gets a configurable message from the config with Placeholder support.
     * @param player Player to process placeholders for.
     * @param configMessage Targeted Configurable message.
     * @param placeholders Placeholders to apply to the message.
     * @return Configured String of the message, with placeholders.
     */
    @SafeVarargs
    public final String getMessage(@NotNull final Player player, final ConfigMessage configMessage, final Tuple<String, String>... placeholders) {
        String message = getMessage(configMessage);

        // Assigned placeholders.
        for(final Tuple<String, String> placeholder : placeholders) {
            message = message.replace(placeholder.getLeft(), placeholder.getRight());
        }

        // Player username placeholder.
        message = message.replace("%player_name%", player.getName());

        // Process placeholders if PlaceholderAPI is installed.
        if(plugin.getHookManager().usePlaceholderAPI()) {
            return PlaceholderAPI.setPlaceholders(player, message);
        }

        return message;
    }

    /**
     * This updates the config in case changes are made.
     */
    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    /**
     * This updates the data in case changes are made.
     */
    public void reloadData() {
        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    public void reloadMessages() {
        messages = YamlConfiguration.loadConfiguration(messagesFile);
    }

    /**
     * Allows us to save the config file after changes are made.
     */
    public void saveData() {
        try {
            data.save(dataFile);
        }
        catch(IOException exception) {
            exception.printStackTrace();
        }
    }
}