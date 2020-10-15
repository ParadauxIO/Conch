/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.hiberniadiscord.api;

import io.paradaux.hiberniadiscord.HiberniaDiscord;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ConfigurationUtils {

    // Static reference to the location of the configuration file.
    static File configurationFile = new File(Bukkit.getServer().getPluginManager().getPlugin("HiberniaDiscord").getDataFolder(), "config.yml");

    // Static reference to the location of the locale file.
    static File localeFile = new File(Bukkit.getServer().getPluginManager().getPlugin("HiberniaDiscord").getDataFolder(), "locale.yml");

    // Static reference to the location of the discord2mc file.
    static File discord2mcFile = new File(Bukkit.getServer().getPluginManager().getPlugin("HiberniaDiscord").getDataFolder(), "discord2mc.yml");

    // Returns whether or not the configuration file exists in the filesystem.
    public static boolean doesConfigurationExist() {
        return configurationFile.exists();
    }

    public static boolean outdatedCheck(FileConfiguration config) {
        return config.getDouble("config-version") != 2.4d;
    }

    public static void backupConfig(FileConfiguration config) {
        configurationFile.renameTo(new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("HiberniaDiscord")).getDataFolder(), "config.yml.bak"));
        configurationFile.delete();
    }

    public static void updateConfigurationFile(FileConfiguration config) {
        if (!doesConfigurationExist()) {
            deployNewConfig(HiberniaDiscord.getPlugin());
            return;
        };

        double configVersion = config.getDouble("config-version");

        if (configVersion == 2.3d) {
            config.set("config-version", "2.4");
            config.set("events.chat-message.message-prefix-disabled", "!");
            config.set("events.chat-message.message-prefix-disabled", true);
            saveChangedConfigurationFile(config);
            return;
        }

        if (configVersion == 2.4d) { return; }

        HiberniaDiscord.log("Your configuration is too old to convert. Generating new configuration files. Please see config.yml.bak as this is your old configuration file.");
        backupConfig(config);
        deployNewConfig(HiberniaDiscord.getPlugin());

    }

    public static void deployNewConfig(Plugin p) {
        try {
            p.saveDefaultConfig();
        } catch(Exception e) {
            HiberniaDiscord.log("Plugin is null.");
        }
    }

    public static void deployLocale(Plugin p) {
        p.saveResource("locale.yml", false);
    }

    public static void deployDiscord2McConfiguration(Plugin p) {
        p.saveResource("discord2mc.yml", false);
    }

    public static YamlConfiguration getLocale() {
        return YamlConfiguration.loadConfiguration(localeFile);
    }

    public static void saveChangedConfigurationFile(FileConfiguration config) {
        try {
            config.save(configurationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static YamlConfiguration getDiscord2McConfigurationFile() { return YamlConfiguration.loadConfiguration(discord2mcFile); }
}
