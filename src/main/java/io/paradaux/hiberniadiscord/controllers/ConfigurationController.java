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

package io.paradaux.hiberniadiscord.controllers;

import io.paradaux.hiberniadiscord.HiberniaDiscord;
import io.paradaux.hiberniadiscord.models.BotConfiguration;
import io.paradaux.hiberniadiscord.models.PluginConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class ConfigurationController {

    private static PluginConfiguration pluginConfiguration;
    private static BotConfiguration botConfiguration;

    public static ConfigurationController INSTANCE;
    private static Logger logger;

    private static final File pluginConfigurationFile;
    private static final File botConfigurationFile;
    private static final File dataFolder;
    private static final File localeFile;

    private static YamlConfiguration botConfig;
    private static YamlConfiguration config;
    private static YamlConfiguration locale;

    static {
        dataFolder = HiberniaDiscord.getPlugin().getDataFolder();
        botConfigurationFile = new File(dataFolder, "discord2mc.yml");
        pluginConfigurationFile = new File(dataFolder, "config.yml");
        localeFile = new File("locale.yml");
    }

    /**
     * Initialise the Configuration Controller.
     * */
    public ConfigurationController() {
        logger = LogController.getLogger();

        if (!pluginConfigurationFile.exists()) {
            deployConfigurationFiles(true, false, false);
        }

        if (!botConfigurationFile.exists()) {
            deployConfigurationFiles(false, true, false);
        }

        if (!localeFile.exists()) {
            deployConfigurationFiles(false, false, true);
        }

        config = YamlConfiguration.loadConfiguration(pluginConfigurationFile);
        botConfig = YamlConfiguration.loadConfiguration(botConfigurationFile);
        locale = YamlConfiguration.loadConfiguration(localeFile);

        updateConfigurationFile(config);

        pluginConfiguration = new PluginConfiguration(config);
        INSTANCE = this;
    }

    /**
     * Copies the existing config.yml to config.yml in the event something goes wrong
     * while modifying it.
     * */
    public static void backupFile(File file) {
        File fileBackup = new File(dataFolder, file.getName() + ".backup");

        try {
            Files.copy(file.toPath(), fileBackup.toPath(), REPLACE_EXISTING);
        } catch (IOException ok) {
            logger.warn("There was an issue backing up your old configuration file.");
        }
    }

    /**
     * Returns the active instance of the configuration file.
     * */
    public static PluginConfiguration getPluginConfiguration() {
        return pluginConfiguration;
    }

    /**
     * Returns the active instance of the discord2mc configuration.
     * */
    public static BotConfiguration getBotConfiguration() {
        return botConfiguration;
    }

    /**
     * Takes the three configuration files and puts them into the data folder for the plugin.
     * @param c Deploy Plugin Configuration.
     * @param b Deploy Bot (discord2mc) Configuration.
     * @param l Deploy Locale Configuration.
     * */
    public static void deployConfigurationFiles(boolean c, boolean b, boolean l) {
        Plugin plugin = HiberniaDiscord.getPlugin();

        if (c) {
            plugin.saveResource("config.yml", false);
        }

        if (b) {
            plugin.saveResource("discord2mc.yml", false);
        }

        if (l) {
            plugin.saveResource("locale.yml", false);
        }
    }

    /**
     * Checks to see if the current configuration is up-to-date, if not
     * Add the changes in the latest version and create a backup of the old
     * copy of the configuration.
     * */
    public void updateConfigurationFile(FileConfiguration config) {
        if (!pluginConfigurationFile.exists()) {
            deployConfigurationFiles(true, false, false);
            return;
        }

        double configVersion = config.getDouble("config-version");

        if (configVersion == 2.3d) {
            config.set("config-version", "2.4");
            config.set("events.chat-message.message-prefix-disabled", "!");
            config.set("events.chat-message.message-prefix-disabled", true);
            saveChangedConfiguration(config, pluginConfigurationFile);
            return;
        }

        if (configVersion == 2.4d) {
            config.set("config-version", "2.5");
            // TODO: Rest of the configuration updates in 3.1
            return;
        }

        HiberniaDiscord.log("Your configuration is too old to convert. Generating new "
                + "configuration files. Please see config.yml.bak as this is your old "
                + "configuration file.");
        backupFile(pluginConfigurationFile);
        deployConfigurationFiles(true, false, false);
    }

    private static void saveChangedConfiguration(FileConfiguration config, File file) {
        try {
            config.save(file);
        } catch (IOException e) {
            logger.error("Error saving configuration file changes.");
        }
    }

    public static YamlConfiguration getConfig() {
        return config;
    }

    public static YamlConfiguration getBotConfig() {
        return botConfig;
    }

    public static YamlConfiguration getLocale() {
        return locale;
    }


}
