package io.paradaux.hiberniadiscord.api;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlaceholderAPIWrapper {

    public PlaceholderAPIWrapper() {}
    public void cancel() {}

    public String withPlaceholders(Player player, String input) { return PlaceholderAPI.setPlaceholders(player, input); }
    public String withPlaceholders(OfflinePlayer player, String input) { return PlaceholderAPI.setPlaceholders(player, input); }
}
