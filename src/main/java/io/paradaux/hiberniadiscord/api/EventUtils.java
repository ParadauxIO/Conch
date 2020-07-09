package io.paradaux.hiberniadiscord.api;

import io.paradaux.hiberniadiscord.HiberniaDiscord;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class EventUtils {

    public static String getColourlessName(Player p) {
        return ChatColor.stripColor(p.getDisplayName());
    }

    public static String removeColor(String s) {
        return ChatColor.stripColor(s);
    }

    public static String createAvatarUrl(UUID uuid) {
        String res = HiberniaDiscord.getConfigurationCache().getAvatarAPI().replace("%uuid%", uuid.toString());
        return res;
    }
}
