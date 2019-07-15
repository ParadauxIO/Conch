package co.paradaux.hdiscord.utils;

import org.bukkit.ChatColor;

public class LogUtil {
    private LogUtil() {}

    public static String getHeading() { return ChatColor.YELLOW + "[" + ChatColor.AQUA + "HiberniaDiscord" + ChatColor.YELLOW + "] " + ChatColor.RESET; }
}
