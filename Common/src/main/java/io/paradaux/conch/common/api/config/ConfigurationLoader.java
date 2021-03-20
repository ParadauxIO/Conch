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

package io.paradaux.conch.common.api.config;

import io.leangen.geantyref.TypeToken;
import io.paradaux.conch.common.api.I18NLogger;
import io.paradaux.conch.common.api.exceptions.NoSuchEventListenerException;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import javax.annotation.CheckReturnValue;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationLoader {

    private static final String[] EVENT_LISTENERS = { "onChatMessage", "onPlayerJoin", "onPlayerLeave", "onPlayerDeath",
                                                      "onPlayerAchievementCompleted", "onPlayerKick", "onPlayerRespawn",
                                                      "onServerStartup", "onGracefulShutdown" };

    public static final String SETTINGS_FILE_NAME = "general-settings.conf";
    public static final String EVENT_SETTINGS_FILE_NAME = "event-settings.conf";
    public static final String BOT_SETTINGS_FILE_NAME = "bot-settings.conf";

    private final Path generalSettingsPath;
    private final Path eventSettingsPath;
    private final Path botSettingsPath;

    private final HoconConfigurationLoader generalSettingsLoader;
    private final HoconConfigurationLoader eventSettingsLoader;
    private final HoconConfigurationLoader botSettingsLoader;

    public ConfigurationLoader(Path configurationDirectory) {
        this(configurationDirectory.toString());
    }

    public ConfigurationLoader(String configurationDirectory) {
        generalSettingsPath = Paths.get(configurationDirectory, SETTINGS_FILE_NAME);
        eventSettingsPath = Paths.get(configurationDirectory, EVENT_SETTINGS_FILE_NAME);
        botSettingsPath = Paths.get(configurationDirectory, BOT_SETTINGS_FILE_NAME);

        generalSettingsLoader = HoconConfigurationLoader.builder()
                .path(generalSettingsPath)
                .build();

        eventSettingsLoader = HoconConfigurationLoader.builder()
                .path(eventSettingsPath)
                .build();

        botSettingsLoader = HoconConfigurationLoader.builder()
                .path(botSettingsPath)
                .build();
    }

    @CheckReturnValue
    @NotNull
    public CachedSettings loadGeneralSettings() throws ConfigurateException {
        CachedSettings.Builder builder = CachedSettings.builder();
        ConfigurationNode root = generalSettingsLoader.load();

        TypeToken<Map<String, String>> type = new TypeToken<Map<String, String>>() {};


        builder.setConfigurationVersion(root.node("configuration-version").getString())
                .setDebug(root.node("debug").getBoolean())
                .setLocaleCode(root.node("locale").getString())
                .setWebhookUrl(root.node("webhook-configuration").node("webhook-url").getString())
                .setEventsEnabled(root.node("webhook-configuration").node("events").getBoolean())
                .setServerName(root.node("placeholders").node("server-name").getString())
                .setAvatarApi(root.node("placeholders").node("avatar-api").getString())
                .setProxyBasedWebhookConfiguration(root.node("proxy-based-webhook-configuration").getBoolean())
                .setProxyWebhookConfiguration(root.node("proxy-webhook-configuration").get(type));

        return builder.build();
    }

    @CheckReturnValue
    @NotNull
    public CachedEventSettings loadEventSettings() throws ConfigurateException {

        CachedEventSettings.Builder builder = CachedEventSettings.builder();
        ConfigurationNode root = eventSettingsLoader.load();

        for (String eventListenerStr : EVENT_LISTENERS) {
            EventConfiguration.Builder eventBuilder = EventConfiguration.builder();
            ConfigurationNode eventListenerNode = root.node(eventListenerStr);

            eventBuilder.setEventName(eventListenerStr)
                        .setEnabled(eventListenerNode.node("enabled").getBoolean())
                        .setWebhookUsernameFormat(eventListenerNode.node("webhook-username-format").getString())
                        .setWebhookAvatarFormat(eventListenerNode.node("webhook-avatar-format").getString())
                        .setWebhookMessageFormat(eventListenerNode.node("webhook-message-format").getString());
            
            try {
                builder.set(eventBuilder.build());
            } catch (NoSuchEventListenerException ok) {
                I18NLogger.error("configuration.invalid-event", eventListenerStr);
            }
        }

        return builder.build();
    }

    // TODO finish
    @CheckReturnValue
    @NotNull
    public  CachedBotSettings loadBotSettings() throws ConfigurateException {
        ConfigurationNode root = eventSettingsLoader.load();

        TypeToken<Map<String, String>> type = new TypeToken<Map<String, String>>() {};
        Map<String, String> mapResult = root.node("proxy-monitored-channels").get(type);

        if (mapResult instanceof HashMap) {
            HashMap proxyMonitoredChannels = (HashMap) mapResult;
        }

        return CachedBotSettings.builder()
                .setEnabled(root.node("").getBoolean())
                .setToken(root.node("").getString())
                .setDiscordCommandsEnabled(root.node("").getBoolean())
                .setCommandPrefix(root.node("").getString())
                .setSendBotMessages(root.node("").getBoolean())
                .setMonitoredChannels(root.node("").getList(String.class))
                .setProxyBasedMonitoring(root.node("").getBoolean())
                .build();
    }

    @CheckReturnValue
    public boolean doesEventSettingsExist() {
        return Files.exists(eventSettingsPath);
    }

    @CheckReturnValue
    public boolean doesBotSettingsExist() {
        return Files.exists(eventSettingsPath);
    }

    @CheckReturnValue
    public boolean doesGeneralSettingsExist() {
        return Files.exists(eventSettingsPath);
    }

    @CheckReturnValue
    public Path getGeneralSettingsPath() {
        return generalSettingsPath;
    }

    @CheckReturnValue
    public Path getEventSettingsPath() {
        return eventSettingsPath;
    }

    @CheckReturnValue
    public Path getBotSettingsPath() {
        return botSettingsPath;
    }
}
