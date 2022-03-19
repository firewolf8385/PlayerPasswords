package com.github.firewolf8385.playerpasswords;

import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * This class checks if there is a new plugin version available.
 */
public class UpdateChecker {
    private final String currentVersion;
    public static String latestVersion;
    public static boolean update = false;

    /**
     * Start the update checker.
     * @param currentVersion Current plugin version.
     */
    public UpdateChecker(String currentVersion) {
        this.currentVersion = currentVersion;
        check.start();
    }

    /**
     * Get if there is an update available.
     * @return Whether or not an update is available.
     */
    public boolean updateAvailble() {
        return !currentVersion.equals(latestVersion);
    }

    /**
     * Check for a new version in a different thread.
     */
    private Thread check = new Thread() {
        @Override
        public void run() {
            URL url = null;
            try {
                url = new URL("https://api.spigotmc.org/legacy/update.php?resource=70520");
            }
            catch (MalformedURLException exception) {
                exception.printStackTrace();
            }

            URLConnection conn = null;
            try {
                conn = url.openConnection();
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                latestVersion = reader.readLine();
                if (!latestVersion.equals(currentVersion)) {
                    update = true;
                    Bukkit.getLogger().info("[PlayerPasswords] There is an update available.\nYour Version: " + currentVersion + "\nLatest Version: " + latestVersion);
                }
                else {
                    update = false;
                }
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    };

}
