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

public class ConfigurationLoader {

    private static final String[] EVENT_LISTENERS = { "onChatMessage", "onPlayerJoin", "onPlayerLeave", "onPlayerDeath",
                                                      "onPlayerAchievementCompleted", "onPlayerKick", "onPlayerRespawn",
                                                      "onServerStartup", "onGracefulShutdown" };

    public static final String SETTINGS_FILE_NAME = "general-settings.conf";
    public static final String EVENT_SETTINGS_FILE_NAME = "event-settings.conf";
    public static final String BOT_SETTINGS_FILE_NAME = "bot-settings.conf";
    public static final String PROXY_SETTINGS_FILE_NAME = "proxy-settings.conf";

    private static final TypeToken<HashMap<String, String>> stringMap = new TypeToken<HashMap<String, String>>() {};

    private final Path generalSettingsPath;
    private final Path eventSettingsPath;
    private final Path botSettingsPath;
    private final Path proxySettingsPath;

    private final HoconConfigurationLoader generalSettingsLoader;
    private final HoconConfigurationLoader eventSettingsLoader;
    private final HoconConfigurationLoader botSettingsLoader;
    private final HoconConfigurationLoader proxySettingsLoader;

    public ConfigurationLoader(Path configurationDirectory) {
        this(configurationDirectory.toString());
    }

    public ConfigurationLoader(String configurationDirectory) {
        generalSettingsPath = Paths.get(configurationDirectory, SETTINGS_FILE_NAME);
        eventSettingsPath = Paths.get(configurationDirectory, EVENT_SETTINGS_FILE_NAME);
        botSettingsPath = Paths.get(configurationDirectory, BOT_SETTINGS_FILE_NAME);
        proxySettingsPath = Paths.get(configurationDirectory, PROXY_SETTINGS_FILE_NAME);

        generalSettingsLoader = HoconConfigurationLoader.builder()
                .path(generalSettingsPath)
                .build();

        eventSettingsLoader = HoconConfigurationLoader.builder()
                .path(eventSettingsPath)
                .build();

        botSettingsLoader = HoconConfigurationLoader.builder()
                .path(botSettingsPath)
                .build();

        proxySettingsLoader = HoconConfigurationLoader.builder()
                .path(proxySettingsPath)
                .build();
    }

    @CheckReturnValue
    @NotNull
    public CachedServerSettings loadGeneralSettings() throws ConfigurateException {
        CachedServerSettings.Builder builder = CachedServerSettings.builder();
        ConfigurationNode root = generalSettingsLoader.load();

        builder.setConfigurationVersion(root.node("configuration-version").getString())
                .setDebug(root.node("debug").getBoolean())
                .setLocaleCode(root.node("locale").getString())
                .setWebhookUrl(root.node("webhook-configuration").node("webhook-url").getString())
                .setEventsEnabled(root.node("webhook-configuration").node("events").getBoolean())
                .setServerName(root.node("placeholders").node("server-name").getString())
                .setAvatarApiHyphen(root.node("placeholders").node("include-hyphens-in-uuid").getBoolean())
                .setAvatarApi(root.node("placeholders").node("avatar-api").getString())
                .setProxyBasedWebhookConfiguration(root.node("proxy-based-webhook-configuration").getBoolean())
                .setProxyWebhookConfiguration(root.node("proxy-webhook-configuration").get(stringMap));

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

    @CheckReturnValue
    @NotNull
    public CachedBotSettings loadBotSettings() throws ConfigurateException {
        ConfigurationNode root = botSettingsLoader.load();

        return CachedBotSettings.builder()
                .setEnabled(root.node("enabled").getBoolean())
                .setToken(root.node("token").getString())
                .setDiscordCommandsEnabled(root.node("discord-commands-enabled").getBoolean())
                .setCommandPrefix(root.node("command-prefix").getString())
                .setSendBotMessages(root.node("send-messages-from-bots").getBoolean())
                .setMonitoredChannels(root.node("monitored-channels").getList(String.class))
                .setProxyBasedMonitoring(root.node("proxy-based-monitoring").getBoolean())
                .setProxyMonitoredChannels(root.node("proxy-monitored-channels").get(stringMap))
                .setMessageFormat(root.node("message-format").getString())
                .build();
    }

    public CachedProxySettings loadProxySettings() throws ConfigurateException {
        ConfigurationNode root = proxySettingsLoader.load();

        return CachedProxySettings.builder()
                .setConfigurationVersion(root.node("configuration-version").getString())
                .setDebug(root.node("debug").getBoolean())
                .setLocaleCode(root.node("locale").getString())
                .setWebhookUrl(root.node("webhook-configuration").node("webhook-url").getString())
                .setEventsEnabled(root.node("webhook-configuration").node("events").getBoolean())
                .setNetworkName(root.node("placeholders").node("network-name").getString())
                .setAvatarApiHyphen(root.node("placeholders").node("include-hyphens-in-uuid").getBoolean())
                .setAvatarApi(root.node("placeholders").node("avatar-api").getString())
                .setProxyBasedWebhookConfiguration(root.node("per-server-webhook-configuration").getBoolean())
                .setProxyWebhookConfiguration(root.node("proxy-webhook-configuration").get(stringMap))
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
    public boolean doesProxySettingsExist() {
        return Files.exists(proxySettingsPath);
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

    @CheckReturnValue
    public Path getProxySettingsPath() {
        return proxySettingsPath;
    }
}
