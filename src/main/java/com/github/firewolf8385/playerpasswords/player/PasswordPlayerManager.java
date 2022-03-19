package com.github.firewolf8385.playerpasswords.player;

import com.github.firewolf8385.playerpasswords.PlayerPasswords;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PasswordPlayerManager {
    private final PlayerPasswords plugin;
    private final Map<Player, PasswordPlayer> players = new HashMap<>();

    public PasswordPlayerManager(PlayerPasswords plugin) {
        this.plugin = plugin;
    }

    public void addPlayer(Player player) {
        players.put(player, new PasswordPlayer(player));
    }

    public PasswordPlayer getPlayer(Player player) {
        return players.get(player);
    }

    public Map<Player, PasswordPlayer> getPlayers() {
        return players;
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }
}
