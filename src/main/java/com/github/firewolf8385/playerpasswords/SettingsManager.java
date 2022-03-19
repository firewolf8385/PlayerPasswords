package com.github.firewolf8385.playerpasswords;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import java.io.File;
import java.io.IOException;

public class SettingsManager
{

    private SettingsManager() {}
    static SettingsManager instance = new SettingsManager();

    /**
     * This allows us to access an instance of this class.
     */
    public static SettingsManager getInstance()
    {
        return instance;
    }

    Plugin pl;
    FileConfiguration config;
    File configFile;

    FileConfiguration data;
    File dataFile;


    /**
     * This allows us to set up the config file if it does not exist.
     * @param pl Instance of the Plugin
     */
    public void setup(Plugin pl)
    {
        config = pl.getConfig();
        config.options().copyDefaults(true);
        configFile = new File(pl.getDataFolder(), "config.yml");
        pl.saveDefaultConfig();

        dataFile = new File(pl.getDataFolder(), "data.yml");
        data = YamlConfiguration.loadConfiguration(dataFile);

        if(!dataFile.exists())
        {
            try
            {
                dataFile.createNewFile();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Allows us to access the config file.
     * @return config file
     */
    public FileConfiguration getConfig()
    {
        return config;
    }

    /**
     * Allows us to save the config file after changes are made.
     */
    public void saveConfig()
    {
        try
        {
            config.save(configFile);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This updates the config in case changes are made.
     */
    public void reloadConfig()
    {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    /**
     * Allows us to access the data file.
     * @return data file
     */
    public FileConfiguration getData()
    {
        return data;
    }

    /**
     * Allows us to save the config file after changes are made.
     */
    public void saveData()
    {
        try
        {
            data.save(dataFile);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This updates the data in case changes are made.
     */
    public void reloadData()
    {
        data = YamlConfiguration.loadConfiguration(dataFile);
    }
}