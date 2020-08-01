package io.paradaux.hiberniadiscord.api;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class EventUtils {

    public static String getColourlessName(Player p) {
        return ChatColor.stripColor(p.getDisplayName());
    }

    public static String removeColor(String s) {
        return ChatColor.stripColor(s);
    }

    public static String sanistiseMessage(String str) {
        if (str.equals("")) {
            // Zero Width space in double quotes.
            return "\u200B";
        }

        return str.replace("@everyone", "")
                .replace("@here", "@");
    }

    public static String parseAvatarUrl(Player player, String str) {
        return str.replace("%playeruuid%", player.getUniqueId().toString());
    }

    public static String parseAvatarUrl(OfflinePlayer player, String str) {
        return str.replace("%playeruuid%", player.getUniqueId().toString());
    }

    public static String parsePlaceholders(ConfigurationCache config, Player player, String str) {
        return str.replace("%playername%", player.getName())
                .replace("%playeruuid%", player.getUniqueId().toString())
                .replace("%servername%", config.getServerName())
                .replace("%avatarapi%", parseAvatarUrl(player, config.getAvatarAPI()));
    }

    public static String parsePlaceholders(ConfigurationCache config, OfflinePlayer player, String str) {
        return str.replace("%playername%", player.getName())
                .replace("%playeruuid%", player.getUniqueId().toString())
                .replace("%servername%", config.getServerName())
                .replace("%avatarapi%", parseAvatarUrl(player, config.getAvatarAPI()));
    }

    public static String parsePlaceholders(ConfigurationCache config, String str) {
        return str.replace("%servername%", config.getServerName());
    }


}
