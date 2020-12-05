/*
 * Copyright (c) 2020, Rían Errity. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Rían Errity <rian@paradaux.io> or visit https://paradaux.io
 * if you need additional information or have any questions.
 * See LICENSE.md for more details.
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
    static File configurationFile = new File(Objects.requireNonNull(Bukkit.getServer()
            .getPluginManager().getPlugin("HiberniaDiscord")).getDataFolder(), "config.yml");

    // Static reference to the location of the locale file.
    static File localeFile = new File(Objects.requireNonNull(Bukkit.getServer()
            .getPluginManager().getPlugin("HiberniaDiscord")).getDataFolder(), "locale.yml");

    // Static reference to the location of the discord2mc file.
    static File discord2mcFile = new File(Objects.requireNonNull(Bukkit.getServer()
            .getPluginManager().getPlugin("HiberniaDiscord")).getDataFolder(),
            "discord2mc.yml");

    // Returns whether or not the configuration file exists in the filesystem.
    public static boolean doesConfigurationExist() {
        return configurationFile.exists();
    }

    public static void backupConfig() {
        if (configurationFile.renameTo(new File(Objects.requireNonNull(Bukkit.getServer()
                .getPluginManager().getPlugin("HiberniaDiscord")).getDataFolder(),
                "config.yml.bak")) || configurationFile.delete()) {
            HiberniaDiscord.getMainLogger().warn("Error backing up configuration file.");
        }
    }

    public static void updateConfigurationFile(FileConfiguration config) {
        if (!doesConfigurationExist()) {
            deployNewConfig(HiberniaDiscord.getPlugin());
            return;
        }

        double configVersion = config.getDouble("config-version");

        if (configVersion == 2.3d) {
            config.set("config-version", "2.4");
            config.set("events.chat-message.message-prefix-disabled", "!");
            config.set("events.chat-message.message-prefix-disabled", true);
            saveChangedConfigurationFile(config);
            return;
        }

        if (configVersion == 2.4d) {
            return;
        }

        HiberniaDiscord.log("Your configuration is too old to convert. Generating new "
                + "configuration files. Please see config.yml.bak as this is your old "
                + "configuration file.");
        backupConfig();
        deployNewConfig(HiberniaDiscord.getPlugin());

    }

    private static void deployNewConfig(Plugin p) {
        try {
            p.saveDefaultConfig();
        } catch (NullPointerException exception) {
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

    private static void saveChangedConfigurationFile(FileConfiguration config) {
        try {
            config.save(configurationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static YamlConfiguration getDiscord2McConfigurationFile() {
        return YamlConfiguration.loadConfiguration(discord2mcFile);
    }
}
