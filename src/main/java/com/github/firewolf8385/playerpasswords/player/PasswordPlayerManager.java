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
package com.github.firewolf8385.playerpasswords.player;

import com.github.firewolf8385.playerpasswords.PlayerPasswordsPlugin;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PasswordPlayerManager {
    private final PlayerPasswordsPlugin plugin;
    private final Map<Player, PasswordPlayer> players = new HashMap<>();

    public PasswordPlayerManager(PlayerPasswordsPlugin plugin) {
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
