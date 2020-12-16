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

package io.paradaux.hiberniadiscord.models;

import org.bukkit.configuration.file.FileConfiguration;

public class PluginConfiguration {

    boolean discordCommand;
    String discordWebhookUrl;

    String serverName;
    String avatarApi;

    boolean chatMessageEnabled;
    String chatMessageUsernameFormat;
    String chatMessageMessageFormat;
    String chatMessageAvatarUrl;

    // Part of the new globalised-messages support in 3.0.1
    boolean messagePrefixDisabled;
    String messagePrefix;

    boolean playerJoinEnabled;
    String playerJoinUsernameFormat;
    String playerJoinMessageFormat;
    String playerJoinAvatarUrl;

    boolean playerLeaveEnabled;
    String playerLeaveUsernameFormat;
    String playerLeaveMessageFormat;
    String playerLeaveAvatarUrl;

    boolean advancementCompletedEnabled;
    String advancementCompletedUsernameFormat;
    String advancementCompletedMessageFormat;
    String advancementCompletedAvatarUrl;

    boolean serverStartupEnabled;
    String serverStartupUsernameFormat;
    String serverStartupMessageFormat;
    String serverStartupAvatarUrl;

    boolean serverShutdownEnabled;
    String serverShutdownUsernameFormat;
    String serverShutdownMessageFormat;
    String serverShutdownAvatarUrl;

    String discordCommandPermission;
    String reloadCommandPermission;

    boolean updateCheck;
    boolean updateNotify;
    double configVersion;

    public PluginConfiguration(FileConfiguration config) {
        this.discordCommand = config.getBoolean("discord.discord-command");
        this.discordWebhookUrl = config.getString("discord.webhook-url");

        this.serverName = config.getString("server-name");
        this.avatarApi = config.getString("avatar-api");

        this.chatMessageEnabled = config.getBoolean("events.chat-message.enabled");
        this.chatMessageUsernameFormat = config
                .getString("events.chat-message.username-format");
        this.chatMessageMessageFormat = config.getString("events.chat-message.message-format");
        this.chatMessageAvatarUrl = config.getString("events.chat-message.avatar-url");

        this.messagePrefixDisabled = config
                .getBoolean("events.chat-message.message-prefix-disabled");
        this.messagePrefix = config.getString("events.chat-message.message-prefix");

        this.playerJoinEnabled = config.getBoolean("events.player-join.enabled");
        this.playerJoinUsernameFormat = config.getString("events.player-join.username-format");
        this.playerJoinMessageFormat = config.getString("events.player-join.message-format");
        this.playerJoinAvatarUrl = config.getString("events.player-join.avatar-url");

        this.playerLeaveEnabled = config.getBoolean("events.player-leave.enabled");
        this.playerLeaveUsernameFormat = config.getString("events.player-leave.username-format");
        this.playerLeaveMessageFormat = config.getString("events.player-leave.message-format");
        this.playerLeaveAvatarUrl = config.getString("events.player-leave.avatar-url");

        this.advancementCompletedEnabled = config
                .getBoolean("events.advancement-completed.enabled");
        this.advancementCompletedUsernameFormat = config
                .getString("events.advancement-completed.username-format");
        this.advancementCompletedMessageFormat = config
                .getString("events.advancement-completed.message-format");
        this.advancementCompletedAvatarUrl = config
                .getString("events.advancement-completed.avatar-url");

        this.serverStartupEnabled = config.getBoolean("events.server-startup.enabled");
        this.serverStartupUsernameFormat = config
                .getString("events.server-startup.username-format");
        this.serverStartupMessageFormat = config
                .getString("events.server-startup.message-format");
        this.serverStartupAvatarUrl = config.getString("events.server-startup.avatar-url");

        this.serverShutdownEnabled = config.getBoolean("events.server-shutdown.enabled");
        this.serverShutdownUsernameFormat = config
                .getString("events.server-shutdown.username-format");
        this.serverShutdownMessageFormat = config
                .getString("events.server-shutdown.message-format");
        this.serverShutdownAvatarUrl = config.getString("events.server-shutdown.avatar-url");

        this.discordCommandPermission = config.getString("settings.discord-command-permission");
        this.reloadCommandPermission = config.getString("settings.reload-command-permission");

        this.updateCheck = config.getBoolean("update.check");
        this.updateNotify = config.getBoolean("update.notify");

        this.configVersion = config.getDouble("config-version");
    }

    public String getDiscordWebhookUrl() {
        return discordWebhookUrl;
    }

    public String getServerName() {
        return serverName;
    }

    public String getAvatarApi() {
        return avatarApi;
    }

    public boolean isChatMessageEnabled() {
        return chatMessageEnabled;
    }

    public String getChatMessageUsernameFormat() {
        return chatMessageUsernameFormat;
    }

    public String getChatMessageMessageFormat() {
        return chatMessageMessageFormat;
    }

    public String getChatMessageAvatarUrl() {
        return chatMessageAvatarUrl;
    }

    public boolean isPlayerJoinEnabled() {
        return playerJoinEnabled;
    }

    public String getPlayerJoinUsernameFormat() {
        return playerJoinUsernameFormat;
    }

    public String getPlayerJoinMessageFormat() {
        return playerJoinMessageFormat;
    }

    public String getPlayerJoinAvatarUrl() {
        return playerJoinAvatarUrl;
    }

    public boolean isPlayerLeaveEnabled() {
        return playerLeaveEnabled;
    }

    public String getPlayerLeaveUsernameFormat() {
        return playerLeaveUsernameFormat;
    }

    public String getPlayerLeaveMessageFormat() {
        return playerLeaveMessageFormat;
    }

    public String getPlayerLeaveAvatarUrl() {
        return playerLeaveAvatarUrl;
    }

    public boolean isAdvancementCompletedEnabled() {
        return advancementCompletedEnabled;
    }

    public String getAdvancementCompletedUsernameFormat() {
        return advancementCompletedUsernameFormat;
    }

    public String getAdvancementCompletedMessageFormat() {
        return advancementCompletedMessageFormat;
    }

    public String getAdvancementCompletedAvatarUrl() {
        return advancementCompletedAvatarUrl;
    }

    public boolean isServerStartupEnabled() {
        return serverStartupEnabled;
    }

    public String getServerStartupUsernameFormat() {
        return serverStartupUsernameFormat;
    }

    public String getServerStartupMessageFormat() {
        return serverStartupMessageFormat;
    }

    public String getServerStartupAvatarUrl() {
        return serverStartupAvatarUrl;
    }

    public boolean isServerShutdownEnabled() {
        return serverShutdownEnabled;
    }

    public String getServerShutdownUsernameFormat() {
        return serverShutdownUsernameFormat;
    }

    public String getServerShutdownMessageFormat() {
        return serverShutdownMessageFormat;
    }

    public String getServerShutdownAvatarUrl() {
        return serverShutdownAvatarUrl;
    }

    public boolean isMessagePrefixDisabled() {
        return messagePrefixDisabled;
    }

    public String getMessagePrefix() {
        return messagePrefix;
    }
}
