/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.hiberniadiscord.api;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;

public class PlaceholderAPIWrapper {

    public PlaceholderAPIWrapper() {}
    public void cancel() {}

    public boolean isPresent() {
        return getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    public String withPlaceholders(Player player, String input) { return PlaceholderAPI.setPlaceholders(player, input); }
    public String withPlaceholders(OfflinePlayer player, String input) { return PlaceholderAPI.setPlaceholders(player, input); }
}
