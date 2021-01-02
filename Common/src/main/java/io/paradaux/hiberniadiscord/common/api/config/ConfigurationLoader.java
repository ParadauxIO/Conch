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

package io.paradaux.hiberniadiscord.common.api.config;

import io.paradaux.hiberniadiscord.common.api.I18NLogger;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigurationLoader {

    private static final String[] EVENT_LISTENERS = { "onChatMessage", "onPlayerJoin", "onPlayerLeave", "onPlayerDeath",
                                                      "onPlayerAchievementCompleted", "onPlayerKick", "onPlayerRespawn",
                                                      "onServerStartup", "onGracefulShutdown" };

    private static final String SETTINGS_FILE_NAME = "general-settings.conf";
    private static final String EVENT_SETTINGS_FILE_NAME = "event-settings.conf";
    private static final String BOT_SETTINGS_FILE_NAME = "bot-settings.conf";

    private Path generalSettingsPath;
    private Path eventSettingsPath;
    private Path botSettingsPath;

    private HoconConfigurationLoader generalSettingsLoader;
    private HoconConfigurationLoader eventSettingsLoader;
    private HoconConfigurationLoader botSettingsLoader;

    public ConfigurationLoader(Path configurationDirectory) {
        this(configurationDirectory.toString());
    }

    public ConfigurationLoader(String configurationDirectory) {
        generalSettingsPath = Paths.get(configurationDirectory, SETTINGS_FILE_NAME);
        eventSettingsPath = Paths.get(configurationDirectory, EVENT_SETTINGS_FILE_NAME);
        botSettingsPath = Paths.get(configurationDirectory, BOT_SETTINGS_FILE_NAME);
    }

    public CachedSettings loadGeneralSettings() {
        return CachedSettings.builder().build();
    }

    public CachedEventSettings loadEventSettings() throws ConfigurateException {

        CachedEventSettings.Builder builder = CachedEventSettings.builder();
        ConfigurationNode root = eventSettingsLoader.load();

        for (String eventListenerStr : EVENT_LISTENERS) {
            ConfigurationNode eventListenerNode = root.node(eventListenerStr);

            EventConfiguration.Builder eventBuilder = EventConfiguration.builder();
            eventBuilder.setEventName(eventListenerStr)
                        .setEnabled(eventListenerNode.getBoolean())
                        .setWebhookUsernameFormat(eventListenerNode.getString())
                        .setWebhookAvatarFormat(eventListenerNode.getString())
                        .setWebhookMessageFormat(eventListenerNode.getString());
            try {
                builder.set(eventBuilder.build());
            } catch (CachedEventSettings.NoSuchEventListenerException ok) {
                I18NLogger.error("configuration.invalid-event", eventListenerStr);
            }
        }

        return builder.build();
    }

    public CachedBotSettings loadBotSettings() {
        return CachedBotSettings.builder().build();
    }





}
