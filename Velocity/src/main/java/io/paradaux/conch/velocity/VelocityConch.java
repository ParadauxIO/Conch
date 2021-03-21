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

package io.paradaux.conch.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import io.paradaux.conch.common.api.DiscordManager;
import io.paradaux.conch.common.api.I18NLogger;
import io.paradaux.conch.common.api.I18NManager;
import io.paradaux.conch.common.api.config.CachedBotSettings;
import io.paradaux.conch.common.api.config.CachedProxySettings;
import io.paradaux.conch.common.api.config.CachedServerSettings;
import io.paradaux.conch.common.api.config.ConfigurationUtil;
import io.paradaux.conch.common.bot.DiscordBot;
import io.paradaux.conch.velocity.api.VelocityConfigurationManager;
import io.paradaux.conch.velocity.listeners.PlayerChatEventListener;
import io.paradaux.conch.velocity.managers.TaskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Plugin(id = "conch", name = "Conch", version = "1.0.0",
        url = "https://conch.rocks", description = "Discord to chat Bridge", authors = {"Paradaux"})
public class VelocityConch {

    private final ProxyServer server;
    private final Logger logger;
    private final Path pluginData;
    private final TaskManager tasks;
    private DiscordManager discord;
    private DiscordBot discordBot;

    @Inject
    public VelocityConch(ProxyServer server, Logger logger, @DataDirectory Path pluginData) {
        this.server = server;
        this.logger = logger;
        this.pluginData = pluginData;

        logger.info("Conch: Initialising locale.");
        loadLocale(logger);

        I18NLogger.rawInfo("Initialising Scheduler");
        this.tasks = new TaskManager(this, server.getScheduler());

    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {

        I18NLogger.rawInfo("Loading Configuration");
        loadConfiguration();

        I18NLogger.rawInfo("Connecting to discord");
        connect();

        I18NLogger.rawInfo("Registering events");
        registerEvents();

        I18NLogger.rawInfo("Starting the discord bot");
    }

    public void loadLocale(Logger logger) {
        Locale locale = new Locale("en_US");

        I18NManager i18NManager = new I18NManager(ResourceBundle.getBundle(I18NManager.RESOURCES_PATH, locale));

        I18NLogger.setLogger(logger);
        I18NLogger.setI18nManager(i18NManager);

        I18NLogger.info("startup.loading-message");
    }

    public void loadConfiguration() {
        VelocityConfigurationManager configurationManager = new VelocityConfigurationManager(pluginData);

        configurationManager.deployResources();
        configurationManager.loadConfigurationFiles();

    }


    public void connect() {
        CachedProxySettings config = ConfigurationUtil.getProxySettings();
        try {
            discord = new DiscordManager(config.getWebhookUrl(), true);
        } catch(IllegalArgumentException exception) {
            I18NLogger.rawInfo("You have not configured a webhook"); // TODO locale
        }
    }

    public void registerEvents() {
        String avatarApiUrl = "https://mc-heads.net/combo/%playeruuid%";
        String userNameFormat = "%playername%";
        String serverName = "Test Server";
        String messagePrefix = null;

        server.getEventManager().register(this, new PlayerChatEventListener(logger));
    }

    public void startDiscordBot() {
        CachedBotSettings config = ConfigurationUtil.getBotSettings();

//        try {
////            discordBot = new DiscordBot();
////            discordBot.connect();
////            discordBot.addNewListener(new DiscordMessageListener(tasks, config));
//        } catch (LoginException ok) {
//            // TODO log
//        }

    }
}
