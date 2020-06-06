package io.paradaux.hiberniadiscord.api;

import io.paradaux.hiberniadiscord.HiberniaDiscord;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Objects;
import java.util.logging.Level;

public class ConfigurationUtils {

    static File configurationFile = new File(Bukkit.getServer().getPluginManager().getPlugin("HiberniaDiscord").getDataFolder(), "config.yml");

    public static void checkIfOutOfDate(FileConfiguration config) {
        double configVersion = config.getDouble("config-version");

        if (configVersion <= 2.1d) {
            HiberniaDiscord.getMainLogger().log(Level.SEVERE, "old-version-config");
            backupOldConfig(config);
            deployNewConfig(HiberniaDiscord.getPlugin());
        } else if (configVersion == 2.2d) {
            return;
        }

        if (configurationFile.exists()) {
            backupOldConfig(config);
        }

        HiberniaDiscord.getMainLogger().log(Level.SEVERE, "config-not-exist");
        deployNewConfig(HiberniaDiscord.getPlugin());

    }

    public static void backupOldConfig(FileConfiguration config) {
        configurationFile.renameTo(new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("HiberniaDiscord")).getDataFolder(), "config.yml.bak"));
        configurationFile.delete();
    }

    public static void deployNewConfig(Plugin p) {
        p.saveResource("config.yml", true);
    }

    public static void reloadConfig() {}
}
