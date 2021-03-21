/*
 * Copyright (c) 2021, Rían Errity. All rights reserved.
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

package io.paradaux.conch.bukkit;

import io.paradaux.conch.bukkit.api.BukkitAPI;
import io.paradaux.conch.bukkit.api.BukkitConfigurationManager;
import io.paradaux.conch.bukkit.bot.DiscordMessageListener;
import io.paradaux.conch.bukkit.listeners.AsyncPlayerChatEventListener;
import io.paradaux.conch.bukkit.managers.MetricsManager;
import io.paradaux.conch.bukkit.managers.TaskManager;
import io.paradaux.conch.bukkit.managers.UpdateManager;
import io.paradaux.conch.common.api.DiscordManager;
import io.paradaux.conch.common.api.I18NLogger;
import io.paradaux.conch.common.api.I18NManager;
import io.paradaux.conch.common.api.config.CachedBotSettings;
import io.paradaux.conch.common.api.config.CachedEventSettings;
import io.paradaux.conch.common.api.config.CachedServerSettings;
import io.paradaux.conch.common.api.config.ConfigurationUtil;
import io.paradaux.conch.common.bot.DiscordBot;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.ResourceBundle;

public class BukkitConch extends JavaPlugin {

    private static BukkitAPI API;

    private MetricsManager metrics;
    private TaskManager tasks;
    private UpdateManager updates;
    private DiscordManager discord;
    private DiscordBot discordBot;

    @Override
    public void onEnable() {
        getLogger().info("Conch: Initialising locale.");
        loadLocale();

        I18NLogger.rawInfo("Initialising Taskchain");
        tasks = new TaskManager(this);

        I18NLogger.rawInfo("Loading Configuration");
        loadConfiguration();

        I18NLogger.rawInfo("Connecting to discord");
        connect();

        I18NLogger.rawInfo("Registering events");
        registerEvents();

        I18NLogger.rawInfo("Registering the API");
        registerAPI();

        I18NLogger.rawInfo("Starting the discord bot");
        startDiscordBot();
    }

    @Override
    public void onDisable() {
        I18NLogger.info("shutdown.shutdown-message");
    }

    public void loadLocale() {
        // TODO make additional locales available.
        Locale locale = new Locale("en_US");

        I18NManager i18NManager = new I18NManager(ResourceBundle.getBundle(I18NManager.RESOURCES_PATH, locale));

        I18NLogger.setLogger(LoggerFactory.getLogger("io.paradaux.conch"));
        I18NLogger.setI18nManager(i18NManager);

        I18NLogger.info("startup.loading-message");
    }

    public void loadConfiguration() {
        Path pluginData = this.getDataFolder().toPath();
        BukkitConfigurationManager configurationManager = new BukkitConfigurationManager(pluginData);

        configurationManager.deployResources();
        configurationManager.loadConfigurationFiles();
    }

    public void connect() {
        CachedServerSettings config = ConfigurationUtil.getGeneralSettings();
        discord = new DiscordManager(config.getWebhookUrl(), true);
    }

    public void registerEvents() {
        PluginManager pm = this.getServer().getPluginManager();
        CachedEventSettings eventSettings = ConfigurationUtil.getEventSettings();

        String serverName = ConfigurationUtil.getGeneralSettings().getServerName();
        String messagePrefix = null;

        boolean debug = ConfigurationUtil.isDebug();
        pm.registerEvents(new AsyncPlayerChatEventListener(discord, debug, serverName, messagePrefix, eventSettings.getOnChatMessage()), this);
    }

    public void startDiscordBot() {
        CachedBotSettings config = ConfigurationUtil.getBotSettings();

        try {
            discordBot = new DiscordBot();
            discordBot.connect();
            discordBot.addNewListener(new DiscordMessageListener(tasks, config));
        } catch (LoginException ok) {
            // TODO log
        }

    }

    public void registerAPI() {
        API = new BukkitAPI();
    }
}
